package com.bnpinnovation.service.alive;

import java.io.IOException;

import com.bnpinnovation.exception.PingServiceException;


public class PingServiceForLinux extends PingServiceTemplate {
	
	@Override
	protected Process pingCheck(String host, long pingTimeout)  {
		String pingCommand = "ping -W " + pingTimeout + " -c 1 " + host;
		
		System.out.println(pingCommand);
		
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
