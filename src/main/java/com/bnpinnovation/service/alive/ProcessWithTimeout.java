package com.bnpinnovation.service.alive;

import com.bnpinnovation.exception.PingServiceException;

public class ProcessWithTimeout extends Thread {
	private Process pingProcess;

	public ProcessWithTimeout(Process process) {
		pingProcess = process;
	}

	public void waitForProcess(long timeoutSeconds) {
		this.start();

		try {
			this.join(timeoutSeconds*1000);
		} catch (InterruptedException e) {
			pingProcess.destroy();
			this.interrupt();
			throw new PingServiceException(PingServiceException.pingTimeoutErrorCode);
		}
	}

	@Override
	public void run() {
		try {
			pingProcess.waitFor();
		} catch (InterruptedException ignore) {
		} catch (Exception ex) {
		}
	}
}