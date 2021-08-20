package system.exceptions;

public class InvalidIDException extends Exception {
	
	private String msg;
	
	public InvalidIDException(String msg) {
		this.msg = msg;
	}
	
	@Override
	public String getMessage() {
		return msg;
	}

}
