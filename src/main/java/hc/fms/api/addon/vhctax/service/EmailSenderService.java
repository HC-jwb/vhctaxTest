package hc.fms.api.addon.vhctax.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import hc.fms.api.addon.vhctax.model.EmailMessage;

@Service
public class EmailSenderService {
	@Autowired
	private JavaMailSender mailSender;
	public boolean sendMail(EmailMessage message) {
		SimpleMailMessage mailMsg = new SimpleMailMessage();
		mailMsg.setSubject(message.getSubject());
		mailMsg.setTo(message.getToAddress());
		mailMsg.setText(message.getBody());
		mailSender.send(mailMsg);
		return true;
	}
}
