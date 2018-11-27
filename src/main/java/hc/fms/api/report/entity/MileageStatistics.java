package hc.fms.api.report.entity;

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
@Table(name="mileage_stat")
@Getter
@Setter
@ToString
public class MileageStatistics {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private Integer trackerId;
	@Column(name="stat_dt")
	private String statDate;
	
	private Double min;
	private Double max;
	private Double avg;
}
