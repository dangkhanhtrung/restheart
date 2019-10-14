package com.fds.repository.qlvtdb;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "qlvt_cua_khau")
public class CuaKhau {
	@Id
	private String id;
	
	private String ma_cuakhau;
	private String ten_cuakhau;
	private CoreObjectSource tinh;
	private CoreObjectSource quocgia_qt;
	private String shortName;
	private String title;
	private String site;
	private String storage;
	private int openAccess;
	private AccessRole[] accessRoles;
	private AccessRole[] accessUsers;
	private AccessRole[] accessEmails;
	private long createdAt;
	private long modifiedAt;

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

	public String getMa_cuakhau() {
		return ma_cuakhau;
	}

	public void setMa_cuakhau(String ma_cuakhau) {
		this.ma_cuakhau = ma_cuakhau;
	}

	public String getTen_cuakhau() {
		return ten_cuakhau;
	}

	public void setTen_cuakhau(String ten_cuakhau) {
		this.ten_cuakhau = ten_cuakhau;
	}

	public CoreObjectSource getTinh() {
		return tinh;
	}

	public void setTinh(CoreObjectSource tinh) {
		this.tinh = tinh;
	}

	public CoreObjectSource getQuocgia_qt() {
		return quocgia_qt;
	}

	public void setQuocgia_qt(CoreObjectSource quocgia_qt) {
		this.quocgia_qt = quocgia_qt;
	}
}
