package hc.fms.api.report.model.fuel.filldrain;

import java.util.List;

import hc.fms.api.report.model.fuel.Column;
import hc.fms.api.report.model.fuel.Datum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString
public class FillDrainSection {
	private String type;//I will extract type 'table' only
	private String header;//"Statistics data", "Data breakdown"
	private String timezone;
	private List<Column> columns;
	private List<Datum<FillDrainRow>> data;
}
