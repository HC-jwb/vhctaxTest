package hc.fms.api.addon.report.model;

import lombok.Data;

@Data
public class ResponseError {
	private String parameter;
	private String error;
}
