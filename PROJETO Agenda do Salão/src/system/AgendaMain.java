package system;

import system.operations.DataPersistence;
import system.operations.DataSystem;
import windows.login.AdminRegistration;
import windows.login.Login;

public class AgendaMain {

	public static void main(String[] args) {
		DataPersistence dp = new DataPersistence();
		DataSystem sys = dp.loadSystem();
	
		if(sys.getAdmin() == null) 
			new AdminRegistration(sys, dp);
		else
			new Login(sys, dp);

	}

}
