package hc.fms.api.report.model.tracker.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class TrackerInfo {
	private int trackerId;
	@JsonProperty("fuelSensorId")
	private int fuelConsumptionSensorId;
	@JsonProperty("mileageSensorId")
	private int hardwareMileageSensorId;
}
