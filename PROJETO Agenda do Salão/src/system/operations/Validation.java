package system.operations;

import java.text.DecimalFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import people.Client;
import people.Colaborator;
import system.Appointment;
import system.Service;
import system.exceptions.InvalidDateException;
import system.exceptions.InvalidEmailException;
import system.exceptions.InvalidIDException;
import system.exceptions.InvalidNameException;
import system.exceptions.InvalidPasswordException;
import system.exceptions.InvalidPhoneNumberException;
import system.exceptions.InvalidTimeException;

/**
 * Essa classe cont�m m�todos est�ticos que asseguram a pr�pria cria��o de v�rios objetos do sistema.
 */
public abstract class Validation {
	
	private static String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
	
	/**
	 * Esse m�todo retorna o float recebido com as duas �ltimas casas decimais vis�veis.
	 * 
	 * @param money o float a ser formatado.
	 *
	 * @return float com as duas �ltimas casas decimais vis�veis.
	 */
	public static float formatMoney(float money) {
		DecimalFormat df = new DecimalFormat("0.00");
		return Float.parseFloat(df.format(money));
	}
	
	/**
	 * Esse m�todo valida um nome ou uma descri��o digitados pelo usu�rio ao cadastrar um Colaborator, Service ou Client.
	 * 
	 * @param name o nome/descri��o a ser validado.
	 * @param isDescription caso name seja uma descri��o.
	 * 
	 * @throws InvalidNameException se name for inv�lido.
	 */
	public static void validateName(String name, boolean isDescription) throws InvalidNameException{
		if(name.isBlank())
			throw new InvalidNameException(isDescription);
		
		char [] chars = name.toCharArray();
		for(char c:chars) 
			if(!Character.isLetter(c) && !Character.isWhitespace(c))
				throw new InvalidNameException(isDescription); 

	}
	
	/**
	 * Esse m�todo valida um email digitado pelo usu�rio ao cadastrar um Colaborator ou Client.
	 * 
	 * @param email o email a ser validado.
	 * 
	 * @throws InvalidEmailException se email n�o coincide com o formato de um email.
	 */
	public static void validateEmail(String email) throws InvalidEmailException {
		if(!email.matches(EMAIL_REGEX)) 
			throw new InvalidEmailException();
	}
	
	/**
	 * Esse m�todo valida um n�mero de telefone digitado pelo usu�rio ao cadastrar um Colaborator ou Client.
	 * 
	 * @param pn o n�mero a ser validado.
	 * 
	 * @throws InvalidPhoneNumberException se pn cont�m caracteres que n�o s�o n�meros.
	 */
	public static void validatePhoneNumber(String pn) throws InvalidPhoneNumberException { 
		try {
			Long.parseLong(pn.replace("(", "").replace(")", "").replace("-", ""));
		} catch (NumberFormatException e) {
			throw new InvalidPhoneNumberException();
		}
	}
	
	/**
	 * Esse m�todo valida a senha digitada pelo usu�rio ao se cadastrar.
	 * 
	 * @param password o array contendo os caracteres da senha.
	 * 
	 * @throws InvalidPasswordException se password estiver vazio.
	 */
	public static void validatePassword(char[] password) throws InvalidPasswordException {
		if(new String(password).isBlank())
			throw new InvalidPasswordException();
	}
	
	/**
	 * Esse m�todo valida os IDs digitados pelo usu�rio ao cadastrar um colaborador.
	 * Ele checa cada ID digitado com o ID de todos os servi�os em allServices.
	 *
	 * @param servicesID um array contendo os IDs de servi�os que o usu�rio digitou.
	 * @param allServices um ArrayList com todos os servi�os cadastrados no sistema.
	 * 
	 * @return um ArrayList contendo os servi�os com os IDs em servicesID.
	 * 
	 * @throws InvalidIDException se algum ID em servicesID n�o coincide com o ID de nenhum servi�o em allServices.
	 */
	public static ArrayList<Service> validateIDs(String[] servicesID, ArrayList<Service> allServices) throws InvalidIDException {
		ArrayList<Service> validServices = new ArrayList<Service>();
		
		for(int i = 0; i < servicesID.length; i++) {
			for(Service svc:allServices) 
				if(servicesID[i].isEmpty()) 
					throw new InvalidIDException("IDs n�o podem ser vazios");
				else {
					try {
						if(Long.parseLong(servicesID[i]) == svc.getID()) {
							validServices.add(svc);
						}
					} catch (NumberFormatException e) {
						throw new InvalidIDException("ID " + servicesID[i] + " inv�lido");
					}
					
				}
		}
		
		for(int i = 0; i < servicesID.length; i++) 
			try {
				if(validServices.get(i).getID() != Long.parseLong(servicesID[i]));
			} catch (IndexOutOfBoundsException e1) {
				throw new InvalidIDException("N�o existe servi�o com ID " + servicesID[i]);
			}

			
		
	
		
		return validServices;
		
	}
	
