package hc.fms.api.addon.report.entity;

public interface FuelStatResult {
	public String getStatDate();
	public Integer getTrackerId();
	public Double getFuelStart();
	public Double getFuelEnd();
	public Double getMileageStart();
	public Double getMileageEnd();
	public Double getFuelUsed();
	public Double getDistanceTravelled();
	public Double getFuelEffRate();
}
