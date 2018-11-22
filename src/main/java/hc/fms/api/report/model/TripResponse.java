package hc.fms.api.report.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import hc.fms.api.report.model.trip.Trip;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class TripResponse {
	private Boolean success;
	private ResponseStatus status;
	private List<ResponseError> errors;
	private List<Trip> list;
	@JsonProperty("limit_exceeded")
	private Boolean limitExceeded;
}
