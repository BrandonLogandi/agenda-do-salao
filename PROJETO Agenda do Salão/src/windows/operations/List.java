package windows.operations;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.ParseException;
import java.time.LocalDate;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListDataListener;
import javax.swing.text.MaskFormatter;

import people.Client;
import people.Colaborator;
import system.Appointment;
import system.Service;
import system.exceptions.InvalidDateException;
import system.operations.DataPersistence;
import system.operations.DataSystem;
import system.operations.SendEmail;
import system.operations.Validation;
import windows.DefaultWindow;
import windows.login.Login;

public class List extends DefaultWindow implements MouseListener, KeyListener {
	
	private boolean isLoggedIn;
	
	//Botões em comum
	private JRadioButton colaboratorButton;
	private JRadioButton serviceButton;
	private JRadioButton appointmentButton;
	private JButton goBackButton = new JButton("Voltar");
	private JButton editButton = new JButton("Editar"); 
	
	//Colaboradores
	private JButton disableButton = new JButton("Desativar"); 
	
	private JList<Colaborator> colabList = new JList<>();
	private DefaultListModel<Colaborator> colabModel =  new DefaultListModel<>();
	private JScrollPane colabScroll;
	
	//Serviços
	private JButton copyIDButton = new JButton("Copiar ID"); 
	
	private JList<Service> servList = new JList<>();
	private DefaultListModel<Service> servModel =  new DefaultListModel<>();
	private JScrollPane servScroll;
	
	//Agendamentos
	private JButton cancelButton = new JButton("Cancelar"); 
	
	private JList<Appointment> apptList = new JList<>();
	private DefaultListModel<Appointment> apptModel =  new DefaultListModel<>();
	private JScrollPane apptScroll;
	
	private JLabel clientLabel;
	private JLabel dateLabel;
	
	private JComboBox<Client> clientCombo = new JComboBox<>();
	private JFormattedTextField dateField = new JFormattedTextField();
	private JCheckBox dateTodayBox = new JCheckBox("Hoje", false);
	
	
	

	public List(DataSystem sys, DataPersistence dp, boolean isLoggedIn) {
		super(sys, dp);
		this.setSize(400, 450);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.isLoggedIn = isLoggedIn;
		
		if(isLoggedIn)
			this.setTitle("Listar");
		else
			this.setTitle("Listar por data");
		
		createLabels();
		createFields();
		createButtons();
		
		this.setLocationRelativeTo(null);
		this.setVisible(true);

	}
	
	private void validateInputDate() {
		try {
			LocalDate ld = Validation.validateDate(this.dateField.getText(), true, false);
			
			this.apptModel.removeAllElements();
			for(Appointment a:this.getSys().getAllAppointments()) 
				if(a.getDate().isEqual(ld))
					this.apptModel.addElement(a);
			
		} catch (InvalidDateException e1) {
			e1.printStackTrace();
		}
	}
	
	
	private void colabFields() {
		for(int i = 0; i < this.getSys().getAllColaborators().size(); i++) {
			this.colabModel.add(i, this.getSys().getAllColaborators().get(i));
		}
		this.colabList.setModel(colabModel);
		this.colabList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.colabList.addMouseListener(this);
		
		colabScroll = new JScrollPane(colabList);
		colabScroll.setBounds(40, 40, 300, 280);
		colabScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.add(colabScroll);
		
		disableButton.setBounds(250, 370, 100, 20);
		disableButton.addActionListener(this);
		this.add(disableButton);
	}
	
	private void servFields() {
		for(int i = 0; i < getSys().getAllServices().size(); i++) 
			this.servModel.add(i, this.getSys().getAllServices().get(i));
		
		this.servList.setModel(servModel);
		this.servList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		copyIDButton.setBounds(220, 370, 100, 20);
		copyIDButton.addActionListener(this);
		this.add(copyIDButton);
		
		servScroll = new JScrollPane(servList);
		servScroll.setBounds(40, 40, 300, 280);
		servScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.add(servScroll);

	}
	
