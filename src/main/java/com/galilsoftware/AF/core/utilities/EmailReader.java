package com.galilsoftware.AF.core.utilities; 

import java.util.ArrayList;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;

import com.galilsoftware.AF.core.logging.SelTestLog;

/**
 * This Class is for reading E-mails.
 * @author Amir Najjar
 *
 */
public class EmailReader {

	private final String protocol = "mail.store.protocol";
	private final String imaps = "imaps";

	private String username;
	private String password;
	private String imapServer;
	private long pollingInterval = 3000;
	private int pollingTimes = 5;



	public EmailReader(String username ,String password ,String  imapServer) {
		this.username = username;
		this.password = password;
		this.imapServer = imapServer;
	}

	public ArrayList<String> getConfirmationLink(String[] innerWords) throws Exception {


		boolean foundEmailFlag = false;

		Properties props = new Properties();
		props.setProperty(protocol,imaps);

		try {
			Message[] msgs = openSession(props);
			ArrayList<String> links = null;
			for (Message msg : msgs) {

				Object content = msg.getContent();
				foundEmailFlag = true;
				for(String innerWord : innerWords){
					if (content.toString().contains(innerWord)) {
						links = pullLinks(content.toString());
					}else{
						foundEmailFlag = false;
						break;
					}
					if(foundEmailFlag)
						return links;
				}
			}

			if (!foundEmailFlag) {
				System.err.println("Did not get Email with inner words :" + innerWords[0]+" ...etc" );
				return null;
			}

		} catch (Exception mex) {
			mex.printStackTrace();
		}

		

		return null;
	}

	public Message getEmail(String[] innerWords) throws Exception {
		for(int i=1 ; i<= pollingTimes ; i++){
			
			SelTestLog.info("Fetching e-mail ...");
			
			boolean foundEmailFlag = false;

			Properties props = new Properties();
			props.setProperty(protocol,imaps);

			try {
				Message[] msgs = openSession(props);
				for (Message msg : msgs) {
					Object content = msg.getContent();
					foundEmailFlag = true;
					for(String innerWord : innerWords){
						if (!content.toString().contains(innerWord)) {
							foundEmailFlag = false;
							break;
						}
					}
					if(foundEmailFlag)
						return msg;
				}

				if (!foundEmailFlag) {
					System.err.println("Did not get Email with inner words :" + innerWords[0]+" ...etc" );
				}

			} catch (Exception mex) {
				mex.printStackTrace();
			}
			WebdriverUtils.sleep(pollingInterval);
			SelTestLog.info("Couldn't find e-mail retrying for the "+i+" time ...");
		}
		return null;
	}

	private Message[] openSession(Properties props) throws MessagingException{
		Session session = Session.getInstance(props, null);
		Store store = session.getStore();
		store.connect(imapServer, username, password);
		Folder inbox = store.getFolder("INBOX");
		inbox.open(Folder.READ_WRITE);
		Message[] msgs = new Message[inbox.getMessageCount()];
		msgs = inbox.getMessages();
		return msgs;
	}

	public ArrayList<String> pullLinks(String text) {
		ArrayList<String> links = new ArrayList<String>();

		String regex = "\\b(https?)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(text);
		while (m.find()) {
			String urlStr = m.group();
			if (urlStr.startsWith("(") && urlStr.endsWith(")")) {
				urlStr = urlStr.substring(1, urlStr.length() - 1);
			}
			links.add(urlStr);
		}
		return links;
	}

	public boolean emptyMailbox(){
		Properties props = new Properties();
		props.setProperty(protocol,imaps);

		try {
			Message[] msgs = openSession(props);
			for (Message msg : msgs) {
				msg.setFlag(Flags.Flag.DELETED, true);
			}

			msgs = openSession(props);

			if (msgs.length !=0) {
				System.err.println("Error emptying mailbox, mailbox returned :" + msgs.length+" emails" );
				return false;
			}
		} catch (Exception mex) {
			mex.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	
	
	public Message getEmailByRecepients(String[] recipients) throws Exception {
		String lastRecipient = recipients[recipients.length-1];
		for(int i=1 ; i<= pollingTimes ; i++){
			
			SelTestLog.info("Fetching e-mail by recipients ...");
			
			boolean foundEmailFlag = false;

			Properties props = new Properties();
			props.setProperty(protocol,imaps);

			try {
				Message[] msgs = openSession(props);
				for (Message msg : msgs) {
					Address[]  address = msg.getAllRecipients();
					foundEmailFlag =  false;
					for(String recipient : recipients){
						for(int j=0; j<address.length; j++){
							if (address[j].toString().equalsIgnoreCase(recipient)) {
								foundEmailFlag = true;
								break;
							}
						}
					if(foundEmailFlag){
						if(recipient.equalsIgnoreCase(lastRecipient))
							return msg;
						foundEmailFlag = false;
						continue;
					}
					break;
				
					}
				}
				if (!foundEmailFlag) {
					return null;
					//System.err.println("Did not get Email with recipients :" + recipients[0]+" ...etc" );
				}

			} catch (Exception mex) {
				mex.printStackTrace();
			}
			WebdriverUtils.sleep(pollingInterval);
			SelTestLog.info("Couldn't find e-mail retrying for the "+i+" time ...");
		}
		return null;
	}
	
	

	public void deleteAllEmailsByFrom(String from) throws Exception {
		
		SelTestLog.info("Fetching e-mail by from ...");
		Properties props = new Properties();
		props.setProperty(protocol,imaps);

		try {
			Message[] msgs = openSession(props);
			for (Message msg : msgs) {
				Address[]  fromAddr = msg.getFrom();
				for(int j=0; j<fromAddr.length; j++){
					if (fromAddr[j].toString().equalsIgnoreCase(from)){
						msg.setFlag(Flags.Flag.DELETED, true);
						break;
					}
				}
						
			}

		}
		catch(Exception e){
			return;
		}
	
	}
}