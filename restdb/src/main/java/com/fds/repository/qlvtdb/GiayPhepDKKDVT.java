package com.fds.repository.qlvtdb;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "qlvt_gp_dkkdvt")
public class GiayPhepDKKDVT {
	@Id
	private String id;

	private String shortName;
	private String title;

	private String so_gp;
	private CoreObjectSource doanh_nghiep;
	public CoreObjectSource getDoanh_nghiep() {
		return doanh_nghiep;
	}
	public void setDoanh_nghiep(CoreObjectSource doanh_nghiep) {
		this.doanh_nghiep = doanh_nghiep;
	}
	private CoreObjectSource loai;
	private CoreObjectSource coquan_ql;
	private Long lan_capphep;
	private Long ngay_cap;
	private Long ngay_hh;
	private Long ngay_thuhoi;
	private String lydo_thuhoi;
	private String description;
	private Long order;
	private CoreObjectSource status;
	
	private String site;
	private String storage;
	private String openAccess;
	private AccessRole[] accessRoles;
	private AccessRole[] accessUsers;
	private AccessRole[] accessEmails;
	private long createdAt;
	private long modifiedAt;
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
	public String getSo_gp() {
		return so_gp;
	}
	public void setSo_gp(String so_gp) {
		this.so_gp = so_gp;
	}
	public CoreObjectSource getLoai() {
		return loai;
	}
	public void setLoai(CoreObjectSource loai) {
		this.loai = loai;
	}
	public CoreObjectSource getCoquan_ql() {
		return coquan_ql;
	}
	public void setCoquan_ql(CoreObjectSource coquan_ql) {
		this.coquan_ql = coquan_ql;
	}
	public Long getLan_capphep() {
		return lan_capphep;
	}
	public void setLan_capphep(Long lan_capphep) {
		this.lan_capphep = lan_capphep;
	}
	public Long getNgay_cap() {
		return ngay_cap;
	}
	public void setNgay_cap(Long ngay_cap) {
		this.ngay_cap = ngay_cap;
	}
	public Long getNgay_hh() {
		return ngay_hh;
	}
	public void setNgay_hh(Long ngay_hh) {
		this.ngay_hh = ngay_hh;
	}
	public Long getNgay_thuhoi() {
		return ngay_thuhoi;
	}
	public void setNgay_thuhoi(Long ngay_thuhoi) {
		this.ngay_thuhoi = ngay_thuhoi;
	}
	public String getLydo_thuhoi() {
		return lydo_thuhoi;
	}
	public void setLydo_thuhoi(String lydo_thuhoi) {
		this.lydo_thuhoi = lydo_thuhoi;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getOrder() {
		return order;
	}
	public void setOrder(Long order) {
		this.order = order;
	}
	public CoreObjectSource getStatus() {
		return status;
	}
	public void setStatus(CoreObjectSource status) {
		this.status = status;
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
	public String getOpenAccess() {
		return openAccess;
	}
	public void setOpenAccess(String openAccess) {
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
	
}
