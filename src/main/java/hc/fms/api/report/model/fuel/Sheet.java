package hc.fms.api.report.model.fuel;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Sheet<T> {//FuelMileageSection or FillDrainSection
	private String header;//"tracker Name"
	@JsonProperty("entity_ids")
	private List<Long> entityIds;
	private List<T> sections;
}
