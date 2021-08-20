package system.operations;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import people.Administrator;
import people.Client;
import people.Colaborator;
import system.Appointment;
import system.Service;
import system.bank.Bank;

public class DataSystem {
	
	private Administrator admin = null;
	
	private ArrayList<Colaborator> allColaborators = new ArrayList<>();
	private ArrayList<Service> allServices = new ArrayList<>();
	private ArrayList<Client> allClients = new ArrayList<>();
	private ArrayList<Appointment> allAppointments = new ArrayList<>();
	
	private Bank salloonBank = new Bank();
	
	
	public void addColaborator(Colaborator c) throws Exception {
		if(!allColaborators.isEmpty()) {
			for(Colaborator col:allColaborators) 
				if(col.getEmail().equals(c.getEmail())) 
					throw new Exception("Colaborador já existe");
			
			allColaborators.add(c);
		}
		
		else 
			allColaborators.add(c);
		
	}
	public Colaborator getColaborator(String email) {
		for(Colaborator c:allColaborators) 
			if(c.getEmail().equals(email)) 
				return c;
		
		return null;
	}
	
	public void addService(Service s) throws Exception {
		if(!allServices.isEmpty()) {
			for(Service svc:allServices) 
				if(svc.getName().equals(s.getName()))
					throw new Exception("Já existe um serviço com nome " + s.getName() + ". Seu ID é " + svc.getID());
			
			allServices.add(s);
		}
		
		else 
			allServices.add(s);

	}
	public Service getService(long ID) {
		for(Service s:allServices) 
			if(s.getID() == ID)
				return s;
		
		return null;
	}
	
	public void addClient(Client c) {
		this.allClients.add(c);	
	}
	public Client getClient (String email) {
		for(Client c:this.allClients) 
			if(c.getEmail().equals(email)) 
				return c;
			
		return null;
		
	}
	
	
	public void addAppointment(Appointment a) {
		this.allAppointments.add(a);
		if(!this.allClients.contains(a.getClient())) 
			this.allClients.add(a.getClient());
		
	}
	
	
	
	
	public Administrator getAdmin() {
		return admin;
	}
	public ArrayList<Colaborator> getAllColaborators() {
		return allColaborators;
	}
	public ArrayList<Service> getAllServices() {
		return allServices;
	}
	public ArrayList<Client> getAllClients() {
		return allClients;
	}
	public ArrayList<Appointment> getAllAppointments() {
		return allAppointments;
	}
	
	public void setAdmin(Administrator a) {
		this.admin = a;
		JOptionPane.showMessageDialog(null, "Admin criado", "", JOptionPane.INFORMATION_MESSAGE);
	}
	public void setAllColaborators(ArrayList<Colaborator> allColaborators) {
		this.allColaborators = allColaborators;
	}
	public void setAllServices(ArrayList<Service> allServices) {
		this.allServices = allServices;
	}
	public void setAllClients(ArrayList<Client> allClients) {
		this.allClients = allClients;
	}
	public void setAllAppointments(ArrayList<Appointment> allAppointments) {
		this.allAppointments = allAppointments;
	}
	
	
	public Bank getSalloonBank() {
		return salloonBank;
	}
	public void setSalloonBank(Bank salloonBank) {
		this.salloonBank = salloonBank;
	}
	
}
