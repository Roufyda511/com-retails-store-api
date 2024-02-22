package eg.retail.store.exception.handler;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.TypeMismatchException;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import eg.retail.store.config.dto.Attribute;
import eg.retail.store.config.dto.Message;
import eg.retail.store.exception.ErrorCode;
import eg.retail.store.exception.FunctionalException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * handler to translate exception into readable error message to be understood
 * by the client.
 */
@Component
@ControllerAdvice
@RequiredArgsConstructor
@Log4j2
public class CustomExceptionsHandler {

	private static final List<String> EXCLUDED_ATTRIBUTES = Arrays.asList("payload", "groups", "message");

	private final MessageSource messageSource;

	private HttpHeaders createHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return headers;
	}

	@ExceptionHandler(FunctionalException.class)
	public ResponseEntity<ErrorMessage> handleFunctionalException(FunctionalException ex) {
		// create error messages
		String messageCode = ex.getMessage();
		String message = messageSource.getMessage(messageCode, null, null, Locale.getDefault());

		ErrorMessage em = new ErrorMessage(ex.getStatus(), messageCode, message);

		if (ex.getAttributes().length > 0)
			em.setAttributes(Arrays.asList(ex.getAttributes()));

		if (!CollectionUtils.isEmpty(ex.getFieldErrors())) {
			Map<String, Message> errors = new HashMap<>();
			ex.getFieldErrors().forEach(fieldError -> {
				Message subMessage = new Message();
				subMessage.setCode(fieldError.getErrorCode());
				if (!CollectionUtils.isEmpty(fieldError.getAttributes())) {
					subMessage.setAttributes(fieldError.getAttributes());
				}
				errors.put(fieldError.getFieldName(), subMessage);
			});
			em.setErrors(errors);
		}

		// logging
		if (log.isDebugEnabled()) {
			log.warn("FunctionalException occurs with message [RefId: {}]: {}", em.getRefId(), messageCode, ex);
		} else {
			log.warn("FunctionalException occurs with message [RefId: {}]: {}", em.getRefId(), messageCode);
		}

		return new ResponseEntity<>(em, createHeaders(), em.getHttpStatus());
	}

	@ExceptionHandler(MissingRequestHeaderException.class)
	public ResponseEntity<ErrorMessage> handleMissingRequestHeaderException(MissingRequestHeaderException ex) {

		ErrorMessage em = new ErrorMessage(HttpStatus.BAD_REQUEST, ErrorCode.MISSING_HEADER);

		em.setAttributes(Arrays.asList(new Attribute("missingHeaderField", ex.getHeaderName())));

		// logging
		if (log.isDebugEnabled()) {
			log.warn("MissingRequestHeaderException occurs with message [RefId: {}]: {}", em.getRefId(),
					ex.getMessage(), ex);
		} else

		{
			log.warn("MissingRequestHeaderException occurs with message [RefId: {}]: {}", em.getRefId(),
					ex.getMessage());
		}

		return new ResponseEntity<>(em, createHeaders(), em.getHttpStatus());
	}

	

	@ExceptionHandler({ BindException.class })
	public ResponseEntity<ErrorMessage> handleInputViolationErrors(BindException ex) {

		ErrorMessage em = new ErrorMessage(HttpStatus.BAD_REQUEST, ErrorCode.INVALID_INPUT);
		em.setErrors(getInputValidationErrors(ex.getBindingResult(), ErrorCode.INVALID_INPUT + '.'));

		// logging
		if (log.isDebugEnabled()) {
			log.warn("BindException occurs with message [RefId: {}]: {}", em.getRefId(), ex.getMessage(), ex);
		} else {
			log.warn("BindException occurs with message [RefId: {}]: {}", em.getRefId(), ex.getMessage());
		}

		return new ResponseEntity<>(em, createHeaders(), em.getHttpStatus());
	}

	@ExceptionHandler({ MethodArgumentNotValidException.class })
	public ResponseEntity<ErrorMessage> handleInputViolationErrors(MethodArgumentNotValidException ex) {

		ErrorMessage em = new ErrorMessage(HttpStatus.BAD_REQUEST, ErrorCode.INVALID_INPUT);
		em.setErrors(getInputValidationErrors(ex.getBindingResult(), ErrorCode.INVALID_INPUT + '.'));

		// logging
		if (log.isDebugEnabled()) {
			log.warn("MethodArgumentNotValidException occurs with message [RefId: {}]: {}", em.getRefId(),
					ex.getMessage(), ex);
		} else {
			log.warn("MethodArgumentNotValidException occurs with message [RefId: {}]: {}", em.getRefId(),
					ex.getMessage());
		}

		return new ResponseEntity<>(em, createHeaders(), em.getHttpStatus());
	}

	@ExceptionHandler({ ConstraintViolationException.class })
	public ResponseEntity<ErrorMessage> handleConstraintViolationErrors(ConstraintViolationException ex) {

		ErrorMessage em = new ErrorMessage(HttpStatus.BAD_REQUEST, ErrorCode.INVALID_INPUT_PARAM);
		em.setErrors(getConstraintViolationErrors(ex.getConstraintViolations(), ErrorCode.INVALID_INPUT_PARAM + '.'));

		// logging
		if (log.isDebugEnabled()) {
			log.warn("ConstraintViolationException occurs with message [RefId: {}]: {}", em.getRefId(), ex.getMessage(),
					ex);
		} else {
			log.warn("ConstraintViolationException occurs with message [RefId: {}]: {}", em.getRefId(),
					ex.getMessage());
		}

		return new ResponseEntity<>(em, createHeaders(), em.getHttpStatus());
	}

	@ExceptionHandler({ TypeMismatchException.class })
	public ResponseEntity<ErrorMessage> handleTypeMismatchException(TypeMismatchException ex) {

		ErrorMessage em = new ErrorMessage(HttpStatus.BAD_REQUEST, ErrorCode.TYPE_MISMATCH);

		em.setAttributes(Arrays
				.asList(new Attribute("propertyName", ex.getPropertyName()),
						new Attribute("value", String.valueOf(ex.getValue())),
						new Attribute("requiredType", ex.getRequiredType().getName()))
				.stream().filter(a -> a.getValue() != null).collect(Collectors.toList()));

		// logging
		if (log.isDebugEnabled()) {
			log.warn("TypeMismatchException occurs with message [RefId: {}]: {}", em.getRefId(), ex.getMessage(), ex);
		} else {
			log.warn("TypeMismatchException occurs with message [RefId: {}]: {}", em.getRefId(), ex.getMessage());
		}

		return new ResponseEntity<>(em, createHeaders(), em.getHttpStatus());
	}

	@ExceptionHandler({ MissingServletRequestPartException.class })
	public ResponseEntity<ErrorMessage> handleMissingServletRequestPartException(
			MissingServletRequestPartException ex) {

		ErrorMessage em = new ErrorMessage(HttpStatus.BAD_REQUEST, ErrorCode.MISSING_REQUEST_PART);

		em.setAttributes(Arrays.asList(new Attribute("requestPartName", ex.getRequestPartName())));

		// logging
		if (log.isDebugEnabled()) {
			log.warn("MissingServletRequestPartException occurs with message [RefId: {}]: {}", em.getRefId(),
					ex.getMessage(), ex);
		} else {
			log.warn("MissingServletRequestPartException occurs with message [RefId: {}]: {}", em.getRefId(),
					ex.getMessage());
		}

		return new ResponseEntity<>(em, createHeaders(), em.getHttpStatus());
	}

	@ExceptionHandler({ MissingServletRequestParameterException.class })
	public ResponseEntity<ErrorMessage> handleMissingServletRequestParameterException(
			MissingServletRequestParameterException ex) {

		ErrorMessage em = new ErrorMessage(HttpStatus.BAD_REQUEST, ErrorCode.MISSING_REQUEST_PARAMETER);

		em.setAttributes(Arrays.asList(new Attribute("parameterName", ex.getParameterName())));

		// logging
		if (log.isDebugEnabled()) {
			log.warn("MissingServletRequestParameterException occurs with message [RefId: {}]: {}", em.getRefId(),
					ex.getMessage(), ex);
		} else {
			log.warn("MissingServletRequestParameterException occurs with message [RefId: {}]: {}", em.getRefId(),
					ex.getMessage());
		}

		return new ResponseEntity<>(em, createHeaders(), em.getHttpStatus());
	}

	@ExceptionHandler({ HttpMessageNotReadableException.class })
	public ResponseEntity<ErrorMessage> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {

		ErrorMessage em = new ErrorMessage(HttpStatus.BAD_REQUEST, ErrorCode.INVALID_MESSAGE_FORMAT);

		// logging
		if (log.isDebugEnabled()) {
			log.warn("HttpMessageNotReadableException occurs with message [RefId: {}]: {}", em.getRefId(),
					ex.getMessage(), ex);
		} else {
			log.warn("HttpMessageNotReadableException occurs with message [RefId: {}]: {}", em.getRefId(),
					ex.getMessage());
		}

		return new ResponseEntity<>(em, createHeaders(), em.getHttpStatus());
	}

	// 403
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorMessage> handleAccessDeniedErrors(AccessDeniedException ex) {

		ErrorMessage em = new ErrorMessage(HttpStatus.FORBIDDEN, ErrorCode.FORBIDDEN);

		// logging
		if (log.isDebugEnabled()) {
			log.warn("AccessDeniedException occurs with message [RefId: {}]: {}", em.getRefId(), ex.getMessage(), ex);
		} else {
			log.warn("AccessDeniedException occurs with message [RefId: {}]: {}", em.getRefId(), ex.getMessage());
		}

		return new ResponseEntity<>(em, createHeaders(), em.getHttpStatus());
	}

	// 405
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ErrorMessage> handleHttpRequestMethodNotSupportedException(
			HttpRequestMethodNotSupportedException ex) {

		ErrorMessage em = new ErrorMessage(HttpStatus.METHOD_NOT_ALLOWED, ErrorCode.HTTP_REQUEST_METHOD_NOT_SUPPORTED);

		em.setAttributes(Arrays.asList(new Attribute("httpMethod", ex.getMethod()),
				new Attribute("supportedHttpMethods",
						ex.getSupportedHttpMethods().stream().map(HttpMethod::name).collect(Collectors.joining(",")),
						null)));

		// logging
		if (log.isDebugEnabled()) {
			log.warn("HttpRequestMethodNotSupportedException occurs with message [RefId: {}]: {}", em.getRefId(),
					ex.getMessage(), ex);
		} else {
			log.warn("HttpRequestMethodNotSupportedException occurs with message [RefId: {}]: {}", em.getRefId(),
					ex.getMessage());
		}

		return new ResponseEntity<>(em, createHeaders(), em.getHttpStatus());
	}

	// 415
	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	public ResponseEntity<ErrorMessage> handleHttpMediaTypeNotSupportedException(
			HttpMediaTypeNotSupportedException ex) {

		ErrorMessage em = new ErrorMessage(HttpStatus.UNSUPPORTED_MEDIA_TYPE, ErrorCode.HTTP_MEDIA_TYPE_NOT_SUPPORTED);

		em.setAttributes(Arrays.asList(new Attribute("contentType", String.valueOf(ex.getContentType())),
				new Attribute("supportedMediaTypes",
						ex.getSupportedMediaTypes().stream().map(MediaType::toString).collect(Collectors.joining(",")),
						null)));

		// logging
		if (log.isDebugEnabled()) {
			log.warn("HttpMediaTypeNotSupportedException occurs with message [RefId: {}]: {}", em.getRefId(),
					ex.getMessage(), ex);
		} else {
			log.warn("HttpMediaTypeNotSupportedException occurs with message [RefId: {}]: {}", em.getRefId(),
					ex.getMessage());
		}

		return new ResponseEntity<>(em, createHeaders(), em.getHttpStatus());
	}

	// 500
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorMessage> handleOtherErrors(Exception ex) {

		ErrorMessage em = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.INTERNAL_SERVER_ERROR);

		// logging
		log.error("unknown Exception occurs with message [RefId: {}]: {}", em.getRefId(), ex.getMessage(), ex);

		return new ResponseEntity<>(em, createHeaders(), em.getHttpStatus());
	}

	private Map<String, Message> getInputValidationErrors(BindingResult result, String errorCodePrefix) {
		Map<String, Message> errors = new HashMap<>();

		result.getFieldErrors().forEach(fieldError -> {
			String fieldName = fieldError.getField();

			if (fieldName != null && fieldName.contains(".")) {
				fieldName = fieldName.substring(fieldName.indexOf('.') + 1);
			}

			if (fieldName != null && fieldName.contains("DTO.")) {
				fieldName = fieldName.substring(fieldName.indexOf("DTO.") + 4);
			}

			Message message = new Message();
			message.setCode(errorCodePrefix + fieldError.getCode());

			message.setAttributes(Arrays
					.asList(new Attribute("message", fieldError.getDefaultMessage()),
							new Attribute("invalidValue", fieldError.getRejectedValue()))
					.stream().filter(attr -> attr.getValue() != null).collect(Collectors.toList()));

			errors.put(fieldName, message);
		});

		return errors;
	}

	private Map<String, Message> getConstraintViolationErrors(Set<ConstraintViolation<?>> constraintViolations,
			String errorCodePrefix) {
		Map<String, Message> errors = new HashMap<>();

		constraintViolations.stream().forEach(error -> {
			String fieldName = String.valueOf(error.getPropertyPath());

			if (fieldName != null && fieldName.contains(".")) {
				fieldName = fieldName.substring(fieldName.indexOf('.') + 1);
			}

			if (fieldName != null && fieldName.contains("DTO.")) {
				fieldName = fieldName.substring(fieldName.indexOf("DTO.") + 4);
			}

			Message message = new Message();
			String validationType = error.getConstraintDescriptor().getAnnotation().annotationType().getName();
			message.setCode(errorCodePrefix + validationType.substring(validationType.lastIndexOf('.') + 1));

			Map<String, Object> attributes = error.getConstraintDescriptor().getAttributes().entrySet().stream()
					.filter(e -> !EXCLUDED_ATTRIBUTES.contains(e.getKey()) && e.getValue() != null)
					.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

			attributes.put("message", error.getMessage());
			attributes.put("invalidValue", error.getInvalidValue());

			message.setAttributes(attributes.entrySet().stream()
					.map(attr -> new Attribute(attr.getKey(), attr.getValue())).collect(Collectors.toList()));
			errors.put(fieldName, message);
		});

		return errors;
	}

	@ExceptionHandler(MissingRequestCookieException.class)
	public ResponseEntity<ErrorMessage> handleMissingRequestCookieException(MissingRequestCookieException ex) {

		ErrorMessage em = new ErrorMessage(HttpStatus.BAD_REQUEST, ErrorCode.MISSING_COOKIE);

		em.setAttributes(Arrays.asList(new Attribute("missingCookieField", ex.getCookieName())));

		// logging
		if (log.isDebugEnabled()) {
			log.warn("MissingRequestCookieException occurs with message [RefId: {}]: {}", em.getRefId(),
					ex.getMessage(), ex);
		} else {
			log.warn("MissingRequestCookieException occurs with message [RefId: {}]: {}", em.getRefId(),
					ex.getMessage());
		}

		return new ResponseEntity<>(em, createHeaders(), em.getHttpStatus());
	}

}
