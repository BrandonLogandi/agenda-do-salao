package windows.operations.bank;

import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.time.LocalDate;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.text.MaskFormatter;

import people.Colaborator;
import system.operations.DataPersistence;
import system.operations.DataSystem;
import system.operations.Validation;
import system.operations.ReportGeneration;
import windows.DefaultWindow;

public class GenerateReport extends DefaultWindow {
	
	JLabel colabs = new JLabel("Colaborador:");
	
	private JRadioButton salonReportRadio = new JRadioButton("Caixa do salão", true);
	private JRadioButton colabReportRadio = new JRadioButton("Caixa de colaborador");
	private JComboBox<Colaborator> colabCombo = new JComboBox<Colaborator>();
	
	private JFormattedTextField dateBetweenField = new JFormattedTextField();
	private JFormattedTextField dateAndField = new JFormattedTextField();
	
	private JButton goBackButton = new JButton("Voltar");
	private JButton generateReportButton = new JButton("Gerar relatório");

	public GenerateReport(DataSystem sys, DataPersistence dp) {
		super(sys, dp);
		this.setSize(320, 220);
		this.setTitle("Gerar relatório");
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		createLabels();
		createButtons();
		createFields();

		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(goBackButton)) {
			new Bank(getSys(), getDp());
			this.dispose();
		}
		else if(e.getSource().equals(generateReportButton)) {
			LocalDate dateBetween = null;
			LocalDate dateAnd = null;
			Colaborator colab = (Colaborator) this.colabCombo.getSelectedItem();
			
			try {
				dateBetween = Validation.validateDate(this.dateBetweenField.getText(), false, true);
				dateAnd = Validation.validateDate(this.dateAndField.getText(), false, true);
				
				if(dateBetween.isAfter(dateAnd))
					JOptionPane.showMessageDialog(this, "Primeira data não pode após a segunda", "Erro", JOptionPane.ERROR_MESSAGE);
				else {
					if(this.salonReportRadio.isSelected())
						ReportGeneration.generateReport(getSys(), dateBetween, dateAnd, null);
					else
						ReportGeneration.generateReport(getSys(), dateBetween, dateAnd, colab);
					
					JOptionPane.showMessageDialog(this, "Relatório gerado com sucesso", "", JOptionPane.INFORMATION_MESSAGE);
					new Bank(getSys(), getDp());
					this.dispose();
				}
				
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(this, e1.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
			}
			
			
			
		}
		
		
		//Se o usuário clicou os radio buttons do topo
		else if(e.getSource().equals(salonReportRadio)) {
			this.colabCombo.setEnabled(false);
			this.colabs.setEnabled(false);
		}
		else if(e.getSource().equals(colabReportRadio)) {
			this.colabCombo.setEnabled(true);
			this.colabs.setEnabled(true);
		}

	}

	@Override
	protected void createLabels() {
		colabs.setBounds(20, 45, 150, 20);
		colabs.setFont(new Font("Arial", Font.PLAIN, 16));
		this.add(colabs);
		colabs.setEnabled(false);
		
		JLabel between = new JLabel("Entre:");
		between.setBounds(20, 80, 150, 20);
		between.setFont(new Font("Arial", Font.PLAIN, 16));
		this.add(between);
		
		JLabel and = new JLabel("e:");
		and.setBounds(48, 110, 150, 20);
		and.setFont(new Font("Arial", Font.PLAIN, 16));
		this.add(and);
	}

	@Override
	protected void createFields() {
		for(Colaborator c:this.getSys().getAllColaborators())
			colabCombo.addItem(c);
		colabCombo.setBounds(130, 45, 150, 20);
		this.add(colabCombo);
		colabCombo.setEnabled(false);
		
		
		MaskFormatter dateFieldMask = null;
		try {
			dateFieldMask = new MaskFormatter("##/##/####");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		dateBetweenField = new JFormattedTextField(dateFieldMask);
		dateAndField = new JFormattedTextField(dateFieldMask);
		
		dateBetweenField.setBounds(90, 82, 80, 20);
		dateBetweenField.setFont(new Font("Arial", Font.PLAIN, 12));
		this.add(dateBetweenField);
		
		dateAndField.setBounds(90, 112, 80, 20);
		dateAndField.setFont(new Font("Arial", Font.PLAIN, 12));
		this.add(dateAndField);
	}

	@Override
	protected void createButtons() {
		goBackButton.setBounds(20, 150, 100, 20);
		goBackButton.addActionListener(this);
		this.add(goBackButton);
		
		generateReportButton.setBounds(135, 150, 145, 20);
		generateReportButton.addActionListener(this);
		this.add(generateReportButton);
		
		salonReportRadio.setBounds(15, 10, 118, 20);
		salonReportRadio.addActionListener(this);
		this.add(salonReportRadio);
		
		colabReportRadio.setBounds(140, 10, 150, 20);
		colabReportRadio.addActionListener(this);
		this.add(colabReportRadio);
		
		ButtonGroup reportRadioGroup = new ButtonGroup();
		reportRadioGroup.add(colabReportRadio);
		reportRadioGroup.add(salonReportRadio);
	}

}
