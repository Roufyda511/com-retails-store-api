package eg.retail.store.service.dto;

import java.util.Date;
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
	
	private int discountAmount;
	

	private Date creationDate;
	
	
	private String marketName;
	
	private List<ItemDto> itemDtoList;

}
