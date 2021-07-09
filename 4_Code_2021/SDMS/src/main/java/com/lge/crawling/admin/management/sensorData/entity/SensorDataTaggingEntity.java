package com.lge.crawling.admin.management.sensorData.entity;

import com.google.gson.Gson;
import com.lge.crawling.admin.common.web.entity.AbstractPage;

/**
 * The type Image tagging entity.
 */
public class SensorDataTaggingEntity extends AbstractPage {

    private String sensorDataFileSq;
    
    private String sensorDataFilePackageIdSq;

    private String sensorDataFileAgent;
    
    private String sensorDataFileGroup;
    
    private String sensorDataFileNm;

    private String sensorDataFilePath;

    private String sensorDataFileSize;

    private String sensorDataFileScaleX;

    private String sensorDataFileScaleY;

    private String sensorDataFileTypeCd;

    private String sensorDataFileTypeCdNm;

    //private String sensorDataFileDownloadPathCd;

    //private String sensorDataFileDownloadPathCdNm;

    private String lastUpdateImageMagnification;

    private String sensorDataJsonFileSq;

    private String sensorDataJsonFileDesc;

    private String sensorDataJsonFileNm;

    private String sensorDataJsonFilePath;

    private String sensorDataJsonFileSize;

    private String sensorDataJsonXmlConvFileDesc;

    private String taggingYn;

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    /**
     * Gets image file sq.
     *
     * @return the image file sq
     */
    public String getSensorDataFileSq() {
        return sensorDataFileSq;
    }

    /**
     * Sets image file sq.
     *
     * @param sensorDataFileSq the image file sq
     */
    public void setSensorDataFileSq(String sensorDataFileSq) {
        this.sensorDataFileSq = sensorDataFileSq;
    }

    
    public String getSensorDataFilePackageIdSq() {
        return sensorDataFilePackageIdSq;
    }

    public void setSensorDataFilePackageIdSq(String sensorDataFilePackageIdSq) {
        this.sensorDataFilePackageIdSq = sensorDataFilePackageIdSq;
    }

    
    /**
     * Gets image file nm.
     *
     * @return the image file nm
     */
    public String getSensorDataFileNm() {
        return sensorDataFileNm;
    }

    /**
     * Sets image file nm.
     *
     * @param sensorDataFileNm the image file nm
     */
    public void setSensorDataFileNm(String sensorDataFileNm) {
        this.sensorDataFileNm = sensorDataFileNm;
    }
    
    public String getSensorDataFileAgent() {
        return sensorDataFileAgent;
    }
    public void setSensorDataFileAgent(String sensorDataFileAgent) {
        this.sensorDataFileAgent = sensorDataFileAgent;
    }
    
    public String getSensorDataFileGroup() {
        return sensorDataFileGroup;
    }
    public void setSensorDataFileGroup(String sensorDataFileGroup) {
        this.sensorDataFileGroup = sensorDataFileGroup;
    }

    /**
     * Gets image file path.
     *
     * @return the image file path
     */
    public String getSensorDataFilePath() {
        return sensorDataFilePath;
    }

    /**
     * Sets image file path.
     *
     * @param sensorDataFilePath the image file path
     */
    public void setSensorDataFilePath(String sensorDataFilePath) {
        this.sensorDataFilePath = sensorDataFilePath;
    }

    /**
     * Gets image file size.
     *
     * @return the image file size
     */
    public String getSensorDataFileSize() {
        return sensorDataFileSize;
    }

    /**
     * Sets image file size.
     *
     * @param sensorDataFileSize the image file size
     */
    public void setSensorDataFileSize(String sensorDataFileSize) {
        this.sensorDataFileSize = sensorDataFileSize;
    }

    /**
     * Gets image file scale x.
     *
     * @return the image file scale x
     */
    public String getSensorDataFileScaleX() {
        return sensorDataFileScaleX;
    }

    /**
     * Sets image file scale x.
     *
     * @param sensorDataFileScaleX the image file scale x
     */
    public void setSensorDataFileScaleX(String sensorDataFileScaleX) {
        this.sensorDataFileScaleX = sensorDataFileScaleX;
    }

    /**
     * Gets image file scale y.
     *
     * @return the image file scale y
     */
    public String getSensorDataFileScaleY() {
        return sensorDataFileScaleY;
    }

    /**
     * Sets image file scale y.
     *
     * @param sensorDataFileScaleY the image file scale y
     */
    public void setSensorDataFileScaleY(String sensorDataFileScaleY) {
        this.sensorDataFileScaleY = sensorDataFileScaleY;
    }

    /**
     * Gets image file type cd.
     *
     * @return the image file type cd
     */
    public String getSensorDataFileTypeCd() {
        return sensorDataFileTypeCd;
    }

    /**
     * Sets image file type cd.
     *
     * @param sensorDataFileTypeCd the image file type cd
     */
    public void setSensorDataFileTypeCd(String sensorDataFileTypeCd) {
        this.sensorDataFileTypeCd = sensorDataFileTypeCd;
    }

    /**
     * Gets image file type cd nm.
     *
     * @return the image file type cd nm
     */
    public String getSensorDataFileTypeCdNm() {
        return sensorDataFileTypeCdNm;
    }

