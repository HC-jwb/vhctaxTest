package hc.fms.api.addon.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ConfigurationProperties(prefix="upload")
@Getter
@Setter
@ToString
public class FileUploadProperties {
	private String uploadDir;
}
