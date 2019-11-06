package com.fds.repository.qlvtdb;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "qlvt_gp_bh_ph")
public class PhuHieuBienHieu {
	@Id
	private String id;

	private String shortName;
	private String title;
	private CoreObjectSource phuong_tien;
	private CoreObjectSource tuyen;
	private CoreObjectSource loaihinh;
	private CoreObjectSource loai_ph;
	private Long ngay_cap;
	private Long ngay_hh;
	private Long ngay_thuhoi;
	private boolean la_bh;
	private CoreObjectSource coquan_ql;
	private String lydo;
	private String description;
	private CoreObjectSource status;
	
	private String site;
	private String storage;
	private String openAccess;
	private AccessRole[] accessRoles;
	private AccessRole[] accessUsers;
	private AccessRole[] accessEmails;
	private long createdAt;
	private long modifiedAt;
	private int order;
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
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
	public CoreObjectSource getPhuong_tien() {
		return phuong_tien;
	}
	public void setPhuong_tien(CoreObjectSource phuong_tien) {
		this.phuong_tien = phuong_tien;
	}
	public CoreObjectSource getTuyen() {
		return tuyen;
	}
	public void setTuyen(CoreObjectSource tuyen) {
		this.tuyen = tuyen;
	}
	public CoreObjectSource getLoaihinh() {
		return loaihinh;
	}
	public void setLoaihinh(CoreObjectSource loaihinh) {
		this.loaihinh = loaihinh;
	}
	public CoreObjectSource getLoai_ph() {
		return loai_ph;
	}
	public void setLoai_ph(CoreObjectSource loai_ph) {
		this.loai_ph = loai_ph;
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
	public boolean isLa_bh() {
		return la_bh;
	}
	public void setLa_bh(boolean la_bh) {
		this.la_bh = la_bh;
	}
	public CoreObjectSource getCoquan_ql() {
		return coquan_ql;
	}
	public void setCoquan_ql(CoreObjectSource coquan_ql) {
		this.coquan_ql = coquan_ql;
	}
	public String getLydo() {
		return lydo;
	}
	public void setLydo(String lydo) {
		this.lydo = lydo;
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
