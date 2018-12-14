package hc.fms.api.addon.vhctax.model;

import java.util.List;

import hc.fms.api.addon.model.ResponseStatus;
import hc.fms.api.addon.report.model.ResponseError;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString
public class VehicleListResponse {
	private Boolean success;
	private ResponseStatus status;
	private List<ResponseError> errors;
	private List<Vehicle> list;
}
