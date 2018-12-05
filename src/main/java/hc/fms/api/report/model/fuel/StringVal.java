package hc.fms.api.report.model.fuel;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class StringVal {
	@JsonProperty("v")
	private String address;
}
