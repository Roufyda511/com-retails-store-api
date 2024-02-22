package eg.retail.store.exception.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import eg.retail.store.exception.ErrorCode;

@Component
public class DefaultAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest req, HttpServletResponse res, AccessDeniedException e)
			throws IOException, ServletException {
		ObjectMapper mapper = new ObjectMapper();
		res.setContentType(MediaType.APPLICATION_JSON_VALUE);
		res.setStatus(403);

		ErrorMessage em = new ErrorMessage(HttpStatus.FORBIDDEN, ErrorCode.UNAUTHORIZED);

		res.getWriter().write(mapper.writeValueAsString(em));
	}
}
