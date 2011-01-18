package org.accesointeligente.server.robots;

import org.accesointeligente.client.services.RequestService;
import org.accesointeligente.model.*;
import org.accesointeligente.server.ApplicationProperties;
import org.accesointeligente.server.services.RequestServiceImpl;
import org.accesointeligente.shared.FileType;
import org.accesointeligente.shared.RequestStatus;

import org.apache.commons.io.IOUtils;
import org.apache.poi.hwpf.extractor.WordExtractor;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.*;
import javax.mail.Flags.Flag;
import javax.mail.internet.MimeUtility;

public class ResponseChecker {
	private Properties props;
	private Session session;
	private Store store;

	public ResponseChecker() {
		props = System.getProperties();
		props.setProperty("mail.store.protocol", "imaps");
	}

	public void connectAndCheck() {
		if (ApplicationProperties.getProperty("email.server") == null || ApplicationProperties.getProperty("email.user") == null ||
				ApplicationProperties.getProperty("email.password") == null || ApplicationProperties.getProperty("email.folder") == null ||
				ApplicationProperties.getProperty("attachment.directory") == null || ApplicationProperties.getProperty("attachment.baseurl") == null) {
			System.err.println("ResponseChecker: No estan definidas las propiedades!");
			return;
		}

		try {
			RequestService requestService = new RequestServiceImpl();
			session = Session.getDefaultInstance(props, null);
			store = session.getStore("imaps");
			store.connect(ApplicationProperties.getProperty("email.server"), ApplicationProperties.getProperty("email.user"), ApplicationProperties.getProperty("email.password"));

			Folder folder = store.getFolder(ApplicationProperties.getProperty("email.folder"));
			folder.open(Folder.READ_WRITE);

			for (Message message : folder.getMessages()) {
				Flags flags = message.getFlags();

				if (flags.contains(Flag.SEEN)) {
					continue;
				}

				System.out.println(message.getFrom()[0] + "\t" + message.getSubject());

				Multipart mp = (Multipart) message.getContent();

				for (int i = 0, n = mp.getCount(); i < n; i++) {
					Part part = mp.getBodyPart(i);
					String disposition = part.getDisposition();

					if (disposition != null && disposition.equalsIgnoreCase(Part.ATTACHMENT)) {
						// TODO: other formats
						if (part.isMimeType("application/msword")) {
							WordExtractor wordExtractor = new WordExtractor(part.getInputStream());
							String[] paragraphs = wordExtractor.getParagraphText();
							Pattern pattern = Pattern.compile("^Solicitud ([A-Z]{2}[0-9]{3}[A-Z]-[0-9]{7}) de *fecha.*$");
							String remoteIdentifier = null;

							for (String paragraph : paragraphs) {
								Matcher matcher = pattern.matcher(paragraph);

								if (matcher.matches()) {
									remoteIdentifier = matcher.group(1);
									break;
								}
							}

							if (remoteIdentifier == null) {
								break;
							}

							String directory = ApplicationProperties.getProperty("attachment.directory");
							String baseUrl = ApplicationProperties.getProperty("attachment.baseurl");

							String filename = MimeUtility.decodeText(part.getFileName());

							if (filename != null) {
								FileOutputStream out = new FileOutputStream(new File(directory + filename));
								IOUtils.copy(part.getInputStream(), out);
								out.close();
							}

							Request request = requestService.getRequest(remoteIdentifier);

							if (request == null) {
								continue;
							}

							request.setStatus(RequestStatus.CLOSED);
							Response response = request.getResponse ();

							if (response == null) {
								response = new Response();
								response.setRequest(request);
							}

							response.setDate(new Date());

							Attachment attachment = new Attachment();
							attachment.setName(filename);
							attachment.setType(FileType.DOC);
							attachment.setUrl(baseUrl + filename);
							attachment.setResponse(response);

							requestService.saveRequest(request);
							requestService.saveResponse(response);
							requestService.saveAttachment(attachment);

							message.setFlag(Flag.SEEN, true);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
	}
}
