package windows.login;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import system.operations.DataPersistence;
import system.operations.DataSystem;
import system.operations.Validation;
import windows.DefaultWindow;
import windows.operations.ControlPanel;
import windows.operations.List;

public class Login extends DefaultWindow {
	
	private JTextField emailField = new JTextField();
	private JPasswordField passwordField = new JPasswordField();
	private JButton loginButton = new JButton("Entrar");
	private JButton viewApptsButton = new JButton("Ver agendamentos");
	
	boolean isFirstLogin = false;
	boolean isLoggedIn = false;
	
	public Login(DataSystem sys, DataPersistence dp, boolean isFirstLogin) {
		super(sys, dp);
		this.setSize(420, 200);
		this.setTitle("Login");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.isFirstLogin = isFirstLogin;
		
		createLabels();
		createButtons();
		createFields();
		
		setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	protected void createLabels() {
		JLabel nome = new JLabel("Login");
		nome.setBounds(180, 3, 150, 40);
		nome.setFont(new Font("Arial", Font.PLAIN, 20));
		this.add(nome);
		
		JLabel email = new JLabel("Email: ");
		email.setBounds(80, 40, 150, 40);
		email.setFont(getArial18());
		this.add(email);
		
		JLabel senha = new JLabel("Senha: ");
		senha.setBounds(72, 70, 150, 40);
		senha.setFont(new Font("Arial", Font.PLAIN, 18));
		this.add(senha);
	}

	protected void createFields() {
		emailField.setBounds(140, 50, 170, 20);
		this.add(emailField);
		
		passwordField.setBounds(140, 80, 170, 20);
		this.add(passwordField);
	}
	
	protected void createButtons() {
		loginButton.setBounds(280, 120, 80, 20);
		loginButton.addActionListener(this);
		this.add(loginButton);
		
		viewApptsButton.setBounds(40, 120, 150, 20);
		viewApptsButton.addActionListener(this);
		this.add(viewApptsButton);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == this.loginButton) {
			if(this.emailField.getText().equals(this.getSys().getAdmin().getEmail()) 
					&& new String(this.passwordField.getPassword()).equals(new String(this.getSys().getAdmin().getPassword()))) {
				JOptionPane.showMessageDialog(this, "Bem-vindo(a), " + this.getSys().getAdmin().getName());
				isLoggedIn = true;
				this.dispose();
				
				if(isFirstLogin) {
					int response = JOptionPane.showConfirmDialog(this, "Para enviar emails pelo programa, é necessário entrar com uma conta Google."
							+ "\nDeseja fazer isso agora?", "Entrar com conta Google",
							JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
					
					switch(response) {
					case JOptionPane.YES_OPTION:
						new LoginWithGoogle(getSys(), getDp(), true);
						break;
					case JOptionPane.NO_OPTION:
						new ControlPanel(getSys(), getDp());
					}
				}
				else
					new ControlPanel(getSys(), getDp());
			}

			else if(this.emailField.getText().equals(this.getSys().getAdmin().getEmail()) == false)
				JOptionPane.showMessageDialog(this, "Email inválido", "Erro", JOptionPane.ERROR_MESSAGE);
			else if(new String(this.passwordField.getPassword()).equals(new String(this.getSys().getAdmin().getPassword())) == false)
				JOptionPane.showMessageDialog(this, "Senha inválida", "Erro", JOptionPane.ERROR_MESSAGE);
			

		}
		
		else if(e.getSource().equals(viewApptsButton)) {
			if(this.getSys().getAllAppointments().isEmpty())
				JOptionPane.showMessageDialog(this, "Não há nenhum agendamento cadastrado", "Erro", JOptionPane.ERROR_MESSAGE);
			else {
				new List(getSys(), getDp(), false);
				this.dispose();
			}
		}
		
	}

	
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	



