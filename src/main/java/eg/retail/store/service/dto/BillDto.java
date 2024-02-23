package eg.retail.store.service.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@Builder(toBuilder = true)
@NoArgsConstructor
public class BillDto {
	
	private int billId ;
	

	private int purchuser;
	
	private String discountAmount;
	

	private String creationDate;
	
	
	private String marketName;
	
	private double totalBeforeDiscount;
	
	private double totalAfterDiscount;
	
	private List<ItemDto> itemsList;

}
