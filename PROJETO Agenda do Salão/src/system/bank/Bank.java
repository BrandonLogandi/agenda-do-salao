package system.bank;

import java.util.ArrayList;

import people.Person;

public class Bank {
	
	private float cash = 0;
	private ArrayList<Transaction> transactions = new ArrayList<>();
	
	
	public float getCash() {
		return cash;
	}
	
	public ArrayList<Transaction> getTransactions() {
		return transactions;
	}
	public void setTransactions(ArrayList<Transaction> changes) {
		this.transactions = changes;
	}
	
	public void addCash(float cash, Person receiver) {
		this.cash += cash;
		transactions.add(new Transaction(cash, receiver));
	}
	public void removeCash(float cash, Person receiver) {
		this.cash -= cash;
		transactions.add(new Transaction(-cash, receiver));
	}


}
