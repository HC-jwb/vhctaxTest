package hc.fms.api.addon.vhctax.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Vehicle {
	private Long id;
	@JsonProperty("max_speed")
	private Integer maxSpeed;
	private String label;
	@JsonProperty("tracker_id")
	private Long tackerId;
	private String model;
	private String type;
	@JsonProperty("subtype")
	private String subType;
	@JsonProperty("garage_id")
	private Long garageId;
	@JsonProperty("reg_number")
	private String regNumber;
	private String vin;
	@JsonProperty("chassis_number")
	private String chassisNumber;
	@JsonProperty("payload_weight")
	private Double payloadWeight;
	@JsonProperty("payload_height")
	private Double payloadHeight;
	@JsonProperty("payload_width")
	private Double payloadWidth;
	private String passengers;
	@JsonProperty("fuel_type")
	private String fuelType;//petrol:가솔린/diesel:디젤/gas:lpg??
	@JsonProperty("fuel_grade")
	private String fuelGrade;
	@JsonProperty("norm_avg_fuel_consumption")
	private Double normAvgFuelConsumption;
	@JsonProperty("wheel_arrangement")
	private String wheelArrangement;
	@JsonProperty("tyre_size")
	private String tireSize;
	@JsonProperty("tyres_number")
	private Integer numberOfTires;
	@JsonProperty("fuel_tank_volume")
	private Double fuelTankVolume;
	@JsonProperty("liability_insurance_valid_till")
	private String liabilityInsuranceValidTill;
	@JsonProperty("liability_insurance_policy_number")
	private String liabilityInsurancePolicyNumber;
	@JsonProperty("free_insurance_policy_number")
	private String freeInsurancePolicyNumber;
	@JsonProperty("free_insurance_valid_till")
	private String freeInsuranceValidTill;
}
