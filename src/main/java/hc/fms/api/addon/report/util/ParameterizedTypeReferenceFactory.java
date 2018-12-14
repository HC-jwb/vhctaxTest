package hc.fms.api.addon.report.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;

import hc.fms.api.addon.model.auth.AuthResponse;
import hc.fms.api.addon.report.model.GroupResponse;
import hc.fms.api.addon.report.model.ReportGenResponse;
import hc.fms.api.addon.report.model.ReportResponse;
import hc.fms.api.addon.report.model.SensorResponse;
import hc.fms.api.addon.report.model.TrackerResponse;
import hc.fms.api.addon.report.model.TripResponse;
import hc.fms.api.addon.report.model.fuel.FuelMileageSection;
import hc.fms.api.addon.report.model.fuel.filldrain.FillDrainSection;
@Configuration
public class ParameterizedTypeReferenceFactory {
	@Bean
	ParameterizedTypeReference<AuthResponse> authTypeRef() {
		return new ParameterizedTypeReference<AuthResponse>() {};
	}
	@Bean
	ParameterizedTypeReference<TripResponse> tripResponseTypeRef() {
		return new ParameterizedTypeReference<TripResponse>() {};
	}
	@Bean
	ParameterizedTypeReference<SensorResponse> sensorResponseTypeRef() {
		return new ParameterizedTypeReference<SensorResponse>() {};
	}
	@Bean
	ParameterizedTypeReference<GroupResponse> groupResponseTypeRef() {
		return new ParameterizedTypeReference<GroupResponse>() {};
	}
	@Bean
	ParameterizedTypeReference<ReportGenResponse> reportGenResponseTypeRef() {
		return new ParameterizedTypeReference<ReportGenResponse>() {};
	}
	@Bean
	ParameterizedTypeReference<ReportResponse<FuelMileageSection>> reportConsumptionResponseTypeRef() {
		return new ParameterizedTypeReference<ReportResponse<FuelMileageSection>>() {};
	}
	@Bean
	ParameterizedTypeReference<ReportResponse<FillDrainSection>> reportFillDrainResponseTypeRef() {
		return new ParameterizedTypeReference<ReportResponse<FillDrainSection>>() {};
	}
	@Bean
	ParameterizedTypeReference<TrackerResponse> trackerResponseTypeRef() {
		return new ParameterizedTypeReference<TrackerResponse>() {};
	}
}
