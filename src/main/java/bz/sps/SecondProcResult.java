package bz.sps;

/*
 * структура результата второй функции
 * 
 * "out resultid bigint,out resultinfo text"
 */
public class SecondProcResult {
	private Long resultid;
	private String resultinfo;

	public SecondProcResult() {
		
	}
	
	public SecondProcResult(Long resultid, String resultinfo) {
		this.resultid = resultid;
		this.resultinfo = resultinfo;
	}

	public void setResultid(Long id) {
		this.resultid = id;
	}

	public Long getResultid() {
		return resultid;
	}

	public void setResultinfo(String info) {
		this.resultinfo = info;
	}

	public String getResultinfo() {
		return resultinfo;
	}
}
