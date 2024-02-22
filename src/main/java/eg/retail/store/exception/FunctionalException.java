package eg.retail.store.exception;

import java.util.List;

import org.springframework.http.HttpStatus;

import eg.retail.store.config.dto.Attribute;

/**
 * general exception handling business errors for which no default handling is
 * possible.
 */
public class FunctionalException extends AbstractException {

	private static final long serialVersionUID = -4780563893463936908L;
	private final List<FieldError> fieldErrors;

	public FunctionalException(HttpStatus status, String message, Attribute... attributes) {
		super(status, message, attributes);
		this.fieldErrors = null;

	}

	public FunctionalException(HttpStatus status, String message, Throwable cause, Attribute... attributes) {
		super(status, message, cause, attributes);
		this.fieldErrors = null;

	}

	public FunctionalException(HttpStatus status, String message, List<FieldError> FieldErrors,
			Attribute... attributes) {
		super(status, message, attributes);
		this.fieldErrors = FieldErrors;

	}

	public FunctionalException(HttpStatus status, String message, Throwable cause, List<FieldError> FieldErrors,
			Attribute... attributes) {
		super(status, message, cause, attributes);
		this.fieldErrors = FieldErrors;

	}

	public List<FieldError> getFieldErrors() {
		return fieldErrors;
	}
}
