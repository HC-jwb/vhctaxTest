package hc.fms.api.report.model.trip;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Trip {
	private Long id;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone="Asia/Seoul")
	@JsonProperty("start_date")
	private Date startDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss", timezone="Asia/Seoul")
	@JsonProperty("end_date")
	private Date endDate;
	
	@JsonProperty("avg_speed")
	private Integer avgSpeed;
	
	@JsonProperty("max_speed")
	private Integer maxSpeed;
	
	private Double length;
	
	private Integer points;
	
	private String type;
	@JsonProperty("start_address")
	private String startAddress;
	@JsonProperty("end_address")
	private String endAddress;
}
