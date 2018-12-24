package hc.fms.api.addon.vhctax.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import hc.fms.api.addon.properties.FmsProperties;
import hc.fms.api.addon.report.util.HttpUtil;
import hc.fms.api.addon.vhctax.entity.ServiceTemplate;
import hc.fms.api.addon.vhctax.model.VehicleListResponse;
import hc.fms.api.addon.vhctax.repository.ServiceTemplateRepository;

@Service
public class VehicleTaxManagementService {
	private RestTemplate restTemplate = new RestTemplate();
	@Autowired
	private ServiceTemplateRepository serviceTemplateRepository;
	@Autowired
	private FmsProperties fmsProps;
	@Autowired
	private HttpHeaders basicUrlEncodedContentTypeHeaders;
	@Autowired
	private ParameterizedTypeReference<VehicleListResponse> vehicleListResponseTypeRef;
	public VehicleListResponse getVehicleList(String hash) {
		MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
		map.add("hash", hash);
		VehicleListResponse response = null;
		try {
			ResponseEntity<VehicleListResponse> responseEntity = restTemplate.exchange(
					String.format("%s%s", fmsProps.getBaseUrl(), fmsProps.getApi().getVehicleList()), 
					HttpMethod.POST, 
					new HttpEntity<>(map, basicUrlEncodedContentTypeHeaders), 
					vehicleListResponseTypeRef
			);
			response= responseEntity.getBody();
		} catch(HttpStatusCodeException  e) {
			try {response = HttpUtil.getObjectMapper().readValue(e.getResponseBodyAsString(), VehicleListResponse.class);} catch(Exception ex) { ex.printStackTrace();}
		}
		return response;
	}
	
	public List<ServiceTemplate> getServiceTemplateList() {
		List<ServiceTemplate> tmplList = serviceTemplateRepository.findAllByOrderById();
		return tmplList;
	}
}
