package eg.retail.store.exception.handler;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import eg.retail.store.config.dto.Message;
import eg.retail.store.config.dto.Message.MessageLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * object describing an error message sent to the client.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = false)
public class ErrorMessage extends Message {

	private HttpStatus httpStatus;

	private Integer status;

	private String refId;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss.SSS")
	private Date timestamp = new Date();

	private Map<String, Message> errors;

	public ErrorMessage(HttpStatus httpStatus, String messageCode, String message) {
		this(httpStatus, messageCode);
		this.setMessage(message);
	}

	public ErrorMessage(HttpStatus httpStatus, String messageCode) {
		this.httpStatus = httpStatus;
		this.setCode(messageCode);
		this.setLevel(MessageLevel.ERROR);
		this.setRefId(UUID.randomUUID().toString());
	}

	public int getStatus() {
		return httpStatus != null ? httpStatus.value() : status;
	}
}