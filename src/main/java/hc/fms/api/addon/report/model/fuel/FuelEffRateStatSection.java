package hc.fms.api.addon.report.model.fuel;

import java.util.List;

public class FuelEffRateStatSection {
	private List<FuelStat> statList;
	private Double totalFuelUsed = 0D;
	private Double totalDistanceTravelled = 0D;
	public List<FuelStat> getStatList() { return this.statList; }
	public void setStatList(List<FuelStat> statList) { this.statList = statList; }
	public void addFuelUsed(Double fuelUsed) {
		this.totalFuelUsed += fuelUsed;
	}
	public void addDistanceTravelled(Double distanceTravelled) {
		this.totalDistanceTravelled += distanceTravelled;
	}
	public String getTotalFuelUsed() {
		return FuelStat.formatter.format(totalFuelUsed);
	}
	public String getTotalDistanceTravelled() {
		return FuelStat.formatter.format(totalDistanceTravelled);
	}
	public String getTotalFuelEffRate() {
		return FuelStat.formatter.format(this.totalDistanceTravelled/this.totalFuelUsed);
	}
}