package system;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import people.Client;
import people.Colaborator;

public class Appointment {
	
	private Colaborator colaborator;
	private Service service;
	private Client client;
	private LocalDate date;
	private LocalTime time;
	
	private float price;
	
	private boolean finished = false;
	private boolean canceled = false;
	
	public Appointment(Colaborator colaborator, Service service, Client client, LocalDate date, LocalTime time, float price) throws Exception {
		this.colaborator = colaborator;
		this.service = service;
		this.client = client;
		this.date = date;
		this.time = time;
		this.price = price;

	}
	
	@Override
	public String toString() {
		if(finished)
			return client.getName() + ", " + colaborator.getName() + ", " + service.getName() + ", Concluído";
		else if(canceled)
			return client.getName() + ", " + colaborator.getName() + ", " + service.getName() + ", Cancelado";
		else
			return client.getName() + ", " + colaborator.getName() + ", " + service.getName() + ", Pendente";
	}

	public Colaborator getColaborator() {
		return colaborator;
	}
	public Service getService() {
		return service;
	}
	public Client getClient() {
		return client;
	}
	public LocalDate getDate() {
		return date;
	}
	public LocalTime getTime() {
		return time;
	}


	public void setColaborator(Colaborator colaborator) {
		this.colaborator = colaborator;
	}
	public void setService(Service service) {
		this.service = service;
	}

	public void setClient(Client client) {
		this.client = client;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public void setTime(LocalTime time) {
		this.time = time;
	}

	
	public boolean isFinished() {
		return finished;
	}
	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public boolean isCanceled() {
		return canceled;
	}
	public void setCanceled(boolean canceled) {
		this.canceled = canceled;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}


}
