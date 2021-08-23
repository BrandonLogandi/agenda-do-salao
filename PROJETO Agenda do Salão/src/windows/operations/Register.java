package windows.operations;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.text.MaskFormatter;

import java.time.LocalDate;
import java.time.LocalTime;

import people.Client;
import people.Colaborator;
import people.Gender;
import system.Appointment;
import system.Service;
import system.operations.DataPersistence;
import system.operations.DataSystem;
import system.operations.Validation;
import windows.DefaultWindow;

public class Register extends DefaultWindow implements KeyListener  {
	
	private Colaborator colabEdit = null;
	private Appointment apptEdit = null;
	
	private MaskFormatter phoneNumberFieldMask = null;

	//Botões em comum
	private JRadioButton colaboratorButton;
	private JRadioButton serviceButton;
	private JRadioButton appointmentButton;
	private JButton registerButton;
	private JButton goBackButton;
	
	//Colaboradores
	private JLabel nameLabel = new JLabel("Nome ");
	private JLabel emailLabel = new JLabel("Email ");
	private JLabel phoneNumberLabel = new JLabel("Telefone ");
	private JLabel sexLabel = new JLabel("Sexo ");
	private JRadioButton sexMaleButton = new JRadioButton("Masculino", true);
	private JRadioButton sexFemButton = new JRadioButton("Feminino");
	private JLabel servicesLabel = new JLabel("Serviços ");
	private JLabel pricesLabel = new JLabel("Preços ");
	private JLabel servicesTipLabel = new JLabel("IDs dos serviços separados por espaço");
	private JLabel pricesTipLabel = new JLabel("Preços dos serviços separados por espaço");
	
	private JTextField nameField = new JTextField();
	private JTextField emailField = new JTextField();
	private JFormattedTextField phoneNumberField = new JFormattedTextField();
	private JTextField serviceField = new JTextField();
	private JTextField priceField = new JTextField();
	
