package hc.fms.api.report.model.fuel;

import java.util.List;

import lombok.Data;

@Data
public class Sheet {
	private String type;//"table"
	private String header;//"Statistics data",  "Data breakdown"
	private String timezone;
	private List<Column> columns;
	private List<Datum> data;
}
