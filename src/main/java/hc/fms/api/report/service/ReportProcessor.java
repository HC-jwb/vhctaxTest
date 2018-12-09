package hc.fms.api.report.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hc.fms.api.report.entity.FillDrainStatistics;
import hc.fms.api.report.entity.FuelStatDetail;
import hc.fms.api.report.entity.FuelStatistics;
import hc.fms.api.report.entity.GenSection;
import hc.fms.api.report.entity.ReportGen;
import hc.fms.api.report.model.fuel.Datum;
import hc.fms.api.report.model.fuel.FuelMileageRow;
import hc.fms.api.report.model.fuel.FuelMileageSection;
import hc.fms.api.report.model.fuel.ReportDesc;
import hc.fms.api.report.model.fuel.Sheet;
import hc.fms.api.report.model.fuel.filldrain.FillDrainRow;
import hc.fms.api.report.model.fuel.filldrain.FillDrainSection;
import hc.fms.api.report.repository.FillDrainStatisticsRepository;
import hc.fms.api.report.repository.FuelStatDetailRepository;
import hc.fms.api.report.repository.FuelStatisticsRepository;
import hc.fms.api.report.repository.GenSectionRepository;
import hc.fms.api.report.repository.ReportGenRepository;

@Component
public class ReportProcessor {
	@Autowired
	private ReportGenRepository reportGenRepository;
	@Autowired
	private GenSectionRepository genSectionRepository;
	@Autowired
	private FuelStatisticsRepository fuelRepository;
	@Autowired
	private FuelStatDetailRepository fuelDetailRepository;
	@Autowired
	private FillDrainStatisticsRepository fillDrainStatRepository;
	
	private Logger logger = LoggerFactory.getLogger(ReportProcessor.class);
	public ReportGen logReportGen(ReportGen reportGen) {
		reportGen.setCreatedDate(new Date());
		return reportGenRepository.save(reportGen);
	}

