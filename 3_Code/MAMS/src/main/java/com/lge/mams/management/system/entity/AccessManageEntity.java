package com.lge.mams.management.system.entity;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.lge.mams.common.web.entity.AbstractPage;

/**
 * AccessManage Entity
 * 접근제어관리정보 Entity
 * @version : 1.0
 * @author  : Copyright (c) 2015 by MIRINCOM CORP. All Rights Reserved.
 */
public class AccessManageEntity extends AbstractPage {

    /**
     * Access control sequence
     * 접근제어등록일련번호
     */
	@NotEmpty
	@Size(max = 20)
    private String accessControlSeq                        = null;

    /**
     * Access control ID
     * 접근제어ID
     */
	@NotEmpty
	@Size(max = 256)
    private String accessControlId                         = null;

    /**
     * Access control pw
     * 접근제어암호
     */
	@NotEmpty
	@Size(max = 256)
    private String accessControlPw                         = null;

    /**
     * Access control ip
     * 접근제어IP
     */
	@Size(max = 128)
    private String accessControlIp                         = null;

    /**
     * Access control agree start date
     * 접근제어허용시작일시
     */
	@NotEmpty
	@Size(max = 14)
    private String accessControlAgreeStartDt               = null;

    /**
     * Access control agree end date
     * 접근제어허용완료일시
     */
	@NotEmpty
	@Size(max = 14)
    private String accessControlAgreeEndDt                 = null;

    /**
     * Access control system name
     * 접근제어시스템명
     */
	@NotEmpty
	@Size(max = 256)
    private String accessControlSystemNm                   = null;

    /**
     * Access control manager name
     * 접근제어시스템담당자명
     */
	@Size(max = 128)
    private String accessControlManagerNm                  = null;

    /**
     * Access control manager tel number
     * 접근제어시스템담당자연락처
     */
	@Size(max = 20)
    private String accessControlManagerTelNo               = null;

    public String getAccessControlSeq() {
        return this.accessControlSeq;
    }
    public void setAccessControlSeq(String accessControlSeq) {
        this.accessControlSeq = accessControlSeq;
    }
    public String getAccessControlId() {
        return this.accessControlId;
    }
    public void setAccessControlId(String accessControlId) {
        this.accessControlId = accessControlId;
    }
    public String getAccessControlPw() {
        return this.accessControlPw;
    }
    public void setAccessControlPw(String accessControlPw) {
        this.accessControlPw = accessControlPw;
    }
    public String getAccessControlIp() {
        return this.accessControlIp;
    }
    public void setAccessControlIp(String accessControlIp) {
        this.accessControlIp = accessControlIp;
    }
    public String getAccessControlAgreeStartDt() {
        return this.accessControlAgreeStartDt;
    }
    public void setAccessControlAgreeStartDt(String accessControlAgreeStartDt) {
        this.accessControlAgreeStartDt = accessControlAgreeStartDt;
    }
    public String getAccessControlAgreeEndDt() {
        return this.accessControlAgreeEndDt;
    }
    public void setAccessControlAgreeEndDt(String accessControlAgreeEndDt) {
        this.accessControlAgreeEndDt = accessControlAgreeEndDt;
    }
    public String getAccessControlSystemNm() {
        return this.accessControlSystemNm;
    }
    public void setAccessControlSystemNm(String accessControlSystemNm) {
        this.accessControlSystemNm = accessControlSystemNm;
    }
    public String getAccessControlManagerNm() {
        return this.accessControlManagerNm;
    }
    public void setAccessControlManagerNm(String accessControlManagerNm) {
        this.accessControlManagerNm = accessControlManagerNm;
    }
    public String getAccessControlManagerTelNo() {
        return this.accessControlManagerTelNo;
    }
    public void setAccessControlManagerTelNo(String accessControlManagerTelNo) {
        this.accessControlManagerTelNo = accessControlManagerTelNo;
    }
}
