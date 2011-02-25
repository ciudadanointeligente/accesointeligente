/**
 * Acceso Inteligente
 *
 * Copyright (C) 2010-2011 Fundación Ciudadano Inteligente
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.accesointeligente.server.robots;

import org.accesointeligente.client.services.RequestService;
import org.accesointeligente.model.*;
import org.accesointeligente.server.ApplicationProperties;
import org.accesointeligente.server.Emailer;
import org.accesointeligente.server.services.RequestServiceImpl;
import org.accesointeligente.shared.FileType;
import org.accesointeligente.shared.Gender;
import org.accesointeligente.shared.RequestStatus;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

import org.apache.commons.io.IOUtils;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.File;
import java.io.FileOutputStream;
import java.util.*;
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
				String remoteIdentifier = null;
				Pattern pattern = Pattern.compile("([A-Z]{2}[0-9]{3}[A-Z]-{0,1}[0-9]{7})");
				List<Attachment> attachments = new ArrayList<Attachment>();

				Multipart mp = (Multipart) message.getContent();

				for (int i = 0, n = mp.getCount(); i < n; i++) {
					Part part = mp.getBodyPart(i);
					String disposition = part.getDisposition();

					if (disposition != null && disposition.equalsIgnoreCase(Part.ATTACHMENT)) {
						Matcher matcher;
						FileType filetype = null;

						if (remoteIdentifier == null) {
							try {
								// TODO: other formats
								if (part.isMimeType("application/msword")) {
									WordExtractor extractor = new WordExtractor(part.getInputStream());
									StringTokenizer tokenizer = new StringTokenizer(extractor.getText());

									while (tokenizer.hasMoreTokens()) {
										matcher = pattern.matcher(tokenizer.nextToken());

										if (matcher.matches()) {
											filetype = FileType.DOC;
											remoteIdentifier = matcher.group(1);
											break;
										}
									}
								} else if (part.isMimeType("application/vnd.openxmlformats-officedocument.wordprocessingml.document")) {
									XWPFWordExtractor extractor = new XWPFWordExtractor(new XWPFDocument(part.getInputStream()));
									StringTokenizer tokenizer = new StringTokenizer(extractor.getText());

									while (tokenizer.hasMoreTokens()) {
										matcher = pattern.matcher(tokenizer.nextToken());

										if (matcher.matches()) {
											filetype = FileType.DOCX;
											remoteIdentifier = matcher.group(1);
											break;
										}
									}
								} else if (part.isMimeType("application/pdf")) {
									PdfReader reader = new PdfReader(part.getInputStream());

									for (int page = 1; page <= reader.getNumberOfPages(); page++) {
										if (remoteIdentifier != null) {
											break;
										}

										StringTokenizer tokenizer = new StringTokenizer(PdfTextExtractor.getTextFromPage(reader, page));

										while (tokenizer.hasMoreTokens()) {
											matcher = pattern.matcher(tokenizer.nextToken());

											if (matcher.matches()) {
												filetype = FileType.PDF;
												remoteIdentifier = matcher.group(1);
												reader.close();
												break;
											}
										}
									}
								}
							} catch (Exception e) {
								System.err.println("Error procesando " + MimeUtility.decodeText(part.getFileName()));
								e.printStackTrace();
								continue;
							}
						}

						String directory = ApplicationProperties.getProperty("attachment.directory");
						String baseUrl = ApplicationProperties.getProperty("attachment.baseurl");

						Attachment attachment = new Attachment();
						attachment = requestService.saveAttachment(attachment);

						String filename = attachment.getId() + filetype.getExtension();

						attachment.setName(filename);
						attachment.setType(filetype);
						attachment.setUrl(baseUrl + filename);

						try {
							FileOutputStream out = new FileOutputStream(new File(directory + filename));
							IOUtils.copy(part.getInputStream(), out);
							out.close();
						} catch (Exception e) {
							requestService.deleteAttachment(attachment);
							System.err.println("Error saving " + directory + filename);
							throw e;
						}

						requestService.saveAttachment(attachment);
						attachments.add(attachment);
					}
				}

				if (remoteIdentifier == null) {
					for (Attachment attachment : attachments) {
						requestService.deleteAttachment(attachment);
					}
				} else {
					Request request = requestService.getRequest(remoteIdentifier);

					if (request == null) {
						for (Attachment attachment : attachments) {
							requestService.deleteAttachment(attachment);
						}
					}

					request.setStatus(RequestStatus.CLOSED);
					requestService.saveRequest(request);

					Response response = new Response();
					response.setSender(message.getFrom().toString());
					response.setDate(new Date());
					response.setRequest(request);
					response = requestService.saveResponse(response);

					for (Attachment attachment : attachments) {
						attachment.setResponse(response);
						requestService.saveAttachment(attachment);
					}

					// TODO: send permalink to request
					User user = request.getUser();

					Emailer emailer = new Emailer();
					emailer.setRecipient(user.getEmail());
					emailer.setSubject(ApplicationProperties.getProperty("email.response.arrived.subject"));
					emailer.setBody(String.format(ApplicationProperties.getProperty("email.response.arrived.body"), (user.getGender().equals(Gender.FEMALE)) ? "Sra. " : "Sr. ", user.getFirstName(), request.getTitle()) + ApplicationProperties.getProperty("email.signature"));
					emailer.connectAndSend();
				}
			}
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
	}
}
