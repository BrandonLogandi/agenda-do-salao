package system.operations;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import people.Colaborator;
import people.Gender;
import system.bank.Transaction;

public abstract class ReportGeneration {
	
	private static LocalDate dateBetween1 = null;
	private static LocalDate dateAnd1 = null;
	private static PdfPTable table = null;
	
	public static void generateReport(DataSystem sys, LocalDate dateBetween, LocalDate dateAnd, Colaborator colab) throws Exception {
		if(colab != null) {
			if(colab.getBank().getTransactions().isEmpty())
				throw new Exception("Não foi realizada nenhuma transação para esse colaborador");
		}
		
		else if(sys.getSalonBank().getTransactions().isEmpty())
			throw new Exception("Não foi realizada nenhuma transação no caixa do salão");

		Document doc = new Document(PageSize.A4.rotate());
		PdfPCell tipo = new PdfPCell(new Paragraph("Tipo"));
		PdfPCell transacao = new PdfPCell(new Paragraph("Transação"));
		PdfPCell data = new PdfPCell(new Paragraph("Data"));
		PdfPCell recebedor = new PdfPCell(new Paragraph("Recebedor"));
		tipo.setColspan(1);
		transacao.setColspan(1);
		data.setColspan(1);
		recebedor.setColspan(1);
		
		
		if(colab == null)
			table = new PdfPTable(4);
		else
			table = new PdfPTable(3);

		table.addCell(tipo);
		table.addCell(transacao);
		table.addCell(data);
		if(colab == null)
			table.addCell(recebedor);
		

		OutputStream os = new FileOutputStream("relatorio.pdf");
		PdfWriter.getInstance(doc, os);
		doc.open();

		String s = "Relatório de transações ";
		Paragraph pg = new Paragraph();
		
		if(colab != null) {
			if(colab.getGender().equals(Gender.MASCULINO))
				pg = new Paragraph(s.concat("do colaborador: " + colab.getName()));
			else
				pg = new Paragraph(s.concat("da colaboradora: " + colab.getName()));
		}
		else 
			pg = new Paragraph(s.concat("do caixa do salão"));

		pg.setAlignment(Element.ALIGN_CENTER);
		doc.add(pg);
		
		
		pg = new Paragraph("Entre " + dateBetween.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + 
				" e " + dateAnd.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		pg.setAlignment(Element.ALIGN_CENTER);
		doc.add(pg);
		
		pg = new Paragraph(" ");
		pg.setAlignment(Element.ALIGN_CENTER);
		doc.add(pg);
			
		if(dateBetween.getDayOfMonth() == 1)
			dateBetween1 = LocalDate.of(dateBetween.getYear(), dateBetween.getMonthValue() - 1, dateBetween.getMonth().length(LocalDate.now().isLeapYear()));
		else
			dateBetween1 = LocalDate.of(dateBetween.getYear(), dateBetween.getMonthValue(), dateBetween.getDayOfMonth() - 1);
		
		if(dateAnd.getDayOfMonth() == dateAnd.lengthOfMonth())
			dateAnd1 = LocalDate.of(dateAnd.getYear(), dateAnd.getMonthValue() + 1, 1);
		else
			dateAnd1 = LocalDate.of(dateAnd.getYear(), dateAnd.getMonthValue() + 1, dateAnd.getDayOfMonth());
		
		
		if(colab != null) {
			for(Transaction t:colab.getBank().getTransactions()) 
				if(t.getDate().toLocalDate().isBefore(dateAnd1) && t.getDate().toLocalDate().isAfter(dateBetween1)) 
					addInfoToTable(t);
		}
		
		else {
			for(Transaction t:sys.getSalonBank().getTransactions()) {
				if(t.getDate().toLocalDate().isBefore(dateAnd1) && t.getDate().toLocalDate().isAfter(dateBetween1)) {
					addInfoToTable(t);
					
					if(t.getReceiver() != null)
						table.addCell(t.getReceiver().getName());
					else
						table.addCell("---");
				}
			}
		}
		
		doc.add(table);
		doc.close();
		
		Desktop desktop = Desktop.getDesktop();
		desktop.open(new File("relatorio.pdf"));
	}
	
	
	private static void addInfoToTable(Transaction t) {
			if(t.getMoney() < 0)
				table.addCell("Retirada");
			else
				table.addCell("Depósito");
			
			table.addCell("R$" + String.valueOf(t.getMoney()));
			table.addCell(t.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
	}

}
