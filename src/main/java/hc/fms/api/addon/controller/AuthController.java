package hc.fms.api.addon.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hc.fms.api.addon.model.AuthResponse;
import hc.fms.api.addon.model.ResponseContainer;
import hc.fms.api.addon.model.ResponseStatus;
import hc.fms.api.addon.report.service.AuthService;

@RestController
@CrossOrigin("*")
public class AuthController {
	@Autowired
	private AuthService authService;
	@RequestMapping("/validate")
	public ResponseContainer<Boolean> validateSession(HttpSession session) {
		ResponseContainer<Boolean> response = new ResponseContainer<>();
		if(hashKey(session)== null) {
			ResponseStatus status = new ResponseStatus();
			status.setDescription("session invalid or expired");
		} else {
			response.setSuccess(true);
			response.setPayload(Boolean.TRUE);
		}
		return response;
		
	}
	@PostMapping("/authenticate")
	public AuthResponse initAuth(@RequestBody Map<String, String> authInfo, HttpSession session) {
		AuthResponse response = authService.sendAuth(authInfo.get("login"), authInfo.get("password"));
		if(response.getSuccess()) {
			session.setAttribute("clientId", authInfo.get("login"));
			session.setAttribute("password", authInfo.get("password"));
			session.setAttribute("hash", response.getHash());
		}
		return response;
	}
	private String hashKey (HttpSession session) {
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
