package hc.fms.api.addon.vhctax.report;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReportGenData {
	private String periodFrom;
	private String periodTo;
	private String reportGenDate;
	private String soughtPaymentType;
	private String soughtPaymentStat;
	private String totalCount;
	private String paidCount;
	private String unpaidCount;
	private String paidCost;
	private String unpaidCost;
	
	private String nearestPaymentTaskToCome;
	private String farthestPaymentTaskToCome;
	
	private List<List<String>> rowData;
	
	
	public static final float[] LIST_TABLE_COL_WIDTHS = new float[] {1, 1, 1, 1, 1, 1, 1, 250, 1};
	public static final String [] TABLE_COLS = {"Label", "Model", "Plate No.", "Type", "Payment No.", "Cost", "Due Date", "Status", "Payment Receipt"};
	
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("MMM. dd , yyyy", Locale.US);
	private static SimpleDateFormat parseFormat = new SimpleDateFormat("yyyy-MM-dd");
	public ReportGenData() {
		reportGenDate = dateFormat.format(new Date());
	}
	public static String getTaskTypeDescription(String type) {
		switch(type) {
		case "C":
			return "Certification";
		case "K":
			return "KIR";
		case "T":
			return "Tax";
		default:
			return "Unknown";
		}
	}
	public static String getSoughtPaymentTypeDescription(String type) {
		switch(type) {
		case "C":
			return "Certification";
		case "K":
			return "KIR";
		case "T":
			return "Tax";
		default:
			return "All";
		}
	}
	public static String getSoughtPaymentStatDescription(String type) {
		switch(type) {
		case "0":
			return "Unpaid";
		case "1":
			return "Paid";
		default:
			return "All";
		}
	}
	public static String convertDateFormat(String yyyyMMdd) throws ParseException {
		return dateFormat.format(parseFormat.parse(yyyyMMdd));
	}
}
