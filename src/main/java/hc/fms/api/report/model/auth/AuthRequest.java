package hc.fms.api.report.model.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
//http://cesco.myhandycar.com/api/user/auth
@Data
@AllArgsConstructor
public class AuthRequest {
	private String login;
	private String password;
}
