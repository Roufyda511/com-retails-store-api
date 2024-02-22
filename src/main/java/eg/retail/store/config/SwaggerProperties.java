package eg.retail.store.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "store.swagger-ui")
public class SwaggerProperties {

	private boolean enabled;
	private String group = "";
	private String basePackage = "eg.retail.store";
	private String title = "RETAIL STORE REST API";
	private String description = "Technical Enterprise RETAIL STORE REST API";
	private String version = "1.0.0";
	private String contactName;

}