package com.Optimus_Poc.util;

//set CLASSPATH=%CLASSPATH%;activation.jar;mail.jar

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;

public class SendMail

{
	public static Properties CONFIG;
	public static void execute(String reportFileName) throws Exception

	{

		Zip.zip(System.getProperty("user.dir") + "/"
				+ ReportUtil.resultFolderName, reportFileName);
		String path = System.getProperty("user.dir") + "/" + reportFileName;
		System.out.println(path);

		String body = "Hello Everyone,\n\n" +
				"Please find the attached Automation Test Results reports.  \nPlease let me know if you need more information." +
				"\n\nThanks\nOptimus QA Team";
		String[] to = {"ashish.pandey@optimusinfo.com"}; //update the to , cc and bcc
		String[] cc = {};
		String[] bcc = {};

		// This is for google

		SendMail.sendMail("optimus.jenkins01@gmail.com",
				"optimus@123",
				"smtp.gmail.com",
				"465", 
				"true",
				"true",
				true,
				"javax.net.ssl.SSLSocketFactory",
				"false",
				to,
				cc,
				bcc,
				"Optimus_Poc Test Reports",
				body,
				path, 
				"TestReport.zip");

	}

	public static boolean sendMail(String userName,
			String passWord,
			String host, 
			String port, 
			String starttls,
			String auth,
			boolean debug,
			String socketFactoryClass, 
			String fallback,
			String[] to,
			String[] cc,
			String[] bcc,
			String subject,
			String text,
			String attachmentPath,
			String attachmentName) {

		Properties props = new Properties();

		// Properties props=System.getProperties();

		props.put("mail.smtp.user", userName);

		props.put("mail.smtp.host", host);

		if (!"".equals(port))

			props.put("mail.smtp.port", port);

		if (!"".equals(starttls))

			props.put("mail.smtp.starttls.enable", starttls);

		props.put("mail.smtp.auth", auth);
		// props.put("mail.smtps.auth", "true");

		if (debug) {

			props.put("mail.smtp.debug", "true");

		} else {

			props.put("mail.smtp.debug", "false");

		}

		if (!"".equals(port))

			props.put("mail.smtp.socketFactory.port", port);

		if (!"".equals(socketFactoryClass))

			props.put("mail.smtp.socketFactory.class", socketFactoryClass);

		if (!"".equals(fallback))

			props.put("mail.smtp.socketFactory.fallback", fallback);

		try

		{

			Session session = Session.getDefaultInstance(props, null);

			session.setDebug(debug);

			 MimeMessage message = new MimeMessage(session);
			// Set Subject: header field
			message.setSubject(subject);

			// Create the message part
			MimeBodyPart messageBodyPart = new MimeBodyPart();

			// Fill the message
			messageBodyPart.setText(text);

			// Create a multipart message
			Multipart multipart = new MimeMultipart();

			// Set text message part
			multipart.addBodyPart(messageBodyPart);

			// Part two is attachment
			 messageBodyPart = new MimeBodyPart();
		
			DataSource source = new FileDataSource(attachmentPath);
			messageBodyPart.setDataHandler(new DataHandler(source));
			
			messageBodyPart.setFileName(attachmentName);
			multipart.addBodyPart(messageBodyPart);

			// Send the complete message parts
			message.setContent(multipart);
			
			
		message.setFrom(new InternetAddress("202.56.215.55"));
			
			// Set To: header field of the header.
			 for(int i=0;i<to.length;i++){
					
				 message.addRecipient(Message.RecipientType.TO, new
					 InternetAddress(to[i]));
					
					 }
					
					 for(int i=0;i<cc.length;i++){
					
						 message.addRecipient(Message.RecipientType.CC, new
					 InternetAddress(cc[i]));
					
					 }
					
					 for(int i=0;i<bcc.length;i++){
					
						 message.addRecipient(Message.RecipientType.BCC, new
					 InternetAddress(bcc[i]));
					
					 }
					
					 message.saveChanges();


			// Send message
			//Transport.send(message);
			System.out.println("Sent message successfully....");
//
			Transport transport = session.getTransport("smtp");
//
			transport.connect(host, userName, passWord);
//
			transport.sendMessage(message, message.getAllRecipients());
//
			transport.close();

			return true;

		}

		catch (Exception mex)

		{

			mex.printStackTrace();

			return false;

		}

	}

}