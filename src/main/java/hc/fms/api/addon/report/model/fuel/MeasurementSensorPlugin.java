package hc.fms.api.addon.report.model.fuel;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
/*This plugin is for fuel consumption and hardware mileage data*/
public class MeasurementSensorPlugin {
	@JsonProperty("details_interval_minutes")
	private int detailsIntervalMinutes = 60 * 6;
	@JsonProperty("plugin_id")
	private int pluginId = 9;//??
	@JsonProperty("graph_type")
	private String graphType="time";
	private boolean smoothing = false;
	@JsonProperty("show_address")
	private boolean showAddress = true;
	private boolean filter = true;
	private List<Sensor> sensors;
	
	@Getter
	@Setter
	@ToString
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Sensor {
		@JsonProperty("tracker_id")
		private int trackerId;
		@JsonProperty("sensor_id")
		private int sensorId;
	}
}
