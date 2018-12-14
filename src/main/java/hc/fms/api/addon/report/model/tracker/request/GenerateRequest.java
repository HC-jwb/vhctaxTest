package hc.fms.api.addon.report.model.tracker.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import hc.fms.api.addon.report.model.fuel.TimeFilter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class GenerateRequest<T> {
	private static ObjectMapper jsonMapper = new ObjectMapper(); 
	private String hash;
	private List<Integer> trackers;
	private String from;//yyyy-MM-dd HH:mm:ss
	private String to;
	private String geocoder = "google";
	@JsonProperty("time_filter")
	private TimeFilter timeFilter = new TimeFilter();
	//private FuelMileagePlugin plugin;
	private T plugin;
	
	public String getTimeFilter() {
		try {
			return jsonMapper.writeValueAsString(this.timeFilter);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "";
	}
	public String getPlugin () {
		try {
			return jsonMapper.writeValueAsString(this.plugin);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "";
	}
	public String getTrackers() {
		try {
			return jsonMapper.writeValueAsString(this.trackers);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "";
	}
}
