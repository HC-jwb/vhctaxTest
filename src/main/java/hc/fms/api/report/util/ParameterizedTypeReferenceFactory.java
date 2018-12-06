package hc.fms.api.report.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;

import hc.fms.api.report.model.ReportResponse;
import hc.fms.api.report.model.FuelMileageSection;
import hc.fms.api.report.model.GroupResponse;
import hc.fms.api.report.model.ReportGenResponse;
import hc.fms.api.report.model.SensorResponse;
import hc.fms.api.report.model.TrackerResponse;
import hc.fms.api.report.model.TripResponse;
import hc.fms.api.report.model.auth.AuthResponse;
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
	ParameterizedTypeReference<TrackerResponse> trackerResponseTypeRef() {
		return new ParameterizedTypeReference<TrackerResponse>() {};
	}
}
