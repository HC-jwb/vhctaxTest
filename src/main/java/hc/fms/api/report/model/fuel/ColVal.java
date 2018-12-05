package hc.fms.api.report.model.fuel;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ColVal <T>{
	private String v;
	private T raw;
}
