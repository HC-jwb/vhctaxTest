package hc.fms.api.report;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.media.jfxmedia.logging.Logger;

import hc.fms.api.report.model.FuelConsumptionReportResponse;
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
import hc.fms.api.report.properties.FmsProperties;
import hc.fms.api.report.service.AuthService;
import hc.fms.api.report.service.TrackerService;

@SpringBootApplication
@EnableConfigurationProperties({FmsProperties.class})
public class HandycarFmsApiReportApplication implements org.springframework.boot.CommandLineRunner {
	@org.springframework.beans.factory.annotation.Autowired
	private AuthService authService;
	@org.springframework.beans.factory.annotation.Autowired
	private TrackerService trackerService;
	public static void main(String[] args) {
		SpringApplication.run(HandycarFmsApiReportApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
/*		
		System.out.println(authService.sendAuth("test@cesco.co.kr", "123456"));
		TripResponse tripResponse = trackerService.getTrip("abe373f67db97cfdab502e26195b1735", 78, "2018-10-22 00:00:00", "2018-10-22 23:59:00");
		if(tripResponse.getSuccess()) {
			tripResponse.getList().stream().forEach(trip -> System.out.println(trip));
		} else {
			System.out.println("Error " + tripResponse.toString());
		}
		trackerService.getSensorList("abe373f67db97cfdab502e26195b1735", 78).getList().stream().forEach(sensor -> System.out.println(sensor));
*/
		//System.out.println(trackerService.getGroupList("abe373f67db97cfdab502e26195b1735"));

		
		//ObjectMapper mapper = new ObjectMapper();
		//System.out.println(mapper.writeValueAsString(genReq));
		getSensorListOfTracker(trackerService);
		getTrackers(trackerService);
		getTripSample(trackerService);
		/*requestGenFuelReportSample(trackerService);*/
		/*requestGenHwMileageReportSample(trackerService)*/;
		//retrieveFuelReportSample(trackerService);
		retrieveMileageReportSample(trackerService);
		

	}
	public static void getSensorListOfTracker(TrackerService service) {
		SensorResponse sensorReponse = service.getSensorList("abe373f67db97cfdab502e26195b1735", 71);
		sensorReponse.getList().stream().forEach(System.out::println);;
	}
	private static void getTrackers(TrackerService service) {
		TrackerResponse trackListResponse = service.getTrackerList("abe373f67db97cfdab502e26195b1735");
		trackListResponse.getList().stream().forEach(System.out::println);
	}
	private static void getTripSample(TrackerService service) {
		TripResponse tripResponse = service.getTrip("abe373f67db97cfdab502e26195b1735", 71, "2018-11-23 00:00:00", "2018-11-23 23:59:00");
		tripResponse.getList().stream().forEach(System.out::println);		
	}
	private static int requestGenFuelReportSample(TrackerService service) {
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
/*		
		sensor = new Plugin.Sensor();
		sensor.setTrackerId(69);
		sensor.setSensorId(645);
		sensors.add(sensor);
*/		
		plugin.setSensors(sensors);
		genReq.setPlugin(plugin);
		ReportGenResponse genResponse = service.requestReportGen(genReq);
		System.out.println(genResponse);
		return genResponse.getId();
	}
	private static int requestGenHwMileageReportSample(TrackerService service) {
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
/*		
		sensor = new Plugin.Sensor();
		sensor.setTrackerId(69);
		sensor.setSensorId(645);
		sensors.add(sensor);
*/		
		plugin.setSensors(sensors);
		genReq.setPlugin(plugin);
		ReportGenResponse genResponse = service.requestReportGen(genReq);
		System.out.println(genResponse);
		return genResponse.getId();
	}
	private static void retrieveFuelReportSample(TrackerService service) {
		FuelConsumptionReportResponse reportConsumptionResponse = service.retrieveReport("abe373f67db97cfdab502e26195b1735", 538);
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
	private static void retrieveMileageReportSample(TrackerService service) {
		FuelConsumptionReportResponse reportConsumptionResponse = service.retrieveReport("abe373f67db97cfdab502e26195b1735", 540);
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
