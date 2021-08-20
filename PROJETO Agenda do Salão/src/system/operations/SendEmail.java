package system.operations;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;

import people.Administrator;
import people.Client;
import people.Colaborator;
import system.GmailAccount;

public abstract class SendEmail {
	
	private static void sendEmail(GmailAccount gm, String email, String sbjct, String msg) {
		Properties props = new Properties();
		
		props.put("mail.smtp.user", email);	//Email do destinatário
        props.put("mail.smtp.host", "smtp.gmail.com"); 
        props.put("mail.smtp.port", "25"); 
        props.put("mail.debug", "true"); 
        props.put("mail.smtp.auth", "true"); 
        props.put("mail.smtp.starttls.enable","true"); 
        props.put("mail.smtp.EnableSSL.enable","true");

        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");   
        props.setProperty("mail.smtp.socketFactory.fallback", "false");   
        props.setProperty("mail.smtp.port", "465");   
        props.setProperty("mail.smtp.socketFactory.port", "465");
        
        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
        	protected PasswordAuthentication getPasswordAuthentication() {
        		return new PasswordAuthentication(gm.getEmail(), gm.getPassword());
        	}
        });
        
        session.setDebug(false);
        
        try {
        	Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(gm.getEmail()));
			
			Address[] toUser = InternetAddress.parse(email);
			
			message.setRecipients(Message.RecipientType.TO, toUser);
			message.setSubject(sbjct);
			message.setText(msg);
			Transport.send(message);
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			JOptionPane.showMessageDialog(null, "Acesso à conta Google foi bloqueado"
					+ "\nO acesso de apps menos seguros deve ser ativado em https://myaccount.google.com/lesssecureapps", "Erro", JOptionPane.ERROR_MESSAGE);
			Desktop dsktp = Desktop.getDesktop();
			try {
				dsktp.browse(new URI("https://myaccount.google.com/lesssecureapps"));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (URISyntaxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	
	
	public static void sendToColab(Administrator adm, Colaborator col, String sbjct, String msg) {
		sendEmail(adm.getGmailAccount(), col.getEmail(), sbjct, msg);
	}
	
	public static void sendToClient(Administrator adm, Client cl, String sbjct, String msg) {
		sendEmail(adm.getGmailAccount(), cl.getEmail(), sbjct, msg);
	}
	
	public static void sendToOther(Administrator adm, String email, String sbjct, String msg) {
		sendEmail(adm.getGmailAccount(), email, sbjct, msg);
	}

}
