package hc.fms.api.report.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import hc.fms.api.report.entity.GenSection;
import hc.fms.api.report.model.ExportableReport;
import hc.fms.api.report.model.fuel.FuelStat;
import hc.fms.api.report.model.fuel.SectionStat;
@Service
public class FileExportService {
	private static String [] fuleReportColumnNames = {"일자", "연료소비량(L)", "운행거리(KM)", "연비(KM/L)"};
	public ByteArrayInputStream exportToExcel(ExportableReport report) {
		Cell cell;
		Font font;
		CellStyle style;
		XSSFWorkbook workbook = new XSSFWorkbook();
		try (
			ByteArrayOutputStream out = new ByteArrayOutputStream();
		){
			XSSFSheet sheet = null;
			//ReportGen reportGen = report.getReportGen();
			List<GenSection> sectionInfoList = report.getSectionInfoList();
			Map<Long, List<SectionStat>> sectionMap = report.getSectionDataMap();
			List<SectionStat> sectionStatList;
			int colCount = fuleReportColumnNames.length;
			int rowNum = 0;
			Row row;
			int colOffset = 1;
			for(GenSection genSection: sectionInfoList) {
				sectionStatList = sectionMap.get(genSection.getTrackerId());
				for(SectionStat sectionStat: sectionStatList) {
					sheet = workbook.createSheet(genSection.getHeader());
					rowNum = 1;
					row = sheet.createRow(rowNum++);//header row
					row.setHeightInPoints(20);

					font = workbook.createFont();
					//font.setFontName("Arial");
					font.setColor(IndexedColors.WHITE.getIndex());
					font.setFontHeightInPoints((short)11);

					for(int colIdx = 0; colIdx < colCount; colIdx++) {
						style = workbook.createCellStyle();
						style.setAlignment(CellStyle.ALIGN_CENTER);
						style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
						style.setFillForegroundColor(IndexedColors.TEAL.getIndex());
						style.setFillPattern(CellStyle.SOLID_FOREGROUND);
						style.setFont(font);
						style.setAlignment(CellStyle.ALIGN_CENTER);
						style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
						
						font.setColor(IndexedColors.WHITE.getIndex());
						if(colIdx == 0) {
							style.setBorderLeft(CellStyle.BORDER_THIN);
							style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
							
							style.setBorderRight(CellStyle.BORDER_THIN);
							style.setRightBorderColor(IndexedColors.WHITE.getIndex());
							
						} else if(colIdx == (colCount-1)) {
							style.setBorderRight(CellStyle.BORDER_THIN);
							style.setRightBorderColor(IndexedColors.BLACK.getIndex());
						} else {
							style.setBorderLeft(CellStyle.BORDER_NONE);
							style.setBorderRight(CellStyle.BORDER_THIN);
							style.setRightBorderColor(IndexedColors.WHITE.getIndex());
						}
						cell = row.createCell(colIdx + colOffset);
						cell.setCellValue(fuleReportColumnNames[colIdx]);
						cell.setCellStyle(style);
						
					}
					for(FuelStat stat : sectionStat.getStatList()) {
						row = sheet.createRow(rowNum++);//create data row
						font = workbook.createFont();
						//font.setFontName("Arial");
						font.setColor(IndexedColors.BLACK.getIndex());
						font.setFontHeightInPoints((short)11);
						
						cell = row.createCell(0 + colOffset);
						style = workbook.createCellStyle();
						style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
						style.setAlignment(CellStyle.ALIGN_CENTER);
						style.setBorderLeft(CellStyle.BORDER_THIN);
						style.setBorderRight(CellStyle.BORDER_THIN);
						style.setBorderTop(CellStyle.BORDER_THIN);
						style.setBorderBottom(CellStyle.BORDER_THIN);
						style.setFont(font);
						cell.setCellStyle(style);
						cell.setCellValue(stat.getStatDate());
						
						cell = row.createCell(1 + colOffset);
						style = workbook.createCellStyle();
						style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
						style.setAlignment(CellStyle.ALIGN_RIGHT);
						style.setBorderLeft(CellStyle.BORDER_THIN);
						style.setBorderRight(CellStyle.BORDER_THIN);
						style.setBorderTop(CellStyle.BORDER_THIN);
						style.setBorderBottom(CellStyle.BORDER_THIN);
						style.setFont(font);
						cell.setCellStyle(style);
						cell.setCellValue(stat.getFuelUsed());
						
						cell = row.createCell(2 + colOffset);
						style = workbook.createCellStyle();
						style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
						style.setAlignment(CellStyle.ALIGN_RIGHT);
						style.setBorderLeft(CellStyle.BORDER_THIN);
						style.setBorderRight(CellStyle.BORDER_THIN);
						style.setBorderTop(CellStyle.BORDER_THIN);
						style.setBorderBottom(CellStyle.BORDER_THIN);
						style.setFont(font);
						cell.setCellStyle(style);
						cell.setCellValue(stat.getDistanceTravelled());
						
						cell = row.createCell(3 + colOffset);
						style = workbook.createCellStyle();
						style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
						style.setAlignment(CellStyle.ALIGN_RIGHT);
						style.setBorderLeft(CellStyle.BORDER_THIN);
						style.setBorderRight(CellStyle.BORDER_THIN);
						style.setBorderTop(CellStyle.BORDER_THIN);
						style.setBorderBottom(CellStyle.BORDER_THIN);
						style.setFont(font);
						cell.setCellStyle(style);
						cell.setCellValue(stat.getFuelEffRate());
					}
					row = sheet.createRow(rowNum++);//create summary row
	
					cell = row.createCell(0 + colOffset);
					style = workbook.createCellStyle();
					style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
					style.setAlignment(CellStyle.ALIGN_RIGHT);
					style.setBorderLeft(CellStyle.BORDER_THIN);
					style.setBorderRight(CellStyle.BORDER_THIN);
					style.setBorderTop(CellStyle.BORDER_THIN);
					style.setBorderBottom(CellStyle.BORDER_THIN);
					font = workbook.createFont();

					font.setColor(IndexedColors.BLACK.getIndex());
					font.setFontHeightInPoints((short)11);
					style.setFont(font);
					cell.setCellStyle(style);
					cell.setCellValue("합 계");
					
					cell = row.createCell(1 + colOffset);
					style = workbook.createCellStyle();
					style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
					style.setAlignment(CellStyle.ALIGN_RIGHT);
					style.setBorderLeft(CellStyle.BORDER_THIN);
					style.setBorderRight(CellStyle.BORDER_THIN);
					style.setBorderTop(CellStyle.BORDER_THIN);
					style.setBorderBottom(CellStyle.BORDER_THIN);
					font = workbook.createFont();

					font.setColor(IndexedColors.BLACK.getIndex());
					font.setFontHeightInPoints((short)11);
					style.setFont(font);
					cell.setCellStyle(style);
					cell.setCellValue(sectionStat.getTotalFuelUsed());
					
					cell = row.createCell(2 + colOffset);
					style = workbook.createCellStyle();
					style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
					style.setAlignment(CellStyle.ALIGN_RIGHT);
					style.setBorderLeft(CellStyle.BORDER_THIN);
					style.setBorderRight(CellStyle.BORDER_THIN);
					style.setBorderTop(CellStyle.BORDER_THIN);
					style.setBorderBottom(CellStyle.BORDER_THIN);
					font = workbook.createFont();

					font.setColor(IndexedColors.BLACK.getIndex());
					font.setFontHeightInPoints((short)11);
					style.setFont(font);
					cell.setCellStyle(style);
					cell.setCellValue(sectionStat.getTotalDistanceTravelled());

					cell = row.createCell(3 + colOffset);
					style = workbook.createCellStyle();
					style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
					style.setAlignment(CellStyle.ALIGN_RIGHT);
					style.setBorderLeft(CellStyle.BORDER_THIN);
					style.setBorderRight(CellStyle.BORDER_THIN);
					style.setBorderTop(CellStyle.BORDER_THIN);
					style.setBorderBottom(CellStyle.BORDER_THIN);
					font = workbook.createFont();

					font.setColor(IndexedColors.BLACK.getIndex());
					font.setFontHeightInPoints((short)11);
					style.setFont(font);
					cell.setCellStyle(style);
					cell.setCellValue(sectionStat.getTotalFuelEffRate());
				}
				for(int i = 0; i < colCount; i++) {
					sheet.setColumnWidth(i + colOffset, 15 * 256);
				}
			}
			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}

