package hc.fms.api.addon.vhctax.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="vhc_tax_tmpl")
@Getter
@Setter
@ToString
public class ServiceTemplate {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="tmpl_id")
	private Long id;
	@Column(name="svc_title")
	private String title;
	@Column(name="svc_desc")
	private String description;

	@Column(name="cert_interval")
	private Integer certificationInterval;//year
	@Column(name="cert_cost")
	private Double certificationCost;
	@Column(name="cert_remind")
	private Integer certificationRemindBeforeDays;
	
	@Column(name="tax_interval")
	private Integer taxInterval;//year
	@Column(name="tax_cost")
	private Double taxCost;
	@Column(name="tax_remind")
	private Integer taxRemindBeforeDays;

	private Integer kirInterval;
	@Column(name="kir_cost")
	private Double kirCost;
	@Column(name="kir_remind")
	private Integer kirRemindBeforeDays;
	
	@Column(name="noti_sms")
	private String notificationSms;
	@Column(name="noti_email")
	private String notificationEmail;
}
