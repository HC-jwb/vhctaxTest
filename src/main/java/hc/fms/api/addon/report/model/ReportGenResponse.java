package hc.fms.api.addon.report.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import hc.fms.api.addon.model.ResponseStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ReportGenResponse {
	private boolean success;
	private long id;
	private ResponseStatus status;
	private List<ResponseError> errors;
	@JsonProperty("max_time_span")
	private String  maxTimeSpan;
}
