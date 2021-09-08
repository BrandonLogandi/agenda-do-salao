package system.operations;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
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
	private static Document doc = new Document(PageSize.A4.rotate());
	
	public static void generateReport(DataSystem sys, LocalDate dateBetween, LocalDate dateAnd, Colaborator colab) throws Exception {
		if(colab != null) {
			if(colab.getBank().getTransactions().isEmpty())
				throw new Exception("Não foi realizada nenhuma transação para esse colaborador");
		}
		
		else if(sys.getSalonBank().getTransactions().isEmpty())
			throw new Exception("Não foi realizada nenhuma transação no caixa do salão");

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
		
		if(colab != null) {
			if(colab.getGender().equals(Gender.MASCULINO))
				addParagraph(s.concat("do colaborador: " + colab.getName()));
			else
				addParagraph(s.concat("da colaboradora: " + colab.getName()));
		}
		else 
			addParagraph(s.concat("do caixa do salão"));
		
		
		addParagraph("Entre " + dateBetween.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + 
				" e " + dateAnd.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		
		addParagraph(" ");
			
		//Se o dia da primeira data for o primeiro dia do mês
		if(dateBetween.getDayOfMonth() == 1) {
			Month m = null;
			int year = 0;
			//Se o mês da primeira data for Janeiro, setar o mês para Dezembro e diminuir o valor do ano em 1
			if(dateBetween.getMonthValue() == 1) {
				m = Month.DECEMBER;
				year = dateBetween.getYear() - 1;
			}
			else {
				m = Month.of(dateBetween.getMonthValue() - 1);
				year = dateBetween.getYear();
			}
			
			System.out.println(Year.isLeap(year));
			dateBetween1 = LocalDate.of(year, m, m.length(Year.isLeap(year)));
		}
		else
			dateBetween1 = LocalDate.of(dateBetween.getYear(), dateBetween.getMonthValue(), dateBetween.getDayOfMonth() - 1);
		
		System.out.println(dateBetween1.getDayOfMonth() + "/" + dateBetween1.getMonthValue() + "/" + dateBetween1.getYear());
		
		
		
		//Se o dia da segunda data for o último dia do mês
		if(dateAnd.getDayOfMonth() == dateAnd.lengthOfMonth()) {
			//Se o mês da segunda data for Dezembro, setar o mês e dia para 1 e aumentar o valor do ano em 1
			if(dateAnd.getMonthValue() == 12)
				dateAnd1 = LocalDate.of(dateAnd.getYear() + 1, Month.JANUARY, 1);
			else
				dateAnd1 = LocalDate.of(dateAnd.getYear(), dateAnd.getMonthValue() + 1, 1);
		}
		else
			dateAnd1 = LocalDate.of(dateAnd.getYear(), dateAnd.getMonthValue(), dateAnd.getDayOfMonth() + 1);
		
		System.out.println(dateAnd1.getDayOfMonth() + "/" + dateAnd1.getMonthValue() + "/" + dateAnd1.getYear());
		
		
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
		
		if(Desktop.isDesktopSupported())
			Desktop.getDesktop().open(new File("relatorio.pdf"));
	}
	
	
	private static void addParagraph(String txt) throws DocumentException {
		Paragraph pg = new Paragraph(txt);
		pg.setAlignment(Element.ALIGN_CENTER);
		doc.add(pg);
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
