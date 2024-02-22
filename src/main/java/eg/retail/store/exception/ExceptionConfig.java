package eg.retail.store.exception;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

import eg.retail.store.exception.handler.CustomExceptionsHandler;



@Configuration
public class ExceptionConfig {

	@Bean
	@ConditionalOnMissingBean
	public CustomExceptionsHandler customExceptionsHandler() {
		return new CustomExceptionsHandler(messageSource());
	}

	@Bean
	@ConditionalOnMissingBean
	public ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource source = new ResourceBundleMessageSource();
		source.setBasenames("errorMessage");
		return source;
	}
}