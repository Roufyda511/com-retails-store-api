package eg.retail.store.config.controller;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BillController {
	
	@GetMapping(value = "/bill/{billId}")
	public void getPurchusrBill(@RequestHeader(name = "Authorization", required = true) String token,
			@Valid @Pattern(regexp = "d+")@PathVariable("billId")int billId 
			) {
		
	}

}
