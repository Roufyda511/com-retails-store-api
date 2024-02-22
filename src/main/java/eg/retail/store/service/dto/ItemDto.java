package eg.retail.store.service.dto;

import java.util.Date;
import java.util.List;

import eg.retail.store.model.Bill;
import eg.retail.store.model.Item;
import eg.retail.store.model.ItemType;
import eg.retail.store.model.Purchuser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ItemDto {
	
	private ItemType Type;
	
	
	private String name;
	
	
	private int price;
	
	private int amount;
	
	private int priceBeforeDiscount;
	
	private int priceAfterDiscount;
	
	
	

}
