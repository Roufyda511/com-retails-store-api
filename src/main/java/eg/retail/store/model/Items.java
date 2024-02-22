package eg.retail.store.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Items {
	
	@Id
	@Column(name = "ID")
	private int id ;
	
	@Column
	@Enumerated(EnumType.STRING)
	private ItemType Type;
	
	@Column
	private String name;
	
	@Column
	private int Price;
	
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "Bill_ID")
	private Bill bill;

}
