package hc.fms.api.report.model.fuel;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Datum {
	private String header;
	private List<Row> rows;
	private DatumTotal total;
}