package eg.retail.store.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Bill implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	private int id ;
	
	@ManyToOne
	@JoinColumn(name = "PURCHUSER_ID")
	private Purchuser purchuser;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "Creation_DATE")
	private Date creationDate;
	
	@Column(name = "MARKET_NAME")
	private String marketName;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "bill")
	private List<Item> itemsList;
	

}
