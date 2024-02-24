package eg.retail.store.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Item implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1013545522310635636L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id ;
	
	
	
	@Column
	@NotNull
	@Enumerated(EnumType.STRING)
	private ItemType Type;
	
	@Column
	@NotNull
	private String name;
	
	@Column
	@NotNull
	private double Price;
	
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "Bill_ID")
	private Bill bill;
	
	@Column
	@NotNull
	private String code;

}