    /**
     * Sets image file type cd nm.
     *
     * @param sensorDataFileTypeCdNm the image file type cd nm
     */
    public void setSensorDataFileTypeCdNm(String sensorDataFileTypeCdNm) {
        this.sensorDataFileTypeCdNm = sensorDataFileTypeCdNm;
    }

    /**
     * Gets image file download path cd.
     *
     * @return the image file download path cd
     */
//    public String getSensorDataFileDownloadPathCd() {
//        return sensorDataFileDownloadPathCd;
//    }

    /**
     * Sets image file download path cd.
     *
     * @param sensorDataFileDownloadPathCd the image file download path cd
     */
//    public void setSensorDataFileDownloadPathCd(String sensorDataFileDownloadPathCd) {
//        this.sensorDataFileDownloadPathCd = sensorDataFileDownloadPathCd;
//    }

    /**
     * Gets image file download path cd nm.
     *
     * @return the image file download path cd nm
     */
//    public String getSensorDataFileDownloadPathCdNm() {
//        return sensorDataFileDownloadPathCdNm;
//    }

    /**
     * Sets image file download path cd nm.
     *
     * @param sensorDataFileDownloadPathCdNm the image file download path cd nm
     */
//    public void setSensorDataFileDownloadPathCdNm(String sensorDataFileDownloadPathCdNm) {
//        this.sensorDataFileDownloadPathCdNm = sensorDataFileDownloadPathCdNm;
//    }

    /**
     * Gets image json file sq.
     *
     * @return the image json file sq
     */
    public String getSensorDataJsonFileSq() {
        return sensorDataJsonFileSq;
    }

    /**
     * Sets image json file sq.
     *
     * @param sensorDataJsonFileSq the image json file sq
     */
    public void setSensorDataJsonFileSq(String sensorDataJsonFileSq) {
        this.sensorDataJsonFileSq = sensorDataJsonFileSq;
    }

    /**
     * Gets image json file desc.
     *
     * @return the image json file desc
     */
    public String getSensorDataJsonFileDesc() {
        return sensorDataJsonFileDesc;
    }

    /**
     * Sets image json file desc.
     *
     * @param sensorDataJsonFileDesc the image json file desc
     */
    public void setSensorDataJsonFileDesc(String sensorDataJsonFileDesc) {
        this.sensorDataJsonFileDesc = sensorDataJsonFileDesc;
    }

    /**
     * Gets image json file nm.
     *
     * @return the image json file nm
     */
    public String getSensorDataJsonFileNm() {
        return sensorDataJsonFileNm;
    }

    /**
     * Sets image json file nm.
     *
     * @param sensorDataJsonFileNm the image json file nm
     */
    public void setSensorDataJsonFileNm(String sensorDataJsonFileNm) {
        this.sensorDataJsonFileNm = sensorDataJsonFileNm;
    }

    /**
     * Gets image json file path.
     *
     * @return the image json file path
     */
    public String getSensorDataJsonFilePath() {
        return sensorDataJsonFilePath;
    }

    /**
     * Sets image json file path.
     *
     * @param sensorDataJsonFilePath the image json file path
     */
    public void setSensorDataJsonFilePath(String sensorDataJsonFilePath) {
        this.sensorDataJsonFilePath = sensorDataJsonFilePath;
    }

    /**
     * Gets image json file size.
     *
     * @return the image json file size
     */
    public String getSensorDataJsonFileSize() {
        return sensorDataJsonFileSize;
    }

    /**
     * Sets image json file size.
     *
     * @param sensorDataJsonFileSize the image json file size
     */
    public void setSensorDataJsonFileSize(String sensorDataJsonFileSize) {
        this.sensorDataJsonFileSize = sensorDataJsonFileSize;
    }

    /**
     * Gets last update image magnification.
     *
     * @return the last update image magnification
     */
    public String getLastUpdateImageMagnification() {
        return lastUpdateImageMagnification;
    }

    /**
     * Sets last update image magnification.
     *
     * @param lastUpdateImageMagnification the last update image magnification
     */
    public void setLastUpdateImageMagnification(String lastUpdateImageMagnification) {
        this.lastUpdateImageMagnification = lastUpdateImageMagnification;
    }

    /**
     * Gets image json xml conv file desc.
     *
     * @return the image json xml conv file desc
     */
    public String getSensorDataJsonXmlConvFileDesc() {
        return sensorDataJsonXmlConvFileDesc;
    }

    /**
     * Sets image json xml conv file desc.
     *
     * @param sensorDataJsonXmlConvFileDesc the image json xml conv file desc
     */
    public void setSensorDataJsonXmlConvFileDesc(String sensorDataJsonXmlConvFileDesc) {
        this.sensorDataJsonXmlConvFileDesc = sensorDataJsonXmlConvFileDesc;
    }

    /**
     * Gets tagging yn.
     *
     * @return the tagging yn
     */
    public String getTaggingYn() {
        return taggingYn;
    }

    /**
     * Sets tagging yn.
     *
     * @param taggingYn the tagging yn
     */
    public void setTaggingYn(String taggingYn) {
        this.taggingYn = taggingYn;
    }
}