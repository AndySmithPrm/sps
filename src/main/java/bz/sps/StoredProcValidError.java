package bz.sps;

/*
 * структура отправляется пользователю при ошибке валидации
 */
public class StoredProcValidError {
	private String field;
	private Object rejectedValue;

	public StoredProcValidError(String field, Object rejectedValue) {
		this.field = field;
		this.rejectedValue = rejectedValue;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getField() {
		return field;
	}

	public void setBadValue(String badValue) {
		this.rejectedValue = badValue;
	}

	public Object getBadValue() {
		return rejectedValue;
	}
}
