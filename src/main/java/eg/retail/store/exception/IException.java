package eg.retail.store.exception;

import org.springframework.http.HttpStatus;

import eg.retail.store.config.dto.Attribute;

/**
 * interface to be implemented by Exceptions to be handled by the Exception
 * handler and generating an automatic exception.
 */
public interface IException {

	/**
	 * @return HTTP status to be returned to the client.
	 */
	public HttpStatus getStatus();

	/**
	 * @return list of attributes to be used to generate the localized messages.
	 */
	public Attribute[] getAttributes();

	/**
	 * @return message code to be localized.
	 */
	public String getMessage();
}
