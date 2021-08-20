package system.bank;

import java.time.LocalDateTime;

import people.Person;

public class Transaction {
	
	private float money;
	private LocalDateTime date;
	private Person receiver;
	
	public Transaction(float money, Person receiver) {
		this.money = money;
		this.receiver = receiver;
		this.date = LocalDateTime.now();
	}
	
	
	public float getMoney() {
		return money;
	}
	public LocalDateTime getDate() {
		return date;
	}
	public Person getReceiver() {
		return receiver;
	}



}
