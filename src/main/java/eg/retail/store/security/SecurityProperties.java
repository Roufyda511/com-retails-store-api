package eg.retail.store.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "ted.security")
public class SecurityProperties {

	private String[] adminEndpoints = new String[] {};

	private String[] insecuredEndpoints = new String[] { "/public**" };

	private boolean xssProtectionEnabled = false;

	private String contentSecurityPolicy = "default-src 'self'";

	private JwtAuth jwtAuth = new JwtAuth();

	@Data
	class JwtAuth {

		private boolean enabled;

		private boolean cookieEnabled;

		private String secretKey;

		private long tokenValidity;

		private long refreshTokenValidity;
	}

	private Cors cors = new Cors();

	@Data
	static class Cors {

		private boolean enabled;

		private boolean allowCredentials = false;

		private List<CorsMapping> mappings = new ArrayList<>();
	}

	@Data
	static class CorsMapping {

		private String pathPattern;

		private List<String> allowedOrigins;

		private List<String> allowedMethods;
	}

}
