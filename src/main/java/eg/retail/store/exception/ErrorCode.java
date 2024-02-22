package eg.retail.store.exception;

public class ErrorCode {

	private ErrorCode() {
	}

	public static final String UNAUTHORIZED = "common.auth.unauthorized";
	public static final String FORBIDDEN = "common.auth.forbidden";

	public static final String INVALID_INPUT = "common.validation.invalidInput";
	public static final String INVALID_INPUT_PARAM = "common.validation.invalidInputParam";
	public static final String MISSING_HEADER = "common.validation.missingHeader";
	public static final String MISSING_COOKIE = "common.validation.missingCookie";
	public static final String TYPE_MISMATCH = "common.validation.typeMismatch";
	public static final String MISSING_REQUEST_PART = "common.validation.missingRequestPart";
	public static final String MISSING_REQUEST_PARAMETER = "common.validation.missingRequestParameter";
	public static final String INVALID_MESSAGE_FORMAT = "common.validation.invalidMessageFormat";
	public static final String NO_HANDLER_FOUND = "common.validation.noHandlerFound";
	public static final String HTTP_REQUEST_METHOD_NOT_SUPPORTED = "common.validation.httpRequestMethodNotSupported";
	public static final String HTTP_MEDIA_TYPE_NOT_SUPPORTED = "common.validation.httpMediaTypeNotSupported";

	public static final String INTERNAL_SERVER_ERROR = "common.internalServerError";
}
