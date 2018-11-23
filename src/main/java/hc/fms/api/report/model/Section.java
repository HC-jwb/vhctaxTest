package hc.fms.api.report.model;

import java.util.List;

import hc.fms.api.report.model.fuel.Column;
import hc.fms.api.report.model.fuel.Datum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Section {
	private String type;
	private String header;//"Statistics data", "Data breakdown"
	private String timezone;
	private List<Column> columns;
	private List<Datum> data;
}
