package hc.fms.api.report.model;

import java.util.List;
import java.util.Map;

import hc.fms.api.report.entity.GenSection;
import hc.fms.api.report.entity.ReportGen;
import hc.fms.api.report.model.fuel.SectionStat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString
public class ExportableReport {
	private Map<Long, List<SectionStat>> sectionDataMap;
	private ReportGen reportGen;
	private List<GenSection> sectionInfoList;
}
