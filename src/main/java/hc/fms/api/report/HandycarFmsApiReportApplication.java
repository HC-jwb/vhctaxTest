package hc.fms.api.report;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import hc.fms.api.report.model.TripResponse;
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
		System.out.println(trackerService.getGroupList("abe373f67db97cfdab502e26195b1735"));
	}

}
