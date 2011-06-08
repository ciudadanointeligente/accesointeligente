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

import org.accesointeligente.server.robots.ResponseChecker;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BackendServlet extends HttpServlet {
	private enum Command {
		check_responses
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/plain");
		Command command = null;

		try {
			command = Enum.valueOf(Command.class, request.getParameter("command"));
		} catch (Exception ex) {
			usage(response.getWriter());
			return;
		}

		if (command == null) {
			usage(response.getWriter());
			return;
		}

		switch (command) {
			case check_responses:
				checkResponses(response.getWriter());
				break;
			default:
				usage(response.getWriter());
		}
	}

	private static void checkResponses(Writer writer) throws IOException {
		Thread thread = new Thread() {
			@Override
			public void run() {
				ResponseChecker responseChecker = new ResponseChecker();
				responseChecker.connectAndCheck();
			}
		};

		thread.start();
		writer.write(Command.check_responses.name() + " started");
	}

	private static void usage(Writer writer) throws IOException {
		writer.write("admin/backend?command=COMMAND\n\ncommands available:\n");

		for (Command command : Command.values()) {
			writer.write("\t" + command.name() + "\n");
		}
	}
}
