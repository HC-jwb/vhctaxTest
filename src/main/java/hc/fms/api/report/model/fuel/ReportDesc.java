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
public class ReportDesc {
	private String created;
	@JsonProperty("time_filter")
	private TimeFilter timeFilter;
}
