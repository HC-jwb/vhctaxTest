package hc.fms.api.report.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hc.fms.api.report.entity.FuelStatResult;
import hc.fms.api.report.entity.ReportGen;
import hc.fms.api.report.model.GroupResponse;
import hc.fms.api.report.model.ReportGenFlatRequest;
import hc.fms.api.report.model.ReportGenResponse;
import hc.fms.api.report.model.ResponseContainer;
import hc.fms.api.report.model.ResponseStatus;
import hc.fms.api.report.model.SensorResponse;
import hc.fms.api.report.model.TrackerResponse;
import hc.fms.api.report.model.auth.AuthResponse;
import hc.fms.api.report.model.tracker.Tracker;
import hc.fms.api.report.model.tracker.TrackerSensor;
import hc.fms.api.report.service.AuthService;
import hc.fms.api.report.service.TrackerService;

@RestController
@RequestMapping("/report/api")
public class ReportApiController {
	@Autowired
	private AuthService authService;
	@Autowired
	private TrackerService trackerService;
	private Logger logger = LoggerFactory.getLogger(ReportApiController.class);
	@PostMapping("/authenticate")
	public AuthResponse initAuth(@RequestBody Map<String, String> authInfo, HttpSession session) {
		AuthResponse response = authService.sendAuth(authInfo.get("login"), authInfo.get("password"));
		if(response.getSuccess()) {
			session.setAttribute("hash", response.getHash());
		}
		return response;
	}
	@RequestMapping("/tracker/list")
	public TrackerResponse getTrackers(@RequestBody Long groupId,HttpSession session) {
		TrackerResponse response = trackerService.getTrackerList(hashKey(session));
		if(response.getSuccess()) {
			List<Tracker> filteredList = response.getList().stream().filter(tracker -> tracker.getGroupId().equals(groupId)).collect(Collectors.toList());
			response.setList(filteredList);
		}
		//trackerListReponse.getList().stream().forEach(System.out::println);
		return response;
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
		 * session Key: d222e37acb3d15cd0da78c229a7b9d70
		{
			
			"trackers": [{"trackerId": 78, "mileageSensorId": 656, "fuelSensorId": 640}, {"trackerId": 72, "mileageSensorId": 655, "fuelSensorId": 644}],
			"from":"2018-11-01 00:00:00",
			"to":"2018-11-26 23:59:59",
			"intervalInMin": 360,
			"label": "01-레이벤-45하8608(2017) & 02-모닝-45하9940(2016) 누적운행거리"
		}
		*/
		req.setHash(hashKey(session));
		logger.info("session Key " + req.getHash());
		req.normalize();//add HH:mm:ss to from and to, modify end Date by changing to previous date and to 59:59:59
		req.getTrackers().forEach(info -> {
			SensorResponse sensorResponse = trackerService.getSensorList(req.getHash(), info.getTrackerId());
			if(sensorResponse.getSuccess()) {
				List<TrackerSensor> sensorList = sensorResponse.getList();
				String sensorName;
				for(TrackerSensor sensor : sensorList) {
					sensorName = sensor.getName().replaceAll(" ", "");
					if(sensorName.equals("누적연료소모량")) {
						info.setFuelConsumptionSensorId(sensor.getId());
					} else if(sensorName.equals("누적운행거리")) {
						info.setHardwareMileageSensorId(sensor.getId());
					}
				}
			} else {
				logger.info(String.format("Failed to load Sensor List for trackerId: %d %s", info.getTrackerId(),sensorResponse));
				throw new RuntimeException(sensorResponse.getStatus().getDescription());
			}
		});
		logger.info(req.toString());
		ReportGenResponse response = trackerService.generateReport(req);
		return response;
	}

	@RequestMapping("/stat/list/{reportId}")
	public List<FuelStatResult> getStatisticsByReport(@PathVariable("reportId") Long reportId) {
		return trackerService.getFuelStatisticsResultListByReportId(reportId);
	}
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
	@RequestMapping("/genlist")
	public ResponseContainer<List<ReportGen>> getGeneratedReportList() {
		ResponseContainer <List<ReportGen>> response = new ResponseContainer<>();
		try {
			response.setPayload(trackerService.getReportGenList());
			response.setSuccess(true);
		} catch(Exception e) {
			e.printStackTrace();
			ResponseStatus status = new ResponseStatus();
			status.setDescription(e.getMessage());
			response.setStatus(status);
		}
		return response;
	}
	@RequestMapping("/genlist/inprogress")
	public  ResponseContainer<List<Long>> getGenListInProgress() {
		ResponseContainer<List<Long>> response = new ResponseContainer<>();
		try {
			response.setPayload(trackerService.getReportGenListInProgress());
			response.setSuccess(true);
		} catch(Exception e) {
			e.printStackTrace();
			ResponseStatus status = new ResponseStatus();
			status.setDescription(e.getMessage());
			response.setStatus(status);
		}
		
		return response;
	}
}
