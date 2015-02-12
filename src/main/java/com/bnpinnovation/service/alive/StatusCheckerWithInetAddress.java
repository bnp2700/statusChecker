package com.bnpinnovation.service.alive;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Value;

import com.bnpinnovation.exception.StatusCheckerException;

public class StatusCheckerWithInetAddress implements StatusCheckerForServer {
	
	@Value("${checker.timeout:3000}")
	private int timeout;

	@Override
	public boolean isAlive(String host) {
		boolean status = false;

		try {
			status = InetAddress.getByName(host).isReachable(getTimeout());
		} catch (UnknownHostException e) {
			throw new StatusCheckerException(StatusCheckerException.unknownErrorCode);
		} catch (IOException e) {
			throw new StatusCheckerException(StatusCheckerException.ioErrorCode);
		}

		return status;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
}
