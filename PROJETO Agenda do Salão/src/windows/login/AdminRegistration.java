package windows.login;

import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import people.Administrator;
import system.operations.DataPersistence;
import system.operations.DataSystem;
import system.operations.Validation;
import windows.DefaultWindow;

public class AdminRegistration extends DefaultWindow {
	
	private JTextField nameField = null;
	private JTextField emailField = null;
	private JPasswordField passwordField = null;
	
	public AdminRegistration(DataSystem sys, DataPersistence dp) {
		super(sys, dp);
		this.setSize(420, 260);
		this.setTitle("Cadastrar administrador");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		createLabels();
		createButtons();
		createFields();
		
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	protected void createLabels() {
		JLabel nome = new JLabel("Nome: ");
		nome.setBounds(30, 40, 150, 40);
		nome.setFont(new Font("Arial", Font.PLAIN, 18));
		this.add(nome);
		
		JLabel email = new JLabel("Email: ");
		email.setBounds(32, 80, 150, 40);
		email.setFont(new Font("Arial", Font.PLAIN, 18));
		this.add(email);
		
		JLabel senha = new JLabel("Senha: ");
		senha.setBounds(25, 120, 150, 40);
		senha.setFont(new Font("Arial", Font.PLAIN, 18));
		this.add(senha);
	}

	protected void createFields() {
		nameField = new JTextField();
		nameField.setBounds(90, 50, 150, 20);
		this.add(nameField);
		
		emailField = new JTextField();
		emailField.setBounds(90, 90, 150, 20);
		this.add(emailField);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(90, 130, 150, 20);
		this.add(passwordField);
	}

	@Override
	protected void createButtons() {
		JButton cadastrar = new JButton("Cadastrar");
		cadastrar.setBounds(270, 150, 100, 15);
		cadastrar.addActionListener(this);
		this.add(cadastrar);
	}
	
	
	public void actionPerformed(ActionEvent e) {
		try {
			Validation.validateName(this.nameField.getText(), false);
			Validation.validateEmail(this.emailField.getText());
			Validation.validatePassword(this.passwordField.getPassword());
			
			this.getSys().setAdmin(new Administrator(this.nameField.getText(), this.emailField.getText(), new String(this.passwordField.getPassword())));
			this.getDp().saveSystem(this.getSys());
			
			new Login(this.getSys(), this.getDp());
			this.dispose();
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(this, e1.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
		}
		
		

	}



	
	
}
