package windows.operations;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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

	//Botões em comum
	private JRadioButton colaboratorButton;
	private JRadioButton serviceButton;
	private JRadioButton appointmentButton;
	private JButton cadastrarButton;
	private JButton voltarButton;
	
	//Colaboradores
	private JLabel nome = new JLabel("Nome ");
	private JLabel email = new JLabel("Email ");
	private JLabel tel = new JLabel("Telefone ");
	private JLabel sexo = new JLabel("Sexo ");
	private JRadioButton sexMaleButton = new JRadioButton("Masculino", true);
	private JRadioButton sexFemButton = new JRadioButton("Feminino");
	private JLabel servicos = new JLabel("Serviços ");
	private JLabel precos = new JLabel("Preços ");
	private JLabel servTip = new JLabel("IDs dos serviços separados por espaço");
	private JLabel prcTip = new JLabel("Preços dos serviços separados por espaço");
	
	private JTextField nomeField = new JTextField();
	private JTextField emailField = new JTextField();
	private JFormattedTextField telField = new JFormattedTextField();
	private JTextField servField = new JTextField();
	private JTextField prcField = new JTextField();
	
	//Servicos
	private JLabel nome2 = new JLabel("Nome ");
	private JLabel desc = new JLabel("Descrição ");
	private JLabel dur = new JLabel("Dur. média ");
	private JLabel min = new JLabel("minutos");
	private JSpinner durSpin = new JSpinner(new SpinnerNumberModel(1, 1, 1440, 1));
	
	private JTextField nomeField2 = new JTextField();
	private JTextField descField = new JTextField();
	
	//Agendamentos
	private JLabel nome3 = new JLabel("Nome");
	private JLabel email2 = new JLabel("Email");
	private JLabel emailTip = new JLabel("Tecle Enter para confirmar o email");
	private JLabel tel2 = new JLabel("Telefone");
	private JLabel sexo2 = new JLabel("Sexo");
	private JLabel svc = new JLabel("Serviço");
	private JLabel col = new JLabel("Colaborador");
	private JLabel data = new JLabel("Data");
	private JLabel hora = new JLabel("Hora");
	
	private JLabel preco = new JLabel("Preço: R$");
	
	private JTextField nomeField3 = new JTextField();
	private JTextField emailField2 = new JTextField();
	private JFormattedTextField telField2 = new JFormattedTextField();
	private JFormattedTextField dateField = new JFormattedTextField();
	private JFormattedTextField hourField = new JFormattedTextField();
	private JRadioButton sexMaleButton2 = new JRadioButton("Masculino", true);
	private JRadioButton sexFemButton2 = new JRadioButton("Feminino");
	
	private JComboBox<Service> serviceCombo = new JComboBox<>();
	private JComboBox<Colaborator> colabCombo = new JComboBox<>();
	
	private JCheckBox isFinishedBox = new JCheckBox("Concluído");
	
	private Client existingClient = null;
	
	private float price = 0;
	



	public Register(DataSystem sys, DataPersistence dp, Colaborator colabEdit, Appointment apptEdit) {
		super(sys, dp);
		
		this.colabEdit = colabEdit;
		this.apptEdit = apptEdit;

		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setSize(400, 450);
		this.setTitle("Cadastrar");
		
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
		nome.setBounds(20, 45, 150, 40);
		nome.setFont(new Font("Arial", Font.PLAIN, 16));
		this.add(nome);
		
		email.setBounds(20, 85, 150, 40);
		email.setFont(new Font("Arial", Font.PLAIN, 16));
		this.add(email);
		
		tel.setBounds(20, 125, 150, 40);
		tel.setFont(new Font("Arial", Font.PLAIN, 16));
		this.add(tel);
		
		sexo.setBounds(20, 165, 150, 40);
		sexo.setFont(new Font("Arial", Font.PLAIN, 16));
		this.add(sexo);
		
		servicos.setBounds(20, 205, 150, 40);
		servicos.setFont(new Font("Arial", Font.PLAIN, 16));
		this.add(servicos);
		
		precos.setBounds(20, 245, 150, 40);
		precos.setFont(new Font("Arial", Font.PLAIN, 16));
		this.add(precos);
		
		
		
		nomeField.setBounds(100, 55, 220, 20);
		this.add(nomeField);
		emailField.setBounds(100, 95, 220, 20);
		this.add(emailField);
		
		MaskFormatter telFieldMask = null;
		try {
			telFieldMask = new MaskFormatter("(##)#####-####");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		telField = new JFormattedTextField(telFieldMask);
		telField.setBounds(100, 135, 220, 20);
		this.add(telField);
		
		
		sexMaleButton.setBounds(100, 173, 100, 20);
		this.add(sexMaleButton);
		
		sexFemButton.setBounds(200, 173, 100, 20);
		this.add(sexFemButton);
		
		servField.setBounds(100, 215, 220, 20);
		this.add(servField);
		
		servTip.setFont(new Font("Arial", Font.ITALIC, 10));
		servTip.setBounds(120, 230, 220, 20);
		this.add(servTip);
		
		prcField.setBounds(100, 255, 220, 20);
		this.add(prcField);
		
		prcTip.setFont(new Font("Arial", Font.ITALIC, 10));
		prcTip.setBounds(110, 270, 220, 20);
		this.add(prcTip);
		
		ButtonGroup radioSexo = new ButtonGroup();
		radioSexo.add(sexMaleButton);
		radioSexo.add(sexFemButton);
		
		
		if(colabEdit != null)  {
			this.setTitle("Editando colaborador");
			this.cadastrarButton.setText("Salvar");
			
			this.colaboratorButton.setVisible(false);
			this.serviceButton.setVisible(false);
			this.appointmentButton.setVisible(false);
			
			nomeField.setText(colabEdit.getName());
			emailField.setText(colabEdit.getEmail());
			emailField.setEditable(false);
			telField.setText(colabEdit.getPhoneNumber());
			
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
				
			servField.setText(svcIDs);
			prcField.setText(svcPrices);
			
			
		}
		
	}

	private void serviceFields() {
		nome2.setBounds(40, 130, 150, 40);
		nome2.setFont(new Font("Arial", Font.PLAIN, 16));
		this.add(nome2);
		
		desc.setBounds(40, 170, 150, 40);
		desc.setFont(new Font("Arial", Font.PLAIN, 16));
		this.add(desc);
		
		dur.setBounds(40, 210, 150, 40);
		dur.setFont(new Font("Arial", Font.PLAIN, 16));
		this.add(dur);
		
		min.setBounds(180, 210, 150, 40);
		min.setFont(new Font("Arial", Font.ITALIC, 14));
		this.add(min);
		
		durSpin.setBounds(130, 220, 40, 20);
		this.add(durSpin);
		
		nomeField2.setBounds(130, 140, 190, 20);
		this.add(nomeField2);
		
		descField.setBounds(130, 180, 190, 20);
		this.add(descField);
		
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
			preco.setText("Preço: R$" + price);	
		}

		
		nome3.setBounds(20, 45, 150, 40);
		nome3.setFont(new Font("Arial", Font.PLAIN, 16));
		this.add(nome3);
		
		email2.setBounds(20, 85, 150, 40);
		email2.setFont(new Font("Arial", Font.PLAIN, 16));
		this.add(email2);
		
		emailTip.setFont(new Font("Arial", Font.ITALIC, 10));
		emailTip.setBounds(125, 110, 220, 20);
		this.add(emailTip);
		
		tel2.setBounds(20, 125, 150, 40);
		tel2.setFont(new Font("Arial", Font.PLAIN, 16));
		this.add(tel2);
		
		sexo2.setBounds(20, 165, 150, 40);
		sexo2.setFont(new Font("Arial", Font.PLAIN, 16));
		this.add(sexo2);
		
		svc.setBounds(20, 205, 150, 40);
		svc.setFont(new Font("Arial", Font.PLAIN, 16));
		this.add(svc);
		
		col.setBounds(20, 245, 150, 40);
		col.setFont(new Font("Arial", Font.PLAIN, 16));
		this.add(col);
		
		data.setBounds(20, 285, 150, 40);
		data.setFont(new Font("Arial", Font.PLAIN, 16));
		this.add(data);
		
		hora.setBounds(20, 325, 150, 40);
		hora.setFont(new Font("Arial", Font.PLAIN, 16));
		this.add(hora);
		
		preco.setBounds(280, 228, 150, 40);
		preco.setFont(new Font("Arial", Font.PLAIN, 14));
		this.add(preco);
		
		
		nomeField3.setBounds(100, 55, 220, 20);
		nomeField3.setFont(new Font("Arial", Font.PLAIN, 16));
		this.add(nomeField3);
		
		emailField2.setBounds(100, 95, 220, 20);
		emailField2.setFont(new Font("Arial", Font.PLAIN, 16));
		emailField2.addKeyListener(this);
		this.add(emailField2);
		
		MaskFormatter telFieldMask = null;
		try {
			telFieldMask = new MaskFormatter("(##)#####-####");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		telField2 = new JFormattedTextField(telFieldMask);
		telField2.setBounds(100, 135, 220, 20);
		this.add(telField2);
		
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
			this.cadastrarButton.setText("Salvar");
			
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
			
			this.nomeField3.setText(apptEdit.getClient().getName());
			this.emailField2.setText(apptEdit.getClient().getEmail());
			this.telField2.setText(apptEdit.getClient().getTel());
			
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
			this.nomeField3.setEditable(false);
			this.telField2.setEditable(false);
			this.sexFemButton2.setEnabled(false);
			this.sexMaleButton2.setEnabled(false);
			
		}

	}
	
	
	
	
	
	//Esconder os campos
	private void setColaboratorFieldsVis(boolean b) {
		nome.setVisible(b);
		email.setVisible(b);
		tel.setVisible(b);
		sexo.setVisible(b);
		sexMaleButton.setVisible(b);
		sexFemButton.setVisible(b);
		servicos.setVisible(b);
		precos.setVisible(b);
		
		nomeField.setVisible(b);
		emailField.setVisible(b);
		telField.setVisible(b);
		servField.setVisible(b);
		prcField.setVisible(b);
		servTip.setVisible(b);
		prcTip.setVisible(b);
	}
	
	private void setServiceFieldsVis(boolean b) {
		nome2.setVisible(b);
		desc.setVisible(b);
		dur.setVisible(b);
		min.setVisible(b);
		durSpin.setVisible(b);
		
		nomeField2.setVisible(b);
		descField.setVisible(b);
	}
	
	private void setApptFieldsVis(boolean b) {
		nome3.setVisible(b);
		email2.setVisible(b);
		emailTip.setVisible(b);
		tel2.setVisible(b);
		sexo2.setVisible(b);
		svc.setVisible(b);
		col.setVisible(b);
		data.setVisible(b);
		hora.setVisible(b);
		
		preco.setVisible(b);
		
		nomeField3.setVisible(b);
		emailField2.setVisible(b);
		telField2.setVisible(b);
		dateField.setVisible(b);
		hourField.setVisible(b);
		sexMaleButton2.setVisible(b);
		sexFemButton2.setVisible(b);
		
		serviceCombo.setVisible(b);
		colabCombo.setVisible(b);
	}
	
	

	private void getExistingClient() {
		this.existingClient = this.getSys().getClient(this.emailField2.getText());
		
		if(existingClient == null) {
			JOptionPane.showMessageDialog(this, "Cliente não encontrado", "", JOptionPane.INFORMATION_MESSAGE);
			this.nomeField3.setEditable(true);
			this.telField2.setEditable(true);
			this.sexFemButton2.setEnabled(true);
			this.sexMaleButton2.setEnabled(true);
		}
		else {
			this.nomeField3.setText(existingClient.getName());
			this.telField2.setText(existingClient.getTel());
			
			switch(existingClient.getGender()) {
			case FEMININO:
				this.sexFemButton2.setEnabled(true);
				break;
			case MASCULINO:
				this.sexMaleButton2.setEnabled(true);
				break;
			}
			
			this.nomeField3.setEditable(false);
			this.telField2.setEditable(false);
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
		if(e.getSource().equals(voltarButton)) 
			goBack();
		
		
		//Se usuário clicou cadastrar
		else if(e.getSource().equals(cadastrarButton)) {
			//Se usuário clicou cadastrar e o RadioButton de colaborador está selecionado, cadastre colaborador
			if(this.colaboratorButton.isSelected()) {
				
				//Arrays pra armazenar o que o usuário digitou e ArrayLists pra armazenar os valores corretos
				String[] servicesID = this.servField.getText().split(" ");
				String[] prices = this.prcField.getText().split(" ");
				ArrayList<Service> servicesArray = null;
				ArrayList<Float> pricesArray = null;
				Gender g = null;
				
				boolean canContinue = true;
				
				//Validar tudo
				try {
					Validation.validateName(this.nomeField.getText(), false);
					Validation.validateEmail(this.emailField.getText());
					Validation.validatePhoneNumber(this.telField.getText());
					
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
								if(!servicesArray.contains(a.getService()))
									throw new Exception("Não é possível remover o servico " + a.getService().getID()
											+ "\n" + colabEdit.getName() + " possui um agendamento ativo com esse serviço");

							colabEdit.setName(this.nomeField.getText());
							colabEdit.setGender(g);
							colabEdit.setPhoneNumber(this.telField.getText());
							
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
							Colaborator c = new Colaborator(this.nomeField.getText(), this.emailField.getText(), g, this.telField.getText(), servicesArray, pricesArray);
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
					Validation.validateName(this.nomeField2.getText(), false);
					Validation.validateName(this.descField.getText(), true);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(this, e1.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
					canContinue = false;
				}
				
				if(canContinue) {
					Service s = new Service(this.nomeField2.getText(), this.descField.getText(), (int) this.durSpin.getValue());
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
							Validation.validateName(this.nomeField3.getText(), false);
							Validation.validateEmail(this.emailField2.getText());
							Validation.validatePhoneNumber(this.telField2.getText());
							clt = new Client(this.nomeField3.getText(), this.emailField2.getText(), g, this.telField2.getText());
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
									this.getSys().getSalloonBank().addCash(formattedMoney, null);
									
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
			
			preco.setText("Preço: R$" + price);
			
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
		
		voltarButton = new JButton("Voltar");
		voltarButton.setBounds(60, 370, 100, 20);
		voltarButton.addActionListener(this);
		this.add(voltarButton);
		
		cadastrarButton = new JButton("Cadastrar");
		cadastrarButton.setBounds(220, 370, 100, 20);
		cadastrarButton.addActionListener(this);
		this.add(cadastrarButton);
		
		ButtonGroup radioRegister = new ButtonGroup();
		radioRegister.add(colaboratorButton);
		radioRegister.add(serviceButton);
		radioRegister.add(appointmentButton);
	}

}

