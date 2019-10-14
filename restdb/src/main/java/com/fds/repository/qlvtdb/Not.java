package com.fds.repository.qlvtdb;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "qlvt_not")
public class Not {
	@Id
	private String id;

	private Long gio_bendi;
	private Long gio_benden;
	
	private CoreObjectSource status;
	
	private NotNgaySource[] not_ngay;

	private String site;
	private String storage;
	private int openAccess;
	private AccessRole[] accessRoles;
	private AccessRole[] accessUsers;
	private AccessRole[] accessEmails;
	private long createdAt;
	private long modifiedAt;
	private String type;
	private CoreObjectSource tuyen;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public CoreObjectSource getTuyen() {
		return tuyen;
	}

	public void setTuyen(CoreObjectSource tuyen) {
		this.tuyen = tuyen;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getStorage() {
		return storage;
	}

	public void setStorage(String storage) {
		this.storage = storage;
	}

	public int getOpenAccess() {
		return openAccess;
	}

	public void setOpenAccess(int openAccess) {
		this.openAccess = openAccess;
	}

	public AccessRole[] getAccessRoles() {
		return accessRoles;
	}

	public void setAccessRoles(AccessRole[] accessRoles) {
		this.accessRoles = accessRoles;
	}

	public AccessRole[] getAccessUsers() {
		return accessUsers;
	}

	public void setAccessUsers(AccessRole[] accessUsers) {
		this.accessUsers = accessUsers;
	}

	public AccessRole[] getAccessEmails() {
		return accessEmails;
	}

	public void setAccessEmails(AccessRole[] accessEmails) {
		this.accessEmails = accessEmails;
	}

	public long getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(long createdAt) {
		this.createdAt = createdAt;
	}

	public long getModifiedAt() {
		return modifiedAt;
	}

	public void setModifiedAt(long modifiedAt) {
		this.modifiedAt = modifiedAt;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getGio_bendi() {
		return gio_bendi;
	}

	public void setGio_bendi(Long gio_bendi) {
		this.gio_bendi = gio_bendi;
	}

	public Long getGio_benden() {
		return gio_benden;
	}

	public void setGio_benden(Long gio_benden) {
		this.gio_benden = gio_benden;
	}

	public CoreObjectSource getStatus() {
		return status;
	}

	public void setStatus(CoreObjectSource status) {
		this.status = status;
	}

	public NotNgaySource[] getNot_ngay() {
		return not_ngay;
	}

	public void setNot_ngay(NotNgaySource[] not_ngay) {
		this.not_ngay = not_ngay;
	}
	
}
