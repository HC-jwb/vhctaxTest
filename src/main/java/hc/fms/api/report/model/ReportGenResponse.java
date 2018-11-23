package hc.fms.api.report.model;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ReportGenResponse {
	private Boolean success;
	private int id;
	private ResponseStatus status;
	private List<ResponseError> errors;
}