	/**
	 * Esse m�todo valida os pre�os digitados pelo usu�rio ao cadastrar um colaborador.
	 *
	 * @param servicesID um array contendo os IDs de servi�os que o usu�rio digitou.
	 * @param prices um array contendo todos os pre�os que o usu�rio digitou.
	 * 
	 * @return um ArrayList com todos os pre�os convertidos em Float.
	 * 
	 * @throws Exception caso o usu�rio tenha digitado mais ou menos pre�os do que IDs, ou quando algum pre�o digitado � inv�lido.
	 */
	public static ArrayList<Float> validatePrices(String[] servicesID, String[] prices) throws Exception {
		ArrayList<Float> validPrices = new ArrayList<Float>();
		
		if(prices.length > servicesID.length) 
			throw new Exception("Foram digitados mais pre�os do que servi�os");
		else if(prices.length < servicesID.length) 
			throw new Exception("Foram digitados mais servi�os do que pre�os");
		
		for(String str:prices) 
			if(str.isEmpty()) 
				throw new Exception("Valores dos pre�os n�o podem ser vazios");
			else {
				try {
					float price = Float.parseFloat(str);
					validPrices.add(price);
				} catch (NumberFormatException e) {
					throw new Exception("Pre�o inv�lido");
				}
				
			}
		
		return validPrices;
		
	}

	/**
	 * Esse m�todo valida a data de um agendamento.
	 *
	 * @param date uma String contendo a data a ser validada e convertida.
	 * @param isEditingOldAppt caso o usu�rio esteja editando um agendamento.
	 * @param isGeneratingReport caso o usu�rio esteja gerando um relat�rio de algum caixa.
	 * 
	 * @return uma LocalDate representando a data digitada em date.
	 * 
	 * @throws InvalidDateException caso a data digitada seja inv�lida (posterior a data atual ou dia, m�s ou ano inv�lido), a menos que o usu�rio esteja editando um agendamento ou gerando um relat�rio.
	 */
	public static LocalDate validateDate(String date, boolean isEditingOldAppt, boolean isGeneratingReport) throws InvalidDateException {
		String[] dateArr = date.split("/");
		LocalDate dateParsed;
		
		try {
			dateParsed = LocalDate.of(Integer.parseInt(dateArr[2]), Integer.parseInt(dateArr[1]), Integer.parseInt(dateArr[0]));
			
			if(dateParsed.isBefore(LocalDate.now()) && !isEditingOldAppt && !isGeneratingReport)
				throw new InvalidDateException();
			
			return dateParsed;
		} catch (DateTimeException | NumberFormatException e) {
			throw new InvalidDateException();
		}
	}
	
	/**
	 * Esse m�todo valida a hora de um agendamento.
	 *
	 * @param time uma String contendo a hora a ser validada e convertida.
	 * @param dateTyped a data digitada e validada previamente.
	 * @param isEditingOldAppt caso o usu�rio esteja editando um agendamento.
	 * 
	 * @return um LocalTime representando a hora digitada em time.
	 * 
	 * @throws InvalidTimeException caso dateTyped seja a data de hoje e time seja inv�lido (posterior a hora atual ou hora ou minuto inv�lidos), a menos que o usu�rio esteja editando um agendamento.
	 */
	public static LocalTime validateTime(String time, LocalDate dateTyped, boolean isEditingOldAppt) throws InvalidTimeException {
		String[] timeArr = time.split(":");
		LocalTime timeParsed;
		
		try {
			timeParsed = LocalTime.of(Integer.parseInt(timeArr[0]), Integer.parseInt(timeArr[1]));
			
			if(dateTyped.isEqual(LocalDate.now()) && timeParsed.isBefore(LocalTime.now()) && !isEditingOldAppt)
				throw new InvalidTimeException();
			
			return timeParsed;
		} catch (DateTimeException | NumberFormatException e) {
			throw new InvalidTimeException();
		}
	}

	/**
	 * Esse m�todo valida um agendamento a ser cadastrado.
	 *
	 * @param col o Colaborator que realizar� o servi�o.
	 * @param cl o Client que receber� o servi�o.
	 * @param s o Service a ser realizado.
	 * @param date a data do agendamento.
	 * @param time a hora do agendamento.
	 * @param apptEdit caso o usu�rio esteja editando um agendamento salvo previamente.
	 * 
	 * @throws Exception caso haja um choque de agendamentos do Colaborator ou do Client.
	 */
	public static void validateAppointment(Colaborator col, Client cl, Service s, LocalDate date, LocalTime time, Appointment apptEdit) throws Exception {
		
		for(Appointment appt:col.getAppointments()) 
			if(appt.equals(apptEdit))
				continue;
			else if(!appt.isCanceled() && !appt.isFinished())
				if(date.isEqual(appt.getDate())) {
					long mins = ChronoUnit.MINUTES.between(appt.getTime(), time);
					
					if(mins < 0)
						mins *=(-1);
					if(mins < appt.getService().getAvrgDuration())
						throw new Exception ("Choque de agentamentos do colaborador");
						
				}
		
		
		for(Appointment appt:cl.getAppointments())
			if(appt.equals(apptEdit))
				continue;
			else if(!appt.isCanceled() && !appt.isFinished())
				if(date.isEqual(appt.getDate())) {
					long mins = ChronoUnit.MINUTES.between(appt.getTime(), time);
					
					if(mins < 0)
						mins *=(-1);
					if(mins < appt.getService().getAvrgDuration())
						throw new Exception ("Choque de agentamentos do cliente");
						
				}
		
	}
}
