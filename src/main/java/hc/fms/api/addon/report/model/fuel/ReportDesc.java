package hc.fms.api.addon.report.model.fuel;

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
public class ReportDesc<T> {
	private String created;
	@JsonProperty("time_filter")
	private TimeFilter timeFilter;
	//private String title;
	private List<Sheet<T>> sheets;
	private String from;
	private String to;
}
