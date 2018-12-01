package hc.fms.api.report.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="report_gen")
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonIgnoreProperties(value={"formattedCreatedDate"}, allowGetters=true)
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
	
	@Column(name="report_proc", columnDefinition="TINYINT(1)")
	private boolean fuelReportProcessed;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_dt", updatable=false)
	private Date createdDate;
	
	@Transient
	private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	public String getFormattedCreatedDate() {
		return formatter.format(createdDate);
	}
}
