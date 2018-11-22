package hc.fms.api.report.model;

import java.util.List;

import hc.fms.api.report.model.tracker.TrackerGroup;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@NoArgsConstructor
@ToString
public class GroupResponse {
	private Boolean success;
	private ResponseStatus status;
	private List<ResponseError> errors;
	private List<TrackerGroup> list;
}
