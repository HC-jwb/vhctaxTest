package hc.fms.api.addon.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hc.fms.api.addon.model.ResponseContainer;
import hc.fms.api.addon.model.ResponseStatus;
import hc.fms.api.addon.report.util.HttpUtil;
import hc.fms.api.addon.vhctax.entity.ServiceTemplate;
import hc.fms.api.addon.vhctax.entity.VehicleTaxTask;
import hc.fms.api.addon.vhctax.model.Vehicle;
import hc.fms.api.addon.vhctax.model.VehicleListResponse;
import hc.fms.api.addon.vhctax.service.VehicleTaxManagementService;

@RestController
@CrossOrigin("*")
@RequestMapping("/addon/vhctax/api/*")
public class VehicleTaxManagementController {
	//private Logger logger = LoggerFactory.getLogger(VehicleTaxManagementController.class);
	@Autowired
	private VehicleTaxManagementService vhcTaxManagementService;
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
	public ResponseContainer<ServiceTemplate> getServiceTemplate(@PathVariable("id") Long id) {
		ResponseContainer<ServiceTemplate> response = new ResponseContainer<>();
		try {
			ServiceTemplate tmpl = vhcTaxManagementService.getServiceTemplate(id).get();
			response.setPayload(tmpl);
			response.setSuccess(true);
		} catch(Exception e) {
			response.setStatus(new ResponseStatus(e.getMessage()));
		}
		return response;
	}
	@PostMapping("task/save")
	public ResponseContainer<List<VehicleTaxTask>> createUpdatePaymentTask(@RequestBody List<VehicleTaxTask> taskList) {
		ResponseContainer<List<VehicleTaxTask>> response = new ResponseContainer<>();
		try {
			response.setPayload(vhcTaxManagementService.createUpdateTaxTaskList(taskList));
			response.setSuccess(true);
		} catch(Exception e) {
			response.setStatus(new ResponseStatus(e.getMessage()));
		}
		return response;
	}
	@RequestMapping("task/list")
	public ResponseContainer<List<VehicleTaxTask>> listVehicleTaxTask(@RequestBody Map<String, String> searchCond) {
		ResponseContainer<List<VehicleTaxTask>> response = new ResponseContainer<>();
		//logger.info(searchCond.toString());
		String taskType = searchCond.get("taskType");
		String paymentStatus = searchCond.get("statType");
		String fromDate = searchCond.get("fromDate");
		String toDate = searchCond.get("toDate");
		
		try {
			//response.setPayload(vhcTaxManagementService.listTaxTaskList(taskType, fromDate, toDate));
			response.setPayload(vhcTaxManagementService.listTaxTaskList(taskType, paymentStatus, fromDate, toDate));
			response.setSuccess(true);
		} catch(Exception e) {
			response.setStatus(new ResponseStatus(e.getMessage()));
		}
		return response;
	}
	@PostMapping("task/remove")
	public ResponseContainer<Void> removePaymentTaskList(@RequestBody List<Long> taskIdList) {
		ResponseContainer<Void> response = new ResponseContainer<>();
		try {
			vhcTaxManagementService.removePaymentTaskListByIdList(taskIdList);
			response.setSuccess(true);
		} catch(Exception e) {
			response.setStatus(new ResponseStatus(e.getMessage()));
		}
		return response;
	}
	@PostMapping("task/complete")
	public ResponseContainer<Void> makePaymentTaskListPaid(@RequestBody List<Long> taskIdList) {
		ResponseContainer<Void> response = new ResponseContainer<>();
		try {
			vhcTaxManagementService.makePaymentTaskListPaid(taskIdList);
			response.setSuccess(true);
		} catch(Exception e) {
			response.setStatus(new ResponseStatus(e.getMessage()));
		}
		return response;
	}
}