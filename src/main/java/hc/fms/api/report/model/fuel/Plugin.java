package hc.fms.api.report.model.fuel;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Plugin {
	@JsonProperty("details_interval_minutes")
	private int detailsIntervalMinutes = 60;
	@JsonProperty("plugin_id")
	private int pluginId = 9;//??
	@JsonProperty("graph_type")
	private String graphType="time";
	private boolean smoothing = false;
	@JsonProperty("show_address")
	private boolean showAddress;
	private boolean filter;
	private List<Sensor> sensors;
	
	@Getter
	@Setter
	@ToString
	public static class Sensor {
		@JsonProperty("tracker_id")
		private int trackerId;
		@JsonProperty("sensor_id")
		private int sensorId;
	}
}
