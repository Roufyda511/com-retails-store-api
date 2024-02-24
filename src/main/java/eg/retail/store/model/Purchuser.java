package eg.retail.store.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
@Table(name = "Purchuser")
public class Purchuser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5573312644937351882L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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
