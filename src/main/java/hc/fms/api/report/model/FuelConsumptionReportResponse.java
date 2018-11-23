package hc.fms.api.report.model;

import java.util.List;

import hc.fms.api.report.model.fuel.ReportDesc;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class FuelConsumptionReportResponse {
	private boolean success;
	private ResponseStatus status;
	private List<ResponseError> errors;
	private ReportDesc report;
}