package hc.fms.api.addon.model;

import java.util.List;

import hc.fms.api.addon.report.model.ResponseError;
import lombok.Data;

@Data
public class AuthResponse {
	private Boolean success;
	private ResponseStatus status;
	private List<ResponseError> errors;
	private String hash;
}
