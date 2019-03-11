package hc.fms.api.addon.vhctax.report;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;

public class TaxTaskReportExporter {
	private PdfFont titleFont;
	private PdfFont tableListFont;
	private PdfFont tableHeaderFont;
	private Color headerColor;
	private Color listItemHgColor;
	private Color sumNotesBgColor;
	private static final int MAX_RECS_PAGE = 28;
	private static final int LIMIT_FOR_NOTES = 21;
	
	
	private ReportGenData genData;

	public TaxTaskReportExporter(ReportGenData data) throws IOException {
		titleFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
		headerColor = new DeviceRgb(200, 190, 240);
		listItemHgColor = new DeviceRgb(220, 210, 210);
		sumNotesBgColor = new DeviceRgb(125, 125, 200);
		tableListFont = PdfFontFactory.createFont(StandardFonts.HELVETICA);
		tableHeaderFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
		this.genData = data;
	}
	public InputStream exportPdfAsInputStream() throws Exception {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		createPdf(os);
		return new ByteArrayInputStream(os.toByteArray());
	}
	public void createPdf(OutputStream os) throws Exception {
		PdfWriter writer = new PdfWriter(os);
		PdfDocument pdf = new PdfDocument(writer);
		Document doc = new Document(pdf, PageSize.A4.rotate());
		doc.setMargins(20, 20, 20, 20);
		
		addTitlePage(doc);
		AreaBreak pageBreak = new AreaBreak(PageSize.A4.rotate());
		doc.add(pageBreak);
		List<List<String>> rows = genData.getRowData();
		addTable(doc, rows);
		
		if(rows.size() > 0 && (rows.size() % MAX_RECS_PAGE) > LIMIT_FOR_NOTES) {
			doc.add(pageBreak);
		}
		addSummaryNotes(doc);
		doc.close();
	}
	
