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
 * Essa classe contém métodos estáticos que asseguram a própria criação de vários objetos do sistema.
 */
public abstract class Validation {
	
	private static String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
	
	/**
	 * Esse método retorna o float recebido com as duas últimas casas decimais visíveis.
	 * 
	 * @param money o float a ser formatado.
	 *
	 * @return float com as duas últimas casas decimais visíveis.
	 */
	public static float formatMoney(float money) {
		DecimalFormat df = new DecimalFormat("0.00");
		return Float.parseFloat(df.format(money));
	}
	
	/**
	 * Esse método valida um nome ou uma descrição digitados pelo usuário ao cadastrar um Colaborator, Service ou Client.
	 * 
	 * @param name o nome/descrição a ser validado.
	 * @param isDescription caso name seja uma descrição.
	 * 
	 * @throws InvalidNameException se name for inválido.
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
	 * Esse método valida um email digitado pelo usuário ao cadastrar um Colaborator ou Client.
	 * 
	 * @param email o email a ser validado.
	 * 
	 * @throws InvalidEmailException se email não coincide com o formato de um email.
	 */
	public static void validateEmail(String email) throws InvalidEmailException {
		if(!email.matches(EMAIL_REGEX)) 
			throw new InvalidEmailException();
	}
	
	/**
	 * Esse método valida um número de telefone digitado pelo usuário ao cadastrar um Colaborator ou Client.
	 * 
	 * @param pn o número a ser validado.
	 * 
	 * @throws InvalidPhoneNumberException se pn contém caracteres que não são números.
	 */
	public static void validatePhoneNumber(String pn) throws InvalidPhoneNumberException { 
		try {
			Long.parseLong(pn.replace("(", "").replace(")", "").replace("-", ""));
		} catch (NumberFormatException e) {
			throw new InvalidPhoneNumberException();
		}
	}
	
	/**
	 * Esse método valida a senha digitada pelo usuário ao se cadastrar.
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
	 * Esse método valida os IDs digitados pelo usuário ao cadastrar um colaborador.
	 * Ele checa cada ID digitado com o ID de todos os serviços em allServices.
	 *
	 * @param servicesID um array contendo os IDs de serviços que o usuário digitou.
	 * @param allServices um ArrayList com todos os serviços cadastrados no sistema.
	 * 
	 * @return um ArrayList contendo os serviços com os IDs em servicesID.
	 * 
	 * @throws InvalidIDException se algum ID em servicesID não coincide com o ID de nenhum serviço em allServices.
	 */
	public static ArrayList<Service> validateIDs(String[] servicesID, ArrayList<Service> allServices) throws InvalidIDException {
		ArrayList<Service> validServices = new ArrayList<Service>();
		
		for(int i = 0; i < servicesID.length; i++) {
			for(Service svc:allServices) 
				if(servicesID[i].isEmpty()) 
					throw new InvalidIDException("IDs não podem ser vazios");
				else {
					try {
						if(Long.parseLong(servicesID[i]) == svc.getID()) {
							validServices.add(svc);
						}
					} catch (NumberFormatException e) {
						throw new InvalidIDException("ID " + servicesID[i] + " inválido");
					}
					
				}
		}
		
		for(int i = 0; i < servicesID.length; i++) 
			try {
				if(validServices.get(i).getID() != Long.parseLong(servicesID[i]));
			} catch (IndexOutOfBoundsException e1) {
				throw new InvalidIDException("Não existe serviço com ID " + servicesID[i]);
			}

			
		
	
		
		return validServices;
		
	}
	
	/**
	 * Esse método valida os preços digitados pelo usuário ao cadastrar um colaborador.
	 *
	 * @param servicesID um array contendo os IDs de serviços que o usuário digitou.
	 * @param prices um array contendo todos os preços que o usuário digitou.
	 * 
	 * @return um ArrayList com todos os preços convertidos em Float.
	 * 
	 * @throws Exception caso o usuário tenha digitado mais ou menos preços do que IDs, ou quando algum preço digitado é inválido.
	 */
	public static ArrayList<Float> validatePrices(String[] servicesID, String[] prices) throws Exception {
		ArrayList<Float> validPrices = new ArrayList<Float>();
		
		if(prices.length > servicesID.length) 
			throw new Exception("Foram digitados mais preços do que serviços");
		else if(prices.length < servicesID.length) 
			throw new Exception("Foram digitados mais serviços do que preços");
		
		for(String str:prices) 
			if(str.isEmpty()) 
				throw new Exception("Valores dos preços não podem ser vazios");
			else {
				try {
					float price = Float.parseFloat(str);
					validPrices.add(price);
				} catch (NumberFormatException e) {
					throw new Exception("Preço inválido");
				}
				
			}
		
		return validPrices;
		
	}

	/**
	 * Esse método valida a data de um agendamento.
	 *
	 * @param date uma String contendo a data a ser validada e convertida.
	 * @param isEditingOldAppt caso o usuário esteja editando um agendamento.
	 * @param isGeneratingReport caso o usuário esteja gerando um relatório de algum caixa.
	 * 
	 * @return uma LocalDate representando a data digitada em date.
	 * 
	 * @throws InvalidDateException caso a data digitada seja inválida (posterior a data atual ou dia, mês ou ano inválido), a menos que o usuário esteja editando um agendamento ou gerando um relatório.
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
	 * Esse método valida a hora de um agendamento.
	 *
	 * @param time uma String contendo a hora a ser validada e convertida.
	 * @param dateTyped a data digitada e validada previamente.
	 * @param isEditingOldAppt caso o usuário esteja editando um agendamento.
	 * 
	 * @return um LocalTime representando a hora digitada em time.
	 * 
	 * @throws InvalidTimeException caso dateTyped seja a data de hoje e time seja inválido (posterior a hora atual ou hora ou minuto inválidos), a menos que o usuário esteja editando um agendamento.
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
	 * Esse método valida um agendamento a ser cadastrado.
	 *
	 * @param col o Colaborator que realizará o serviço.
	 * @param cl o Client que receberá o serviço.
	 * @param s o Service a ser realizado.
	 * @param date a data do agendamento.
	 * @param time a hora do agendamento.
	 * @param apptEdit caso o usuário esteja editando um agendamento salvo previamente.
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
