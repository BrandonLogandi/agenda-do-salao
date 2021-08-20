package system.exceptions;

public class InvalidNameException extends Exception {
	
	private String msg;
	
	public InvalidNameException(boolean isDescription) {
		if(!isDescription)
			msg = "Nome inválido";
		else
			msg = "Descrição inválida";
	}
	
	public String getMessage() {
		return msg;
	}


}
