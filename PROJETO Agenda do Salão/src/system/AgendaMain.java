package system;

import java.time.LocalDate;

import system.exceptions.InvalidNameException;
import system.operations.DataPersistence;
import system.operations.DataSystem;
import system.operations.Validation;
import windows.login.AdminRegistration;
import windows.login.Login;
import windows.login.LoginWithGoogle;
import windows.operations.ControlPanel;
import windows.operations.List;
import windows.operations.Register;
import windows.operations.SendEmailWindow;
import windows.operations.bank.Bank;
import windows.operations.bank.GenerateReport;
import windows.operations.bank.PaySingleColabs;

public class AgendaMain {

	public static void main(String[] args) {
		DataPersistence dp = new DataPersistence();
		DataSystem sys = dp.loadSystem();
	
//		if(sys.getAdmin() == null) 
//			new AdminRegistration(sys, dp);
//		else
//			new Login(sys, dp);
		
//		new List(sys, dp, false);
		
//		new ControlPanel(sys, dp);
		new Register(sys, dp, null, null);
//		new List(sys, dp, true);
//		new Bank(sys, dp);
//		new GenerateReport(sys, dp);
//		new PaySingleColabs(sys, dp);
//		new LoginWithGoogle(sys, dp);
//		new SendEmailWindow(sys, dp);
	

	}

}
