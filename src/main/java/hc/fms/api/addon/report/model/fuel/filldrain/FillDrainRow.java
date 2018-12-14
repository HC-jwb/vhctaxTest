package hc.fms.api.addon.report.model.fuel.filldrain;

import hc.fms.api.addon.report.model.fuel.ColVal;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FillDrainRow {
	private ColVal<Integer> number;
	private ColVal<Double> volume;
	private ColVal<Double> startVolume;
	private ColVal<Double> endVolume;
	private ColVal<Long> time;
	private ColVal<Double> mileage;
	private ColVal<String> address;
	private ColVal<String> type;
}
