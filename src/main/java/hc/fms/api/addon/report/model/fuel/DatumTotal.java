package hc.fms.api.addon.report.model.fuel;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DatumTotal {
	private String text;
	
	private ColVal<Double> min;
	private ColVal<Double> max;
	private ColVal<Double> avg;
}