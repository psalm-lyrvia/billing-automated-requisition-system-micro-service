package com.accenture.bars.microservices.client.renzchler.s.oxino.exception;

public class BarsException extends Exception {

	private static final long serialVersionUID = 1L;
	public static final String INVALID_START_DATE_FORMAT = "Invalid Start Date"
			+ " format at row ";
	public static final  String INVALID_END_DATE_FORMAT = "Invalid End Date"
			+ " format at row ";
	public static final String BILLING_CYCLE_NOT_ON_RANGE = "Billing Cycle"
			+ " not on range at row ";
	public static final String PATH_DOES_NOT_EXIST = "File does"
			+ " not exists.";
	public static final String NO_SUPPORTED_FILE = "File is not"
			+ " supported for processing.";
	public static final String NO_RECORDS_TO_READ = "No request(s)"
			+ " to read from the input file.";
	public static  final String NO_RECORDS_TO_WRITE = "No request(s)"
			+ " to read from the input file.";

	public BarsException() {
		super();
	}

	public BarsException(String message) {
		super(message);
	}

	public BarsException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
