package hc.fms.api.report.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties(prefix="fms")
@Data
public class FmsProperties {
	private String siteAddress;
	private String baseUrl;
	private Api api;
	
	@Data
	public static class Api {
		private String userAuth;
		private String tracker;
		private String trackerGroup;
		private String trackerSensor;
		private String trip;
		private String reportGen;
		private String reportRetrieve;
		
		private String accumulatedFuelConsumptionLabel;
		private String accumulatedHardwareMileageLabel;
	}
}
