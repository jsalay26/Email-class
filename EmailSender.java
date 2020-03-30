package com.ccbf.automation.c2c2_211_Remittance_advices.utils;

import org.apache.poi.hsmf.exceptions.ChunkNotFoundException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMultipart;
import javax.mail.PasswordAuthentication;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import org.apache.poi.hsmf.MAPIMessage;
import javax.mail.MessagingException;
import javax.mail.Authenticator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.Multipart;
import java.util.Properties;
import javax.mail.Transport;
import java.io.IOException;
import javax.mail.Session;
import javax.mail.Message;
import javax.mail.Address;
import jline.internal.Log;

import java.util.List;

/**
 * <h1>Description:</h1> The mailing class gives the option to include an
 * attachment in the body when is necessary otherwise just send mail.
 * 
 * @author JSALAY
 * @since 26/03/2020
 */

public class EmailSender {

	private String emailFrom;
	private String emailPassword;
	private String emailTo;
	private String pathMessage;
	private String customer;
	private String account_Number;
	private String invoice_Number;
	private String check_Number;
	private String[] properties;
	private List<String> listFiles;


	/**
	 * @param emailFrom: inbound email.
	 * @param emailPassword:inbox email password.
	 * @param emailTo: who is the mail for.
	 * @param pathFileBody: .msg path.
	 * @param customer: customer name.
	 * @param account_Number: customer account number.
	 * @param invoice_Number:invoice number.
	 * @param check_Number: check number.
	 * @param properties:Configurations by properties to our message for sending.
	 * @param list:list of files that will be included in the mail.
	 * 
	 * <h1>Description:</h1> The send email object is created which includes
	 * a list for the files to be included in the mail.
	 * @author JSALAY
	 * @since 26/03/2020
	 */

	public EmailSender(String emailFrom, String emailPassword, String emailTo, String pathMessage, String customer,
			String account_Number, String invoice_Number, String check_Number, String[] properties, List<String> list) {

		this.emailFrom = emailFrom;
		this.emailPassword = emailPassword;
		this.emailTo = emailTo;
		this.pathMessage = pathMessage;
		this.customer = customer;
		this.account_Number = account_Number;
		this.invoice_Number = invoice_Number;
		this.check_Number = check_Number;
		this.properties = properties;
		this.listFiles = list;
		
		
	}

	/**
	 * Validating that the emailFrom !=null and that ends in ".cocacolaflorida.com
	 * 
	 * @param String regexRule
	 */

	public Boolean containsCocaCola(String regexRule) {
		if (this.emailFrom == null) {
			Log.error("[containsCocaCola]: emailfrom parameter has no values and is equal to null");
			return false;
			
		} else {
			Pattern pattern = Pattern.compile(regexRule);
			Matcher mather = pattern.matcher(this.emailFrom);

			if (!mather.find()) {
				Log.error("[containsCocaCola]: Parameter emailFrom invalid need to end in .cocacolaflorida");
				return false;
			} else {
				return true;
			}
		}
	}

	/**
	 * Validating that the password is not empty
	 * 
	 */

