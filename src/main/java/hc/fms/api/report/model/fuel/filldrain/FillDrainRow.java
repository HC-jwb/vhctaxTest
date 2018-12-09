package hc.fms.api.report.model.fuel.filldrain;

import hc.fms.api.report.model.fuel.ColVal;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FillDrainRow {
	private ColVal<Integer> number;
	private ColVal<Double> volume;
	private ColVal<Double> max;
	private ColVal<Double> avg;
	private ColVal<Long> time;
	private ColVal<Double> mileage;
	private ColVal<Double> value;
	private ColVal<String> address;
	private ColVal<String> type;
}
