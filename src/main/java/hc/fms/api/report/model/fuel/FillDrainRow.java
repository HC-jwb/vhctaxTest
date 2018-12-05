package hc.fms.api.report.model.fuel;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FillDrainRow {
	private ColVal<Long>  date;
	private ColVal<Double> volume;
	private ColVal<Double> max;
	private ColVal<Double> avg;
	private ColVal<Double> time;
	private ColVal<Double> value;
	private StringVal address;
}