	//Servicos
	private JLabel nameLabel2 = new JLabel("Nome ");
	private JLabel descriptionLabel = new JLabel("Descrição ");
	private JLabel durationLabel = new JLabel("Dur. média ");
	private JLabel minutesLabel = new JLabel("minutos");
	private JSpinner durationSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 1440, 1));
	
	private JTextField nameField2 = new JTextField();
	private JTextField descriptionField = new JTextField();
	
	//Agendamentos
	private JLabel nameLabel3 = new JLabel("Nome");
	private JLabel emailLabel2 = new JLabel("Email");
	private JLabel emailTipLabel = new JLabel("Tecle Enter para confirmar o email");
	private JLabel phoneNumberLabel2 = new JLabel("Telefone");
	private JLabel sexLabel2 = new JLabel("Sexo");
	private JLabel serviceLabel = new JLabel("Serviço");
	private JLabel colaboratorLabel = new JLabel("Colaborador");
	private JLabel dateLabel = new JLabel("Data");
	private JLabel timeLabel = new JLabel("Hora");
	
	private JLabel priceLabel = new JLabel("Preço: R$");
	
	private JTextField nameField3 = new JTextField();
	private JTextField emailField2 = new JTextField();
	private JFormattedTextField phoneNumberField2 = new JFormattedTextField();
	private JFormattedTextField dateField = new JFormattedTextField();
	private JFormattedTextField hourField = new JFormattedTextField();
	private JRadioButton sexMaleButton2 = new JRadioButton("Masculino", true);
	private JRadioButton sexFemButton2 = new JRadioButton("Feminino");
	
	private JLabel timeTip = new JLabel("Formato 24 horas");
	
	private JComboBox<Service> serviceCombo = new JComboBox<>();
	private JComboBox<Colaborator> colabCombo = new JComboBox<>();
	
	private JCheckBox isFinishedBox = new JCheckBox("Concluído");
	
	private Client existingClient = null;
	
	private float price = 0;
	



	public Register(DataSystem sys, DataPersistence dp, Colaborator colabEdit, Appointment apptEdit) {
		super(sys, dp);
		
		this.colabEdit = colabEdit;
		this.apptEdit = apptEdit;

		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setSize(400, 450);
		this.setTitle("Cadastrar");
		
		try {
			phoneNumberFieldMask = new MaskFormatter("(##)#####-####");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		createLabels();
		createButtons();
		createFields();
		
		
		if(colabEdit != null) 
			this.colaboratorFields();
		
		else if(apptEdit != null) 
			this.appointmentFields();
		
		else {
			this.colaboratorFields();
			this.serviceFields();
			this.setServiceFieldsVis(false);
			this.appointmentFields();
			this.setApptFieldsVis(false);
		}
				
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	
	private void colaboratorFields() {
		nameLabel.setBounds(20, 45, 150, 40);
		nameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		this.add(nameLabel);
		
		emailLabel.setBounds(20, 85, 150, 40);
		emailLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		this.add(emailLabel);
		
		phoneNumberLabel.setBounds(20, 125, 150, 40);
		phoneNumberLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		this.add(phoneNumberLabel);
		
		sexLabel.setBounds(20, 165, 150, 40);
		sexLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		this.add(sexLabel);
		
		servicesLabel.setBounds(20, 205, 150, 40);
		servicesLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		this.add(servicesLabel);
		
		pricesLabel.setBounds(20, 245, 150, 40);
		pricesLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		this.add(pricesLabel);
		
		
		
		nameField.setBounds(100, 55, 220, 20);
		this.add(nameField);
		emailField.setBounds(100, 95, 220, 20);
		this.add(emailField);
		
		phoneNumberField = new JFormattedTextField(phoneNumberFieldMask);
		phoneNumberField.setBounds(100, 135, 220, 20);
		this.add(phoneNumberField);
		
		
		sexMaleButton.setBounds(100, 173, 100, 20);
		this.add(sexMaleButton);
		
		sexFemButton.setBounds(200, 173, 100, 20);
		this.add(sexFemButton);
		
		serviceField.setBounds(100, 215, 220, 20);
		this.add(serviceField);
		
		servicesTipLabel.setFont(new Font("Arial", Font.ITALIC, 10));
		servicesTipLabel.setBounds(120, 230, 220, 20);
		this.add(servicesTipLabel);
		
		priceField.setBounds(100, 255, 220, 20);
		this.add(priceField);
		
		pricesTipLabel.setFont(new Font("Arial", Font.ITALIC, 10));
		pricesTipLabel.setBounds(110, 270, 220, 20);
		this.add(pricesTipLabel);
		
		ButtonGroup sexRadioGroup = new ButtonGroup();
		sexRadioGroup.add(sexMaleButton);
		sexRadioGroup.add(sexFemButton);
		
		
		if(colabEdit != null)  {
			this.setTitle("Editando colaborador");
			this.registerButton.setText("Salvar");
			
			this.colaboratorButton.setVisible(false);
			this.serviceButton.setVisible(false);
			this.appointmentButton.setVisible(false);
			
			nameField.setText(colabEdit.getName());
			emailField.setText(colabEdit.getEmail());
			emailField.setEditable(false);
			phoneNumberField.setText(colabEdit.getPhoneNumber());
			
			if(colabEdit.getGender().equals(Gender.MASCULINO)) 
				this.sexMaleButton.setSelected(true);
			else
				this.sexFemButton.setSelected(true);
			
			String svcIDs = "";
			String svcPrices = "";
			
			for(Service s:colabEdit.getServices()) 
				svcIDs = svcIDs.concat(s.getID() + " ");
			
			for(float p:colabEdit.getPrices())
				svcPrices = svcPrices.concat(String.valueOf(p) + " ");
				
			serviceField.setText(svcIDs);
			priceField.setText(svcPrices);
			
			
		}
		
	}

	private void serviceFields() {
		nameLabel2.setBounds(40, 130, 150, 40);
		nameLabel2.setFont(new Font("Arial", Font.PLAIN, 16));
		this.add(nameLabel2);
		
		descriptionLabel.setBounds(40, 170, 150, 40);
		descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		this.add(descriptionLabel);
		
		durationLabel.setBounds(40, 210, 150, 40);
		durationLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		this.add(durationLabel);
		
		minutesLabel.setBounds(180, 210, 150, 40);
		minutesLabel.setFont(new Font("Arial", Font.ITALIC, 14));
		this.add(minutesLabel);
		
		durationSpinner.setBounds(130, 220, 40, 20);
		this.add(durationSpinner);
		
		nameField2.setBounds(130, 140, 190, 20);
		this.add(nameField2);
		
		descriptionField.setBounds(130, 180, 190, 20);
		this.add(descriptionField);
		
	}

	private void appointmentFields() {
		for(Service s:this.getSys().getAllServices()) {
			serviceCombo.addItem(s);
		}
		
		Service s = (Service) serviceCombo.getItemAt(0);
		
		if(s != null) 
			for(Colaborator c:s.getColaborators()) 
				if(c.isActive())
					colabCombo.addItem(c);
		
		if(colabCombo.getItemAt(0) != null) {
			price = Validation.formatMoney((colabCombo.getItemAt(0).getPrices().get(0) / 60) * s.getAvrgDuration());
			priceLabel.setText("Preço: R$" + price);	
		}

		
		nameLabel3.setBounds(20, 45, 150, 40);
		nameLabel3.setFont(new Font("Arial", Font.PLAIN, 16));
		this.add(nameLabel3);
		
		emailLabel2.setBounds(20, 85, 150, 40);
		emailLabel2.setFont(new Font("Arial", Font.PLAIN, 16));
		this.add(emailLabel2);
		
		emailTipLabel.setFont(new Font("Arial", Font.ITALIC, 10));
		emailTipLabel.setBounds(125, 110, 220, 20);
		this.add(emailTipLabel);
		
		phoneNumberLabel2.setBounds(20, 125, 150, 40);
		phoneNumberLabel2.setFont(new Font("Arial", Font.PLAIN, 16));
		this.add(phoneNumberLabel2);
		
		sexLabel2.setBounds(20, 165, 150, 40);
		sexLabel2.setFont(new Font("Arial", Font.PLAIN, 16));
		this.add(sexLabel2);
		
		serviceLabel.setBounds(20, 205, 150, 40);
		serviceLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		this.add(serviceLabel);
		
		colaboratorLabel.setBounds(20, 245, 150, 40);
		colaboratorLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		this.add(colaboratorLabel);
		
		dateLabel.setBounds(20, 285, 150, 40);
		dateLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		this.add(dateLabel);
		
		timeLabel.setBounds(20, 325, 150, 40);
		timeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		this.add(timeLabel);
		
		priceLabel.setBounds(280, 228, 150, 40);
		priceLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		this.add(priceLabel);
		
		timeTip.setBounds(190, 325, 150, 40);
		timeTip.setFont(new Font("Arial", Font.ITALIC, 10));
		this.add(timeTip);
		
		nameField3.setBounds(100, 55, 220, 20);
		this.add(nameField3);
		
		emailField2.setBounds(100, 95, 220, 20);
		emailField2.addKeyListener(this);
		this.add(emailField2);

		phoneNumberField2 = new JFormattedTextField(phoneNumberFieldMask);
		phoneNumberField2.setBounds(100, 135, 220, 20);
		this.add(phoneNumberField2);
		
		sexMaleButton2.setBounds(100, 173, 100, 20);
		this.add(sexMaleButton2);
		
		sexFemButton2.setBounds(200, 173, 100, 20);
		this.add(sexFemButton2);
		
		serviceCombo.setBounds(120, 215, 150, 25);
		serviceCombo.addActionListener(this);
		this.add(serviceCombo);
		
		colabCombo.setBounds(120, 255, 150, 25);
		colabCombo.addActionListener(this);
		this.add(colabCombo);
		
		MaskFormatter dateFieldMask = null;
		try {
			dateFieldMask = new MaskFormatter("##/##/####");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		dateField = new JFormattedTextField(dateFieldMask);
		dateField.setBounds(100, 295, 80, 20);
		dateField.setFont(new Font("Arial", Font.PLAIN, 12));
		this.add(dateField);
		
		MaskFormatter hourFieldMask = null;
		try {
			hourFieldMask = new MaskFormatter("##:##");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		hourField = new JFormattedTextField(hourFieldMask);
		hourField.setBounds(100, 335, 80, 20);
		hourField.setFont(new Font("Arial", Font.PLAIN, 12));
		this.add(hourField);
		
		ButtonGroup radioSexo = new ButtonGroup();
		radioSexo.add(sexMaleButton2);
		radioSexo.add(sexFemButton2);
		
		if(apptEdit != null) {
			this.setTitle("Editando agendamento");
			this.registerButton.setText("Salvar");
			
			this.colaboratorButton.setVisible(false);
			this.serviceButton.setVisible(false);
			this.appointmentButton.setVisible(false);
			this.appointmentButton.setSelected(true);
			
			this.isFinishedBox.setBounds(230, 315, 100, 20);
			this.isFinishedBox.addActionListener(this);
			this.add(isFinishedBox);
			
			this.serviceCombo.removeAllItems();
			this.serviceCombo.addItem(apptEdit.getService());
			this.serviceCombo.setEnabled(false);
			
			this.colabCombo.removeAllItems();
			for(Colaborator c:apptEdit.getService().getColaborators())
				this.colabCombo.addItem(c);
			this.colabCombo.setSelectedItem(apptEdit.getColaborator());
			
			this.nameField3.setText(apptEdit.getClient().getName());
			this.emailField2.setText(apptEdit.getClient().getEmail());
			this.phoneNumberField2.setText(apptEdit.getClient().getPhoneNumber());
			
			if(apptEdit.getClient().getGender().equals(Gender.MASCULINO)) 
				this.sexMaleButton2.setSelected(true);
			else
				this.sexFemButton2.setSelected(true);

			
			LocalDate date = apptEdit.getDate();
			LocalTime time = apptEdit.getTime();
			
			//Converter dia, mês e ano
			String dayValue = "";
			String monthValue = "";
			String yearValue = String.valueOf(date.getYear());
			
			if(date.getDayOfMonth() < 10) 
				dayValue = "0" + date.getDayOfMonth();
			else
				dayValue = String.valueOf(date.getDayOfMonth());
			
			if(date.getMonthValue() < 10) 
				monthValue = "0" + date.getMonthValue();
			else
				monthValue = String.valueOf(date.getMonthValue());

			//Converter hora e minuto
			String hourValue = "";
			String minValue = "";
			
			if(time.getHour() < 10) 
				hourValue = "0" + time.getHour();
			else
				hourValue = String.valueOf(time.getHour());
			
			if(time.getMinute() < 10) 
				minValue = "0" + time.getMinute();
			else
				minValue = String.valueOf(time.getMinute());
			
			
			this.dateField.setText(dayValue + "/" + monthValue + "/" + yearValue);
			this.hourField.setText(hourValue + ":" + minValue);
			
			this.existingClient = this.getSys().getClient(this.emailField2.getText());
			this.nameField3.setEditable(false);
			this.phoneNumberField2.setEditable(false);
			this.sexFemButton2.setEnabled(false);
			this.sexMaleButton2.setEnabled(false);
			
		}

	}
	
	
	
	
	
	//Esconder os campos
	private void setColaboratorFieldsVis(boolean b) {
		nameLabel.setVisible(b);
		emailLabel.setVisible(b);
		phoneNumberLabel.setVisible(b);
		sexLabel.setVisible(b);
		sexMaleButton.setVisible(b);
		sexFemButton.setVisible(b);
		servicesLabel.setVisible(b);
		pricesLabel.setVisible(b);
		
		nameField.setVisible(b);
		emailField.setVisible(b);
		phoneNumberField.setVisible(b);
		serviceField.setVisible(b);
		priceField.setVisible(b);
		servicesTipLabel.setVisible(b);
		pricesTipLabel.setVisible(b);
	}
	
	private void setServiceFieldsVis(boolean b) {
		nameLabel2.setVisible(b);
		descriptionLabel.setVisible(b);
		durationLabel.setVisible(b);
		minutesLabel.setVisible(b);
		durationSpinner.setVisible(b);
		
		nameField2.setVisible(b);
		descriptionField.setVisible(b);
	}
	
	private void setApptFieldsVis(boolean b) {
		nameLabel3.setVisible(b);
		emailLabel2.setVisible(b);
		emailTipLabel.setVisible(b);
		phoneNumberLabel2.setVisible(b);
		sexLabel2.setVisible(b);
		serviceLabel.setVisible(b);
		colaboratorLabel.setVisible(b);
		dateLabel.setVisible(b);
		timeLabel.setVisible(b);
		
		priceLabel.setVisible(b);
		
		nameField3.setVisible(b);
		emailField2.setVisible(b);
		phoneNumberField2.setVisible(b);
		dateField.setVisible(b);
		hourField.setVisible(b);
		sexMaleButton2.setVisible(b);
		sexFemButton2.setVisible(b);
		
		timeTip.setVisible(b);
		
		serviceCombo.setVisible(b);
		colabCombo.setVisible(b);
	}
	
	

	private void getExistingClient() {
		this.existingClient = this.getSys().getClient(this.emailField2.getText());
		
		if(existingClient == null) {
			JOptionPane.showMessageDialog(this, "Cliente não encontrado", "", JOptionPane.INFORMATION_MESSAGE);
			this.nameField3.setEditable(true);
			this.phoneNumberField2.setEditable(true);
			this.sexFemButton2.setEnabled(true);
			this.sexMaleButton2.setEnabled(true);
		}
		else {
			this.nameField3.setText(existingClient.getName());
			this.phoneNumberField2.setText(existingClient.getPhoneNumber());
			
			switch(existingClient.getGender()) {
			case FEMININO:
				this.sexFemButton2.setEnabled(true);
				break;
			case MASCULINO:
				this.sexMaleButton2.setEnabled(true);
				break;
			}
			
			this.nameField3.setEditable(false);
			this.phoneNumberField2.setEditable(false);
			this.sexFemButton2.setEnabled(false);
			this.sexMaleButton2.setEnabled(false);
		}
	}
	
	private void goBack() {
		if(colabEdit != null || apptEdit != null)
			new List(getSys(), getDp(), true);
		else
			new ControlPanel(getSys(), getDp());
		
		this.dispose();
	}
	
	
	public void actionPerformed(ActionEvent e) {
		//Se usuário clicou voltar
		if(e.getSource().equals(goBackButton)) 
			goBack();
		
		
		//Se usuário clicou cadastrar
		else if(e.getSource().equals(registerButton)) {
			//Se usuário clicou cadastrar e o RadioButton de colaborador está selecionado, cadastre colaborador
			if(this.colaboratorButton.isSelected()) {
				
				//Arrays pra armazenar o que o usuário digitou e ArrayLists pra armazenar os valores corretos
				String[] servicesID = this.serviceField.getText().split(" ");
				String[] prices = this.priceField.getText().split(" ");
				ArrayList<Service> servicesArray = null;
				ArrayList<Float> pricesArray = null;
				Gender g = null;
				
				boolean canContinue = true;
				
				//Validar tudo
				try {
					Validation.validateName(this.nameField.getText(), false);
					Validation.validateEmail(this.emailField.getText());
					Validation.validatePhoneNumber(this.phoneNumberField.getText());
					
					servicesArray = Validation.validateIDs(servicesID, this.getSys().getAllServices());
					pricesArray = Validation.validatePrices(servicesID, prices);
					
					if(this.sexMaleButton.isSelected())
						g = Gender.MASCULINO;
					else
						g = Gender.FEMININO;
					
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(this, e1.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
					canContinue = false;
				}
				
				//Tenta adicionar o colaborador
				if(canContinue) {
					try {
						if(colabEdit != null) {			
							for(Appointment a:colabEdit.getAppointments())
								if(!servicesArray.contains(a.getService()) && !a.isCanceled() && !a.isFinished())
									throw new Exception("Não é possível remover o servico " + a.getService().getID()
											+ "\n" + colabEdit.getName() + " possui um agendamento ativo com esse serviço");

							colabEdit.setName(this.nameField.getText());
							colabEdit.setGender(g);
							colabEdit.setPhoneNumber(this.phoneNumberField.getText());
							
							//Remove o colaborador de todos os serviços e o adiciona de volta aos serviços que o usuário digitou
							for(Service s:this.getSys().getAllServices()) {
								if(s.getColaborators().contains(colabEdit))
									s.getColaborators().remove(colabEdit);
							}
							for(Service s:servicesArray) 
								s.getColaborators().add(colabEdit);
							
							colabEdit.setServices(servicesArray);
							colabEdit.setPrices(pricesArray);
							
							
							
							JOptionPane.showMessageDialog(this, "Colaborador editado com sucesso");
						}
						else {
							Colaborator c = new Colaborator(this.nameField.getText(), this.emailField.getText(), g, this.phoneNumberField.getText(), servicesArray, pricesArray);
							for(Service s:servicesArray) 
								s.getColaborators().add(c);
							
							this.getSys().addColaborator(c);
							JOptionPane.showMessageDialog(this, "Colaborador cadastrado com sucesso");
						}
						this.getDp().saveSystem(getSys());
						goBack();
						
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(this, e1.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
					}
					
				}
				
			}
			
			//Se usuário clicou cadastrar e o RadioButton de serviço está selecionado
			else if(this.serviceButton.isSelected()) {
				boolean canContinue = true;
				
				//Validar nome e descrição
				try {
					Validation.validateName(this.nameField2.getText(), false);
					Validation.validateName(this.descriptionField.getText(), true);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(this, e1.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
					canContinue = false;
				}
				
				if(canContinue) {
					Service s = new Service(this.nameField2.getText(), this.descriptionField.getText(), (int) this.durationSpinner.getValue());
					try {
						this.getSys().addService(s);
						this.getDp().saveSystem(getSys());
						JOptionPane.showMessageDialog(this, "Serviço criado com ID " + s.getID());
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(this, e1.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
					}
					
					goBack();
				}
			}

			
			//Se usuário clicou cadastrar e o RadioButton de agendamento está selecionado
			else if(this.appointmentButton.isSelected()) {
				Gender g = null;
				if(this.sexMaleButton2.isSelected())
					g = Gender.MASCULINO;
				else
					g = Gender.FEMININO;

				//Pegar colaborador e serviços escolhidos dos comboBoxes
				Service s = (Service) this.serviceCombo.getSelectedItem();
				Colaborator c = (Colaborator) this.colabCombo.getSelectedItem();
				
				if(c == null)
					JOptionPane.showMessageDialog(this, "Não há colaboradores que podem realizar esse serviço", "Erro", JOptionPane.ERROR_MESSAGE);
			
				
				//Se cliente já existe, use ele. Senão, valide os campos e crie um novo
				Client clt = null;
				
				if(c != null) {
					if(this.existingClient != null) 
						clt = this.existingClient;
					else
						try {
							Validation.validateName(this.nameField3.getText(), false);
							Validation.validateEmail(this.emailField2.getText());
							Validation.validatePhoneNumber(this.phoneNumberField2.getText());
							clt = new Client(this.nameField3.getText(), this.emailField2.getText(), g, this.phoneNumberField2.getText());
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(this, e1.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
						}
					
					if(clt != null) {
						try {
							//Criar data e hora
							LocalDate date = null;
							LocalTime time = null;
							if(this.isFinishedBox.isSelected()) {
								date = Validation.validateDate(this.dateField.getText(), true, false);
								time = Validation.validateTime(this.hourField.getText(), date, true);
							}
							else {
								date = Validation.validateDate(this.dateField.getText(), false, false);
								time = Validation.validateTime(this.hourField.getText(), date, false);
							}			
							
							if(apptEdit != null) {
								Validation.validateAppointment(c, clt, s, date, time, apptEdit);
								
								apptEdit.getColaborator().getAppointments().remove(apptEdit);
								
								apptEdit.setClient(clt);
								apptEdit.setColaborator(c);
								apptEdit.setDate(date);
								apptEdit.setTime(time);
								for(int i = 0; i < c.getServices().size(); i++) 
									if(c.getServices().get(i).getID() == apptEdit.getService().getID()) {
										float formattedMoney = Validation.formatMoney((c.getPrices().get(i) / 60) * s.getAvrgDuration());
										apptEdit.setPrice(formattedMoney);
										break;
									}

								if(this.isFinishedBox.isSelected()) {
									apptEdit.setFinished(true);
									float formattedMoney = Validation.formatMoney(apptEdit.getPrice());
									
									JOptionPane.showMessageDialog(this, "Foram adicionados R$" + formattedMoney + " ao caixa do salão", "Transação", JOptionPane.INFORMATION_MESSAGE);
									this.getSys().getSalonBank().addCash(formattedMoney, null);
									
									c.setPendingPay(Validation.formatMoney((float) (apptEdit.getPrice() * 0.8)));
								}
								else
									apptEdit.setFinished(false);
								
								c.getAppointments().add(apptEdit);
								JOptionPane.showMessageDialog(this, "Agendamento editado com sucesso");
								this.getDp().saveSystem(getSys());
								goBack();
							}
							
							else {
								Validation.validateAppointment(c, clt, s, date, time, null);
								//Criar agendamento
								Appointment appt = new Appointment(c, s, clt, date, time, price);
								c.getAppointments().add(appt);
								clt.getAppointments().add(appt);
								this.getSys().addAppointment(appt);
								this.getDp().saveSystem(getSys());
								
								JOptionPane.showMessageDialog(this, "Agendamento cadastrado com sucesso");
								goBack();
							}
							
						} catch (Exception e1) {
							JOptionPane.showMessageDialog(this, e1.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
						}
					}
				}
				
			} 
		}
		
		
		//Se usuário escolheu um serviço do serviceCombo, mostre os colaboradores dele no colabCombo
		else if(apptEdit == null && e.getSource().equals(this.serviceCombo)) {
			Service s = (Service) serviceCombo.getSelectedItem();
			this.colabCombo.removeAllItems();
			
			for(Colaborator c:s.getColaborators()) 
				if(c.isActive()) 
					this.colabCombo.addItem(c);
		}
		
		//Se usuário escolheu um colaborador do colabCombo, mostre quanto ele cobrará pelo serviço
		else if(e.getSource().equals(this.colabCombo)) {
			Service s = (Service) serviceCombo.getSelectedItem();
			Colaborator c = (Colaborator) colabCombo.getSelectedItem();
			
			if(c != null)
				for(int i = 0; i < c.getServices().size(); i++) 
					if(c.getServices().get(i).getID() == s.getID()) {
						price = Validation.formatMoney((c.getPrices().get(i) / 60) * s.getAvrgDuration());
						break;
					}
			
			priceLabel.setText("Preço: R$" + price);
			
		}
		
		
		//Se usuário clicou os radioButtons do topo
		else if(e.getSource().equals(this.colaboratorButton)) {
			this.setColaboratorFieldsVis(true);
			this.setServiceFieldsVis(false);
			this.setApptFieldsVis(false);
		}
		else if(e.getSource().equals(this.serviceButton)) {
			this.setColaboratorFieldsVis(false);
			this.setServiceFieldsVis(true);
			this.setApptFieldsVis(false);
		}
		else if(e.getSource().equals(this.appointmentButton)) {
			this.setColaboratorFieldsVis(false);
			this.setServiceFieldsVis(false);
			this.setApptFieldsVis(true);
		}
	
	}


	
	
	public void keyTyped(KeyEvent e) {	
	}

	public void keyPressed(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) {
		if(e.getSource().equals(this.emailField2) && e.getKeyCode() == KeyEvent.VK_ENTER) {
			try {
				Validation.validateEmail(this.emailField2.getText());
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(this, e1.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
			}
			
			this.getExistingClient();
			
		}
		
	}


	@Override
	protected void createLabels() {		
	}


	@Override
	protected void createFields() {
	}


	@Override
	protected void createButtons() {
		colaboratorButton = new JRadioButton("Colaborador", true);
		colaboratorButton.setBounds(15, 10, 100, 20);
		colaboratorButton.addActionListener(this);
		this.add(colaboratorButton);
		
		serviceButton = new JRadioButton("Serviço");
		serviceButton.setBounds(155, 10, 100, 20);
		serviceButton.addActionListener(this);
		this.add(serviceButton);
		
		appointmentButton = new JRadioButton("Agendamento");
		appointmentButton.setBounds(265, 10, 120, 20);
		appointmentButton.addActionListener(this);
		this.add(appointmentButton);
		
		goBackButton = new JButton("Voltar");
		goBackButton.setBounds(60, 370, 100, 20);
		goBackButton.addActionListener(this);
		this.add(goBackButton);
		
		registerButton = new JButton("Cadastrar");
		registerButton.setBounds(220, 370, 100, 20);
		registerButton.addActionListener(this);
		this.add(registerButton);
		
		ButtonGroup radioRegister = new ButtonGroup();
		radioRegister.add(colaboratorButton);
		radioRegister.add(serviceButton);
		radioRegister.add(appointmentButton);
	}

}

