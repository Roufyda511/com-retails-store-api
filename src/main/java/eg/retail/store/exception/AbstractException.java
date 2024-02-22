package eg.retail.store.exception;

import org.springframework.http.HttpStatus;

import eg.retail.store.config.dto.Attribute;
import eg.retail.store.exception.handler.ErrorMessage;

/**
 * abstract exception containing all necessary data to create a
 * ErrorMessage @see {@link ErrorMessage} with localized messages
 */
abstract class AbstractException extends Exception implements IException {

	private static final long serialVersionUID = 5603791996497203685L;

	/**
	 * http status code to return to the client. Can be static defined for special
	 * exception
	 */
	private final HttpStatus status;

	/** parameters to generate the localized message. */
	private final Attribute[] attributes;

	public AbstractException(HttpStatus status, String message, Throwable cause, Attribute... attributes) {
		super(message, cause);
		this.status = status;
		this.attributes = attributes;
	}

	public AbstractException(HttpStatus status, String message, Attribute... attributes) {
		super(message);
		this.status = status;
		this.attributes = attributes;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public Attribute[] getAttributes() {
		return attributes;
	}
}
