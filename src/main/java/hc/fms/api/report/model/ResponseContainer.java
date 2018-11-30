package hc.fms.api.report.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ResponseContainer <T>{
	private boolean success;
	private T payload;
	private ResponseStatus status;
}
