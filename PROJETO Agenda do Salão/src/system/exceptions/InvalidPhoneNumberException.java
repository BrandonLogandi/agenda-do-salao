package system.exceptions;

public class InvalidPhoneNumberException extends Exception {
	
	@Override
	public String getMessage() {
		return "Telefone inválido";
	}

}
