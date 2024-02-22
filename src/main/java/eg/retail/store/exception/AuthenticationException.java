package eg.retail.store.exception;

import org.springframework.http.HttpStatus;

import eg.retail.store.config.dto.Attribute;



/**
 * specialized exception for authentication. HTTP Status is set to 401 and can
 * not be overwritten.
 */
public class AuthenticationException extends AbstractException {

	private static final long serialVersionUID = -989935189316058277L;

	public AuthenticationException(String message, Throwable cause, Attribute... attributes) {
		super(HttpStatus.UNAUTHORIZED, message, cause, attributes);
	}

	public AuthenticationException(String message, Attribute... attributes) {
		super(HttpStatus.UNAUTHORIZED, message, attributes);
	}
}
