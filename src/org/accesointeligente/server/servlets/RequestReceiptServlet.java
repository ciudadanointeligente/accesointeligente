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
package org.accesointeligente.server.servlets;

import org.accesointeligente.model.Request;
import org.accesointeligente.model.User;
import org.accesointeligente.server.ApplicationProperties;
import org.accesointeligente.server.services.RequestServiceImpl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

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

			BaseFont fontVeraMono = BaseFont.createFont("org/accesointeligente/server/servlets/VeraMono.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			BaseFont fontVeraMonoBold = BaseFont.createFont("org/accesointeligente/server/servlets/VeraMoBd.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

			Font VERA_NORMAL = new Font(fontVeraMono, 12);
			Font VERA_NORMAL_TINY = new Font(fontVeraMono, 8);
			Font VERA_BOLD = new Font(fontVeraMonoBold, 12);
			Font VERA_BOLD_TITLE = new Font(fontVeraMonoBold, 14);

			response.setContentType("application/pdf; charset=utf-8");
			String disposition = "attachment; fileName=" + filename;
			response.setHeader("Content-Disposition", disposition);
			ServletOutputStream servletOutputStream = response.getOutputStream();

			Document document = new Document(PageSize.LETTER, 30, 30, 30, 30);
			PdfWriter writer = PdfWriter.getInstance(document, servletOutputStream);
			document.open();

			document.addTitle("AccesoInteligente Mandato Solicitud: " + userRequest.getId().toString());
			document.addAuthor("Acceso Inteligente");
			document.addCreator("Acceso Inteligente");
			document.addKeywords("Acceso");
			document.addKeywords("Inteligente");
			document.addKeywords("Mandato");
			document.addKeywords("Transparencia");
			document.addCreationDate();

			Paragraph documentTitle = new Paragraph(ApplicationProperties.getProperty("receipt.title"), VERA_BOLD_TITLE);
			addEmptyLine(documentTitle, 1);
			document.add(documentTitle);

			Paragraph sectionTopTitle = new Paragraph(ApplicationProperties.getProperty("receipt.body"), VERA_NORMAL);
			addEmptyLine(sectionTopTitle, 1);
			document.add(sectionTopTitle);

			Paragraph listTitle = new Paragraph(ApplicationProperties.getProperty("receipt.body.list.title"), VERA_NORMAL);
			document.add(listTitle);
			List list = new List();
			list.setListSymbol("- ");
			list.add(new ListItem(ApplicationProperties.getProperty("receipt.body.list.item1"), VERA_NORMAL));
			list.add(new ListItem(ApplicationProperties.getProperty("receipt.body.list.item2"), VERA_NORMAL));
			document.add(list);

			Paragraph documentCut = new Paragraph(ApplicationProperties.getProperty("receipt.cut"), VERA_BOLD);
			addEmptyLine(documentCut, 2);
			document.add(documentCut);

			Paragraph sectionBottomTitle = new Paragraph(ApplicationProperties.getProperty("receipt.footer.title"), VERA_BOLD_TITLE);
			addEmptyLine(sectionBottomTitle, 1);
			document.add(sectionBottomTitle);

			Paragraph footerBody = new Paragraph(String.format(ApplicationProperties.getProperty("receipt.footer.body"), user.getFirstName() + " " + user.getLastName(), userRequest.getTitle(), userRequest.getId(), userRequest.getInstitution().getName()), VERA_NORMAL);
			addEmptyLine(footerBody, 2);
			document.add(footerBody);

			document.close();
			writer.close();

		} catch (Exception ex) {
			throw new ServletException(ex.getMessage());
		}
	}

	private static void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}
}
