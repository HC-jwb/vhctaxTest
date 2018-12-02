package hc.fms.api.report.model.fuel;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

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
	private String header;//"tracker Name"
	@JsonProperty("entity_ids")
	private List<Long> entityIds;
	private List<Section> sections;
}
