package com.bnpinnovation.exception;

public class PingServiceException extends RuntimeException {
	private static final long serialVersionUID = 4534891119268209139L;

	public static final int pingExecuteErrorCode = 0x01;

	public static final int pingTimeoutErrorCode = 0x02;

	public static final int unknownErrorCode = 0x00;
	
	private int errorCode;
	
	public PingServiceException(int errorCode) {
		this.errorCode = errorCode;
	}
	
	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
}