	private void addTitlePage(Document doc) throws ParseException {
		String periodFrom = genData.getPeriodFrom(), periodTo = genData.getPeriodTo();
		String reportGenDate = genData.getReportGenDate();
		TimeZone timezone = TimeZone.getDefault();
		Paragraph title = new Paragraph("Tax Task Report")
		.setFont(titleFont)
		.setFontSize(33)
		.setTextAlignment(TextAlignment.CENTER);
		
		Paragraph subTitle = new Paragraph()
				.add(new Paragraph("For the Period: "))
				.add(new Paragraph(String.format("%s ~ %s",ReportGenData.convertDateFormat(periodFrom),ReportGenData.convertDateFormat(periodTo))))
				.setFontSize(27)
				.setTextAlignment(TextAlignment.CENTER);
		Paragraph footerTitle = new Paragraph()
				.add(new Paragraph(String.format("Created: %s", reportGenDate)))
				.setFontSize(20)
				.setTextAlignment(TextAlignment.CENTER);
		Paragraph tz = new Paragraph("TimeZone: " + timezone.getID())
				.setFontSize(18)
				.setTextAlignment(TextAlignment.CENTER);
		
		title.setMarginTop(100);
		subTitle.setMarginTop(80);
		footerTitle.setMarginTop(80);
		doc.add(title);
		doc.add(subTitle);
		doc.add(footerTitle);
		doc.add(tz);
	}
	private IBlockElement buildSearchCondLabel() {
		Paragraph condLabel = new Paragraph();
		Paragraph paymentType = new Paragraph("Payment Type: ")
		.add(new Paragraph(genData.getSoughtPaymentType()))
		.setFont(tableHeaderFont)
		.setFontSize(9);
		
		Paragraph paymentStat = new Paragraph("Payment Status: ")
		.add(new Paragraph(genData.getSoughtPaymentStat()))
		.setFont(tableHeaderFont)
		.setFontSize(9);
		
		paymentType.setMarginRight(35);
		paymentType.setMarginBottom(0);
		condLabel.add(paymentType).add(paymentStat);
		return condLabel;
	}
	private void addTable(Document doc, List<List<String>> rows) {
		Table table = new Table(ReportGenData.LIST_TABLE_COL_WIDTHS);
		table.setWidth(UnitValue.createPercentValue(100));
		process(table, Arrays.asList(Arrays.asList(ReportGenData.TABLE_COLS)), true);
		
		process(table, rows, false);
		doc.add(table);
	}
	private void process(Table table, List<List<String>> lines, boolean isHeader) {
		Cell cell;
		int col;
		List<String> line;
		String item;
		for(int row = 0; row < lines.size(); row++) {
			line = lines.get(row);
			if(isHeader) {
				cell = new Cell(1, 9);
				cell.add(buildSearchCondLabel());
				cell.setBorder(Border.NO_BORDER);
				table.addHeaderCell(cell);
			}
			for(col = 0; col < line.size(); col++) {
				item = line.get(col);
				if(isHeader) {
					cell = new Cell().add(new Paragraph(item).setFontSize(8.7F)).setFont(tableHeaderFont);
					cell.setTextAlignment(TextAlignment.CENTER);
					cell.setBackgroundColor(headerColor, 0.2F);
					table.addHeaderCell(cell);
				} else {
					cell = new Cell().add(new Paragraph(item).setFontSize(8.7F)).setFont(tableListFont);
					if(col == 5) {
						cell.setTextAlignment(TextAlignment.RIGHT);
					} else if(col == 7) {
						cell.setTextAlignment(TextAlignment.LEFT);
					} else {
						cell.setTextAlignment(TextAlignment.CENTER);
					}
					if(row % 2 != 0) cell.setBackgroundColor(listItemHgColor, 0.25F);
					table.addCell(cell);
				}
			}
		}
		
	}
	private void addSummaryNotes(Document doc) {
		Paragraph totalLabel = new Paragraph();
		
		Paragraph total = new Paragraph()
		.add(new Paragraph("Total: ").setMargin(0))
		.add(new Paragraph(genData.getTotalCount())
		.setMargin(0));
		
		Paragraph paid = new Paragraph()
		.add(new Paragraph("Paid: ").setMargin(0))
		.add(new Paragraph(genData.getPaidCount())
		.setMargin(0));
		
		Paragraph unpaid = new Paragraph()
		.add(new Paragraph("Unpaid: ").setMargin(0))
		.add(new Paragraph(genData.getUnpaidCount())
		.setMargin(0));
		
		total.setMarginRight(15);
		paid.setMarginRight(15);
		
		totalLabel.add(total).add(paid).add(unpaid).setFont(tableHeaderFont).setFontSize(8.5F);
		
		Table table = new Table(new float[] {120, 120});
		table.setWidth(240);
		Cell cell = new Cell()
			.add(new Paragraph("Unpaid Task"))
			.add(new Paragraph("Total Cost:"))
			.setFont(tableHeaderFont).setFontSize(8.7F)
			.setTextAlignment(TextAlignment.CENTER);
		table.addCell(cell);
		cell = new Cell()
			.add(
				new Paragraph(genData.getUnpaidCost())
				.setMarginTop(8).setMarginRight(5)
			)
			.setFont(tableHeaderFont)
			.setFontSize(9F)	
			.setTextAlignment(TextAlignment.RIGHT);
		table.addCell(cell);
		
		cell = new Cell()
			.add(new Paragraph("Completed Task"))
			.add(new Paragraph("Total Cost:"))
			.setFontSize(8.5F)
			.setFont(tableHeaderFont)
			.setTextAlignment(TextAlignment.CENTER);
		table.addCell(cell);
		cell = new Cell()
			.add(
				new Paragraph(genData.getPaidCost())
				.setMarginTop(8).setMarginRight(5)
			)
			.setFont(tableHeaderFont)
			.setFontSize(9F)
			.setTextAlignment(TextAlignment.RIGHT);
		table.addCell(cell);
		table.setBackgroundColor(sumNotesBgColor, 0.3F);
		
		
		Paragraph nearestTask = new Paragraph()
		.add(new Paragraph("The nearest payment task: ").setFont(tableHeaderFont).setMargin(0))
		.add(
			new Paragraph(genData.getNearestPaymentTaskToCome())
			.setMargin(0)
		)
		.setFontSize(8.5F)
		.setMarginBottom(0);
		
		Paragraph latestTask = new Paragraph()
		.add(new Paragraph("The latest payment task: ").setFont(tableHeaderFont).setMargin(0))
		.add(
			new Paragraph(genData.getFarthestPaymentTaskToCome())
			.setMargin(0)
		)
		.setFontSize(8.5F)
		.setMarginTop(0);

		doc.add(totalLabel)
		.add(table)
		.add(nearestTask)
		.add(latestTask);
	}
	
