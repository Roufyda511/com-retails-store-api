package eg.retail.store.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.hamcrest.core.Is;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import eg.retail.store.RetailsStoreApiApplication;
import eg.retail.store.repository.BillRepossitory;
import eg.retail.store.repository.ItemPepository;
import eg.retail.store.repository.PurchuserRepossitory;

@AutoConfigureMockMvc
@SpringBootTest(classes = RetailsStoreApiApplication.class)
@ActiveProfiles("junit")
@TestInstance(Lifecycle.PER_CLASS)
@DirtiesContext
public class BillControllerTest {
	private final static String CONTEXT_PATH = "/api";

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private BillRepossitory billRepossitory;
	
	@Autowired
	private PurchuserRepossitory purchuserPepository;
	
	@Autowired
	private ItemPepository itemPepository;

	@Test
	void WhenGetBillForCustomerLessThantwoYears_ThenDiscountShouldBe0() throws Exception {

		// When
		mockMvc.perform(get("/api/bill/1").contextPath(CONTEXT_PATH).header(HttpHeaders.AUTHORIZATION, "MOCK")
				.with(user("123")).contentType(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(MockMvcResultMatchers.jsonPath("$.itemsList.size()", Is.is(6)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.billId", Is.is(1)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.purchuser", Is.is(1)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.discountAmount", Is.is("0%")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.creationDate", Is.is("23/02/2024 13:19:36")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.marketName", Is.is("GO")))

		;
	}
	@Test
	void WhenGetBillForEmployee_ThenCorrectResult() throws Exception {

		// When
		mockMvc.perform(get("/api/bill/2").contextPath(CONTEXT_PATH).header(HttpHeaders.AUTHORIZATION, "MOCK")
				.with(user("123")).contentType(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(MockMvcResultMatchers.jsonPath("$.itemsList.size()", Is.is(7)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.billId", Is.is(2)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.purchuser", Is.is(2)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.discountAmount", Is.is("30%")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.creationDate", Is.is("23/02/2024 13:19:36")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.marketName", Is.is("GO")))

		;
	}
	
	@Test
	void WhenGetBillForAffertail_ThenCorrectResult() throws Exception {

		// When
		mockMvc.perform(get("/api/bill/3").contextPath(CONTEXT_PATH).header(HttpHeaders.AUTHORIZATION, "MOCK")
				.with(user("123")).contentType(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(MockMvcResultMatchers.jsonPath("$.itemsList.size()", Is.is(7)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.billId", Is.is(3)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.purchuser", Is.is(3)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.discountAmount", Is.is("10%")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.creationDate", Is.is("23/02/2024 13:19:36")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.marketName", Is.is("GO")))

		;
	}
	
	@Test
	void WhenGetBillForCustomerMoreThantwoYears_ThenDiscountShouldBe5() throws Exception {

		// When
		mockMvc.perform(get("/api/bill/4").contextPath(CONTEXT_PATH).header(HttpHeaders.AUTHORIZATION, "MOCK")
				.with(user("123")).contentType(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(MockMvcResultMatchers.jsonPath("$.itemsList.size()", Is.is(7)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.billId", Is.is(4)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.purchuser", Is.is(4)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.discountAmount", Is.is("5%")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.totalBeforeDiscount", Is.is(305.5)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.totalAfterDiscount", Is.is(294.725)))
				.andExpect(MockMvcResultMatchers.jsonPath("$.creationDate", Is.is("23/02/2024 13:19:36")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.marketName", Is.is("GO")))

		;
	}

	@Test
	void WhenGetBillWithInvalidBillId_ThenCorrectResult() throws Exception {

		// When
		mockMvc.perform(get("/api/bill/5").contextPath(CONTEXT_PATH).header(HttpHeaders.AUTHORIZATION, "MOCK")
				.with(user("123")).contentType(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is("retails.store.invalid.billId")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.level", Is.is("ERROR")))

				.andExpect(MockMvcResultMatchers.jsonPath("$.refId", IsNull.notNullValue()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.timestamp", IsNull.notNullValue()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.httpStatus", Is.is(HttpStatus.BAD_REQUEST.name())));
	}
	
	@Test
	void WhenGetBillWithStringBillId_ThenCorrectResult() throws Exception {

		// When
		mockMvc.perform(get("/api/bill/test").contextPath(CONTEXT_PATH).header(HttpHeaders.AUTHORIZATION, "MOCK")
				.with(user("123")).contentType(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is("common.validation.typeMismatch")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.level", Is.is("ERROR")))

				.andExpect(MockMvcResultMatchers.jsonPath("$.refId", IsNull.notNullValue()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.timestamp", IsNull.notNullValue()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.httpStatus", Is.is(HttpStatus.BAD_REQUEST.name())));
	}
	
	@Test
	void WhenGetBillWithinvalidMethod_ThenCorrectResult() throws Exception {

		// When
		mockMvc.perform(post("/api/bill/123").contextPath(CONTEXT_PATH).header(HttpHeaders.AUTHORIZATION, "MOCK")
				.with(user("123")).contentType(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is("common.validation.httpRequestMethodNotSupported")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.level", Is.is("ERROR")))

				.andExpect(MockMvcResultMatchers.jsonPath("$.refId", IsNull.notNullValue()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.timestamp", IsNull.notNullValue()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.httpStatus", Is.is(HttpStatus.METHOD_NOT_ALLOWED.name())));
	}
	
	@AfterAll
	void cleanup() {
		itemPepository.deleteAll();
		billRepossitory.deleteAll();
		purchuserPepository.deleteAll();
		
		
	}
}
