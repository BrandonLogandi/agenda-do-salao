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

public abstract class Validation {
	
	private static String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
	

	public static float formatMoney(float money) {
		DecimalFormat df = new DecimalFormat("0.00");
		return Float.parseFloat(df.format(money));
	}
	
	
	public static void validateName(String name, boolean isDescription) throws InvalidNameException{
		if(name.isBlank())
			throw new InvalidNameException(isDescription);
		
		char [] chars = name.toCharArray();
		for(char c:chars) 
			if(!Character.isLetter(c) && !Character.isWhitespace(c))
				throw new InvalidNameException(isDescription); 

	}
	
	public static void validateEmail(String email) throws InvalidEmailException {
		if(!email.matches(EMAIL_REGEX)) 
			throw new InvalidEmailException();
	}
	
	public static void validatePhoneNumber(String pn) throws InvalidPhoneNumberException { 
		try {
			Long.parseLong(pn.replace("(", "").replace(")", "").replace("-", ""));
		} catch (NumberFormatException e) {
			throw new InvalidPhoneNumberException();
		}
	}
	
	
	public static void validatePassword(char[] password) throws InvalidPasswordException {
		if(new String(password).isBlank())
			throw new InvalidPasswordException();
	}
	
	
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
