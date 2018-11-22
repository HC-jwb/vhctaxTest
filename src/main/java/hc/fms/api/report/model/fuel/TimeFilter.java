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
public class TimeFilter {
	private String from;
	private String to;
	@JsonProperty("week_days")
	private List<Integer> weekDays;
}
