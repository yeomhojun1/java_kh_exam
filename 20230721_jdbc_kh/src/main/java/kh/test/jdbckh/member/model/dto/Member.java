package kh.test.jdbckh.member.model.dto;

public class Member {
//	MID    NOT NULL VARCHAR2(20)  
//	MPWD   NOT NULL VARCHAR2(20)  
//	MNAME  NOT NULL VARCHAR2(50)  
//	MEMAIL NOT NULL VARCHAR2(150) 
	private String mid;
	private String mpwd;
	private String mname;
	private String memail;
	public Member(){
		super();
	}
	public Member(String mid, String mpwd) {
		super();
		this.mid = mid;
		this.mpwd = mpwd;
	}

	public Member(String mid, String mname, String memail) {
		super();
		this.mid = mid;
		this.mname = mname;
		this.memail = memail;
	}

	@Override
	public String toString() {
		return "Member [mid=" + mid + ", mpwd=" + mpwd + ", mname=" + mname + ", memail=" + memail + "]";
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getMpwd() {
		return mpwd;
	}
	public void setMpwd(String mpwd) {
		this.mpwd = mpwd;
	}
	public String getMname() {
		return mname;
	}
	public void setMname(String mname) {
		this.mname = mname;
	}
	public String getMemail() {
		return memail;
	}
	public void setMemail(String memail) {
		this.memail = memail;
	}
	
}
