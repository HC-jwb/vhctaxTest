package hc.fms.api.addon.controller;

import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hc.fms.api.addon.model.ResponseContainer;
import hc.fms.api.addon.model.ResponseStatus;
import hc.fms.api.addon.report.util.HttpUtil;
import hc.fms.api.addon.vhctax.entity.ServiceTemplate;
import hc.fms.api.addon.vhctax.entity.VehicleTaxTask;
import hc.fms.api.addon.vhctax.model.TaxTaskListRequest;
import hc.fms.api.addon.vhctax.model.Vehicle;
import hc.fms.api.addon.vhctax.model.VehicleListResponse;
import hc.fms.api.addon.vhctax.service.TaxTaskReportService;
import hc.fms.api.addon.vhctax.service.VehicleTaxManagementService;

@RestController
@CrossOrigin("*")
@RequestMapping("/addon/vhctax/api/*")
public class VehicleTaxManagementController {
	//private Logger logger = LoggerFactory.getLogger(VehicleTaxManagementController.class);
	@Autowired
	private VehicleTaxManagementService vhcTaxManagementService;
	@Autowired
	private TaxTaskReportService reportExporter;
	@RequestMapping("vehicle/list")
	public ResponseContainer<List<Vehicle>> getVehicleList(HttpSession session) {
		ResponseContainer<List<Vehicle>> response = new ResponseContainer<>();
		try {
			HttpUtil.validateSession(session);
			VehicleListResponse vhcListResponse =  vhcTaxManagementService.getVehicleList(HttpUtil.hashKey(session));
			if(vhcListResponse.getSuccess()) {
				response.setSuccess(true);
				response.setPayload(vhcListResponse.getList());
			} else {
				response.setStatus(vhcListResponse.getStatus());
			}
		} catch(Exception e) {
			ResponseStatus status = new ResponseStatus();
			status.setDescription(e.getMessage());
			response.setStatus(status);
		}
		return response;
	}
	@RequestMapping("tmpl/list")
	public ResponseContainer<List<ServiceTemplate>> getTemplateList(HttpSession session) {
		ResponseContainer<List<ServiceTemplate>> response = new ResponseContainer<>();
		try {
			HttpUtil.validateSession(session);
			List<ServiceTemplate> tmplList = vhcTaxManagementService.getServiceTemplateList();
			response.setSuccess(true);
			response.setPayload(tmplList);
		} catch(Exception e) {
			ResponseStatus status = new ResponseStatus();
			status.setDescription(e.getMessage());
			response.setStatus(status);
		}
		return response;
	}

