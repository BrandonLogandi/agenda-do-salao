package windows.operations.bank;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import people.Colaborator;
import system.operations.DataPersistence;
import system.operations.DataSystem;
import system.operations.SendEmail;
import windows.DefaultWindow;
import windows.operations.ControlPanel;

public class Bank extends DefaultWindow {
	
	private Font arialBold10 = new Font("Arial", Font.BOLD, 10);
	
	private JLabel moneyInDaBank = new JLabel("Saldo: R$" + this.getSys().getSalonBank().getCash());
	private JLabel pendingPay = new JLabel();
	private float pendingPayTotal = 0;
	private JButton goBackButton = new JButton("Voltar");
	private JButton payButton = new JButton("Pagar colaborador");
	private JButton payAllButton = new JButton("Pagar todos");
	private JButton reportButton = new JButton("Gerar relatório");

	public Bank(DataSystem sys, DataPersistence dp) {
		super(sys, dp);
		this.setSize(535, 220);
		this.setTitle("Caixa");
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		createLabels();
		createButtons();
		createFields();

		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}


	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(this.goBackButton)) {
			new ControlPanel(getSys(), getDp());
			this.dispose();
		}
		
		else if(e.getSource().equals(payButton)) {
			this.dispose();
			if(LocalDate.now().getDayOfMonth() != LocalDate.now().lengthOfMonth())
				JOptionPane.showMessageDialog(this, "Lembre-se de que não estamos no último dia do mês."
						+ "\nPortanto, você estará adiantando o pagamento dos colaboradores, o que quer dizer"
						+ "\nque eles terão uma redução de 10% em seus pagamentos.", "Aviso", JOptionPane.WARNING_MESSAGE);
			
			new PaySingleColabs(getSys(), getDp());
		}
		
		else if(e.getSource().equals(payAllButton)) {
			this.dispose();
			int opt = JOptionPane.showOptionDialog(this, "Tem certeza de que quer pagar todos os colaboradores?"
					+ "\nApós essa operação, sobrarão R$" + (this.getSys().getSalonBank().getCash() - pendingPayTotal) + " no caixa", 
					"Tem certeza?", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
			
			if(opt == JOptionPane.YES_OPTION) {
				for(Colaborator c:this.getSys().getAllColaborators()) {
					if(c.getPendingPay() != 0) {
						float totalPay = c.getPendingPay();
						this.getSys().getSalonBank().removeCash(totalPay, c);
						c.getBank().addCash(totalPay, c);
						c.setPendingPay(0);
						
						if(this.getSys().getAdmin().getGmailAccount() != null)
							SendEmail.sendToColab(this.getSys().getAdmin(), c, "Pagamento recebido",
									"Prezado(a) " + c.getName() + ":"
									+ "\n Foram depositados R$" + totalPay + " em sua conta");
						
						this.getDp().saveSystem(getSys());
					}
				}
			}
			
			new Bank(getSys(), getDp());
		}
		
		else if(e.getSource().equals(this.reportButton)) {
			new GenerateReport(getSys(), getDp());
			this.dispose();
		}

	}



	protected void createLabels() {
		JLabel bank = new JLabel("Caixa");
		bank.setBounds(240, 0, 180, 40);
		bank.setFont(new Font("Arial Black", Font.PLAIN, 16));
		this.add(bank);
		
		moneyInDaBank.setBounds(20, 45, 180, 40);
		moneyInDaBank.setFont(new Font("Arial", Font.PLAIN, 16));
		this.add(moneyInDaBank);
		
		for(Colaborator c:this.getSys().getAllColaborators())
			pendingPayTotal += c.getPendingPay();
		
		pendingPay.setText("A pagar: R$" + pendingPayTotal);
		pendingPay.setBounds(20, 70, 180, 40);
		pendingPay.setFont(new Font("Arial", Font.PLAIN, 16));
		this.add(pendingPay);
	}


	protected void createFields() {
		
	}


	protected void createButtons() {
		goBackButton.setBounds(20, 140, 90, 20);
		goBackButton.setFont(arialBold10);
		goBackButton.addActionListener(this);
		this.add(goBackButton);
		
		payButton.setBounds(120, 140, 150, 20);
		payButton.setFont(arialBold10);
		payButton.addActionListener(this);
		this.add(payButton);
		
		payAllButton.setBounds(280, 140, 100, 20);
		payAllButton.setFont(arialBold10);
		payAllButton.addActionListener(this);
		
		if(LocalDate.now().getDayOfMonth() != LocalDate.now().lengthOfMonth())
			payAllButton.setEnabled(false);
		if(pendingPayTotal == 0) {
			payButton.setEnabled(false);
			payAllButton.setEnabled(false);
		}
		
		this.add(payAllButton);
		
		reportButton.setBounds(390, 140, 110, 20);
		reportButton.setFont(arialBold10);
		reportButton.addActionListener(this);
		this.add(reportButton);
	}

}
