package hc.fms.api.report.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="report_gen")
@Getter
@Setter
@NoArgsConstructor
public class ReportGen {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="report_id")
	private Long id;
	
	private long fuelReportId;
	private long mileageReportId;
	@Column(name="start")
	private String from;
	@Column(name="end")
	private String to;
	private String label;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_dt", updatable=false)
	private Date createdDate;

}
