package system.exceptions;

public class InvalidPasswordException extends Exception {
	
	@Override
	public String getMessage() {
		return "Senha inválida";
	}

}
