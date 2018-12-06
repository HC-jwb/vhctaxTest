package hc.fms.api.report.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import hc.fms.api.report.model.tracker.request.TrackerInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@JsonIgnoreProperties({"hash", "clientId"})
public class ReportGenFlatRequest {
	//trackers => [{trackerId:73, sensorId: 647} ,{trackerId:69, sensorId: 645}], from=> 2018-11-23 00:00:00, to=> 2018-11-23 23:59:59, detailsIntervalMinutes => 360
	private List<TrackerInfo> trackers;
	private String from;
	private String to;
	private int intervalInMin;
	private String label;
	private String clientId;
	private String hash;
	public void normalize() {
		this.from = String.format("%s %s", this.from, "00:00:00");
		this.to = String.format("%s %s", this.to, "23:59:59");
	}
}