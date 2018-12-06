package hc.fms.api.report.model;

import java.util.List;

import hc.fms.api.report.model.fuel.Column;
import hc.fms.api.report.model.fuel.Datum;
import hc.fms.api.report.model.fuel.FuelMileageRow;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@NoArgsConstructor
@ToString
public class FuelMileageSection {
	private String type;//I will extract type 'table' only
	private String header;//"Statistics data", "Data breakdown"
	private String timezone;
	private List<Column> columns;
	private List<Datum<FuelMileageRow>> data;
}
