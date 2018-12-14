package hc.fms.api.addon.report.model.tracker.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
//http://cesco.myhandycar.com/api/track/list
@Data
public class TripRequest {
	private String hash;
	@JsonProperty("tracker_id")
	private Integer trackerId;
	private String from;//yyyy-MM-dd HH:mm:ss
	private String to;//yyyy-MM-dd HH:mm:ss
}