	@PostMapping("tmpl/save")
	public ResponseContainer<ServiceTemplate> createUpdateServiceTemplate(@RequestBody ServiceTemplate svcTmpl, HttpSession session) {
		ResponseContainer<ServiceTemplate> response = new ResponseContainer<>();
		try {
			HttpUtil.validateSession(session);
			ServiceTemplate savedTmpl = vhcTaxManagementService.createUpdateServiceTemplate(svcTmpl);
			response.setPayload(savedTmpl);
			response.setSuccess(true);
		} catch(Exception e) {
			response.setStatus(new ResponseStatus(e.getMessage()));
		}
		return response;
	}
	@RequestMapping("tmpl/remove/{id}")
	public ResponseContainer<Long> removeServiceTemplate(@PathVariable("id") Long id, HttpSession session) {
		ResponseContainer<Long> response = new ResponseContainer<>();
		try {
			HttpUtil.validateSession(session);
			vhcTaxManagementService.deleteServiceTemplate(id);
			response.setPayload(id);
			response.setSuccess(true);
		} catch(Exception e) {
			response.setStatus(new ResponseStatus(e.getMessage()));
		}
		return response;
	}
	@GetMapping("tmpl/get/{id}")
	public ResponseContainer<ServiceTemplate> getServiceTemplate(@PathVariable("id") Long id, HttpSession session) {
		ResponseContainer<ServiceTemplate> response = new ResponseContainer<>();
		try {
			HttpUtil.validateSession(session);
			ServiceTemplate tmpl = vhcTaxManagementService.getServiceTemplate(id).get();
			response.setPayload(tmpl);
			response.setSuccess(true);
		} catch(Exception e) {
			response.setStatus(new ResponseStatus(e.getMessage()));
		}
		return response;
	}
	@PostMapping("task/save")
	public ResponseContainer<List<VehicleTaxTask>> createUpdatePaymentTask(@RequestBody List<VehicleTaxTask> taskList, HttpSession session) {
		ResponseContainer<List<VehicleTaxTask>> response = new ResponseContainer<>();
		try {
			HttpUtil.validateSession(session);
			response.setPayload(vhcTaxManagementService.createUpdateTaxTaskList(taskList));
			response.setSuccess(true);
		} catch(Exception e) {
			response.setStatus(new ResponseStatus(e.getMessage()));
		}
		return response;
	}
	@RequestMapping("task/list")
	public ResponseContainer<List<VehicleTaxTask>> listVehicleTaxTask(@RequestBody TaxTaskListRequest listReq, HttpSession session) {
		ResponseContainer<List<VehicleTaxTask>> response = new ResponseContainer<>();
		try {
			HttpUtil.validateSession(session);
			response.setPayload(getVehicleTaxTaskList(listReq));
			response.setSuccess(true);
		} catch(Exception e) {
			response.setStatus(new ResponseStatus(e.getMessage()));
		}
		return response;
	}
	@RequestMapping("task/list/print/{fromDate}/{toDate}")
	public ResponseEntity<?> printTaxTaskListReport(
			@PathVariable("fromDate")String fromDate, @PathVariable("toDate") String toDate, 
			@RequestParam("taskType") String taskType, @RequestParam("statType") String statType,
			HttpSession session) throws Exception {
		TaxTaskListRequest listReq = new TaxTaskListRequest();
		listReq.setFromDate(fromDate);
		listReq.setToDate(toDate);
		listReq.setTaskType(taskType);
		listReq.setStatType(statType);
		listReq.setReportFileFormat("pdf");
		HttpHeaders headers = new HttpHeaders();
		try {
			HttpUtil.validateSession(session);
			InputStream in = reportExporter.exportEntity(getVehicleTaxTaskList(listReq), listReq);
			//headers.add("Content-Disposition",String.format("attachment; filename=tax_task_report_%s.%s", System.currentTimeMillis(), listReq.getReportFileFormat()));
			return ResponseEntity
				.ok()
				.contentType(MediaType.parseMediaType("application/pdf"))
				.headers(headers).body(new InputStreamResource(in));
		} catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity
					.ok()
					.contentType(MediaType.parseMediaType("text/plain"))
					.headers(headers).body(e.getMessage());
		}
	}
	@RequestMapping("task/list/download/{fromDate}/{toDate}")
	public ResponseEntity<?> downloadTaxTaskListReport(
			@PathVariable("fromDate")String fromDate, @PathVariable("toDate") String toDate, 
			@RequestParam("taskType") String taskType, @RequestParam("statType") String statType, 
			@RequestParam("format") String reportFileFormat, 
			HttpSession session) throws Exception {
		TaxTaskListRequest listReq = new TaxTaskListRequest();
		listReq.setFromDate(fromDate);
		listReq.setToDate(toDate);
		listReq.setTaskType(taskType);
		listReq.setStatType(statType);
		listReq.setReportFileFormat(reportFileFormat);
		HttpHeaders headers = new HttpHeaders();
		try {
			HttpUtil.validateSession(session);
			InputStream in = reportExporter.exportEntity(getVehicleTaxTaskList(listReq), listReq);
			headers.add("Content-Disposition",String.format("attachment; filename=tax_task_report_%s.%s", System.currentTimeMillis(), listReq.getReportFileFormat()));
			return ResponseEntity
				.ok()
				.contentType(MediaType.parseMediaType("application/octet-stream"))
				.headers(headers).body(new InputStreamResource(in));
		} catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity
					.ok()
					.contentType(MediaType.parseMediaType("text/plain"))
					.headers(headers).body(e.getMessage());
		}
	}
	
	private List<VehicleTaxTask> getVehicleTaxTaskList(TaxTaskListRequest listReq) throws Exception {
		//logger.info(searchCond.toString());
		String taskType = listReq.getTaskType();
		String paymentStatus = listReq.getStatType();
		String fromDate = listReq.getFromDate();
		String toDate = listReq.getToDate();
		return vhcTaxManagementService.listTaxTaskList(taskType, paymentStatus, fromDate, toDate);
	}
	@PostMapping("task/remove")
	public ResponseContainer<Void> removePaymentTaskList(@RequestBody List<Long> taskIdList, HttpSession session) {
		ResponseContainer<Void> response = new ResponseContainer<>();
		try {
			HttpUtil.validateSession(session);
			vhcTaxManagementService.removePaymentTaskListByIdList(taskIdList);
			response.setSuccess(true);
		} catch(Exception e) {
			response.setStatus(new ResponseStatus(e.getMessage()));
		}
		return response;
	}
	@PostMapping("task/complete")
	public ResponseContainer<Void> makePaymentTaskListPaid(@RequestBody List<Long> taskIdList, HttpSession session) {
		ResponseContainer<Void> response = new ResponseContainer<>();
		try {
			HttpUtil.validateSession(session);
			vhcTaxManagementService.makePaymentTaskListPaid(taskIdList);
			response.setSuccess(true);
		} catch(Exception e) {
			response.setStatus(new ResponseStatus(e.getMessage()));
		}
		return response;
	}
}