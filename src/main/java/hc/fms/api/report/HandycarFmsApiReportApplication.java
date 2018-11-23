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
		GenerateRequest genReq = new GenerateRequest();
		genReq.setHash("abe373f67db97cfdab502e26195b1735");
		genReq.setTrackers(Arrays.asList(73, 69));
		genReq.setFrom("2018-11-04 00:00:00");
		genReq.setTo("2018-11-10 23:59:59");
		genReq.setTimeFilter(new TimeFilter());
		Plugin plugin = new Plugin();
		plugin.setFilter(true);
		List<Plugin.Sensor> sensors = new ArrayList<>();
		Plugin.Sensor sensor = new Plugin.Sensor();
		sensor.setTrackerId(73);
		sensor.setSensorId(643);
		sensors.add(sensor);
		
		sensor = new Plugin.Sensor();
		sensor.setTrackerId(69);
		sensor.setSensorId(645);
		sensors.add(sensor);
		
		plugin.setSensors(sensors);
		genReq.setPlugin(plugin);
		/*
		ObjectMapper mapper = new ObjectMapper();
		System.out.println(mapper.writeValueAsString(genReq));
		*/
		//ReportGenResponse genResponse = trackerService.requestReportGen(genReq);
		//System.out.println(genResponse);
		FuelConsumptionReportResponse reportConsumptionResponse = trackerService.retrieveReport("abe373f67db97cfdab502e26195b1735", 514);
		if(reportConsumptionResponse.isSuccess()) {
			ReportDesc report = reportConsumptionResponse.getReport();
			List<Sheet> sheets = report.getSheets();
			for(Sheet sheet: sheets) {
				List<Section> sectionList = sheet.getSections().stream().filter(section -> section.getType().equalsIgnoreCase("table")).collect(Collectors.toList());
				for(Section section: sectionList) {
					System.out.println(section.getHeader());
					section.getData().stream().forEach(datum -> datum.getRows().stream().forEach(row-> System.out.println(row)));
					
				}
				System.out.println("-------------------------------------------------\n\n\n");
			}
		}
		//System.out.println(reportConsumptionResponse);
	}

}
