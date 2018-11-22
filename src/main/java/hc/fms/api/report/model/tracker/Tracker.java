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
public class Tracker {
	private Long id;
	@JsonProperty("group_id")
	private Long groupId;
	private String label;
	private TrackerSource source;
	private Boolean clone;
}
