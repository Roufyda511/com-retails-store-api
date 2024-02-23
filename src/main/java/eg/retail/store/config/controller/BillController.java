package eg.retail.store.config.controller;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import eg.retail.store.config.dto.Message;
import eg.retail.store.exception.FunctionalException;
import eg.retail.store.service.BillService;
import eg.retail.store.service.dto.BillDto;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@Validated
public class BillController {
	
	private final BillService billService;
	
	@GetMapping(value = "/bill/{billId}")
	@ApiResponses(value = {
	        @ApiResponse(responseCode = "200", description = "Bill Successfully retrieved",content = { @Content(schema = @Schema(implementation = BillDto.class), mediaType = "application/json") }), 
	        @ApiResponse(responseCode = "404", description = "retails.store.invalid.billId",content = { @Content(schema = @Schema(implementation = Message.class), mediaType = "application/json") }),
	        @ApiResponse(responseCode = "400", description = "common.validation.typeMismatch",content = { @Content(schema = @Schema(implementation = Message.class), mediaType = "application/json")}),
	        @ApiResponse(responseCode = "401", description = "common.auth.unauthorized",content = {@Content(schema = @Schema(implementation = Message.class), mediaType = "application/json")})
	    })
	public ResponseEntity<BillDto> getPurchuserBill(@RequestHeader(name = "Authorization", required = true) String token,
			@Valid @Pattern(regexp = "d+")@PathVariable("billId")int billId 
			) throws FunctionalException {
		return ResponseEntity.ok().body(billService.calculatePurchuserBill(billId));
	}

}
