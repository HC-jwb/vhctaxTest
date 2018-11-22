package hc.fms.api.report.model.tracker;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class TrackerSource {
	private Long id;
	private String model;
	@JsonProperty("device_id")
	private String deviceId;
	private Boolean blocked;
	private String phone;
	@JsonProperty("creation_date")
	private String creationDate;
}
