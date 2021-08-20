package system.exceptions;

public class InvalidEmailException extends Exception {
	
	public String getMessage() {
		return "Email inválido";
	}

}
