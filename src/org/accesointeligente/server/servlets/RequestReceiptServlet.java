package org.accesointeligente.server.servlets;

import org.accesointeligente.model.Request;
import org.accesointeligente.model.User;
import org.accesointeligente.server.ApplicationProperties;
import org.accesointeligente.server.services.RequestServiceImpl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestReceiptServlet extends HttpServlet {

	@Override
	public void init() {
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			String requestId = request.getParameter("requestId");
			RequestServiceImpl requestService = new RequestServiceImpl();
			Request userRequest = requestService.getRequest(Integer.parseInt(requestId));
			User user = userRequest.getUser();

			String filename = "AccesoInteligente-Mandato-ID" + userRequest.getId().toString() + ".pdf";

			response.setContentType("application/pdf; charset=utf-8");
			String disposition = "attachment; fileName=" + filename;
			response.setHeader("Content-Disposition", disposition);
			ServletOutputStream servletOutputStream = response.getOutputStream();

			Document document = new Document(PageSize.LETTER, 30, 30, 30, 30);
			PdfWriter writer = PdfWriter.getInstance(document, servletOutputStream);
			document.open();

			Paragraph documentTitle = new Paragraph(ApplicationProperties.getProperty("receipt.title"), FontFactory.getFont(FontFactory.COURIER_BOLD, 14));
			document.add(documentTitle);

			Paragraph sectionTopTitle = new Paragraph(ApplicationProperties.getProperty("receipt.body"), FontFactory.getFont(FontFactory.COURIER, 12));
			document.add(sectionTopTitle);

			Paragraph listTitle = new Paragraph(ApplicationProperties.getProperty("receipt.body.list.title"), FontFactory.getFont(FontFactory.COURIER, 12));
			document.add(listTitle);
			List list = new List();
			list.setListSymbol("- ");
			list.add(new ListItem(ApplicationProperties.getProperty("receipt.body.list.item1"), FontFactory.getFont(FontFactory.COURIER, 12)));
			list.add(new ListItem(ApplicationProperties.getProperty("receipt.body.list.item2"), FontFactory.getFont(FontFactory.COURIER, 12)));
			document.add(list);

			Paragraph documentCut = new Paragraph(ApplicationProperties.getProperty("receipt.cut"), FontFactory.getFont(FontFactory.COURIER_BOLD, 12));
			document.add(documentCut);

			Paragraph sectionBottomTitle = new Paragraph(ApplicationProperties.getProperty("receipt.footer.title"), FontFactory.getFont(FontFactory.COURIER, 12));
			document.add(sectionBottomTitle);
			Paragraph footerBody = new Paragraph(String.format(ApplicationProperties.getProperty("receipt.footer.body"),
				user.getFirstName() + " " + user.getLastName(), userRequest.getTitle(), userRequest.getId(), userRequest.getInstitution().getName()), FontFactory.getFont(FontFactory.COURIER, 12));
			document.add(footerBody);

			document.close();

		} catch (Exception ex) {
			throw new ServletException(ex.getMessage());
		}
	}
}
