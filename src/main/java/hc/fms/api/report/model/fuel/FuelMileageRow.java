package hc.fms.api.report.model.fuel;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class FuelMileageRow {
	/*
	private LongVal  date;
	private DoubleVal min;
	private DoubleVal max;
	private DoubleVal avg;
	private DoubleVal time;
	private DoubleVal value;
	private StringVal address;
	*/
	private ColVal<Long>  date;
	private ColVal<Double> min;
	private ColVal<Double> max;
	private ColVal<Double> avg;
	private ColVal<Double> time;
	private ColVal<Double> value;
	private ColVal<String> address;
	
}