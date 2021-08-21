package windows.operations;

import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import people.Client;
import people.Colaborator;
import system.exceptions.InvalidEmailException;
import system.operations.DataPersistence;
import system.operations.DataSystem;
import system.operations.SendEmail;
import system.operations.Validation;
import windows.DefaultWindow;

public class SendEmailWindow extends DefaultWindow {
	
	private String[] options = {"Todos os colaboradores", "Todos os clientes", "Outro"};
	private JComboBox<String> whoToSendCombo = new JComboBox<>(options);
	private JLabel otherEmailLabel = new JLabel("Endereço: ");
	private JTextField otherEmailField = new JTextField();
	private JTextField subjectField = new JTextField();
	
	private JTextArea messageArea = new JTextArea();
	
	private JButton goBackButton = new JButton("Voltar");
	private JButton sendButton = new JButton("Enviar");

	public SendEmailWindow(DataSystem sys, DataPersistence dp) {
		super(sys, dp);
		this.setSize(600, 430);
		this.setTitle("Enviar email");
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		createLabels();
		createButtons();
		createFields();
		
		this.otherEmailLabel.setEnabled(false);
		this.otherEmailField.setEnabled(false);

		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	
	protected void createLabels() {
		JLabel whoToSendLabel = new JLabel("Enviar para: ");
		whoToSendLabel.setBounds(25, 10, 150, 40);
		whoToSendLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		this.add(whoToSendLabel);
		
		otherEmailLabel.setBounds(25, 40, 150, 40);
		otherEmailLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		this.add(otherEmailLabel);
		
		JLabel subjectLabel = new JLabel("Assunto: ");
		subjectLabel.setBounds(25, 70, 150, 40);
		subjectLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		this.add(subjectLabel);
		
		JLabel messageLabel = new JLabel("Mensagem: ");
		messageLabel.setBounds(25, 100, 150, 40);
		messageLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		this.add(messageLabel);
	}

	protected void createFields() {
		whoToSendCombo.setBounds(120, 21, 170, 20);
		whoToSendCombo.addActionListener(this);
		this.add(whoToSendCombo);
		
		otherEmailField.setBounds(120, 51, 170, 20);
		this.add(otherEmailField);
		
		subjectField.setBounds(120, 81, 170, 20);
		this.add(subjectField);
		
		messageArea.setBounds(25, 140, 535, 200);
		this.add(messageArea);
	}
	
	protected void createButtons() {
		goBackButton.setBounds(25, 355, 100, 20);
		goBackButton.addActionListener(this);
		this.add(goBackButton);
		
		sendButton.setBounds(460, 355, 100, 20);
		sendButton.addActionListener(this);
		this.add(sendButton);
	}
	
	private void goBack() {
		this.dispose();
		new ControlPanel(getSys(), getDp());
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(goBackButton)) 
			goBack();
		
		else if(e.getSource().equals(whoToSendCombo)) {
			switch(this.whoToSendCombo.getSelectedIndex()) {
			case 0, 1:
				this.otherEmailLabel.setEnabled(false);
				this.otherEmailField.setEnabled(false);
				break;
			case 2:
				this.otherEmailLabel.setEnabled(true);
				this.otherEmailField.setEnabled(true);
			}
			
		}
		
		else if(e.getSource().equals(sendButton)) {
			switch(this.whoToSendCombo.getSelectedIndex()) {
			case 0:
				if(this.getSys().getAllColaborators().size() == 0)
					JOptionPane.showMessageDialog(this, "Não há nenhum colaborador cadastrado", "Erro", JOptionPane.ERROR_MESSAGE);
				else {
					goBack();
					for(Colaborator c:this.getSys().getAllColaborators())
						SendEmail.sendToColab(this.getSys().getAdmin(), c, this.subjectField.getText(), this.messageArea.getText());
				}
				
			case 1:
				if(this.getSys().getAllClients().size() == 0)
					JOptionPane.showMessageDialog(this, "Não há nenhum cliente cadastrado", "Erro", JOptionPane.ERROR_MESSAGE);
				else {
					goBack();
					for(Client cl:this.getSys().getAllClients())
						SendEmail.sendToClient(this.getSys().getAdmin(), cl, this.subjectField.getText(), this.messageArea.getText());
				}
				
			case 2:
				String[] otherEmails = this.otherEmailField.getText().split(" ");
				int i = 0;
					try {
						for(String s:otherEmails) {
							Validation.validateEmail(s);
							i++;
						}
						for(String s:otherEmails)
							SendEmail.sendToOther(this.getSys().getAdmin(), s, this.subjectField.getText(), this.messageArea.getText());
						
						if(i > 1)
							JOptionPane.showMessageDialog(null, "Mensagens enviadas");
						else
							JOptionPane.showMessageDialog(null, "Mensagem enviada");
						
						goBack();
					} catch (InvalidEmailException e1) {
						JOptionPane.showMessageDialog(this, "Email " + i + " inválido", "Erro", JOptionPane.ERROR_MESSAGE);
					}
			}
		}
			

	}

}
