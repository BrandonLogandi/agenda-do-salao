package windows.login;

import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import system.GmailAccount;
import system.operations.DataPersistence;
import system.operations.DataSystem;
import windows.DefaultWindow;
import windows.operations.ControlPanel;
import windows.operations.SendEmailWindow;


public class LoginWithGoogle extends DefaultWindow {
	
	private JTextField emailField = new JTextField();
	private JPasswordField senhaField = new JPasswordField();
	private JButton entrar = new JButton("Entrar");
	private JButton voltar = new JButton("Voltar");

	public LoginWithGoogle(DataSystem sys, DataPersistence dp) {
		super(sys, dp);
		this.setSize(420, 200);
		this.setTitle("Login");
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		createLabels();
		createButtons();
		createFields();
		
		setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(voltar)) {
			new ControlPanel(getSys(), getDp());
			this.dispose();
		}
		else if(e.getSource().equals(entrar)) {
			this.getSys().getAdmin().setGmailAccount(
					new GmailAccount(this.emailField.getText().concat("@gmail.com"), new String(this.senhaField.getPassword())));
			this.getDp().saveSystem(getSys());
			this.dispose();
			
			JOptionPane.showMessageDialog(this, "Para permitir que o programa envie emails utilizando esta conta, é necessário"
					+ "\npermitir o acesso à apps menos seguros."
					+ "\nUma nova página abrirá em seu navegador para que você permita o acesso.", "Aviso", JOptionPane.WARNING_MESSAGE);

			try {
				Desktop.getDesktop().browse(new URI("https://myaccount.google.com/lesssecureapps"));
			} catch (Exception e1) {
				e1.printStackTrace();
			} 
			new SendEmailWindow(getSys(), getDp());
			
		}

	}

	protected void createLabels() {
		JLabel nome = new JLabel("Login com conta Google");
		nome.setBounds(100, 3, 250, 40);
		nome.setFont(new Font("Arial", Font.PLAIN, 20));
		this.add(nome);
		
		JLabel email = new JLabel("Email: ");
		email.setBounds(80, 40, 150, 40);
		email.setFont(new Font("Arial", Font.PLAIN, 18));
		this.add(email);
		
		JLabel atgmail = new JLabel("@gmail.com");
		atgmail.setBounds(260, 40, 150, 40);
		atgmail.setFont(new Font("Arial", Font.PLAIN, 12));
		this.add(atgmail);
		
		JLabel senha = new JLabel("Senha: ");
		senha.setBounds(72, 70, 150, 40);
		senha.setFont(new Font("Arial", Font.PLAIN, 18));
		this.add(senha);
	}

	protected void createFields() {
		emailField.setBounds(140, 50, 110, 20);
		this.add(emailField);
		
		senhaField.setBounds(140, 80, 190, 20);
		this.add(senhaField);
	}

	protected void createButtons() {
		entrar.setBounds(250, 120, 80, 20);
		entrar.addActionListener(this);
		this.add(entrar);
		
		voltar.setBounds(80, 120, 80, 20);
		voltar.addActionListener(this);
		this.add(voltar);
	}

}
