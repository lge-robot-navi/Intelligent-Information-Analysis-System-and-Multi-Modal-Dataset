package com.lge.crawling.admin.management.image.entity;

import com.google.gson.Gson;
import com.lge.crawling.admin.common.web.entity.AbstractPage;

/**
 * The type Image tagging entity.
 */
public class ImageTaggingEntity extends AbstractPage {

    private String imageFileSq;

    private String imageFileNm;

    private String imageFilePath;

    private String imageFileSize;

    private String imageFileScaleX;

    private String imageFileScaleY;

    private String imageFileTypeCd;

    private String imageFileTypeCdNm;

    private String imageFileDownloadPathCd;

    private String imageFileDownloadPathCdNm;

    private String lastUpdateImageMagnification;

    private String imageJsonFileSq;

    private String imageJsonFileDesc;

    private String imageJsonFileNm;

    private String imageJsonFilePath;

    private String imageJsonFileSize;

    private String imageJsonXmlConvFileDesc;

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
    public String getImageFileSq() {
        return imageFileSq;
    }

    /**
     * Sets image file sq.
     *
     * @param imageFileSq the image file sq
     */
    public void setImageFileSq(String imageFileSq) {
        this.imageFileSq = imageFileSq;
    }

    /**
     * Gets image file nm.
     *
     * @return the image file nm
     */
    public String getImageFileNm() {
        return imageFileNm;
    }

    /**
     * Sets image file nm.
     *
     * @param imageFileNm the image file nm
     */
    public void setImageFileNm(String imageFileNm) {
        this.imageFileNm = imageFileNm;
    }

    /**
     * Gets image file path.
     *
     * @return the image file path
     */
    public String getImageFilePath() {
        return imageFilePath;
    }

    /**
     * Sets image file path.
     *
     * @param imageFilePath the image file path
     */
    public void setImageFilePath(String imageFilePath) {
        this.imageFilePath = imageFilePath;
    }

    /**
     * Gets image file size.
     *
     * @return the image file size
     */
    public String getImageFileSize() {
        return imageFileSize;
    }

    /**
     * Sets image file size.
     *
     * @param imageFileSize the image file size
     */
    public void setImageFileSize(String imageFileSize) {
        this.imageFileSize = imageFileSize;
    }

    /**
     * Gets image file scale x.
     *
     * @return the image file scale x
     */
    public String getImageFileScaleX() {
        return imageFileScaleX;
    }

    /**
     * Sets image file scale x.
     *
     * @param imageFileScaleX the image file scale x
     */
    public void setImageFileScaleX(String imageFileScaleX) {
        this.imageFileScaleX = imageFileScaleX;
    }

    /**
     * Gets image file scale y.
     *
     * @return the image file scale y
     */
    public String getImageFileScaleY() {
        return imageFileScaleY;
    }

    /**
     * Sets image file scale y.
     *
     * @param imageFileScaleY the image file scale y
     */
    public void setImageFileScaleY(String imageFileScaleY) {
        this.imageFileScaleY = imageFileScaleY;
    }

    /**
     * Gets image file type cd.
     *
     * @return the image file type cd
     */
    public String getImageFileTypeCd() {
        return imageFileTypeCd;
    }

    /**
     * Sets image file type cd.
     *
     * @param imageFileTypeCd the image file type cd
     */
    public void setImageFileTypeCd(String imageFileTypeCd) {
        this.imageFileTypeCd = imageFileTypeCd;
    }

    /**
     * Gets image file type cd nm.
     *
     * @return the image file type cd nm
     */
    public String getImageFileTypeCdNm() {
        return imageFileTypeCdNm;
    }

    /**
     * Sets image file type cd nm.
     *
     * @param imageFileTypeCdNm the image file type cd nm
     */
    public void setImageFileTypeCdNm(String imageFileTypeCdNm) {
        this.imageFileTypeCdNm = imageFileTypeCdNm;
    }

    /**
     * Gets image file download path cd.
     *
     * @return the image file download path cd
     */
    public String getImageFileDownloadPathCd() {
        return imageFileDownloadPathCd;
    }

    /**
     * Sets image file download path cd.
     *
     * @param imageFileDownloadPathCd the image file download path cd
     */
    public void setImageFileDownloadPathCd(String imageFileDownloadPathCd) {
        this.imageFileDownloadPathCd = imageFileDownloadPathCd;
    }

    /**
     * Gets image file download path cd nm.
     *
     * @return the image file download path cd nm
     */
    public String getImageFileDownloadPathCdNm() {
        return imageFileDownloadPathCdNm;
    }

    /**
     * Sets image file download path cd nm.
     *
     * @param imageFileDownloadPathCdNm the image file download path cd nm
     */
    public void setImageFileDownloadPathCdNm(String imageFileDownloadPathCdNm) {
        this.imageFileDownloadPathCdNm = imageFileDownloadPathCdNm;
    }

    /**
     * Gets image json file sq.
     *
     * @return the image json file sq
     */
    public String getImageJsonFileSq() {
        return imageJsonFileSq;
    }

    /**
     * Sets image json file sq.
     *
     * @param imageJsonFileSq the image json file sq
     */
    public void setImageJsonFileSq(String imageJsonFileSq) {
        this.imageJsonFileSq = imageJsonFileSq;
    }

    /**
     * Gets image json file desc.
     *
     * @return the image json file desc
     */
    public String getImageJsonFileDesc() {
        return imageJsonFileDesc;
    }

    /**
     * Sets image json file desc.
     *
     * @param imageJsonFileDesc the image json file desc
     */
    public void setImageJsonFileDesc(String imageJsonFileDesc) {
        this.imageJsonFileDesc = imageJsonFileDesc;
    }

    /**
     * Gets image json file nm.
     *
     * @return the image json file nm
     */
    public String getImageJsonFileNm() {
        return imageJsonFileNm;
    }

    /**
     * Sets image json file nm.
     *
     * @param imageJsonFileNm the image json file nm
     */
    public void setImageJsonFileNm(String imageJsonFileNm) {
        this.imageJsonFileNm = imageJsonFileNm;
    }

    /**
     * Gets image json file path.
     *
     * @return the image json file path
     */
    public String getImageJsonFilePath() {
        return imageJsonFilePath;
    }

    /**
     * Sets image json file path.
     *
     * @param imageJsonFilePath the image json file path
     */
    public void setImageJsonFilePath(String imageJsonFilePath) {
        this.imageJsonFilePath = imageJsonFilePath;
    }

    /**
     * Gets image json file size.
     *
     * @return the image json file size
     */
    public String getImageJsonFileSize() {
        return imageJsonFileSize;
    }

    /**
     * Sets image json file size.
     *
     * @param imageJsonFileSize the image json file size
     */
    public void setImageJsonFileSize(String imageJsonFileSize) {
        this.imageJsonFileSize = imageJsonFileSize;
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
    public String getImageJsonXmlConvFileDesc() {
        return imageJsonXmlConvFileDesc;
    }

    /**
     * Sets image json xml conv file desc.
     *
     * @param imageJsonXmlConvFileDesc the image json xml conv file desc
     */
    public void setImageJsonXmlConvFileDesc(String imageJsonXmlConvFileDesc) {
        this.imageJsonXmlConvFileDesc = imageJsonXmlConvFileDesc;
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