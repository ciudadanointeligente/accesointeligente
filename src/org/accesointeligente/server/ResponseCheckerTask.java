package org.accesointeligente.server;

import java.util.TimerTask;

import org.accesointeligente.server.robots.ResponseChecker;

public class ResponseCheckerTask extends TimerTask {
	@Override
	public void run() {
		System.err.println("Iniciando ResponseCheckerTask");
		ResponseChecker responseChecker = new ResponseChecker();
		responseChecker.connectAndCheck();
	}
}
