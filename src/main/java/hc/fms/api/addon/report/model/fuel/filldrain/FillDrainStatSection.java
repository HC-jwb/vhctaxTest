package hc.fms.api.addon.report.model.fuel.filldrain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import hc.fms.api.addon.report.entity.FillDrainStatistics;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString
@JsonIgnoreProperties({"TYPE_FILLING", "TYPE_DRAINING"})
public class FillDrainStatSection {
	public static final String TYPE_FILLING = "F";
	public static final String TYPE_DRAINING = "D";
	private List<FillDrainStatistics> statList;
	private List<FillDrainStatistics> fillingList;
	private List<FillDrainStatistics> drainingList;
	private Double percentMin = 20D;//20% 이상
	
	
	public void analyze() {
	
	}

}
