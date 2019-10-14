package com.fds.repository.qlvtdb;

public class GioBen {
	private String ben_xe;
	private String status;
	private CoreObjectSource[] doanh_nghiep;
	public String getBen_xe() {
		return ben_xe;
	}
	public void setBen_xe(String ben_xe) {
		this.ben_xe = ben_xe;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public CoreObjectSource[] getDoanh_nghiep() {
		return doanh_nghiep;
	}
	public void setDoanh_nghiep(CoreObjectSource[] doanh_nghiep) {
		this.doanh_nghiep = doanh_nghiep;
	}
	
}
