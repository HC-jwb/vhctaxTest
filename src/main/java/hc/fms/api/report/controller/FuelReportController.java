package hc.fms.api.report.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
@RequestMapping("/report")
@Controller
public class FuelReportController {
	@RequestMapping("/fuel")
	public String fuelConsumptionReport(Model model, @RequestParam(value="hash", required=false) String hashKey) {
		return "fuelreport";
	}
}