package com.lge.crawling.admin.management.system.entity;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.lge.crawling.admin.common.web.entity.AbstractPage;

/**
 * Admin Entity
 * @version : 1.0
 * @author  : Copyright (c) 2015 by UBIVELOX CORP. All Rights Reserved.
 */
public class AdminEntity extends AbstractPage {

	/** 관리자ID일련번호 */
	@NotEmpty
	@Size(max=20)
	private String adminIdSq                               = null;

	/** 관리자ID */
	@NotEmpty
	@Size(max=128)
	private String adminId                                 = null;

	/** 권한그룹번호 */
	@NotEmpty
	@Size(max=4)
	private String adminGrpId                              = null;

	/** 권한그룹명 */
	private String adminGrpNm                              = null;

	/** 관리자암호 */
	@NotEmpty
	@Size(max=512)
	private String adminPw                                 = null;

	/** 관리자명 */
	@Size(max=256)
	private String adminNm                                 = null;

	/** 관리자구분 */
	@NotEmpty
	@Size(max=8)
	private String adminCd                                 = null;

	/** 관리자구분명 */
	private String adminCdNm                               = null;

	/** 등록상태구분 */
	@NotEmpty
	@Size(max=8)
	private String adminStatusCd                           = null;


	/** 등록상태구분명 */
	private String adminStatusCdNm                         = null;

	/** 등록레벨구분 */
	@NotEmpty
	@Size(max=8)
	private String adminLevelCd                           = null;

	/** 전화번호 */
	@Size(max=20)
	private String telNo                                   = null;

	/** 휴대폰번호 */
	@Size(max=20)
	private String phoneNo                                 = null;

	/** 이메일주소 */
	@Size(max=512)
	private String emailDs                                 = null;

	/** 국적 */
	@Size(max=8)
	private String countryCd                               = null;

	/** 소속회사명 */
	@Size(max=64)
	private String companyNm                               = null;

	/** 부서명 */
	@Size(max=64)
	private String deptNm                                  = null;

	/** 직급명 */
	@Size(max=64)
	private String positionNm                              = null;

	/** 홈페이지주소 */
	@Size(max=512)
	private String homepageAddr                            = null;

	/** 소속회사주소 */
	@Size(max=256)
	private String companyAddr                             = null;

	/** description - 설명 */
	@Size(max=4000)
	private String adminDesc                             = null;


	public String getAdminIdSq() {
		return this.adminIdSq;
	}
	public void setAdminIdSq(String adminIdSq) {
		this.adminIdSq = adminIdSq;
	}
	public String getAdminId() {
		return this.adminId;
	}
	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}
	public String getAdminGrpId() {
		return this.adminGrpId;
	}
	public void setAdminGrpId(String adminGrpId) {
		this.adminGrpId = adminGrpId;
	}
	public String getAdminPw() {
		return this.adminPw;
	}
	public void setAdminPw(String adminPw) {
		this.adminPw = adminPw;
	}
	public String getAdminNm() {
		return this.adminNm;
	}
	public void setAdminNm(String adminNm) {
		this.adminNm = adminNm;
	}
	public String getTelNo() {
		return this.telNo;
	}
	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}
	public String getPhoneNo() {
		return this.phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getEmailDs() {
		return this.emailDs;
	}
	public void setEmailDs(String emailDs) {
		this.emailDs = emailDs;
	}
	public String getCountryCd() {
		return this.countryCd;
	}
	public void setCountryCd(String countryCd) {
		this.countryCd = countryCd;
	}
	public String getCompanyNm() {
		return this.companyNm;
	}
	public void setCompanyNm(String companyNm) {
		this.companyNm = companyNm;
	}
	public String getDeptNm() {
		return this.deptNm;
	}
	public void setDeptNm(String deptNm) {
		this.deptNm = deptNm;
	}
	public String getPositionNm() {
		return this.positionNm;
	}
	public void setPositionNm(String positionNm) {
		this.positionNm = positionNm;
	}
	public String getHomepageAddr() {
		return this.homepageAddr;
	}
	public void setHomepageAddr(String homepageAddr) {
		this.homepageAddr = homepageAddr;
	}
	public String getCompanyAddr() {
		return this.companyAddr;
	}
	public void setCompanyAddr(String companyAddr) {
		this.companyAddr = companyAddr;
	}
	public String getAdminCd() {
		return this.adminCd;
	}
	public void setAdminCd(String adminCd) {
		this.adminCd = adminCd;
	}
	public String getAdminStatusCd() {
		return this.adminStatusCd;
	}
	public void setAdminStatusCd(String adminStatusCd) {
		this.adminStatusCd = adminStatusCd;
	}
	public String getAdminLevelCd() {
		return adminLevelCd;
	}
	public void setAdminLevelCd(String adminLevelCd) {
		this.adminLevelCd = adminLevelCd;
	}
	public String getAdminGrpNm() {
		return adminGrpNm;
	}
	public void setAdminGrpNm(String adminGrpNm) {
		this.adminGrpNm = adminGrpNm;
	}
	public String getAdminCdNm() {
		return adminCdNm;
	}
	public void setAdminCdNm(String adminCdNm) {
		this.adminCdNm = adminCdNm;
	}
	public String getAdminStatusCdNm() {
		return adminStatusCdNm;
	}
	public void setAdminStatusCdNm(String adminStatusCdNm) {
		this.adminStatusCdNm = adminStatusCdNm;
	}
	public String getAdminDesc() {
		return adminDesc;
	}
	public void setAdminDesc(String adminDesc) {
		this.adminDesc = adminDesc;
	}
}
