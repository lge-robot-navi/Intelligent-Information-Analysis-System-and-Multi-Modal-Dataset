package com.lge.crawling.admin.common.util;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

/**
 * Mail Service
 * @version : 1.0
 * @author :  Copyright (c) 2015 by MIRINCOM CORP. All Rights Reserved.
 */
@Component
public class MailUtil {

	/**
	 * Mail Sender
	 */
	@Autowired
	private JavaMailSender mailSender;

	/**
	 * Send Mime Mail
	 * @method sendMimeMail
	 * @param from
	 * @param to
	 * @param subject
	 * @param text
	 * @throws MessagingException
	 */
	public void sendMimeMail(String from, String to, String subject, String text) throws MessagingException {

		MimeMessage message = mailSender.createMimeMessage();
		message.setSubject(subject);

		MimeMessageHelper helper;
		helper = new MimeMessageHelper(message, true);
		helper.setFrom(from);
		helper.setTo(to);
		helper.setText(text, true);

		mailSender.send(message);
	}

	/**
	 * Send Mime Mail (Muiltiple)
	 * @method sendMimeMail
	 * @param from
	 * @param to
	 * @param subject
	 * @param text
	 * @throws MessagingException
	 */
	public void sendMimeMail(String from, String[] to, String subject, String text) throws MessagingException {

		MimeMessage message = mailSender.createMimeMessage();
		message.setSubject(subject);

		MimeMessageHelper helper;
		helper = new MimeMessageHelper(message, true);
		helper.setFrom(from);
		helper.setTo(to);
		helper.setText(text, true);

		mailSender.send(message);
	}

	/**
	 * Send Mail
	 * @method sendMail
	 * @param from
	 * @param to
	 * @param subject
	 * @param text
	 */
	public void sendMail(String from, String to, String subject, String text) {

		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(from);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);

		mailSender.send(message);
	}

	/**
	 * Send Mail (multiple)
	 * @method sendMail
	 * @param from
	 * @param to
	 * @param subject
	 * @param text
	 */
	public void sendMail(String from, String[] to, String subject, String text) {

		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(from);
		message.setTo(to);
		message.setSubject(subject);
		message.setText(text);

		mailSender.send(message);
	}
}
