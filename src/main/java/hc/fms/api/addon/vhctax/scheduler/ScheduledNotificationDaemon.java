package hc.fms.api.addon.vhctax.scheduler;

import java.text.ParseException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import hc.fms.api.addon.vhctax.entity.VehicleTaxTask;
import hc.fms.api.addon.vhctax.model.EmailMessage;
import hc.fms.api.addon.vhctax.repository.VehicleTaxPaymentTaskRepository;
import hc.fms.api.addon.vhctax.service.EmailSenderService;

@Component
public class ScheduledNotificationDaemon {
	private Logger logger = LoggerFactory.getLogger(ScheduledNotificationDaemon.class);
	@Autowired
	private VehicleTaxPaymentTaskRepository vhcTaskRepository;
	@Autowired
	private EmailSenderService emailSender;
	@Scheduled(cron="0 0 9-18 * * MON-FRI")
	public void checkAndfireNotification() {
		//System.out.println("CRON executed " + new Date());
		List<VehicleTaxTask> notificationEntries = vhcTaskRepository.findAllTaskForNotification();
		String emailTo;
		for(VehicleTaxTask task: notificationEntries) {
			/*to, subject, body*/
			emailTo = task.getNotificationEmail();
			if(emailTo!= null && !"".equals(emailTo.trim())) {
				EmailMessage message;
				try {
					message = new EmailMessage(emailTo, String.format("Notification for Vehicle Tax (%s)", task.getRegistrationNo()), buildBodyMsg(task));
					emailSender.sendMail(message);
					logger.info("Email Message Sent " + task.getRegistrationNo());
				} catch (ParseException e) {
					logger.info(e.getMessage());
					e.printStackTrace();
				}
			}
		}
	}
	private String buildBodyMsg(VehicleTaxTask task) throws ParseException {
		StringBuilder body = new StringBuilder();
		body.append(String.format("\n%s days(s) left before tax payment with registration no. %s.", task.getDaysLeft(), task.getRegistrationNo()))
		.append(String.format("\nPayment Type:%s\nLabel:%s\nModel:%s\nPlate No.:%s\nTax no.:%s", taskType(task.getTaskType()), task.getLabel(), task.getModel(), task.getPlateNo(), task.getRegistrationNo()));
		return body.toString();
	}
	private String taskType(final String type) {
		switch(type) {
			case "T":
				return "Vehicle Tax";
			case "C":
				return "Certification";
			case "K":
				return "KIR";
			default:
				return "Unknown : " + type;
		}
	}
}
