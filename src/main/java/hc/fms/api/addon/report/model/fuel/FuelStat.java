package hc.fms.api.addon.report.model.fuel;

import java.text.DecimalFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import hc.fms.api.addon.report.entity.FuelStatResult;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties({"formatter"})
public class FuelStat {
	public static DecimalFormat formatter = new DecimalFormat("###,###.##");
	private String statDate;
	private Integer trackerId;
	private Double fuelStart;
	private Double fuelEnd;
	private Double mileageStart;
	private Double mileageEnd;
	private Double fuelUsed;
	private Double distanceTravelled;
	private Double fuelEffRate;
	public FuelStat() {}
	public FuelStat(FuelStatResult result) {
		this.statDate = result.getStatDate();
		this.trackerId = result.getTrackerId();
		this.fuelStart = result.getFuelStart();
		this.fuelEnd = result.getFuelEnd();
		this.mileageStart = result.getMileageStart();
		this.mileageEnd = result.getMileageEnd();
		this.fuelUsed = result.getFuelUsed();
		this.distanceTravelled = result.getDistanceTravelled();
		this.fuelEffRate = result.getFuelEffRate();
	}
	public String getFuelStart() {
		return formatter.format(this.fuelStart);
	}
	public String getFuelEnd() {
		return formatter.format(this.fuelEnd);
	}
	public String getMileageStart() {
		return formatter.format(this.mileageStart);
	}
	public String getMileageEnd() {
		return formatter.format(this.mileageEnd);
	}
	public String getFuelUsed() {
		return formatter.format(this.fuelUsed);
	}
	public String getDistanceTravelled() {
		return formatter.format(this.distanceTravelled);
	}
	public String getFuelEffRate() {
		return formatter.format(this.fuelEffRate);
	}
}
