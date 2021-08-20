package people;

import java.util.ArrayList;

import system.Appointment;
import system.Service;
import system.bank.Bank;


public class Colaborator extends Person {
	
	private Gender gender;
	private String phoneNumber;
	private ArrayList<Service> services = new ArrayList<>();
	private ArrayList<Float> prices = new ArrayList<>();
	private ArrayList<Appointment> appointments = new ArrayList<>();
	private boolean isActive = true;
	
	private float pendingPay = 0;
	private Bank colabBank = new Bank();
	
	public Colaborator(String name, String email, Gender gender, String pn, ArrayList<Service> svcs, ArrayList<Float> prices) {
		super(name, email);
		this.gender = gender;
		this.phoneNumber = pn;
		this.services = svcs;
		this.prices = prices;
	}
	
	@Override
	public String toString() {
		return this.getName() + ", " + this.getEmail();
	}

	

	public Gender getGender() {
		return gender;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public ArrayList<Service> getServices() {
		return services;
	}
	public ArrayList<Float> getPrices() {
		return prices;
	}
	public ArrayList<Appointment> getAppointments() {
		return appointments;
	}
	

	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public void setServices(ArrayList<Service> services) {
		this.services = services;
	}
	public void setPrices(ArrayList<Float> prices) {
		this.prices = prices;
	}
	public void setAppointments(ArrayList<Appointment> appointments) {
		this.appointments = appointments;
	}
	
	
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	


	public float getPendingPay() {
		return pendingPay;
	}
	public Bank getBank() {
		return colabBank;
	}
	
	public void setPendingPay(float pendingPay) {
		this.pendingPay = pendingPay;
	}


}
