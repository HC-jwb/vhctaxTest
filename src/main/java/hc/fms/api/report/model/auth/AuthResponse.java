package hc.fms.api.report.model.auth;

import java.util.List;

import hc.fms.api.report.model.ResponseError;
import hc.fms.api.report.model.ResponseStatus;
import lombok.Data;

@Data
public class AuthResponse {
	private Boolean success;
	private ResponseStatus status;
	private List<ResponseError> errors;
	private String hash;
}
