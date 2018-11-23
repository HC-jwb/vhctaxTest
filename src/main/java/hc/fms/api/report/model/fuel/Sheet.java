package hc.fms.api.report.model.fuel;

import java.util.List;

import hc.fms.api.report.model.Section;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Sheet {
	//private String type;//"table"
	private String header;//"Statistics data",  "Data breakdown"
	//private String timezone;

	private List<Section> sections;
}
