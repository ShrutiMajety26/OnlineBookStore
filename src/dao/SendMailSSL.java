package dao;

import javax.mail.*;
import javax.mail.internet.*;

import java.math.BigInteger;
import java.util.Properties;

public class SendMailSSL 
{
	String host, port, emailid, username;
	Properties props = System.getProperties();
	Session l_session = null;
	BigInteger password;
	public SendMailSSL() 
	{
		host = "smtp.gmail.com";
		port = "587"; // 587
		emailid = "671books@gmail.com";
		username = "671books@gmail.com"; // type your own user name
		password = new BigInteger("35181369483934127328036233399953694957192506062498905629280703156245705447669668453526299919933922252588555355157"); // type your own password
		emailSettings();
		createSession();
	}

	public void emailSettings() {
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.auth", "true");
		props.put("mail.debug", "false");
		props.put("mail.smtps.ssl.enable", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.port", port);
		props.put("mail.smtp.starttls.enable","true");
	}
	RSA rsa=new RSA(1024);
	public void createSession()
	{

		l_session = Session.getInstance(props, new javax.mail.Authenticator() 
		{
			@Override
			protected PasswordAuthentication getPasswordAuthentication() 
			{
				//BigInteger  plaintext = rsa.decrypt(password);
			  //  String text2 = new String(plaintext.toByteArray());			   
				return new PasswordAuthentication(username, "BooksForever");
			}
		});

		l_session.setDebug(true); // Enable the debug mode
	}

	public boolean sendMessage(String emailFromUser, String toEmail,String subject, String msg) 
	{	
		try 
		{		
			MimeMessage message = new MimeMessage(l_session);
			emailid = emailFromUser;
			message.setFrom(new InternetAddress(this.emailid));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
			message.setSubject(subject);
			message.setContent(msg, "text/html");
			Transport.send(message);
			System.out.println("Message Sent");
		} 
		catch (MessagingException mex) 
		{
			mex.printStackTrace();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}// end catch block
		return true;
	}

	public static void main(String[] args)	
	{				
		String msg = "<table cellspacing=10><tr><th colspan=3>Order deatils</th></tr>"
				+ "<tr><td><b>Item</b></td><td><b>Qty</b></td><td><b>Price</b></td></tr>"
				+ "<tr><td>The Liar</td><td>2</td><td>&#36;32</td></tr>"
				+ "<tr><td>Database System concepts</td><td>12</td><td>&#36;80</td></tr>"
				+ "<tr><td colspan=2><b>Total</b></td><td><b>&#36;112</b></td></tr>"
				+"<tr><td colspan=2><b>Status</b></td><td>Paid</td></tr>"
				+ "</table>";
		new SendMailSSL().sendMessage("671books@gmail.com","somya_5666@yahoo.co.in", "Invoice Details", msg);
	}
}