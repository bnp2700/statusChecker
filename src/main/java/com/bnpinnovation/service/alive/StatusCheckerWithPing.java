package com.bnpinnovation.service.alive;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.bnpinnovation.exception.PingServiceException;

public class StatusCheckerWithPing implements StatusCheckerForServer {
	private static Logger logger = Logger.getLogger(StatusCheckerWithPing.class);
	
	@Value("${checker.timeout}")
	private int timeout;
	
	@Autowired 
	private PingService pingService;

	@Override
	public boolean isAlive(String host) {

		try {
			pingService.isAlive(host);
		}catch(PingServiceException pingServiceException) {
			logger.info("ping failed : [ " + host + " ], caused code : " + pingServiceException.getErrorCode());
			return false;
		}
		

		return true;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
}
