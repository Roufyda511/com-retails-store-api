package eg.retail.store.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.fasterxml.jackson.databind.ObjectMapper;

import eg.retail.store.exception.ErrorCode;
import eg.retail.store.exception.handler.ErrorMessage;

/*
 * This class is invoked when a user tries to access a protected resource without authentication 
 * In this case, we simply return a 401 Unauthorized response
 */
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException e)
			throws IOException, ServletException {

		ObjectMapper mapper = new ObjectMapper();
		res.setContentType(MediaType.APPLICATION_JSON_VALUE);
		res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		ErrorMessage em = new ErrorMessage(HttpStatus.UNAUTHORIZED, ErrorCode.UNAUTHORIZED);

		res.getWriter().write(mapper.writeValueAsString(em));
	}
}
