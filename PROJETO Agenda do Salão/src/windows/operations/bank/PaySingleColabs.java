package windows.operations.bank;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import people.Colaborator;
import system.operations.DataPersistence;
import system.operations.DataSystem;
import system.operations.SendEmail;
import windows.DefaultWindow;

public class PaySingleColabs extends DefaultWindow implements MouseListener {
	private JLabel total = new JLabel("A pagar: R$");
	private float totalPay = 0;
	
	private JButton voltarButton = new JButton("Voltar");
	private JButton pagarButton = new JButton("Pagar"); 
	
	private JList<Colaborator> colabList = new JList<>();
	private DefaultListModel<Colaborator> colabModel =  new DefaultListModel<>();
	private JScrollPane colabScroll;

	public PaySingleColabs(DataSystem sys, DataPersistence dp) {
		super(sys, dp);
		this.setSize(400, 450);
		this.setTitle("Pagando colaboradores");
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		int i = 0;
		this.colabModel.removeAllElements();
		for(Colaborator c : this.getSys().getAllColaborators()) {
			if(c.getPendingPay() > 0) {
				this.colabModel.addElement(c);
				i++;
			}
				
		}
		
		if(i > 0) {
			createLabels();
			createButtons();
			createFields();
			
			
			this.setLocationRelativeTo(null);
			this.setVisible(true);
		}
		else {
			new Bank(sys, dp);
			this.dispose();
		}
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(this.voltarButton)) {
			new Bank(getSys(), getDp());
			this.dispose();
		}
		else if(e.getSource().equals(this.pagarButton)) {
			this.dispose();
			if(this.colabList.getSelectedValue() == null)
				JOptionPane.showMessageDialog(this, "Você deve selecionar o colaborador a pagar", "Erro", JOptionPane.ERROR_MESSAGE);
			else {
				Colaborator c = this.colabList.getSelectedValue();
				
				switch (JOptionPane.showConfirmDialog(this, 
						"Você está prestes a pagar R$" + this.totalPay + " a " + c.getName(), 
						"Tem certeza?", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE)) {
				case JOptionPane.YES_OPTION:
					this.getSys().getSalloonBank().removeCash(totalPay, c);
					c.getBank().addCash(totalPay, c);
					c.setPendingPay(0);
					
					SendEmail.sendToColab(this.getSys().getAdmin(), c, "Pagamento recebido",
							"Prezado(a) " + c.getName() + ":"
							+ "\nForam depositados R$" + totalPay + " em sua conta");
					
					JOptionPane.showMessageDialog(this, c.getName() + " foi pago"
							+ "\nSobram R$" + this.getSys().getSalloonBank().getCash() + " no caixa do salão", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
					this.getDp().saveSystem(getSys());
				}
			}
			
			new PaySingleColabs(getSys(), getDp());
					
		}
		

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource().equals(colabList)) {
			if(LocalDate.now().getDayOfMonth() != LocalDate.now().lengthOfMonth())
				this.totalPay = (float) (colabList.getSelectedValue().getPendingPay() * 0.9);
			else
				this.totalPay = colabList.getSelectedValue().getPendingPay();
			
			this.total.setText("A pagar: R$".concat(Float.toString(this.totalPay)));
		}
	}

	
	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	
	@Override
	protected void createLabels() {
		this.total.setBounds(120, 310, 150, 50);
		this.total.setFont(new Font("Arial", Font.PLAIN, 16));
		this.add(total);
	}

	@Override
	protected void createFields() {
		
		
		this.colabList.setModel(colabModel);
		this.colabList.addMouseListener(this);
		this.colabList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		colabScroll = new JScrollPane(colabList);
		colabScroll.setBounds(40, 40, 300, 280);
		colabScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.add(colabScroll);
	}

	@Override
	protected void createButtons() {
		voltarButton.setBounds(60, 370, 100, 20);
		voltarButton.addActionListener(this);
		this.add(voltarButton);
		
		pagarButton.setBounds(220, 370, 100, 20);
		pagarButton.addActionListener(this);
		this.add(pagarButton);
	}

}
