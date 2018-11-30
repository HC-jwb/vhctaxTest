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
public class TrackerSensor {
	private int id;
	private String type;
	private String name;
	@JsonProperty("input_number")
	private Integer inputNumber;
	
	private String units;
	
	@JsonProperty("units_type")
	private String unitsType;
	
	@JsonProperty("sensor_type")
	private String sensorType;
	@JsonProperty("group_type")
	private String groupType;
	
	private Integer divider;
	private Integer accuracy;
}
