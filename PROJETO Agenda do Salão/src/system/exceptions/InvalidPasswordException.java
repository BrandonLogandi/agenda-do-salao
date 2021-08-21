package system.exceptions;

public class InvalidPasswordException extends Exception {
	
	public String getMessage() {
		return "Senha inválida";
	}

}
