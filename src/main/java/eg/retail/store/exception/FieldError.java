package eg.retail.store.exception;

import java.util.List;

import eg.retail.store.config.dto.Attribute;

public class FieldError {

	private String errorCode;
	private String fieldName;
	private List<Attribute> attributes;

	public FieldError(String errorCode, String fieldName) {
		this.errorCode = errorCode;
		this.fieldName = fieldName;
	}

	public FieldError(String errorCode, String fieldName, List<Attribute> attributes) {
		this(errorCode, fieldName);
		this.attributes = attributes;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getFieldName() {
		return fieldName;
	}

	public List<Attribute> getAttributes() {
		return attributes;
	}
}