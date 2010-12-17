package org.accesointeligente.server.robots;

import org.accesointeligente.model.Request;
import org.accesointeligente.shared.RequestStatus;

import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SGSRobot extends Robot {
	private HttpClient client;
	private HtmlCleaner cleaner;
	private Boolean loggedIn = false;
	private String characterEncoding = "ISO-8859-1";

	public SGSRobot() {
		client = new DefaultHttpClient();
		HttpProtocolParams.setUserAgent(client.getParams(), "Mozilla/5.0 (X11; U; Linux x86_64; es-CL; rv:1.9.2.12) Gecko/20101027 Ubuntu/10.10 (maverick) Firefox/3.6.12");
		cleaner = new HtmlCleaner();
	}

	public static void main(String args[]) throws Exception {
		Robot robot = new SGSRobot();
		robot.setUsername("rarcos@gmail.com");
		robot.setPassword("vieNgo8o");
		robot.login();
	}

	@Override
	public void login() throws RobotException {
		List<NameValuePair> formParams;
		HttpPost post;
		HttpGet get;
		HttpResponse response;
		TagNode document;
		Header location;

		try {
			formParams = new ArrayList<NameValuePair>();
			formParams.add(new BasicNameValuePair("login", username));
			formParams.add(new BasicNameValuePair("password", password));
			formParams.add(new BasicNameValuePair("Ingresar", "Ingresar"));

			post = new HttpPost("http://www.sename.cl/sgs/index.php?accion=login");
			post.addHeader("Referer", "http://www.sename.cl/sgs/index.php?accion=Home");
			post.setEntity(new UrlEncodedFormEntity(formParams, characterEncoding));
			response = client.execute(post);
			location = response.getFirstHeader("Location");

			if (location == null || !"index.php".equals(location.getValue())) {
				throw new Exception();
			}

			EntityUtils.consume(response.getEntity());

			get = new HttpGet("http://www.sename.cl/sgs/index.php");
			get.addHeader("Referer", "http://www.sename.cl/sgs/index.php?accion=Home");
			response = client.execute(get);
			document = cleaner.clean(new InputStreamReader(response.getEntity().getContent(), characterEncoding));

			if (document.getElementListByAttValue("href", "index.php?accion=Salir", true, true).isEmpty()) {
				throw new Exception();
			}

			loggedIn = true;
		} catch (Throwable ex) {
			throw new RobotException();
		}
	}

	@Override
	public RequestStatus makeRequest(Request request) throws RobotException {
		if (!loggedIn) {
			login();
		}
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RequestStatus checkRequestStatus(Request request) throws RobotException {
		if (!loggedIn) {
			login();
		}

		// TODO Auto-generated method stub
		return null;
	}
}
