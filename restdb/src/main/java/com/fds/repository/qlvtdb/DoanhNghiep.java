package com.fds.repository.qlvtdb;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "qlvt_doanh_nghiep")
public class DoanhNghiep {
	@Id
	private String id;

	private String ma_dn;
	private String ten_dn;
	private String ten_viettat;
	private String so_gcndkkd;
	private Long ngaycap_gcndkkd;
	private Long ngayhh_gcndkkd;
	private String coquan_capphep;
	private String ms_thue;
	private CoreObjectSource loai_dn;
	
	private String diachi;
	private CoreObject tinh;
	private CoreObject huyen;
	private CoreObject xa;
	private String sdt;
	private String fax;
	private String email;
	private String web;

	private String site;
	private String storage;
	private String openAccess;
	private AccessRole[] accessRoles;
	private AccessRole[] accessUsers;
	private AccessRole[] accessEmails;
	private long createdAt;

	private Long total_phuongtien;
	private String shortName;
	private String title;

	public Long getTotal_phuongtien() {
		return total_phuongtien;
	}

	public void setTotal_phuongtien(Long total_phuongtien) {
		this.total_phuongtien = total_phuongtien;
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

	public String getOpenAccess() {
		return this.openAccess;
	}

	public void setOpenAccess(String openAccess) {
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
	private long modifiedAt;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMa_dn() {
		return ma_dn;
	}
	public void setMa_dn(String ma_dn) {
		this.ma_dn = ma_dn;
	}
	public String getTen_dn() {
		return ten_dn;
	}
	public void setTen_dn(String ten_dn) {
		this.ten_dn = ten_dn;
	}
	public String getTen_viettat() {
		return ten_viettat;
	}
	public void setTen_viettat(String ten_viettat) {
		this.ten_viettat = ten_viettat;
	}
	public String getSo_gcndkkd() {
		return so_gcndkkd;
	}
	public void setSo_gcndkkd(String so_gcndkkd) {
		this.so_gcndkkd = so_gcndkkd;
	}
	public Long getNgaycap_gcndkkd() {
		return ngaycap_gcndkkd;
	}
	public void setNgaycap_gcndkkd(Long ngaycap_gcndkkd) {
		this.ngaycap_gcndkkd = ngaycap_gcndkkd;
	}
	public Long getNgayhh_gcndkkd() {
		return ngayhh_gcndkkd;
	}
	public void setNgayhh_gcndkkd(Long ngayhh_gcndkkd) {
		this.ngayhh_gcndkkd = ngayhh_gcndkkd;
	}
	public String getCoquan_capphep() {
		return coquan_capphep;
	}
	public void setCoquan_capphep(String coquan_capphep) {
		this.coquan_capphep = coquan_capphep;
	}
	public String getMs_thue() {
		return ms_thue;
	}
	public void setMs_thue(String ms_thue) {
		this.ms_thue = ms_thue;
	}
	public CoreObjectSource getLoai_dn() {
		return loai_dn;
	}
	public void setLoai_dn(CoreObjectSource loai_dn) {
		this.loai_dn = loai_dn;
	}
	public String getDiachi() {
		return diachi;
	}
	public void setDiachi(String diachi) {
		this.diachi = diachi;
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
	public String getSdt() {
		return sdt;
	}
	public void setSdt(String sdt) {
		this.sdt = sdt;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getWeb() {
		return web;
	}
	public void setWeb(String web) {
		this.web = web;
	}
}
