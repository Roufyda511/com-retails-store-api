package eg.retail.store.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "store.security")
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

	
}
