package hc.fms.api.report.model.fuel.filldrain;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString
/*This plugin is for fuel filling and draining data request */ 
public class ObdFuelPlugin {
	@JsonProperty("graph_type")
	private String graphType="mileage";
	@JsonProperty("plugin_id")
	private int pluginId = 10;
	@JsonProperty("detailed_by_dates")
	private boolean detailedByDates = false;
	@JsonProperty("include_summary_sheet_only")
	private boolean includeSummarySheetOnly = false;
	@JsonProperty("include_mileage_plot")
	private boolean includeMileagePlot = false;
	private boolean filter = true;
	@JsonProperty("include_speed_plot")
	private boolean includeSpeedPlot = false;
	private boolean smoothing = true;
	@JsonProperty("surge_filter")
	private boolean surgeFilter = true;
	
	@JsonProperty("surge_filter_threshold")
	private double surgeFilterThreshold = 0.19;//19%
	@JsonProperty("speed_filter")
	private boolean speedFilter = false;
	@JsonProperty("speed_filter_threshold")
	private double speedFilterThreshold = 1.0;
}
