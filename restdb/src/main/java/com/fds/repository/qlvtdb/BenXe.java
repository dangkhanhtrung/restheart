package com.fds.repository.qlvtdb;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "qlvt_ben_xe")
public class BenXe {
	@Id
	private String id;

	private Long ma_bx;
	private String ten_bx;
	private String ten_viettat;
	private String diachi;
	private String geo;
	private String sdt;
	private CoreObject tinh;
	private CoreObject huyen;
	private CoreObject xa;
	private CoreObject coquan_ql;
	private int trangthai;
	private String ghichu;

	private String site;
	private String storage;
	private int openAccess;
	private AccessRole[] accessRoles;
	private AccessRole[] accessUsers;
	private AccessRole[] accessEmails;
	private long createdAt;
	private long modifiedAt;
	private String shortName;
	private String title;

	public String getSite() {
		return this.site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getStorage() {
		return this.storage;
	}

	public void setStorage(String storage) {
		this.storage = storage;
	}

	public int getOpenAccess() {
		return this.openAccess;
	}

	public void setOpenAccess(int openAccess) {
		this.openAccess = openAccess;
	}

	public AccessRole[] getAccessRoles() {
		return this.accessRoles;
	}

	public void setAccessRoles(AccessRole[] accessRoles) {
		this.accessRoles = accessRoles;
	}

	public AccessRole[] getAccessUsers() {
		return this.accessUsers;
	}

	public void setAccessUsers(AccessRole[] accessUsers) {
		this.accessUsers = accessUsers;
	}

	public AccessRole[] getAccessEmails() {
		return this.accessEmails;
	}

	public void setAccessEmails(AccessRole[] accessEmails) {
		this.accessEmails = accessEmails;
	}

	public long getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(long createdAt) {
		this.createdAt = createdAt;
	}

	public long getModifiedAt() {
		return this.modifiedAt;
	}

	public void setModifiedAt(long modifiedAt) {
		this.modifiedAt = modifiedAt;
	}

	public String getShortName() {
		return this.shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Long getMa_bx() {
		return ma_bx;
	}
	public void setMa_bx(Long ma_bx) {
		this.ma_bx = ma_bx;
	}
	public String getTen_bx() {
		return ten_bx;
	}
	public void setTen_bx(String ten_bx) {
		this.ten_bx = ten_bx;
	}
	public String getTen_viettat() {
		return ten_viettat;
	}
	public void setTen_viettat(String ten_viettat) {
		this.ten_viettat = ten_viettat;
	}
	public String getDiachi() {
		return diachi;
	}
	public void setDiachi(String diachi) {
		this.diachi = diachi;
	}
	public String getGeo() {
		return geo;
	}
	public void setGeo(String geo) {
		this.geo = geo;
	}
	public String getSdt() {
		return sdt;
	}
	public void setSdt(String sdt) {
		this.sdt = sdt;
	}
	public CoreObject getTinh() {
		return tinh;
	}
	public void setTinh(CoreObject tinh) {
		this.tinh = tinh;
	}
	public CoreObject getHuyen() {
		return huyen;
	}
	public void setHuyen(CoreObject huyen) {
		this.huyen = huyen;
	}
	public CoreObject getXa() {
		return xa;
	}
	public void setXa(CoreObject xa) {
		this.xa = xa;
	}
	public CoreObject getCoquan_ql() {
		return coquan_ql;
	}
	public void setCoquan_ql(CoreObject coquan_ql) {
		this.coquan_ql = coquan_ql;
	}
	public int getTrangthai() {
		return trangthai;
	}
	public void setTrangthai(int trangthai) {
		this.trangthai = trangthai;
	}
	public String getGhichu() {
		return ghichu;
	}
	public void setGhichu(String ghichu) {
		this.ghichu = ghichu;
	}
	
}
