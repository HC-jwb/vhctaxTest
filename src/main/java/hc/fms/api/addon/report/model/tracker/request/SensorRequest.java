package hc.fms.api.addon.report.model.tracker.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SensorRequest {
	private String hash;
	private Integer trackerId;
}
