package hc.fms.api.addon.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ResponseStatus {
	private int code;
	private String description;
	public ResponseStatus(String desc) {
		this.setDescription(desc);
	}
}
