package eg.retail.store.config.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Message {

	private String code;
	private String message;
	private MessageLevel level;
	private List<Attribute> attributes;

	public enum MessageLevel {
		INFO("info"), WARNING("warning"), ERROR("error"), CONFIRM("confirm");

		private final String value;

		MessageLevel(String value) {
			this.value = value;
		}

		public String value() {
			return value;
		}
	}
}
