package bz.sps;

/*
 * структура результата отдаваемого клиенту в http response body
 */
public class StoredProcResult {
	private final boolean success;
	private final String msg; // OK или код ошибки
	private final Object data; // результат вызова или подробности ошибки

	public StoredProcResult(boolean success, String msg, Object data) {
		this.success = success;
		this.msg = msg;
		this.data = data;
	}
	
	public boolean getSuccess() {
		return success;
	}
	
	public String getMsg() {
		return msg;
	}
	
	public Object getData() {
		return data;
	}
}
