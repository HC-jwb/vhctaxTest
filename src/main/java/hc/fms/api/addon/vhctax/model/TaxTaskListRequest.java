package hc.fms.api.addon.vhctax.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TaxTaskListRequest {
	private String taskType;
	private String statType;
	private String fromDate;
	private String toDate;
	private String reportFileFormat;
	public String getReportFileFormat() {
		return this.reportFileFormat.equals("xls")? "xlsx" : this.reportFileFormat;
	}
}
