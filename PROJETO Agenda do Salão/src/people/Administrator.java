package people;

import system.GmailAccount;

public class Administrator extends Person {
	
	private String password;
	private GmailAccount gmailAccount;

	public Administrator(String name, String email, String password) {
		super(name, email);
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public GmailAccount getGmailAccount() {
		return gmailAccount;
	}
	
	public void setGmailAccount(GmailAccount gma) {
		this.gmailAccount = gma;
	}

	

}