	private void apptFields() {
		MaskFormatter dateFieldMask = null;
		try {
			dateFieldMask = new MaskFormatter("##/##/####");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		dateField = new JFormattedTextField(dateFieldMask);
		dateField.setFont(new Font("Arial", Font.PLAIN, 12));
		dateField.addKeyListener(this);
		this.add(dateField);
		
		dateTodayBox.setFont(new Font("Arial", Font.PLAIN, 12));
		dateTodayBox.addActionListener(this);
		this.add(dateTodayBox);
		
		this.apptList.setModel(apptModel);
		this.apptList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		apptScroll = new JScrollPane(apptList);
		apptScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.add(apptScroll);
		
		if(isLoggedIn) {
			dateField.setBounds(125, 70, 120, 20);
			dateTodayBox.setBounds(255, 70, 120, 20);
			apptScroll.setBounds(40, 100, 300, 240);
			
			for(Appointment a:this.getSys().getAllAppointments()) 
				this.apptModel.addElement(a);
			
			this.clientCombo.addItem(new Client("Todos", "", null, null));
			for(Client c:this.getSys().getAllClients())
				this.clientCombo.addItem(c);
			
			clientCombo.setBounds(110, 40, 210, 20);
			clientCombo.setFont(new Font("Arial", Font.PLAIN, 12));
			clientCombo.addActionListener(this);
			this.add(clientCombo);
		}
		else {
			dateField.setBounds(125, 20, 120, 20);
			dateTodayBox.setBounds(255, 20, 120, 20);
			apptScroll.setBounds(40, 50, 300, 310);
		}
		
	}

	
	private void setColabFieldsVis(boolean b) {
		colabScroll.setVisible(b);
		disableButton.setVisible(b);
		
		if(b) {
			goBackButton.setBounds(30, 370, 100, 20);
		}

	}
	
	private void setServiceFieldsVis(boolean b) {
		servScroll.setVisible(b);
		copyIDButton.setVisible(b);
		
		if(b) {
			goBackButton.setBounds(55, 370, 100, 20);
		}

	}
	
	private void setApptFieldsVis(boolean b) {
		apptScroll.setVisible(b);

		clientLabel.setVisible(b);
		dateLabel.setVisible(b);
		
		clientCombo.setVisible(b);
		dateField.setVisible(b);
		dateTodayBox.setVisible(b);
		
		cancelButton.setVisible(b);
		
		if(b) {
			goBackButton.setBounds(30, 370, 100, 20);
		}

	}
	
	
	
	public void actionPerformed(ActionEvent e) {
		//Se usuário clicou voltar
			if(e.getSource().equals(goBackButton)) {
				this.dispose();
				if(isLoggedIn)
					new ControlPanel(getSys(), getDp());
				else
					new Login(getSys(), getDp(), false);	
			}
			
			//Se o usuário clicou editar
			else if(e.getSource().equals(editButton)) {
				//e o radio de colaboradores está selecionado
				if(this.colaboratorButton.isSelected()) {
					if(this.colabList.getSelectedIndex() == -1) 
						JOptionPane.showMessageDialog(this, "Você deve selecionar o colaborador a ser editado", "Erro", JOptionPane.ERROR_MESSAGE);
					else {
						new Register(getSys(), getDp(), this.colabList.getSelectedValue(), null);
						this.dispose();
					}
					
					
				}
				//e o radio de agendamentos está selecionado
				else if(this.appointmentButton.isSelected()) {
					
					if(this.apptList.getSelectedIndex() == -1) 
						JOptionPane.showMessageDialog(this, "Você deve selecionar o agendamento a ser editado", "Erro", JOptionPane.ERROR_MESSAGE);
					else if (this.apptList.getSelectedValue().isCanceled())
						JOptionPane.showMessageDialog(this, "Não é possível editar agendamentos cancelados", "Erro", JOptionPane.ERROR_MESSAGE);
					else if (this.apptList.getSelectedValue().isFinished())
						JOptionPane.showMessageDialog(this, "Não é possível editar agendamentos concluídos", "Erro", JOptionPane.ERROR_MESSAGE);
					
					else {
						new Register(getSys(), getDp(), null, this.apptList.getSelectedValue());
						this.dispose();
					}
				}
			}
			
			else if(e.getSource().equals(disableButton)) {
				Colaborator c = this.colabList.getSelectedValue();
				
				if(c == null)
					JOptionPane.showMessageDialog(this, "Você deve selecionar o colaborador a ser desativado", "Erro", JOptionPane.ERROR_MESSAGE);
				else {
					this.dispose();
					if(c.isActive()) {
						switch (JOptionPane.showConfirmDialog(this,
								"Tem certeza de que quer desativar " + c.getName() + "?"
										+ "\nSe " + c.getName() + " possuir agendamentos ativos, eles serão cancelados", "Tem certeza?"
										, JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE)) {
						
										case JOptionPane.YES_OPTION:
											c.setActive(false);
											for(Appointment a:c.getAppointments()) {
												if(!a.isFinished() && !a.isCanceled())
													a.setCanceled(true);
												SendEmail.sendToClient(this.getSys().getAdmin(), a.getClient(),
														"Caro(a) " + a.getClient().getName() + ", seu agendamento foi cancelado",
														"Caro(a) " + a.getClient().getName() + ", \n\nLamentamos informar que o seguinte agendamento foi cancelado: "
														+ "\n" + a.getService().getName()
														+ "\nData " + a.getDate() + " " + a.getTime());
												
												this.getDp().saveSystem(getSys());
											}
						}

					}
					else {
						c.setActive(true);
						JOptionPane.showMessageDialog(this, c.getName() + " foi reativado", "", JOptionPane.INFORMATION_MESSAGE);
					}

						new List(getSys(), getDp(), true);
				}
				
			}
			
			
			else if(e.getSource().equals(copyIDButton)) {
				if(this.servList.getSelectedValue() == null) 
					JOptionPane.showMessageDialog(this, "Você deve selecionar o serviço a ser copiado", "Erro", JOptionPane.ERROR_MESSAGE);
				else {
					String IDtoString = String.valueOf(this.servList.getSelectedValue().getID());
					StringSelection stringSelection = new StringSelection(IDtoString);
					Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
					clipboard.setContents(stringSelection, null);
					JOptionPane.showMessageDialog(this, "ID copiado para a área de transferência", "", JOptionPane.INFORMATION_MESSAGE);
				}
			}
			
			else if(e.getSource().equals(cancelButton)) {
				Appointment a = this.apptList.getSelectedValue();
				
				if(a == null)
					JOptionPane.showMessageDialog(this, "Você deve selecionar o agendamento a ser cancelado", "Erro", JOptionPane.ERROR_MESSAGE);
				else {
					if(a.isCanceled())
						JOptionPane.showMessageDialog(this, "Esse agendamento já foi cancelado", "Erro", JOptionPane.ERROR_MESSAGE);
					else if(a.isFinished())
						JOptionPane.showMessageDialog(this, "Não é possível cancelar agendamentos que foram concluídos", "Erro", JOptionPane.ERROR_MESSAGE);
					else {
						this.dispose();
						switch (JOptionPane.showConfirmDialog(this,
								"Tem certeza de que quer cancelar esse agendamento?", "Tem certeza?"
								, JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE)) {
						
								case JOptionPane.YES_OPTION:
									a.setCanceled(true);
									SendEmail.sendToClient(this.getSys().getAdmin(), a.getClient(),
											"Caro(a) " + a.getClient().getName() + ", seu agendamento foi cancelado",
											"Caro(a) " + a.getClient().getName() + ", \n\nLamentamos informar que o seguinte agendamento foi cancelado: "
											+ "\n" + a.getService().getName()
											+ "\n Data " + a.getDate() + " " + a.getTime());
									
									this.getDp().saveSystem(getSys());
									
						}
						
						new List(getSys(), getDp(), true);
					
					}
					
				}
				
			}
			
			else if(e.getSource().equals(this.clientCombo)) {
				Client cl = (Client) this.clientCombo.getSelectedItem();
				apptModel.removeAllElements();
				
				if(cl.getName().equals("Todos")) 
					for(int i = 0; i < this.getSys().getAllAppointments().size(); i++) 
						apptModel.addElement(this.getSys().getAllAppointments().get(i));
				
				else
					for(int i = 0; i < cl.getAppointments().size(); i++) 
						apptModel.addElement(cl.getAppointments().get(i));
			
			}
			
			else if(e.getSource().equals(this.dateTodayBox)) {
				if(this.dateTodayBox.isSelected()) {
					LocalDate ld = LocalDate.now();
					//Converter dia, mês e ano
					String dayValue = "";
					String monthValue = "";
					String yearValue = String.valueOf(ld.getYear());
					
					if(ld.getDayOfMonth() < 10) 
						dayValue = "0" + ld.getDayOfMonth();
					else
						dayValue = String.valueOf(ld.getDayOfMonth());
					
					if(ld.getMonthValue() < 10) 
						monthValue = "0" + ld.getMonthValue();
					else
						monthValue = String.valueOf(ld.getMonthValue());

					this.apptModel.removeAllElements();
					this.dateField.setText(dayValue + "/" + monthValue + "/" + yearValue);
					this.dateField.setEnabled(false);
					validateInputDate();
				}
				else {
					this.dateField.setEnabled(true);
					this.dateField.setText("");
					
					this.apptModel.removeAllElements();
					if(isLoggedIn) {
						for(Appointment a:this.getSys().getAllAppointments()) 
							this.apptModel.addElement(a);
					}
				}
				
			}
	
			
			
		//Se usuário clicou os radioButtons do topo
		else if(e.getSource().equals(this.colaboratorButton)) {
				this.setColabFieldsVis(true);
				this.setServiceFieldsVis(false);
				this.setApptFieldsVis(false);
				
				editButton.setVisible(true);
			}
			else if(e.getSource().equals(this.serviceButton)) {
				this.setColabFieldsVis(false);
				this.setServiceFieldsVis(true);
				this.setApptFieldsVis(false);
				
				editButton.setVisible(false);
			}
			else if(e.getSource().equals(this.appointmentButton)) {
				this.setColabFieldsVis(false);
				this.setServiceFieldsVis(false);
				this.setApptFieldsVis(true);
				
				editButton.setVisible(true);
			}

	}


	public void mouseClicked(MouseEvent e) {
		if(e.getSource().equals(this.colabList))
			if(!this.colabList.getSelectedValue().isActive())
				this.disableButton.setText("Ativar");
			else
				this.disableButton.setText("Desativar");
		
	}


	public void mousePressed(MouseEvent e) {	
	}


	public void mouseReleased(MouseEvent e) {
	}


	public void mouseEntered(MouseEvent e) {
	}


	public void mouseExited(MouseEvent e) {
	}


	protected void createLabels() {
		clientLabel = new JLabel("Cliente");
		dateLabel = new JLabel("Data");
		
		dateLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		this.add(dateLabel);
		
		if(isLoggedIn) {
			clientLabel.setBounds(50, 38, 190, 20);
			clientLabel.setFont(new Font("Arial", Font.PLAIN, 16));
			this.add(clientLabel);
			
			dateLabel.setBounds(75, 68, 190, 20);
		}
		else 
			dateLabel.setBounds(75, 18, 190, 20);
	}


	protected void createFields() {
		if(isLoggedIn) {
			this.colabFields();
			this.setColabFieldsVis(true);
			this.servFields();
			this.setServiceFieldsVis(false);
			this.apptFields();
			this.setApptFieldsVis(false);
		}
		else {
			this.apptFields();
			this.setApptFieldsVis(true);
		}
		
	}


	protected void createButtons() {
		goBackButton.addActionListener(this);
		this.add(goBackButton);
		
		if(isLoggedIn) {
			colaboratorButton = new JRadioButton("Colaboradores", true);
			colaboratorButton.setBounds(15, 10, 110, 20);
			colaboratorButton.addActionListener(this);
			this.add(colaboratorButton);
			
			serviceButton = new JRadioButton("Serviços");
			serviceButton.setBounds(150, 10, 100, 20);
			serviceButton.addActionListener(this);
			this.add(serviceButton);
			
			appointmentButton = new JRadioButton("Agendamentos");
			appointmentButton.setBounds(255, 10, 120, 20);
			appointmentButton.addActionListener(this);
			this.add(appointmentButton);
			
			editButton.setBounds(140, 370, 100, 20);
			editButton.addActionListener(this);
			this.add(editButton);
			
			ButtonGroup radioRegister = new ButtonGroup();
			radioRegister.add(colaboratorButton);
			radioRegister.add(serviceButton);
			radioRegister.add(appointmentButton);
			
			cancelButton.setBounds(250, 370, 100, 20);
			cancelButton.addActionListener(this);
			this.add(cancelButton);
			
		}
		else
			goBackButton.setBounds(90, 370, 200, 20);
	}


	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getSource().equals(this.dateField) && e.getKeyCode() == KeyEvent.VK_ENTER) {
			validateInputDate();
		}
		
	}


	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
