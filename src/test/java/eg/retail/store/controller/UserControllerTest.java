package eg.retail.store.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.hamcrest.core.Is;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import eg.retail.store.RetailsStoreApiApplication;

@AutoConfigureMockMvc
@SpringBootTest(classes = RetailsStoreApiApplication.class)
@ActiveProfiles("junit")
@TestInstance(Lifecycle.PER_CLASS)
@DirtiesContext
class UserControllerTest {

	private final static String CONTEXT_PATH = "/api";

	@Autowired
	private MockMvc mockMvc;

	@Test
	void WhenloginWithvalidUserId_ThenSuccessResponse() throws Exception {

		// When
		mockMvc.perform(get("/api/login/123").contextPath(CONTEXT_PATH).with(user("123"))
				.contentType(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(MockMvcResultMatchers.status().isOk());

	}

	@Test
	void WhenGetBillWithInvalidUserId_ThenCorrectResult() throws Exception {

		// When
		mockMvc.perform(get("/api/login/test").contextPath(CONTEXT_PATH).with(user("test"))
				.contentType(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(MockMvcResultMatchers.jsonPath("$.code", Is.is("common.validation.typeMismatch")))
				.andExpect(MockMvcResultMatchers.jsonPath("$.level", Is.is("ERROR")))

				.andExpect(MockMvcResultMatchers.jsonPath("$.refId", IsNull.notNullValue()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.timestamp", IsNull.notNullValue()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.httpStatus", Is.is(HttpStatus.BAD_REQUEST.name())));
	}
	
	

}
