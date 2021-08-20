package people;

import java.util.ArrayList;

import system.Appointment;

public class Client extends Person {
	
	private Gender gender;
	private String tel;
	private ArrayList<Appointment> appointments = new ArrayList<>();

	public Client(String name, String email, Gender gender, String tel) {
		super(name, email);
		this.gender = gender;
		this.tel = tel;
	}
	
	
	@Override
	public String toString() {
		return super.getName() + " " + super.getEmail();
	}
	
	
	public Gender getGender() {
		return gender;
	}
	public ArrayList<Appointment> getAppointments() {
		return appointments;
	}
	public String getTel() {
		return tel;
	}

	
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public void setAppointments(ArrayList<Appointment> appointments) {
		this.appointments = appointments;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}




	


}
