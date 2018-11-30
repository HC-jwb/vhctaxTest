package hc.fms.api.report.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import hc.fms.api.report.model.tracker.request.TrackerInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@JsonIgnoreProperties({"hash", "formatter"})
public class ReportGenFlatRequest {
	//trackers => [{trackerId:73, sensorId: 647} ,{trackerId:69, sensorId: 645}], from=> 2018-11-23 00:00:00, to=> 2018-11-23 23:59:59, detailsIntervalMinutes => 360
	private List<TrackerInfo> trackers;
	private String from;
	private String to;
	private int intervalInMin;
	private String label;
	private static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private String hash;
	public void normalize() {
		this.from = String.format("%s %s", this.from, "00:00:00");
		this.to = String.format("%s %s", this.to, "00:00:00");
		Date date;
		try {
			date = formatter.parse(to);
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.SECOND, -1);
			this.to= formatter.format(cal.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
	}
}
