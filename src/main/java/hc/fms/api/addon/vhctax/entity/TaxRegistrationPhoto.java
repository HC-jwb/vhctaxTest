package hc.fms.api.addon.vhctax.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table (name="vhc_tax_photo")
@Getter
@Setter
@ToString
public class TaxRegistrationPhoto {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String type;
	@Lob
	private byte [] image;

}
