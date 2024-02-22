package eg.retail.store.config.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Attribute {

	@NotBlank
	@Min(3)
	private String key;
	private Object value;
	private UNIT unit;

	public Attribute(String key, Object value) {
		this.key = key;
		this.value = value;
	}

	public enum UNIT {
		DATE("date"), DATETIME("datetime"), MONEY("money");

		private final String value;

		UNIT(String value) {
			this.value = value;
		}

		public String value() {
			return value;
		}
	}
}
