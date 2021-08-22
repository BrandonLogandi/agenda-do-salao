package windows.operations;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import people.Colaborator;
import system.Appointment;
import system.operations.DataPersistence;
import system.operations.DataSystem;
import windows.DefaultWindow;
import windows.login.Login;
import windows.login.LoginWithGoogle;
import windows.operations.bank.Bank;

public class ControlPanel extends DefaultWindow {

	private JButton registerButton = new JButton("Cadastrar");
	private JButton listButton = new JButton("Listar");
	private JButton emailButton = new JButton("Enviar email");
	private JButton bankButton = new JButton("Caixa");
	private JButton logoffButton = new JButton("Sair");
	
	private JLabel moneyInDaBank = new JLabel("Saldo: R$" + this.getSys().getSalonBank().getCash());
	private JLabel pendingAppts = new JLabel();
	private JLabel availableColabs = new JLabel();

	public ControlPanel(DataSystem sys, DataPersistence dp) {
		super(sys, dp);
		this.setSize(570, 220);
		this.setTitle("Painel de controle");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		createLabels();
		createButtons();
		createFields();

		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(logoffButton)) {
			new Login(getSys(), getDp(), false);
			this.dispose();
		}
		
		else if(e.getSource().equals(registerButton)) {
			new Register(getSys(), getDp(), null, null);
			this.dispose();
		}
		
		else if(e.getSource().equals(listButton)) {
			new List(getSys(), getDp(), true);
			this.dispose();
		}
		
		else if(e.getSource().equals(emailButton)) {
			this.dispose();

			if(this.getSys().getAdmin().getGmailAccount() == null) {
				JOptionPane.showMessageDialog(this, "Para enviar emails, é necessário entrar com uma conta Google primeiro", "", JOptionPane.WARNING_MESSAGE);
				new LoginWithGoogle(getSys(), getDp(), false);
			}
			
			else
				new SendEmailWindow(getSys(), getDp());	
		}
		
		else if(e.getSource().equals(bankButton)) {
			new Bank(getSys(), getDp());
			this.dispose();
		}
		
	}

	
	protected void createLabels() {
		JLabel nome = new JLabel("Painel de controle");
		nome.setBounds(200, 0, 180, 40);
		nome.setFont(new Font("Arial Black", Font.PLAIN, 16));
		this.add(nome);
		
		SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy");
		JLabel dateTime = new JLabel(ft.format(new Date()));
		dateTime.setBounds(450, 0, 180, 40);
		dateTime.setFont(new Font("Arial", Font.PLAIN, 16));
		this.add(dateTime);
		
		int pendingApptsTotal = 0;
		int availableColabsTotal = 0;
		for(Appointment a:this.getSys().getAllAppointments()) {
			if(!a.isFinished() && !a.isCanceled())
				pendingApptsTotal++;
		}
		for(Colaborator c:this.getSys().getAllColaborators()) {
			if(c.isActive())
				availableColabsTotal++;
		}
		
		moneyInDaBank.setBounds(20, 35, 180, 40);
		moneyInDaBank.setFont(new Font("Arial", Font.PLAIN, 16));
		this.add(moneyInDaBank);
		
		pendingAppts.setText("Agendamentos pendentes: " + pendingApptsTotal);
		pendingAppts.setBounds(20, 60, 250, 40);
		pendingAppts.setFont(new Font("Arial", Font.PLAIN, 16));
		this.add(pendingAppts);
		
		availableColabs.setText("Colaboradores disponíveis: " + availableColabsTotal);
		availableColabs.setBounds(20, 85, 250, 40);
		availableColabs.setFont(new Font("Arial", Font.PLAIN, 16));
		this.add(availableColabs);
	}

	protected void createFields() {
	}

	protected void createButtons() {
		registerButton.setFont(new Font("Arial", Font.BOLD, 10));
		registerButton.setBounds(20, 140, 90, 20);
		registerButton.addActionListener(this);
		this.add(registerButton);
		
		listButton.setFont(new Font("Arial", Font.BOLD, 10));
		listButton.setBounds(120, 140, 90, 20);
		listButton.addActionListener(this);
		this.add(listButton);
		
		emailButton.setFont(new Font("Arial", Font.BOLD, 10));
		emailButton.setBounds(220, 140, 100, 20);
		emailButton.addActionListener(this);
		this.add(emailButton);
		
		bankButton.setFont(new Font("Arial", Font.BOLD, 10));
		bankButton.setBounds(330, 140, 100, 20);
		bankButton.addActionListener(this);
		this.add(bankButton);
		
		logoffButton.setFont(new Font("Arial", Font.BOLD, 10));
		logoffButton.setBounds(440, 140, 100, 20);
		logoffButton.addActionListener(this);
		this.add(logoffButton);
	}



}
