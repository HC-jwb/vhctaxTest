package hc.fms.api.report.model.fuel;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ColMinMaxAvg {
	private String v;
	private BigDecimal raw;
	public String getRaw() { return (raw == null)? null: raw.toPlainString(); }
}
