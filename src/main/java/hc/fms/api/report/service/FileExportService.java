package hc.fms.api.report.service;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import hc.fms.api.report.entity.GenSection;
import hc.fms.api.report.entity.ReportGen;
import hc.fms.api.report.model.SectionStat;
@Service
public class FileExportService {
	private static String [] fuleReportColumnNames = {"일자", "연료소비량(L)", "운행거리(KM)", "연비(KM/L)"};
	public static ByteArrayInputStream exportToExcel(ReportGen reportGen, Map<Long, GenSection> sectionMap, List<SectionStat> statList) {
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = null;
		for(SectionStat section: statList) {
			sheet = workbook.createSheet("sheetName here");
		}
		
		
		return null;
	}
}

