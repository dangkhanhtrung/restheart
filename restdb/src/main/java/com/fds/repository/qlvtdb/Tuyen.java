package com.fds.repository.qlvtdb;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "qlvt_tuyen")
public class Tuyen {
	@Id
	private String id;

	private int stt_qg;
	private String maso_tuyen;
	private String ten_tuyen;
	public String getTen_tuyen() {
		return ten_tuyen;
	}
	public void setTen_tuyen(String ten_tuyen) {
		this.ten_tuyen = ten_tuyen;
	}
	private double cu_ly;
	private int gian_cach;
	private int ll_quyhoach;
	private int ll_kt;
	private String lotrinh_di;
	private String lotrinh_den;
	private String lotrinh_ve;
	public String getLotrinh_ve() {
		return lotrinh_ve;
	}
	public void setLotrinh_ve(String lotrinh_ve) {
		this.lotrinh_ve = lotrinh_ve;
	}
	private int trangthai;
	private String ghichu;
	private CuaKhauSource cua_khau;
	@JsonProperty(value = "STT_QG")
	private Integer STT_QG;
	private String shortName;
	private String title;
	private BenXeSource ben_xe;
	private LoaiTuyenSource loai_tuyen;
	private String site;
	public CoreObjectSource[] getCoquan_ql() {
		return coquan_ql;
	}
	public void setCoquan_ql(CoreObjectSource[] coquan_ql) {
		this.coquan_ql = coquan_ql;
	}
	private String storage;
	private String openAccess;
	private AccessRole[] accessRoles;
	private AccessRole[] accessUsers;
	private AccessRole[] accessEmails;
	private long createdAt;
	private long modifiedAt;

	private BenXeSource ben_xe_di;
	private BenXeSource ben_xe_den;
	private CoreObjectSource pl_quyhoach;
	private CoreObjectSource noi_di;
	private CoreObjectSource noi_den;
	private CoreObjectSource[] coquan_ql;
	private CoreObjectSource[] review_status;
	private DoanhNghiepSource[] doanh_nghiep;
	
	public DoanhNghiepSource[] getDoanh_nghiep() {
		return doanh_nghiep;
	}
	public void setDoanh_nghiep(DoanhNghiepSource[] doanh_nghiep) {
		this.doanh_nghiep = doanh_nghiep;
	}
	public CoreObjectSource[] getReview_status() {
		return review_status;
	}
	public void setReview_status(CoreObjectSource[] review_status) {
		this.review_status = review_status;
	}
	public CoreObjectSource getNoi_di() {
		return noi_di;
	}
	public void setNoi_di(CoreObjectSource noi_di) {
		this.noi_di = noi_di;
	}
	public CoreObjectSource getNoi_den() {
		return noi_den;
	}
	public void setNoi_den(CoreObjectSource noi_den) {
		this.noi_den = noi_den;
	}
	public CoreObjectSource getPl_quyhoach() {
		return pl_quyhoach;
	}
	public void setPl_quyhoach(CoreObjectSource pl_quyhoach) {
		this.pl_quyhoach = pl_quyhoach;
	}
	private CoreObjectSource status;
	private String description;
	
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
	public BenXeSource getBen_xe_di() {
		return ben_xe_di;
	}
	public void setBen_xe_di(BenXeSource ben_xe_di) {
		this.ben_xe_di = ben_xe_di;
	}
	public BenXeSource getBen_xe_den() {
		return ben_xe_den;
	}
	public void setBen_xe_den(BenXeSource ben_xe_den) {
		this.ben_xe_den = ben_xe_den;
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

	public LoaiTuyenSource getLoai_tuyen() {
		return this.loai_tuyen;
	}

	public void setLoai_tuyen(LoaiTuyenSource loai_tuyen) {
		this.loai_tuyen = loai_tuyen;
	}

	public BenXeSource getBen_xe() {
		return this.ben_xe;
	}

	public void setBen_xe(BenXeSource ben_xe) {
		this.ben_xe = ben_xe;
	}

	public Integer getSTT_QG() {
		return this.STT_QG;
	}

	public void setSTT_QG(Integer STT_QG) {
		this.STT_QG = STT_QG;
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

	public CuaKhauSource getCua_khau() {
		return this.cua_khau;
	}

	public void setCua_khau(CuaKhauSource cua_khau) {
		this.cua_khau = cua_khau;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getStt_qg() {
		return stt_qg;
	}
	public void setStt_qg(int stt_qg) {
		this.stt_qg = stt_qg;
	}
	public String getMaso_tuyen() {
		return maso_tuyen;
	}
	public void setMaso_tuyen(String maso_tuyen) {
		this.maso_tuyen = maso_tuyen;
	}
	public double getCu_ly() {
		return cu_ly;
	}
	public void setCu_ly(double cu_ly) {
		this.cu_ly = cu_ly;
	}
	public int getGian_cach() {
		return gian_cach;
	}
	public void setGian_cach(int gian_cach) {
		this.gian_cach = gian_cach;
	}
	public int getLl_quyhoach() {
		return ll_quyhoach;
	}
	public void setLl_quyhoach(int ll_quyhoach) {
		this.ll_quyhoach = ll_quyhoach;
	}
	public int getLl_kt() {
		return ll_kt;
	}
	public void setLl_kt(int ll_kt) {
		this.ll_kt = ll_kt;
	}
	public String getLotrinh_di() {
		return lotrinh_di;
	}
	public void setLotrinh_di(String lotrinh_di) {
		this.lotrinh_di = lotrinh_di;
	}
	public String getLotrinh_den() {
		return lotrinh_den;
	}
	public void setLotrinh_den(String lotrinh_den) {
		this.lotrinh_den = lotrinh_den;
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
