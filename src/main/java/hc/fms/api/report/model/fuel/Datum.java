package hc.fms.api.report.model.fuel;

import java.util.List;

import lombok.Data;

@Data
public class Datum {
	private String header;
	private List<Row> rows;
	private Double total;
}
