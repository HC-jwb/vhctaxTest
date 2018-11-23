package hc.fms.api.report.model.fuel;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DatumTotal {
	private String text;
	private ColMinMaxAvg min;
	private ColMinMaxAvg max;
	private ColMinMaxAvg avg;
}