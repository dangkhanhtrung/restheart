package com.fds.repository.qlvtdb;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection = "qlvt_phuong_tien")
public class PhuongTien {
	@Id
	private String id;

	private String bks;
	private int succhua;
	private int nam_sx;
	private CoreObject nuoc_sx;
	private CoreObject hieuxe;
	private CoreObject mauson;
	private int sokhung;
	private int somay;
	private int nam_hetnienhan;
	private int nam_caitao;
	private int trongtai;
	private CoreObject loai_pt;
	private String nguoi_sohuu;
	private String ghichu;
	private int trangthai;
	private CoreObject coquan_ql;

	private String site;
	private String storage;
	private int openAccess;
	private AccessRole[] accessRoles;
	private AccessRole[] accessUsers;
	private AccessRole[] accessEmails;
	private long createdAt;
	private long modifiedAt;
	private CoreObject doanh_nghiep;

	public CoreObject getDoanh_nghiep() {
		return this.doanh_nghiep;
	}

	public void setDoanh_nghiep(CoreObject doanh_nghiep) {
		this.doanh_nghiep = doanh_nghiep;
	}

	public long getModifiedAt() {
		return this.modifiedAt;
	}

	public void setModifiedAt(long modifiedAt) {
		this.modifiedAt = modifiedAt;
	}

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
	public String getBks() {
		return bks;
	}
	public void setBks(String bks) {
		this.bks = bks;
	}
	public int getSucchua() {
		return succhua;
	}
	public void setSucchua(int succhua) {
		this.succhua = succhua;
	}
	public int getNam_sx() {
		return nam_sx;
	}
	public void setNam_sx(int nam_sx) {
		this.nam_sx = nam_sx;
	}
	public CoreObject getNuoc_sx() {
		return nuoc_sx;
	}
	public void setNuoc_sx(CoreObject nuoc_sx) {
		this.nuoc_sx = nuoc_sx;
	}
	public CoreObject getHieuxe() {
		return hieuxe;
	}
	public void setHieuxe(CoreObject hieuxe) {
		this.hieuxe = hieuxe;
	}
	public CoreObject getMauson() {
		return mauson;
	}
	public void setMauson(CoreObject mauson) {
		this.mauson = mauson;
	}
	public int getSokhung() {
		return sokhung;
	}
	public void setSokhung(int sokhung) {
		this.sokhung = sokhung;
	}
	public int getSomay() {
		return somay;
	}
	public void setSomay(int somay) {
		this.somay = somay;
	}
	public int getNam_hetnienhan() {
		return nam_hetnienhan;
	}
	public void setNam_hetnienhan(int nam_hetnienhan) {
		this.nam_hetnienhan = nam_hetnienhan;
	}
	public int getNam_caitao() {
		return nam_caitao;
	}
	public void setNam_caitao(int nam_caitao) {
		this.nam_caitao = nam_caitao;
	}
	public int getTrongtai() {
		return trongtai;
	}
	public void setTrongtai(int trongtai) {
		this.trongtai = trongtai;
	}
	public CoreObject getLoai_pt() {
		return loai_pt;
	}
	public void setLoai_pt(CoreObject loai_pt) {
		this.loai_pt = loai_pt;
	}
	public String getNguoi_sohuu() {
		return nguoi_sohuu;
	}
	public void setNguoi_sohuu(String nguoi_sohuu) {
		this.nguoi_sohuu = nguoi_sohuu;
	}
	public String getGhichu() {
		return ghichu;
	}
	public void setGhichu(String ghichu) {
		this.ghichu = ghichu;
	}
	public int getTrangthai() {
		return trangthai;
	}
	public void setTrangthai(int trangthai) {
		this.trangthai = trangthai;
	}
	public CoreObject getCoquan_ql() {
		return coquan_ql;
	}
	public void setCoquan_ql(CoreObject coquan_ql) {
		this.coquan_ql = coquan_ql;
	}
}
