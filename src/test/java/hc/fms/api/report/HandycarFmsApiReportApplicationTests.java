package hc.fms.api.report;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import hc.fms.api.report.model.ReportResponse;
import hc.fms.api.report.model.ReportGenResponse;
import hc.fms.api.report.model.Section;
import hc.fms.api.report.model.SensorResponse;
import hc.fms.api.report.model.TrackerResponse;
import hc.fms.api.report.model.TripResponse;
import hc.fms.api.report.model.fuel.Plugin;
import hc.fms.api.report.model.fuel.ReportDesc;
import hc.fms.api.report.model.fuel.Sheet;
import hc.fms.api.report.model.fuel.TimeFilter;
import hc.fms.api.report.model.tracker.request.GenerateRequest;
import hc.fms.api.report.service.AuthService;
import hc.fms.api.report.service.TrackerService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HandycarFmsApiReportApplicationTests {
	@Autowired
	private AuthService authService;
	@Autowired
	private TrackerService trackerService;
	@Test
	private void getTrackers() {
		TrackerResponse trackListResponse = trackerService.getTrackerList("abe373f67db97cfdab502e26195b1735");
		trackListResponse.getList().stream().forEach(System.out::println);
	}
	public void getSensorListOfTracker() {
		SensorResponse sensorReponse = trackerService.getSensorList("abe373f67db97cfdab502e26195b1735", 71);
		sensorReponse.getList().stream().forEach(System.out::println);;
	}
	
	private void getTripSample() {
		TripResponse tripResponse = trackerService.getTrip("abe373f67db97cfdab502e26195b1735", 71, "2018-11-23 00:00:00", "2018-11-23 23:59:00");
		tripResponse.getList().stream().forEach(System.out::println);		
	}
	private long requestGenFuelReportSample() {
		GenerateRequest genReq = new GenerateRequest();
		genReq.setHash("abe373f67db97cfdab502e26195b1735");
		//genReq.setTrackers(Arrays.asList(73, 69));
		genReq.setTrackers(Arrays.asList(71));
		genReq.setFrom("2018-11-23 00:00:00");
		genReq.setTo("2018-11-23 23:59:59");
		genReq.setTimeFilter(new TimeFilter());
		Plugin plugin = new Plugin();
		plugin.setDetailsIntervalMinutes(5);
		plugin.setShowAddress(false);
		plugin.setFilter(true);
		List<Plugin.Sensor> sensors = new ArrayList<>();
		Plugin.Sensor sensor = new Plugin.Sensor();
		sensor.setTrackerId(71);
		sensor.setSensorId(647);
		sensors.add(sensor);

		sensor = new Plugin.Sensor();
		sensor.setTrackerId(69);
		sensor.setSensorId(645);
		sensors.add(sensor);
		
		plugin.setSensors(sensors);
		genReq.setPlugin(plugin);
		ReportGenResponse genResponse = trackerService.requestReportGen(genReq);
		System.out.println(genResponse);
		return genResponse.getId();
	}
	private long requestGenHwMileageReportSample() {
		//651 <-- tracker_id : 71
		GenerateRequest genReq = new GenerateRequest();
		genReq.setHash("abe373f67db97cfdab502e26195b1735");
		//genReq.setTrackers(Arrays.asList(73, 69));
		genReq.setTrackers(Arrays.asList(71));
		genReq.setFrom("2018-11-23 00:00:00");
		genReq.setTo("2018-11-23 23:59:59");
		genReq.setTimeFilter(new TimeFilter());
		Plugin plugin = new Plugin();
		plugin.setDetailsIntervalMinutes(5);
		plugin.setShowAddress(false);
		plugin.setFilter(true);
		List<Plugin.Sensor> sensors = new ArrayList<>();
		Plugin.Sensor sensor = new Plugin.Sensor();
		sensor.setTrackerId(71);
		sensor.setSensorId(651);
		sensors.add(sensor);
		
		sensor = new Plugin.Sensor();
		sensor.setTrackerId(69);
		sensor.setSensorId(645);
		sensors.add(sensor);
		
		plugin.setSensors(sensors);
		genReq.setPlugin(plugin);
		ReportGenResponse genResponse = trackerService.requestReportGen(genReq);
		System.out.println(genResponse);
		return genResponse.getId();
	}
	private void retrieveFuelReportSample() {
		ReportResponse reportConsumptionResponse = trackerService.retrieveReport("abe373f67db97cfdab502e26195b1735", 538);
		System.out.println(reportConsumptionResponse.getStatus());
		if(reportConsumptionResponse.isSuccess()) {
			ReportDesc report = reportConsumptionResponse.getReport();
			List<Sheet> sheets = report.getSheets();
			for(Sheet sheet: sheets) {
				List<Section> sectionList = sheet.getSections().stream().filter(section -> section.getType().equalsIgnoreCase("table")).collect(Collectors.toList());
				for(int i = 0; i < sectionList.size(); i++) {
					if(i != 1) continue; 
					System.out.println(sectionList.get(i).getHeader());
					sectionList.get(i).getData().stream().forEach(datum -> datum.getRows().stream().forEach(row-> System.out.println(row)));
					
				}
				System.out.println("-------------------------------------------------\n\n\n");
			}
		}
	}
	private void retrieveMileageReportSample() {
		ReportResponse reportConsumptionResponse = trackerService.retrieveReport("abe373f67db97cfdab502e26195b1735", 540);
		System.out.println(reportConsumptionResponse.getStatus());
		if(reportConsumptionResponse.isSuccess()) {
			ReportDesc report = reportConsumptionResponse.getReport();
			List<Sheet> sheets = report.getSheets();
			for(Sheet sheet: sheets) {
				List<Section> sectionList = sheet.getSections().stream().filter(section -> section.getType().equalsIgnoreCase("table")).collect(Collectors.toList());
				for(int i = 0; i < sectionList.size(); i++) {
					if(i != 1) continue; 
					System.out.println(sectionList.get(i).getHeader());
					sectionList.get(i).getData().stream().forEach(datum -> datum.getRows().stream().forEach(row-> System.out.println(row)));
					
				}
				System.out.println("-------------------------------------------------\n\n\n");
			}
		}
	}
}
