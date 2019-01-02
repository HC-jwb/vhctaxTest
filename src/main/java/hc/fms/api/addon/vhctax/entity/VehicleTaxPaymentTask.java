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
@Table(name="tax_payment_task")
@Getter
@Setter
@ToString
public class VehicleTaxPaymentTask {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(name="vhc_id")
	private Long vehicleId;
	private String vin;
	private String label;
	private String model;
	@Column(name="reg_number")
	private String regNumber;
	@Column(name="cert_no")
	private String certificationNo;
	@Column(name="cert_cost")
	private Double certificationCost;
	@Column(name="cert_remind")
	private Integer certificationRemindBeforeDays;
	@Column(name="cert_base_dt")
	private String certificationBaseDate;
	@Column(name="cert_photo_id")
	private Long certificatePhotoId;
	
	@Column(name="tax_no")
	private String taxNo;
	@Column(name="tax_cost")
	private Double taxCost;
	@Column(name="tax_remind")
	private Integer taxRemindBeforeDays;
	@Column(name="tax_base_dt")
	private String taxBaseDate;
	@Column(name="tax_photo_id")
	private Long taxPhotoId;
	
	@Column(name="noti_sms")
	private String notificationSms;
	@Column(name="noti_email")
	private String notificationEmail;
	
	@Column(name="cert_paid", columnDefinition="TINYINT(1)")
	private boolean certPaid;
	
	@Column(name="tax_paid", columnDefinition="TINYINT(1)")
	private boolean taxPaid;
}
