package eg.retail.store.config;

import java.time.YearMonth;

import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.SpringDocUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.StringSchema;
import lombok.RequiredArgsConstructor;

@Configuration
@ConditionalOnProperty(prefix = "store.swagger-ui", value = "enabled", matchIfMissing = true)
@EnableConfigurationProperties(SwaggerProperties.class)
@RequiredArgsConstructor
public class SwaggerConfig {

	private final SwaggerProperties props;

	static {
		SpringDocUtils.getConfig().replaceWithSchema(YearMonth.class, new StringSchema().example(YearMonth.now()));
	}

	@Bean
	public GroupedOpenApi defaultApi() {
		return GroupedOpenApi.builder().group(props.getGroup()).packagesToScan(props.getBasePackage())
				.pathsToMatch("/**").build();
	}

	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI().info(new Info().title(props.getTitle()).description(props.getDescription())
				.version(props.getVersion()).contact(new Contact().name(props.getContactName())));
	}

}