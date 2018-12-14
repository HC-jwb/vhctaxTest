package hc.fms.api.addon.report.service;

import java.io.IOException;

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

import hc.fms.api.addon.model.AuthRequest;
import hc.fms.api.addon.model.AuthResponse;
import hc.fms.api.addon.properties.FmsProperties;
import hc.fms.api.addon.report.util.HttpUtil;

@Service
public class AuthService {
	private RestTemplate restTemplate = new RestTemplate();
	@Autowired
	private FmsProperties fmsProps;
	@Autowired
	private HttpHeaders basicUrlEncodedContentTypeHeaders;
	@Autowired
	private ParameterizedTypeReference<AuthResponse> authTypeRef;
	public AuthResponse sendAuth(AuthRequest req) {
		return sendAuth(req.getLogin(), req.getPassword());
	}
	public AuthResponse sendAuth(String login, String password) {
		MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
		map.add("login", login);
		map.add("password", password);
		AuthResponse response = null;;
		try {
			ResponseEntity<AuthResponse> responseEntity = restTemplate.exchange(String.format("%s%s", fmsProps.getBaseUrl(), fmsProps.getApi().getUserAuth()), HttpMethod.POST, new HttpEntity<>(map, basicUrlEncodedContentTypeHeaders), authTypeRef);
			response = responseEntity.getBody();
		} catch(HttpStatusCodeException e) {
			try {
				response = HttpUtil.getObjectMapper().readValue(e.getResponseBodyAsString(), AuthResponse.class);
			} catch (IOException ex) {ex.printStackTrace();}
		}
		
		return response;
	}
}
