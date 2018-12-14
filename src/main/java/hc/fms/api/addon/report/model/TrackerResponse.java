package hc.fms.api.addon.report.model;

import java.util.List;

import hc.fms.api.addon.report.model.tracker.Tracker;
import lombok.Data;

@Data
public class TrackerResponse {
	private Boolean success;
	private ResponseStatus status;
	private List<ResponseError> errors;
	private List<Tracker> list;
}
