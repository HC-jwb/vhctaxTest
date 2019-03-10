package hc.fms.api.addon.vhctax.service;

import java.io.InputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import hc.fms.api.addon.vhctax.entity.VehicleTaxTask;
import hc.fms.api.addon.vhctax.model.TaxTaskListRequest;
import hc.fms.api.addon.vhctax.report.ReportGenData;
import hc.fms.api.addon.vhctax.report.TaxTaskReportPdfExporter;

@Service
public class TaxTaskReportService {
	private NumberFormat numberFormat = NumberFormat.getInstance();
	
	public InputStream exportAsPdf(ReportGenData genData) throws Exception {
		
		TaxTaskReportPdfExporter exporter = new TaxTaskReportPdfExporter(genData);
		return exporter.exportAsInputStream();
	}
	
	public InputStream exportAsXls(ReportGenData data) {
		return null;
	}
	public InputStream exportEntity(List<VehicleTaxTask> vhcTaxTaskList, TaxTaskListRequest reportReq) throws Exception {
		ReportGenData genData = new ReportGenData();
		genData.setPeriodFrom(reportReq.getFromDate());
		genData.setPeriodTo(reportReq.getToDate());
		genData.setSoughtPaymentType(ReportGenData.getSoughtPaymentTypeDescription(reportReq.getTaskType()));
		genData.setSoughtPaymentStat(ReportGenData.getSoughtPaymentStatDescription(reportReq.getStatType()));
		
		List<List<String>> rows = new ArrayList<>();
		List<String> row;
		int paid = 0, unpaid = 0;
		Double paidCost = 0d, unpaidCost = 0d;
		VehicleTaxTask nearestUnpaidTask = null, farthestUnpaidTask = null;
		long daysLeft;
		for(VehicleTaxTask task: vhcTaxTaskList) {
			row = new ArrayList<String>();
			row.add(task.getLabel());
			row.add(task.getModel());
			row.add(task.getPlateNo());
			row.add(ReportGenData.getTaskTypeDescription(task.getTaskType()));
			row.add(task.getRegistrationNo());
			row.add(numberFormat.format(task.getCost()));
			row.add(task.getDateValidTill());
			if(task.isPaid()) {
				row.add("Paid");
				paid++;
				paidCost += task.getCost();
			} else {
				daysLeft = task.getDaysLeft();
				if(nearestUnpaidTask == null) {
					nearestUnpaidTask = task;
				} else {
					if(daysLeft < nearestUnpaidTask.getDaysLeft()) nearestUnpaidTask = task;
				}
				if(farthestUnpaidTask == null) {
					farthestUnpaidTask = task;
				} else {
					if(daysLeft > farthestUnpaidTask.getDaysLeft()) farthestUnpaidTask = task;
				}
				row.add(String.format("%s day(s) left", numberFormat.format(daysLeft)));
				unpaid++;
				unpaidCost += task.getCost();
			}
			row.add(task.getReceiptImageURL().equals("") ? "Not uploaded" : "Uploaded");
			rows.add(row);

		}
		genData.setTotalCount(numberFormat.format(vhcTaxTaskList.size()));
		genData.setPaidCount(numberFormat.format(paid));
		genData.setPaidCost(numberFormat.format(paidCost));
		
		genData.setUnpaidCount(numberFormat.format(unpaid));
		genData.setUnpaidCost(numberFormat.format(unpaidCost));
		
		genData.setNearestPaymentTaskToCome(
			nearestUnpaidTask == null ? "" :
			String.format(
				"%s - %s(%s) %s (%s days left)", 
				nearestUnpaidTask.getLabel(), 
				ReportGenData.getTaskTypeDescription(nearestUnpaidTask.getTaskType()),
				nearestUnpaidTask.getPlateNo(),
				nearestUnpaidTask.getDateValidTill(),
				numberFormat.format(nearestUnpaidTask.getDaysLeft())
			)
		);
		genData.setFarthestPaymentTaskToCome(
				farthestUnpaidTask == null ? "" : 
			String.format(
				"%s - %s(%s) %s (%s days left)", 
				farthestUnpaidTask.getLabel(), 
				ReportGenData.getTaskTypeDescription(farthestUnpaidTask.getTaskType()),
				farthestUnpaidTask.getPlateNo(),
				farthestUnpaidTask.getDateValidTill(),
				numberFormat.format(farthestUnpaidTask.getDaysLeft())
			)
		);
		
		genData.setRowData(rows);
		
		
		
		switch(reportReq.getReportFileFormat()) {
		case "pdf":
			return exportAsPdf(genData);
		case "xls":
			return exportAsXls(genData);
		default:
			throw new RuntimeException("Unknown export Type " + reportReq.getReportFileFormat());
		}
	}
}
