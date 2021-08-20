package system.exceptions;

public class InvalidNameException extends Exception {
	
	private String msg;
	
	public InvalidNameException(boolean isDescription) {
		if(!isDescription)
			msg = "Nome inv�lido";
		else
			msg = "Descri��o inv�lida";
	}
	
	public String getMessage() {
		return msg;
	}


}
