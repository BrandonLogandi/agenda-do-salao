package people;

import java.util.ArrayList;

import system.Appointment;

public class Client extends Person {
	
	private Gender gender;
	private String phoneNumber;
	private ArrayList<Appointment> appointments = new ArrayList<>();

	public Client(String name, String email, Gender gender, String pn) {
		super(name, email);
		this.gender = gender;
		this.phoneNumber = pn;
	}
	
	
	public String toString() {
		return super.getName() + " " + super.getEmail();
	}
	
	
	public Gender getGender() {
		return gender;
	}
	public ArrayList<Appointment> getAppointments() {
		return appointments;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}

}
