package hc.fms.api.report.model.fuel;

import lombok.Data;

@Data
public class Row {
	private ColDate  date;
	private ColMinMaxAvg min;
	private ColMinMaxAvg max;
	private ColMinMaxAvg avg;
	private ColMinMaxAvg time;
	private ColMinMaxAvg value;
}