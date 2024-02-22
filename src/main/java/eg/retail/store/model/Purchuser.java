package eg.retail.store.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Purchuser {

	@Id
	@Column(name = "ID")
	private int id;

	@Size(max = 100)
	@Column(name = "NAME")
	private String name;

	@Size(max = 100)
	@Column(name = "PHONE")
	private String phone;

	@Column
	@Enumerated(EnumType.STRING)
	private PurchuserType purchuserType;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "REGESTRATION_DATE")
	private Date registerationDate;
	
	@OneToMany(mappedBy = "purchuser")
	private List<Bill> BillList;

}
