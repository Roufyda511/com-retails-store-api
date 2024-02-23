package eg.retail.store.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * Filters incoming requests and installs a Spring Security principal if a
 * header corresponding to a valid user is found.
 */
@Component
@Log4j2
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

	private final JWTUtil jwtUtil;
	private final SecurityProperties securityProperties;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String jwtToken = "";
			log.info("JWTFilter for request {}", request.getRequestURI());

			jwtToken = jwtUtil.resolveToken(request);

			if (StringUtils.hasText(jwtToken) && jwtUtil.validateToken(jwtToken)) {

				if (StringUtils.hasText(jwtUtil.getSubject(jwtToken))) {
					Authentication authentication = jwtUtil.getAuthentication(jwtToken);
					SecurityContextHolder.getContext().setAuthentication(authentication);
				} else {
					log.warn("Invalid token. Subject field is missing");
				}
			}

		} catch (ExpiredJwtException e) {
			log.info("Security exception for user {} - {}", e.getClaims().getSubject(), e.getMessage());

			log.trace("Security exception trace: {}", e);

		} catch (Exception ex) {
			logger.error("Could not set user authentication in security context", ex);
		}
		filterChain.doFilter(request, response);
	}

}