	public Boolean isnotEmptyPasswords() {
		if (this.emailPassword == null) {
			Log.error("[isnotEmptyPasswords]: The password parameter has no values");
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Validating that emailTo !=null and that it is a valid email
	 * 
	 * @param regexMailFrom
	 */

	public Boolean isnotEmptyEmailFrom(String regexMailFrom) {
		if (this.emailTo == null) {
			Log.error("[isnotEmptyEmailFrom]: emailTo parameter has no values is equal to null");
			return false;
		} else {
			Pattern pattern = Pattern.compile(regexMailFrom);

			Matcher mather = pattern.matcher(this.emailTo);

			if (!mather.find()) {
				Log.error("[isnotEmptyEmailFrom]: emailTo no valid, error to send email");
				return false;
			} else {
				return true;
			}
		}
	}

	/**
	 * Validating if pathFile is equal to null.
	 */

	public Boolean isnotEmptypathMessage() {
		if (this.pathMessage == null) {
			Log.error("[isnotEmptypathMessage]: pathMessage parameter has no values is equal to null");
			return false;
		} else {
			return true;
		}

	}

	/**
	 * Validating if Customer is equal to null
	 */

	public Boolean isnotEmptyCustomer() {
		if (this.customer == null) {
			Log.error("[isnotEmptyCustomer]: Customer parameter has no values is equal to null");
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Validating if Account number is equal to null
	 */

	public Boolean isnotEmptyAccountNumber() {
		if (this.account_Number == null) {
			Log.error("[isnotEmptyAccountNumber]: Account_Number parameter has no values is equal to null");
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Validating if Invoice Number is equal to null.
	 */

	public Boolean isnotEmptyInvoiceNumber() {
		if (this.invoice_Number == null) {
			Log.error("[isnotEmptyInvoiceNumber]: Invoice_Number parameter has no values is equal to null");
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Validating if Check Number is equal to null
	 */

	public Boolean isnotEmptyCheckNumber() {
		if (this.check_Number == null) {
			Log.error("[isnotEmptyCheckNumber]: Check_Number parameter has no values is equal to null");
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Validating if Properties is equal to null.
	 */

	public Boolean isnotEmptyProperties() {
		if (this.properties == null) {
			Log.error("[isnotEmptyProperties]: Properties parameter has no values is equal to null");
			return false;
		} else if (this.properties.length < 3) {
			Log.error("[isnotEmptyProperties]: Properties parameter has no enough values");
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Validating ig ListFiles is equal to null.
	 */

	public Boolean isnotEmptyList() {
		if (this.listFiles == null) {
			Log.error("[isnotEmptyList]: ListFiles parameter has no values is equal to null");
			return false;
		} else {
			return true;
		}
	}

	/**
	 * <h1>Description:</h1> Start the session that'll be used in constructing our
	 * message for sending.
	 * 
	 * @return emailFrom: inbound email.
	 * @return emailPassword: inbox email password.
	 * @throws ChunkNotFoundException, IOException.
	 * @author JSALAY
	 * @since 25/03/2020
	 */

	public void sendEmail() throws IOException, ChunkNotFoundException {
		Log.info("[sendEmail]: logging in outlook mail");
		final Session session = Session.getInstance(this.getEmailProperties(), new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(emailFrom, emailPassword);

			}

		});

		/**
		 * <h1>Description:</h1> the mail message is created in parts indicating the emailfrom, emailto, subject and cc.
		 * 
		 * @throws ChunkNotFoundException, IOException.
		 * @author JSALAY
		 * @since 25/03/2020
		 */
		
		try {
			final Message message = new MimeMessage(session);
			Log.info("[Message]: creating the components of the .msg");
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(this.emailTo));
			message.setFrom(new InternetAddress(this.emailFrom));
			message.setSubject(readMSGOutLook(this.pathMessage)[1]);
			
			String[] addressString = readCC(this.pathMessage);
			Address[] addressA = new Address[addressString.length];

			for (int i = 0; i < addressString.length; i++) {
				addressA[i] = new InternetAddress(addressString[i]);
			}

			message.setRecipients(Message.RecipientType.CC, addressA);
			
			Log.info("[Message]: define the content on .html");
			Multipart emailContent = new MimeMultipart();
			MimeBodyPart textBodyPart = new MimeBodyPart();
			textBodyPart.setContent(readMSGOutLook(this.pathMessage)[0], "text/html");
			emailContent.addBodyPart(textBodyPart);
			Log.info("[Message]: if there is a list attach it to mail body");
			
			for (String attPath : this.listFiles) {
				MimeBodyPart pdfAttachment = new MimeBodyPart();
				try {
					pdfAttachment.attachFile(attPath);
				} catch (IOException exception) {
					Log.error("[Message][Exception]:" + exception.getMessage() + "[Cause]:" + exception.getCause());
				}
				emailContent.addBodyPart(pdfAttachment);
			}

			message.setContent(emailContent);

			Transport.send(message);
			Log.info("[email]: Mail successfully sent");

		} catch (final MessagingException exception) {
			Log.error("[Message][Exception]:" + exception.getMessage() + "[Cause]:" + exception.getCause());
			throw new RuntimeException(exception);
		}

	}

	/**
	 * <h1>Description:</h1> Configurations by properties to our message for
	 * sending.
	 * @author JSALAY
	 * @since 26/03/2020
	 */

	public Properties getEmailProperties() {

		Properties props = new Properties();
		props.put("mail.smtp.host", this.properties[0]);
		props.put("mail.smtp.port", this.properties[1]);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.ssl.trust", this.properties[2]);
		props.put("mail.smtp.emailpassword", this.emailPassword);

		return props;
	}

	/**
	 * Method that include the subject and the body into the .msg
	 * 
	 * @param pathMessage
	 * @author JSALAY
	 * @since 26/03/2020
	 */

	@SuppressWarnings("resource")
	public String[] readMSGOutLook(String pathMessage) {

		String[] content = new String[2];
		MAPIMessage msg = null;

		try {
			msg = new MAPIMessage(pathMessage);
		} catch (IOException exception) {
			Log.error("[Message][Exception]:" + exception.getMessage() + "[Cause]:" + exception.getCause());
			throw new RuntimeException(exception);

		}
		String body = "";
		try {
			body = msg.getTextBody().toString();
		} catch (ChunkNotFoundException exception) {
			Log.error("[Message][Exception]:" + exception.getMessage() + "[Cause]:" + exception.getCause());
			throw new RuntimeException(exception);
		}
		String bodyStr = new String();

		bodyStr = body.toString();
		bodyStr = bodyStr.replace("@@ClientName@@", this.customer);
		bodyStr = bodyStr.replace("@@CheckNumber@@", this.check_Number);

		content[0] = bodyStr;

		String subject = "";
		try {
			subject = msg.getSubject();
		} catch (ChunkNotFoundException exception) {
			Log.error("[Message][Exception]:" + exception.getMessage() + "[Cause]:" + exception.getCause());
			throw new RuntimeException(exception);
		}
		String subjectStr = new String();

		subjectStr = subject.toString();
		subjectStr = subjectStr.replace("@@AccountNumber@@", this.account_Number);
		subjectStr = subjectStr.replace("@@InvoiceNumber@@", this.invoice_Number);
		subjectStr = subjectStr.replace("@@CheckNumber@@", this.check_Number);

		content[1] = subjectStr;

		return content;

	}

	/**
	 * Method that include a list of emailaddress into the .msg
	 * 
	 * @param pathMessage
	 * @author JSALAY
	 * @since 26/03/2020
	 */

	@SuppressWarnings("resource")
	public String[] readCC(String pathMessage) {

		MAPIMessage msg = null;
		String[] ccAddress = null;

		try {
			msg = new MAPIMessage(pathMessage);
		} catch (IOException exception) {
			Log.error("[Message][Exception]:" + exception.getMessage() + "[Cause]:" + exception.getCause());
			throw new RuntimeException(exception);
		}

		try {
			ccAddress = msg.getRecipientEmailAddressList();

		} catch (ChunkNotFoundException exception) {
			Log.error("[Message][Exception]:" + exception.getMessage() + "[Cause]:" + exception.getCause());
			throw new RuntimeException(exception);

		} finally {
			ccAddress = new String[0];
		}

		return ccAddress;
	}

}
