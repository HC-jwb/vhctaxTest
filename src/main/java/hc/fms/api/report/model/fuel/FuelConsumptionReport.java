package hc.fms.api.report.model.fuel;

import java.util.List;

import lombok.Data;

@Data
public class FuelConsumptionReport {
	private ReportDesc report;
	private String title;
	private List<Sheet> sheets;
	private String from;
	private String to;
}