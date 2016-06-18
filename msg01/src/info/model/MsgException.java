package info.model;

public class MsgException extends RuntimeException {

	private static final long serialVersionUID = 6354688719923390852L;

	public MsgException() {
		super();
	}

	public MsgException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public MsgException(String message, Throwable cause) {
		super(message, cause);
	}

	public MsgException(String message) {
		super(message);
	}

	public MsgException(Throwable cause) {
		super(cause);
	}

}
