package com.bnpinnovation.exception;

public class StatusCheckerException extends RuntimeException {
	private static final long serialVersionUID = -6178799797482612255L;
	public static final int unknownErrorCode = 0x01;
	public static final int ioErrorCode = 0x02;
	public static final int jobProducerErrorCode = 0x03;
	public static final int serverStatusRegistErrorCode = 0x04;
	public static final int jobTakeErrorCode = 0x05;
	public static final int statusTakeErrorCode = 0x06;
	public static final int jobProducerTerminationErrorCode = 0x07;

	private int errorCode;
	
	public StatusCheckerException(int errorCode ) {
		this.setErrorCode(errorCode);
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

}
