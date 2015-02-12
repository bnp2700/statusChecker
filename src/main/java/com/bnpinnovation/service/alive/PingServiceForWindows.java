package com.bnpinnovation.service.alive;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.bnpinnovation.exception.PingServiceException;

public class PingServiceForWindows extends PingServiceTemplate {
	private static Logger logger = Logger
			.getLogger(PingServiceForWindows.class);

	@Override
	protected Process pingCheck(String host, long pingTimeout) {
		String pingCommand = "ping -w " + pingTimeout * 1000 + " -n 1 " + host;
		Process process;
		try {
			process = Runtime.getRuntime().exec(pingCommand);
		} catch (IOException e) {
			throw new PingServiceException(
					PingServiceException.pingExecuteErrorCode);
		}
		return process;
	}
}
