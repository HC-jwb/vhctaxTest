package hc.fms.api.addon.report.model;

import java.util.List;

import hc.fms.api.addon.report.model.tracker.TrackerSensor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class SensorResponse {
	private Boolean success;
	private ResponseStatus status;
	private List<ResponseError> errors;
	private List<TrackerSensor> list;
}
