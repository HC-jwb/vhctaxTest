package hc.fms.api.report;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import hc.fms.api.report.properties.FmsProperties;

@SpringBootApplication
@EnableConfigurationProperties({FmsProperties.class})
public class HandycarFmsApiReportApplication {
	public static void main(String[] args) {
		SpringApplication.run(HandycarFmsApiReportApplication.class, args);
	}
}