	@Transactional
	public void processFuelMileageReport(ReportGen reportGenSaved, ReportDesc<FuelMileageSection> fuelReport, ReportDesc<FuelMileageSection> mileageReport) {
		List<Sheet<FuelMileageSection>> sheets = fuelReport.getSheets();
		for(Sheet<FuelMileageSection> sheet : sheets) {
			logger.info("ReportProcessor.process -> fuel sheet " + sheet);
			processFuelMileageSheet(sheet, "F", reportGenSaved.getId(), reportGenSaved.getFuelReportId());
			logger.info("fuel sheet processed");
		}
		sheets = mileageReport.getSheets();
		for(Sheet<FuelMileageSection> sheet : sheets) {
			logger.info("sheet " + sheet);
			processFuelMileageSheet(sheet, "M", reportGenSaved.getId(), reportGenSaved.getMileageReportId());
			logger.info("sheet processed");
		}
		List<GenSection> genSectionList = new ArrayList<>();
		for(Sheet<FuelMileageSection> sheet : sheets) {
			GenSection genSection = new GenSection();
			genSection.setReportId(reportGenSaved.getId());
			genSection.setTrackerId(sheet.getEntityIds().get(0));
			genSection.setHeader(sheet.getHeader());
			genSectionList.add(genSection);
		}
/*		
		List<GenSection> genSectionList = sheets.stream().map(sheet -> {
			GenSection genSection = new GenSection();
			genSection.setReportId(reportGenSaved.getId());
			genSection.setTrackerId(sheet.getEntityIds().get(0));
			genSection.setHeader(sheet.getHeader());
			return genSection;
		}).collect(Collectors.toList());
*/
		reportGenSaved.setFuelReportProcessed(true);
		reportGenRepository.save(reportGenSaved);
		genSectionRepository.saveAll(genSectionList);
	}
	private void processFuelMileageSheet(Sheet<FuelMileageSection> sheet, final String type, final long reportId, final long generationId) {
		final Long trackerId = sheet.getEntityIds().get(0);
		//filter only table type section
		List<FuelMileageSection> sectionList = sheet.getSections().stream().filter(section -> "table".equalsIgnoreCase(section.getType())).collect(Collectors.toList());
		if(sectionList.size() == 0 ) {
			logger.info("No data found in sections");
			return;
		}
		FuelMileageSection statSection = sectionList.get(0);
		List<FuelMileageRow> sectionRows = statSection.getData().get(0).getRows();
		List<FuelStatistics> statList = new ArrayList<>();
		FuelStatistics fuelStat;
		for(FuelMileageRow row: sectionRows) {
			fuelStat = new FuelStatistics();
			try {
				fuelStat.setReportId(reportId);
				fuelStat.setGenerationId(generationId);
				fuelStat.setTrackerId(trackerId);
				fuelStat.setType(type);
				fuelStat.setStatDate(row.getDate().getV());
				fuelStat.setRawDate(row.getDate().getRaw());

				if(row.getMin().getRaw() != null) fuelStat.setMin(row.getMin().getRaw().doubleValue());
				if(row.getMax().getRaw() != null) fuelStat.setMax(row.getMax().getRaw().doubleValue());
				fuelStat.setStatDate(row.getDate().getV());
				statList.add(fuelStat);
			} catch(Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
/*		
		List<FuelStatistics> statList = statSection.getData().get(0).getRows().stream().map(row -> {
			FuelStatistics fuelStat = new FuelStatistics();
			try {
				fuelStat.setReportId(reportId);
				fuelStat.setGenerationId(generationId);
				fuelStat.setTrackerId(trackerId);
				fuelStat.setType(type);
				fuelStat.setStatDate(row.getDate().getV());
				fuelStat.setRawDate(row.getDate().getRaw());

				if(row.getMin().getRaw() != null) fuelStat.setMin(row.getMin().getRaw().doubleValue());
				if(row.getMax().getRaw() != null) fuelStat.setMax(row.getMax().getRaw().doubleValue());
				fuelStat.setStatDate(row.getDate().getV());
			} catch(Exception e) {
				e.printStackTrace();
				throw e;
			}
			return fuelStat;
		}).collect(Collectors.toList());
*/		
		FuelMileageSection statDetailSection = sectionList.get(1);
		final List<FuelStatDetail> statDetailList = new ArrayList<>();
		List<Datum<FuelMileageRow>> data =  statDetailSection.getData();
		for(Datum<FuelMileageRow> datum : data) {
			List<FuelStatDetail> detailList = new ArrayList<>();
			List<FuelMileageRow> rows = datum.getRows();
			for(FuelMileageRow row : rows) {
				try {
					if(row.getValue().getRaw() == null) {
						detailList.add(null);
						continue;
					}
					FuelStatDetail fuelDetail = new FuelStatDetail();
					fuelDetail.setReportId(reportId);
					fuelDetail.setType(type);
					fuelDetail.setGenerationId(generationId);
					fuelDetail.setTrackerId(trackerId);
					fuelDetail.setAddress(row.getAddress().getAddress());
					fuelDetail.setTrackerId(trackerId);
					fuelDetail.setMin(row.getMin().getRaw());
					fuelDetail.setMax(row.getMax().getRaw());
					fuelDetail.setTime(row.getTime().getV());
					if(row.getTime().getRaw() != null) {
						fuelDetail.setRawTime(row.getTime().getRaw().longValue());
					}
					fuelDetail.setValue(row.getValue().getRaw());
					detailList.add(fuelDetail);
				} catch(Exception e) {
					e.printStackTrace();
					throw e;
				}
			}
			statDetailList.addAll(detailList);
		}
/*		
		statDetailSection.getData().forEach(datum -> {
			List<FuelStatDetail> detailList = datum.getRows().stream().map(row -> {
				try {
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
					fuelDetail.setTime(row.getTime().getV());
					if(row.getTime().getRaw() != null) {
						fuelDetail.setRawTime(row.getTime().getRaw().longValue());
					}
					fuelDetail.setValue(row.getValue().getRaw());
					return fuelDetail;
				} catch(Exception e) {
					e.printStackTrace();
					throw e;
				}
				
			}).collect(Collectors.toList());
			statDetailList.addAll(detailList);
		});
*/		
		statList = fuelRepository.saveAll(statList);
		//filter only non-null data and save'em all
/*		
		List<FuelStatDetail> filteredStatDetailList = statDetailList.stream().filter(fuelDetail -> {
			return (fuelDetail != null);
		}).collect(Collectors.toList());
		for(FuelStatDetail detail: filteredStatDetailList) {
			System.out.println(detail);
			fuelDetailRepository.save(detail);
		}
*/
		fuelDetailRepository.saveAll(statDetailList.stream().filter(fuelDetail -> {
			return (fuelDetail != null);
		}).collect(Collectors.toList()));
	}
	@Transactional
	public void processFillDrainReport(ReportGen reportGenSaved, ReportDesc<FillDrainSection> fillDrainReport) {
		List<Sheet<FillDrainSection>> sheets = fillDrainReport.getSheets();
		for(Sheet<FillDrainSection> sheet : sheets) {
			logger.info("ReportProcessor.process -> FillDrain sheet " + sheet);
			processFillDrainSheet(sheet, reportGenSaved.getId(), reportGenSaved.getFillDrainReportId());
			logger.info("fuel sheet processed");
		}
		
		List<GenSection> genSectionList = new ArrayList<>();
		for(Sheet<FillDrainSection> sheet : sheets) {
			GenSection genSection = new GenSection();
			genSection.setReportId(reportGenSaved.getId());
			genSection.setTrackerId(sheet.getEntityIds().get(0));
			genSection.setHeader(sheet.getHeader());
			genSectionList.add(genSection);
		}
		reportGenSaved.setFuelReportProcessed(true);
		reportGenRepository.save(reportGenSaved);
		genSectionRepository.saveAll(genSectionList);
	}
	private void processFillDrainSheet(Sheet<FillDrainSection> sheet, final long reportId, final long generationId) {
		final Long trackerId = sheet.getEntityIds().get(0);
		//filter only table type section
		List<FillDrainSection> sectionList = sheet.getSections().stream().filter(section -> "table".equalsIgnoreCase(section.getType())).collect(Collectors.toList());
		if(sectionList.size() == 0 ) {
			logger.info("No data found in FillDrain sections");
			return;
		}
		FillDrainSection statSection = sectionList.get(0);
		List<FillDrainRow> sectionRows = statSection.getData().get(0).getRows();
		
		List<FillDrainStatistics> statList = new ArrayList<>();
		FillDrainStatistics fillDrainStat;
		for(FillDrainRow row: sectionRows) {
			fillDrainStat = new FillDrainStatistics();
			try {
				fillDrainStat.setReportId(reportId);
				fillDrainStat.setGenerationId(generationId);
				fillDrainStat.setTrackerId(trackerId);
				//fillDrainStat.setType(type);
				fillDrainStat.setEventDate(row.getTime().getV());
				fillDrainStat.setRawDate(row.getTime().getRaw());
				fillDrainStat.setAddress(row.getAddress().getV());
				fillDrainStat.setVolume(row.getVolume().getRaw());
				fillDrainStat.setMileageFrom(row.getMileage().getRaw());
				fillDrainStat.setEventId(row.getNumber().getRaw());
				fillDrainStat.setType(String.valueOf(row.getType().getV().charAt(0)));
				statList.add(fillDrainStat);
			} catch(Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		statList = fillDrainStatRepository.saveAll(statList);
	}
}
