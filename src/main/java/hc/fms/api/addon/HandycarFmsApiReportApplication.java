package hc.fms.api.addon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import hc.fms.api.addon.properties.FmsProperties;
import hc.fms.api.addon.properties.FileUploadProperties;

@SpringBootApplication
@EnableConfigurationProperties({FmsProperties.class, FileUploadProperties.class})
public class HandycarFmsApiReportApplication {
	public static void main(String[] args) {
		SpringApplication.run(HandycarFmsApiReportApplication.class, args);
	}
}
