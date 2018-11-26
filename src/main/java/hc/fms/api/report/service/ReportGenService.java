package hc.fms.api.report.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hc.fms.api.report.entity.ReportGen;
import hc.fms.api.report.model.Section;
import hc.fms.api.report.model.fuel.ReportDesc;
import hc.fms.api.report.model.fuel.Sheet;
import hc.fms.api.report.repository.ReportGenRepository;

@Service
public class ReportGenService {
	@Autowired
	private ReportGenRepository reportGenRepository;
	
	public ReportGen logReportGen(ReportGen reportGen) {
		reportGen.setCreatedDate(new Date());
		return reportGenRepository.save(reportGen);
	}

	public void analyzeReport(ReportDesc report) {
		report.getSheets().forEach(this::procSheet);
		List<Sheet> sheets = report.getSheets();
		for(Sheet sheet: sheets) {
			
			List<Section> sectionList = sheet.getSections().stream().filter(section -> section.getType().equalsIgnoreCase("table")).collect(Collectors.toList());
			for(int i = 0; i < sectionList.size(); i++) {
				System.out.println(sectionList.get(i).getHeader());
				sectionList.get(i).getData().stream().forEach(datum -> datum.getRows().stream().forEach(row-> System.out.println(row)));
			}
		}
	}
	public void procSheet(Sheet sheet) {
		//sheet.getSections().stream().filter(section -> section.getType().equalsIgnoreCase("table")).collect(Collectors.toList())
	}
}
