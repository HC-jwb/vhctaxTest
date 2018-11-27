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
@Table(name="mileage_time")
@Getter
@Setter
@ToString
public class MileageStatDetail {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(name="stat_id")
	private Long statId;
	private String time;
	private Double min;
	private Double max;
	private Double value;
	private Double avg;
	private String address;
}
