package eg.retail.store.config.controller;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import eg.retail.store.exception.FunctionalException;
import eg.retail.store.service.BillService;
import eg.retail.store.service.dto.BillDto;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class BillController {
	
	private final BillService billService;
	
	@GetMapping(value = "/bill/{billId}")
	public ResponseEntity<BillDto> getPurchuserBill(@RequestHeader(name = "Authorization", required = true) String token,
			@Valid @Pattern(regexp = "d+")@PathVariable("billId")int billId 
			) throws FunctionalException {
		return ResponseEntity.ok().body(billService.calculatePurchuserBill(billId));
	}

}
