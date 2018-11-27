package hc.fms.api.report.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hc.fms.api.report.entity.FuelStatDetail;
import hc.fms.api.report.entity.FuelStatistics;
import hc.fms.api.report.entity.ReportGen;
import hc.fms.api.report.model.Section;
import hc.fms.api.report.model.fuel.ReportDesc;
import hc.fms.api.report.model.fuel.Sheet;
import hc.fms.api.report.repository.FuelStatDetailRepository;
import hc.fms.api.report.repository.FuelStatisticsRepository;
import hc.fms.api.report.repository.ReportGenRepository;

@Component
public class ReportProcessor {
	@Autowired
	private ReportGenRepository reportGenRepository;
	@Autowired
	private FuelStatisticsRepository fuelRepository;
	@Autowired
	private FuelStatDetailRepository fuelDetailRepository;

	public ReportGen logReportGen(ReportGen reportGen) {
		reportGen.setCreatedDate(new Date());
		return reportGenRepository.save(reportGen);
	}

	@Transactional
	public void process(ReportGen reportGenSaved, ReportDesc fuelReport, ReportDesc mileageReport) {
		List<Sheet> sheets = fuelReport.getSheets();
		for(Sheet sheet : sheets) {
			processSheet(sheet, "F", reportGenSaved.getId(), reportGenSaved.getFuelReportId());
		}
		sheets = mileageReport.getSheets();
		for(Sheet sheet : sheets) {
			processSheet(sheet, "M", reportGenSaved.getId(), reportGenSaved.getMileageReportId());
		}
		reportGenSaved.setFuelReportProcessed(true);
		reportGenRepository.save(reportGenSaved);
	}
	private void processSheet(Sheet sheet, final String type, final long reportId, final long generationId) {
		//String header = sheet.getHeader();
		final Long trackerId = sheet.getEntityIds().get(0);
		//filter only table type section
		List<Section> sectionList = sheet.getSections().stream().filter(section -> section.getType().equalsIgnoreCase("table")).collect(Collectors.toList());
		Section statSection = sectionList.get(0);
		List<FuelStatistics> statList = statSection.getData().get(0).getRows().stream().map(row -> {
			FuelStatistics fuelStat = new FuelStatistics();
			fuelStat.setReportId(reportId);
			fuelStat.setGenerationId(generationId);
			fuelStat.setTrackerId(trackerId);
			fuelStat.setType(type);
			fuelStat.setStatDate(row.getDate().getV());
			fuelStat.setMin(row.getMin().getRaw().doubleValue());
			fuelStat.setMax(row.getMax().getRaw().doubleValue());
			fuelStat.setAvg(row.getAvg().getRaw().doubleValue());
			fuelStat.setStatDate(row.getDate().getV());
			return fuelStat;
		}).collect(Collectors.toList());
		Section statDetailSection = sectionList.get(1);
		final List<FuelStatDetail> statDetailList = new ArrayList<>();
		statDetailSection.getData().forEach(datum -> {
			List<FuelStatDetail> detailList = datum.getRows().stream().map(row -> {
				if(row.getValue().getRaw() == null) return null;
				FuelStatDetail fuelDetail = new FuelStatDetail();
				fuelDetail.setReportId(reportId);
				fuelDetail.setType(type);
				fuelDetail.setGenerationId(generationId);
				fuelDetail.setTrackerId(trackerId);
				fuelDetail.setAddress(row.getAddress().getAddress());
				fuelDetail.setTrackerId(trackerId);
				fuelDetail.setMin(row.getMin().getRaw());
				fuelDetail.setMax(row.getMax().getRaw());
				fuelDetail.setAvg(row.getAvg().getRaw());
				fuelDetail.setTime(row.getTime().getV());
				if(row.getTime().getRaw() != null) {
					fuelDetail.setRawTime(row.getTime().getRaw().longValue());
				}
				fuelDetail.setValue(row.getValue().getRaw());
				return fuelDetail;
			}).collect(Collectors.toList());
			statDetailList.addAll(detailList);
		});
		statList = fuelRepository.saveAll(statList);
		//filter only non-null data and save'em all
		fuelDetailRepository.saveAll(
			statDetailList.stream().filter(fuelDetail -> {
				return (fuelDetail != null);
			}).collect(Collectors.toList())
		);
		
	}
	
}
