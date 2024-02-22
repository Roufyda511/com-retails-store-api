package eg.retail.store.security;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import eg.retail.store.config.SwaggerProperties;
import eg.retail.store.exception.handler.DefaultAccessDeniedHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Configuration
@EnableWebSecurity
@ConditionalOnWebApplication
@EnableConfigurationProperties({ SecurityProperties.class, SwaggerProperties.class })
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

	private static final String[] SWAGGER_WHITE_LIST_PATHS = { "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html",
			"/webjars/**" };

	private final SecurityProperties securityProperties;

	private final SwaggerProperties swaggerProperties;

	@Bean("MainSecurityFilterChain")
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// Swagger security authorization
		log.info("Swagger enable is {}", swaggerProperties.isEnabled());
		if (swaggerProperties.isEnabled()) {
			http.authorizeRequests().antMatchers(SWAGGER_WHITE_LIST_PATHS).permitAll();
		} else {
			http.authorizeRequests().antMatchers(SWAGGER_WHITE_LIST_PATHS).denyAll();
		}

		// JWT security authorization
		log.info("JWT Security filter enable is {}", securityProperties.getJwtAuth().isEnabled());

		if (securityProperties.getJwtAuth().isEnabled()) {
			http.addFilterBefore(jwtFilter(jwtUtil()), UsernamePasswordAuthenticationFilter.class);
		}

		
		// Security customizations
		http.csrf().disable().formLogin().disable().logout().disable().httpBasic().disable().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)

				.and().authorizeRequests().antMatchers(securityProperties.getInsecuredEndpoints()).permitAll()
				.antMatchers(securityProperties.getAdminEndpoints()).hasRole("ADMIN").anyRequest().authenticated()

				.and().exceptionHandling().authenticationEntryPoint(authenticationEntryPoint())
				.accessDeniedHandler(accessDeniedHandler())

				.and().headers().frameOptions().disable()

				.xssProtection().xssProtectionEnabled(securityProperties.isXssProtectionEnabled());

		if (!StringUtils.isBlank(securityProperties.getContentSecurityPolicy())) {
			http.headers().contentSecurityPolicy(securityProperties.getContentSecurityPolicy());
		}

		return http.build();
	}

	

	@Bean
	public AuthenticationEntryPoint authenticationEntryPoint() {
		return new JwtAuthenticationEntryPoint();
	}

	@Bean
	public AccessDeniedHandler accessDeniedHandler() {
		return new DefaultAccessDeniedHandler();
	}

	@Bean
	@ConditionalOnMissingBean
	ObjectMapper objectMapper() {
		return new ObjectMapper();
	}

	@Bean
	public JWTUtil jwtUtil() {
		return new JWTUtil(objectMapper(), securityProperties);
	}

	@Bean
	public JWTFilter jwtFilter(JWTUtil jwtUtil) {
		return new JWTFilter(jwtUtil, securityProperties);
	}
}