	public InputStream exportXlsAsInputStream() throws Exception {
		org.apache.poi.ss.usermodel.Cell cell;
		Font font;
		CellStyle style;
		XSSFWorkbook workbook = new XSSFWorkbook();
		try (ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			XSSFSheet sheet = null;
			int colCount = ReportGenData.TABLE_COLS.length;
			int rowNum = 0;
			Row row;
			int colOffset = 1;
			CellRangeAddress rangeAddress;
			sheet = workbook.createSheet();
			font = workbook.createFont();
			font.setFontName("monospaced");
			font.setColor(IndexedColors.BLACK.getIndex());
			font.setFontHeightInPoints((short)16);
			
			/////////////////////////////// top header: search condition Label /////////////////
			row = sheet.createRow(rowNum++);//starts from 0
			cell = row.createCell(colOffset);
			row.setHeightInPoints(35);
			
			style = workbook.createCellStyle();
			style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			style.setAlignment(CellStyle.ALIGN_CENTER);
			style.setFont(font);
			cell.setCellStyle(style);
			cell.setCellValue(String.format("Payment Type: %s, Payment Status: %s", genData.getSoughtPaymentType(), genData.getSoughtPaymentStat()));
			rangeAddress = new CellRangeAddress(0,0, colOffset, colCount -1 + colOffset);
			RegionUtil.setBorderBottom(CellStyle.BORDER_MEDIUM, rangeAddress, sheet, workbook);
			sheet.addMergedRegion(rangeAddress);
			
			row = sheet.createRow(rowNum++);//table header starts from row number 1
			row.setHeightInPoints(20);
			font = workbook.createFont();
			font.setFontHeightInPoints((short)11);
			
			////////////// Table Header Section //////////////////////			
			for(int colIdx = 0; colIdx < colCount; colIdx++) {
				style = workbook.createCellStyle();
				style.setAlignment(CellStyle.ALIGN_CENTER);
				style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
				style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
				((XSSFCellStyle)style).setFillForegroundColor(new XSSFColor(new java.awt.Color(215, 220, 220)));
				style.setFillPattern(CellStyle.SOLID_FOREGROUND);
				style.setFont(font);
				style.setAlignment(CellStyle.ALIGN_CENTER);
				style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
				if(colIdx == 0) {
					style.setBorderLeft(CellStyle.BORDER_THIN);
					style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
					style.setBorderRight(CellStyle.BORDER_THIN);
					style.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
				} else if(colIdx == (colCount-1)) {
					style.setBorderRight(CellStyle.BORDER_THIN);
					style.setRightBorderColor(IndexedColors.BLACK.getIndex());
				} else {
					style.setBorderLeft(CellStyle.BORDER_NONE);
					style.setBorderRight(CellStyle.BORDER_THIN);
					style.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
				}
				cell = row.createCell(colIdx + colOffset);
				cell.setCellValue(ReportGenData.TABLE_COLS[colIdx]);
				cell.setCellStyle(style);
			}
			//////////////Table row data Section ///////////////////
			for(List<String> rowData: genData.getRowData()) {
				row = sheet.createRow(rowNum++);//create data row
				font = workbook.createFont();
				//font.setFontName("monospaced");
				font.setColor(IndexedColors.BLACK.getIndex());
				font.setFontHeightInPoints((short)11);
				for(int colIdx = 0; colIdx < ReportGenData.TABLE_COLS.length; colIdx++) {
					cell = row.createCell(colIdx + colOffset);
					style = workbook.createCellStyle();
					style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
					style.setBorderLeft(CellStyle.BORDER_THIN);
					style.setBorderRight(CellStyle.BORDER_THIN);
					style.setBorderTop(CellStyle.BORDER_THIN);
					style.setBorderBottom(CellStyle.BORDER_THIN);
					style.setFont(font);
					switch(colIdx) {
						case 5:
							style.setAlignment(CellStyle.ALIGN_RIGHT);
						break;
						case 7:
							style.setAlignment(CellStyle.ALIGN_LEFT);
						break;
						default:
							style.setAlignment(CellStyle.ALIGN_CENTER);
						break;
					}
					cell.setCellStyle(style);
					cell.setCellValue(rowData.get(colIdx));
				}
			}

			for(int colIdx = 0; colIdx < colCount; colIdx++) {
				switch(colIdx) {
				case 0://Label
					sheet.setColumnWidth(colIdx + colOffset, 20 * 256);
					break;
				case 1://Model
					sheet.setColumnWidth(colIdx + colOffset, 20 * 256);
					break;
				case 2://Plate No
					sheet.setColumnWidth(colIdx + colOffset, 17 * 256);
					break;
				case 3://Type
					sheet.setColumnWidth(colIdx + colOffset, 15 * 256);
					break;
				case 4://Payment No.
					sheet.setColumnWidth(colIdx + colOffset, 20 * 256);
					break;
				case 5://Cost
					sheet.setColumnWidth(colIdx + colOffset, 20 * 256);
					break;
				case 6://Due Date
					sheet.setColumnWidth(colIdx + colOffset, 15 * 256);
					break;
				case 7://Status
					sheet.setColumnWidth(colIdx + colOffset, 30 * 256);	 
					break;
				case 8://Payment Receipt
					sheet.setColumnWidth(colIdx + colOffset, 25 * 256);
					break;
				}
			}

			/////////////// Summary and subtable section//////////
			int colIdx = 0;
			XSSFColor sumTableBgClr = new XSSFColor(new java.awt.Color(200, 225, 250));
			rowNum++;
			row = sheet.createRow(rowNum);
			row.setHeightInPoints(25F);
			font = workbook.createFont();
			font.setFontHeightInPoints((short)10);
			font.setColor(IndexedColors.BLACK.getIndex());
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);
			cell = row.createCell(colOffset + colIdx++);
			style = workbook.createCellStyle();
			style.setFont(font);
			style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			cell.setCellValue(String.format("Total: %s Paid: %s Unpaid: %s", genData.getTotalCount(), genData.getPaidCount(), genData.getUnpaidCount()));
			cell.setCellStyle(style);

			
			rangeAddress = new CellRangeAddress(rowNum, rowNum, colOffset, 4);//4 column wide merge
			sheet.addMergedRegion(rangeAddress);
			
			rowNum++;
			colIdx = 0;
			row = sheet.createRow(rowNum);
			row.setHeightInPoints(40F);
			cell = row.createCell(colOffset + colIdx++);
			style = workbook.createCellStyle();
			style.setFont(font);
			style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			style.setAlignment(CellStyle.ALIGN_CENTER);
			style.setBorderTop(CellStyle.BORDER_THIN);
			style.setBorderLeft(CellStyle.BORDER_THIN);
			style.setBorderRight(CellStyle.BORDER_THIN);
			style.setBorderBottom(CellStyle.BORDER_DOUBLE);
			((XSSFCellStyle)style).setFillForegroundColor(sumTableBgClr);
			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			style.setWrapText(true);
			cell.setCellValue("Unpaid Task\nTotal Cost:");
			cell.setCellStyle(style);
			

			cell = row.createCell(colOffset + colIdx++);
			style = workbook.createCellStyle();
			style.setFont(font);
			style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			style.setAlignment(CellStyle.ALIGN_RIGHT);
			style.setBorderTop(CellStyle.BORDER_THIN);
			style.setBorderLeft(CellStyle.BORDER_THIN);
			style.setBorderRight(CellStyle.BORDER_THIN);
			style.setBorderBottom(CellStyle.BORDER_DOUBLE);
			((XSSFCellStyle)style).setFillForegroundColor(sumTableBgClr);
			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			cell.setCellValue(genData.getUnpaidCost());
			cell.setCellStyle(style);
			
			rowNum++;
			colIdx = 0;
			row = sheet.createRow(rowNum);
			row.setHeightInPoints(40F);
			
			cell = row.createCell(colOffset + colIdx++);
			style = workbook.createCellStyle();
			style.setFont(font);
			style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			style.setAlignment(CellStyle.ALIGN_CENTER);
			style.setBorderLeft(CellStyle.BORDER_THIN);
			style.setBorderRight(CellStyle.BORDER_THIN);
			style.setBorderBottom(CellStyle.BORDER_THIN);
			((XSSFCellStyle)style).setFillForegroundColor(sumTableBgClr);
			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			style.setWrapText(true);
			cell.setCellValue("Paid Task\nTotal Cost:");
			cell.setCellStyle(style);
			
			cell = row.createCell(colOffset + colIdx++);
			style = workbook.createCellStyle();
			style.setFont(font);
			style.setBorderLeft(CellStyle.BORDER_THIN);
			style.setBorderRight(CellStyle.BORDER_THIN);
			style.setBorderBottom(CellStyle.BORDER_THIN);
			((XSSFCellStyle)style).setFillForegroundColor(sumTableBgClr);
			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			style.setAlignment(CellStyle.ALIGN_RIGHT);
			cell.setCellValue(genData.getPaidCost());
			cell.setCellStyle(style);		
			
			rowNum++;
			row = sheet.createRow(rowNum);
			row.setHeightInPoints(20F);
			cell = row.createCell(colOffset);
			style = workbook.createCellStyle();
			style.setFont(font);
			cell.setCellValue("The nearest payment task: " + genData.getNearestPaymentTaskToCome());
			cell.setCellStyle(style); 
			
			rangeAddress = new CellRangeAddress(rowNum,rowNum, colOffset, 4);
			RegionUtil.setBorderBottom(CellStyle.BORDER_DASHED, rangeAddress, sheet, workbook);
			sheet.addMergedRegion(rangeAddress);
			
			rowNum++;
			row = sheet.createRow(rowNum);
			row.setHeightInPoints(20F);
			cell = row.createCell(colOffset);
			style = workbook.createCellStyle();
			style.setFont(font);
			cell.setCellValue("The latest payment task: " + genData.getFarthestPaymentTaskToCome());
			cell.setCellStyle(style); 
			
			rangeAddress = new CellRangeAddress(rowNum,rowNum, colOffset, 4);
			RegionUtil.setBorderBottom(CellStyle.BORDER_DASHED, rangeAddress, sheet, workbook);
			sheet.addMergedRegion(rangeAddress);
			
			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}