package hc.fms.api.report.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FuelReportController {
	@RequestMapping("/ful")
	public String fuelConsumptionReport(Model model, @RequestParam(value="hash", required=false) String hashKey) {
		System.out.println("sending ddd");
		return "flcnsmp";
	}
}