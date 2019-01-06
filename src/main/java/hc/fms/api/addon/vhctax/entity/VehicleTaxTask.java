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
@Table(name="vhc_tax_task")
@Getter
@Setter
@ToString
public class VehicleTaxTask {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="vhc_id")
	private Long vehicleId;
	private String vin;
	private String label;
	private String model;
	@Column(name="plate_no")
	private String plateNo;
	
	
	@Column(name="reg_no")
	private String registrationNo;
	private Double cost;
	@Column(name="remind")
	private Integer remindBeforeDays;
	@Column(name="valid_till_dt")
	private String dateValidTill;
	
	@Column(name="noti_sms")
	private String notificationSms;
	@Column(name="noti_email")
	private String notificationEmail;
		
	@Column(name="paid", columnDefinition="TINYINT(1)")
	private boolean paid;
	
	@Column(name="task_type")
	private String taskType;
	
	@Column(name="photo_id")
	private Long photoId;
}
