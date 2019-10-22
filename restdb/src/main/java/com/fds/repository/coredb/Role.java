package com.fds.repository.coredb;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fds.repository.qlvtdb.AccessRole;
import com.fds.repository.qlvtdb.CoreObjectSource;

@Document(collection = "role")

public class Role {
	@Id
	private String id;

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
	private String type;
	private String email;
	private String username;
	private String description;
	private CoreObjectSource status;
	private Integer order;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public CoreObjectSource getStatus() {
		return status;
	}
	public void setStatus(CoreObjectSource status) {
		this.status = status;
	}
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
}
