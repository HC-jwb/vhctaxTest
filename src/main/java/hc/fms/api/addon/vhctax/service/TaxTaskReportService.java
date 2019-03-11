package hc.fms.api.addon.vhctax.service;

import java.io.InputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import hc.fms.api.addon.vhctax.entity.VehicleTaxTask;
import hc.fms.api.addon.vhctax.model.TaxTaskListRequest;
import hc.fms.api.addon.vhctax.report.ReportGenData;
import hc.fms.api.addon.vhctax.report.TaxTaskReportExporter;

@Service
public class TaxTaskReportService {
	private NumberFormat numberFormat = NumberFormat.getInstance();
	
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
		VehicleTaxTask nearestUnpaidTask = null, latestPaymentTask = null;
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
			daysLeft = task.getDaysLeft();
			
			if(task.isPaid()) {
				row.add("Paid");
				
				if(latestPaymentTask == null) {
					latestPaymentTask = task;
				} else {
					if(daysLeft > latestPaymentTask.getDaysLeft()) latestPaymentTask = task;
				}
				
				paid++;
				paidCost += task.getCost();
			} else {
				if(nearestUnpaidTask == null) {
					nearestUnpaidTask = task;
				} else {
					if(daysLeft < nearestUnpaidTask.getDaysLeft()) nearestUnpaidTask = task;
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
				latestPaymentTask == null ? "" : 
			String.format(
				"%s - %s(%s) %s (%s days left)", 
				latestPaymentTask.getLabel(), 
				ReportGenData.getTaskTypeDescription(latestPaymentTask.getTaskType()),
				latestPaymentTask.getPlateNo(),
				latestPaymentTask.getDateValidTill(),
				numberFormat.format(latestPaymentTask.getDaysLeft())
			)
		);
		
		genData.setRowData(rows);
		
		TaxTaskReportExporter exporter = new TaxTaskReportExporter(genData);
		switch(reportReq.getReportFileFormat()) {
		case "pdf":
			return exporter.exportPdfAsInputStream();
		case "xlsx":
			return exporter.exportXlsAsInputStream();
		default:
			throw new RuntimeException("Unknown export Type " + reportReq.getReportFileFormat());
		}
	}
}
