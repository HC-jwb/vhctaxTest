package hc.fms.api.report.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
@RequestMapping("/report")
@Controller
@CrossOrigin("*")
public class FuelReportController {
	@RequestMapping("/fuel")
	public String fuelConsumptionReport(Model model) {
		return "fuelreport";
	}
}