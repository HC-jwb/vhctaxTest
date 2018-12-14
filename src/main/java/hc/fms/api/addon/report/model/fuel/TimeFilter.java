package hc.fms.api.addon.report.model.fuel;

import java.util.Arrays;
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
	private String from = "00:00:00";
	private String to = "23:59:59";
	@JsonProperty("weekdays")
	private List<Integer> weekDays = Arrays.asList(1,2,3,4,5,6,7);//defaults to every weekday
}
