package hc.fms.api.addon.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hc.fms.api.addon.model.ResponseContainer;
import hc.fms.api.addon.model.ResponseStatus;
import hc.fms.api.addon.report.util.HttpUtil;
import hc.fms.api.addon.vhctax.entity.ServiceTemplate;
import hc.fms.api.addon.vhctax.model.Vehicle;
import hc.fms.api.addon.vhctax.model.VehicleListResponse;
import hc.fms.api.addon.vhctax.service.VehicleTaxManagementService;

@RestController
@CrossOrigin("*")
@RequestMapping("/vhctax/api/*")
public class VehicleTaxManagementController {
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
	@PostMapping("tmpl/create")
	public ResponseContainer<ServiceTemplate> createServiceTemplate(@RequestBody ServiceTemplate svcTmpl, HttpSession session) {
		ResponseContainer<ServiceTemplate> response = new ResponseContainer<>();
		try {
			HttpUtil.validateSession(session);
			ServiceTemplate savedTmpl = vhcTaxManagementService.createServiceTemplate(svcTmpl);
			response.setPayload(savedTmpl);
			response.setSuccess(true);
		} catch(Exception e) {
			response.setStatus(new ResponseStatus(e.getMessage()));
		}
		return response;
	}
}
