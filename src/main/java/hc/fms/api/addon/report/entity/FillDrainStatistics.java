package hc.fms.api.addon.report.entity;

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
@Table(name="filldrain_stat")
@Getter
@Setter
@ToString
public class FillDrainStatistics {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private Long reportId;
	private Long trackerId;
	@Column(name="gen_id")
	private Long generationId;
	@Column(name="event_dt")
	private String eventDate;
	private Integer eventId;
	private String address;
	private String type;
	private Double startVolume;
	private Double endVolume;
	private Double volume;
	private Double mileageFrom;
	@Column(name="raw_dt")
	private Long rawDate;
}
