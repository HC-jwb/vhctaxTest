package hc.fms.api.report.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hc.fms.api.report.model.GroupResponse;
import hc.fms.api.report.model.ReportGenFlatRequest;
import hc.fms.api.report.model.ReportGenResponse;
import hc.fms.api.report.model.SensorResponse;
import hc.fms.api.report.model.TrackerResponse;
import hc.fms.api.report.model.auth.AuthResponse;
import hc.fms.api.report.service.AuthService;
import hc.fms.api.report.service.TrackerService;

@RestController
@RequestMapping("/report/api")
public class ReportApiController {
	@Autowired
	private AuthService authService;
	@Autowired
	private TrackerService trackerService;

	@PostMapping("/authenticate")
	public AuthResponse initAuth(@RequestBody Map<String, String> authInfo, HttpSession session) {
		AuthResponse response = authService.sendAuth(authInfo.get("login"), authInfo.get("password"));
		if(response.getSuccess()) {
			session.setAttribute("hash", response.getHash());
		}
		return response;
	}
	@RequestMapping("/tracker/list")
	public TrackerResponse getTrackers(HttpSession session) {
		TrackerResponse trackerListReponse = trackerService.getTrackerList(hashKey(session));
		trackerListReponse.getList().stream().forEach(System.out::println);
		return trackerListReponse;
	}
	@RequestMapping("/tracker/group/list")
	public GroupResponse getGroupList(HttpSession session) {
		GroupResponse response = trackerService.getGroupList(hashKey(session));
		return response;
	}
	@RequestMapping("/tracker/sensor/{trackerId}/list")
	public SensorResponse getSensorList(@PathVariable("trackerId") int trackerId, HttpSession session) {
		SensorResponse response = trackerService.getSensorList(hashKey(session), trackerId);
		return response;
	}
	@PostMapping("/generate")
	public ReportGenResponse requestReportGen(@RequestBody ReportGenFlatRequest req, HttpSession session) {
		/*
		 * 
		{
			"trackers": [{"trackerId": 78, "mileageSensorId": 656, "fuelSensorId": 640}, {"trackerId": 72, "mileageSensorId": 655, "fuelSensorId": 644}],
			"from":"2018-11-01 00:00:00",
			"to":"2018-11-26 23:59:59",
			"intervalInMin": 360,
			"label": "01-레이벤-45하8608(2017) & 02-모닝-45하9940(2016) 누적운행거리"
		}
		*/
		req.setHash(hashKey(session));
		System.out.println("session Key " + req.getHash());
		ReportGenResponse response = trackerService.generateReport(req);
		return response;
	}
/*	
	@RequestMapping("/normalize/{reportId}")
	public ReportResponse analyzeReport(@PathVariable("reportId") long reportId, HttpSession session) {
		ReportResponse response = trackerService.retrieveReport(hashKey(session), reportId);
		if(response.isSuccess()) {
			reportGenService.analyzeReport(response.getReport());
		}
		return response;
	}
*/	
	private String hashKey (HttpSession session) {
		String hash = null;
		Object attrHash = session.getAttribute("hash");
		if(attrHash != null) {
			hash = String.valueOf(attrHash);
		} else {
			throw new RuntimeException("no session hash");
		}
		return hash;
	}
}
