package hc.fms.api.addon.report.util;

import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;

@Configuration
@Getter
public class HttpUtil {
	private static ObjectMapper objectMapper = new ObjectMapper();
	@Bean
	public HttpHeaders basicUrlEncodedContentTypeHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		return headers;
	}
	public static ObjectMapper getObjectMapper() { return objectMapper; }
	public static String hashKey (HttpSession session) {
		String hash = null;
		Object attrHash = session.getAttribute("hash");
		if(attrHash != null) {
			hash = String.valueOf(attrHash);
		} /*else {
			throw new RuntimeException("no session hash");
		}*/
		return hash;
	}
}
