package com.lge.crawling.admin.management.statistics.entity;

import java.util.List;

import com.lge.crawling.admin.common.web.entity.AbstractPage;

/**
 * Image File Info Entity
 * @version : 1.0
 * @author  : Copyright (c) 2017 by Mirincom CORP. All Rights Reserved.
 */
public class ImageFileEntity extends AbstractPage {

	// 이미지파일일련번호
	private String imageFileSq                              = null;

	// 이미지파일패키지ID일련번호
	private String imageFilePackageIdSq                     = null;

	// 이미지파일명
	private String imageFileNm                              = null;

	// 이미지파일경로
	private String imageFilePath                            = null;

	// 이미지파일크기
	private String imageFileSize                            = null;

	// 이미지파일크기_X
	private String imageFileScaleX                          = null;

	// 이미지파일크기_Y
	private String imageFileScaleY                          = null;

	// 이미지파일타입구분_TA004
	private String imageFileTypeCd                          = null;

	// 이미지파일다운경로구분_TA005
	private String imageFileDownloadPathCd                  = null;

	// 이미지파일등록상세일시
	private String imageFileRegistDt                        = null;

	// Google Count
	private String googleCnt		                        = null;

	// Flickr Count
	private String flickrCnt		                        = null;

	// Tumblr Count
	private String tumblrCnt		                        = null;

	// Twitter Count
	private String twitterCnt		                        = null;

	// Total Count
	private String totalCnt			                        = null;

	// Google Avg
	private String googleAvg		                        = null;

	// Flickr Avg
	private String flickrAvg		                        = null;

	// Tumblr Avg
	private String tumblrAvg		                        = null;

	// Twitter Avg
	private String twitterAvg		                        = null;

	// JPG Count
	private String jpgCnt		                        	= null;

	// PNG Count
	private String pngCnt		                        	= null;

	// BMP Count
	private String bmpCnt		                        	= null;

	// Flickr Avg
	private String jpgAvg		                        	= null;

	// Tumblr Avg
	private String pngAvg		                        	= null;

	// Twitter Avg
	private String bmpAvg		                        	= null;

	// Rectangle Count
	private String rectangleCnt		                        = null;

	// Polygon Count
	private String polygonCnt		                        = null;

	// Tumblr Avg
	private String rectangleAvg		                        = null;

	// Twitter Avg
	private String polygonAvg		                        = null;

	// First Image Tagging Data Dictionary Id Sequence List
	private List<String> imageTaggingDataDicIdSqList		= null;

	// Tagging Count
	private String taggingCnt									= null;

	// Image Tagging Data Dictionary Name
	private String imageTaggingDataDicNm						= null;

	private List<String> imageTaggingDataDicNmList				= null;

	private List<String> taggingCntList							= null;

	private List<String> taggingCntPerList						= null;

	private String totalPercentage								= null;

	public String getImageFileSq() {
		return imageFileSq;
	}

	public void setImageFileSq(String imageFileSq) {
		this.imageFileSq = imageFileSq;
	}

	public String getImageFilePackageIdSq() {
		return imageFilePackageIdSq;
	}

	public void setImageFilePackageIdSq(String imageFilePackageIdSq) {
		this.imageFilePackageIdSq = imageFilePackageIdSq;
	}

	public String getImageFileNm() {
		return imageFileNm;
	}

	public void setImageFileNm(String imageFileNm) {
		this.imageFileNm = imageFileNm;
	}

	public String getImageFilePath() {
		return imageFilePath;
	}

	public void setImageFilePath(String imageFilePath) {
		this.imageFilePath = imageFilePath;
	}

	public String getImageFileSize() {
		return imageFileSize;
	}

	public void setImageFileSize(String imageFileSize) {
		this.imageFileSize = imageFileSize;
	}

	public String getImageFileScaleX() {
		return imageFileScaleX;
	}

	public void setImageFileScaleX(String imageFileScaleX) {
		this.imageFileScaleX = imageFileScaleX;
	}

	public String getImageFileScaleY() {
		return imageFileScaleY;
	}

	public void setImageFileScaleY(String imageFileScaleY) {
		this.imageFileScaleY = imageFileScaleY;
	}

	public String getImageFileTypeCd() {
		return imageFileTypeCd;
	}

	public void setImageFileTypeCd(String imageFileTypeCd) {
		this.imageFileTypeCd = imageFileTypeCd;
	}

	public String getImageFileDownloadPathCd() {
		return imageFileDownloadPathCd;
	}

	public void setImageFileDownloadPathCd(String imageFileDownloadPathCd) {
		this.imageFileDownloadPathCd = imageFileDownloadPathCd;
	}

	public String getImageFileRegistDt() {
		return imageFileRegistDt;
	}

	public void setImageFileRegistDt(String imageFileRegistDt) {
		this.imageFileRegistDt = imageFileRegistDt;
	}

	public String getGoogleCnt() {
		return googleCnt;
	}

	public void setGoogleCnt(String googleCnt) {
		this.googleCnt = googleCnt;
	}

	public String getFlickrCnt() {
		return flickrCnt;
	}

	public void setFlickrCnt(String flickrCnt) {
		this.flickrCnt = flickrCnt;
	}

	public String getTumblrCnt() {
		return tumblrCnt;
	}

	public void setTumblrCnt(String tumblrCnt) {
		this.tumblrCnt = tumblrCnt;
	}

	public String getTwitterCnt() {
		return twitterCnt;
	}

	public void setTwitterCnt(String twitterCnt) {
		this.twitterCnt = twitterCnt;
	}

	public String getTotalCnt() {
		return totalCnt;
	}

	public void setTotalCnt(String totalCnt) {
		this.totalCnt = totalCnt;
	}

	public String getGoogleAvg() {
		return googleAvg;
	}

	public void setGoogleAvg(String googleAvg) {
		this.googleAvg = googleAvg;
	}

	public String getFlickrAvg() {
		return flickrAvg;
	}

	public void setFlickrAvg(String flickrAvg) {
		this.flickrAvg = flickrAvg;
	}

	public String getTumblrAvg() {
		return tumblrAvg;
	}

	public void setTumblrAvg(String tumblrAvg) {
		this.tumblrAvg = tumblrAvg;
	}

	public String getTwitterAvg() {
		return twitterAvg;
	}

	public void setTwitterAvg(String twitterAvg) {
		this.twitterAvg = twitterAvg;
	}

	public String getJpgCnt() {
		return jpgCnt;
	}

	public void setJpgCnt(String jpgCnt) {
		this.jpgCnt = jpgCnt;
	}

	public String getPngCnt() {
		return pngCnt;
	}

	public void setPngCnt(String pngCnt) {
		this.pngCnt = pngCnt;
	}

	public String getBmpCnt() {
		return bmpCnt;
	}

	public void setBmpCnt(String bmpCnt) {
		this.bmpCnt = bmpCnt;
	}

	public String getRectangleCnt() {
		return rectangleCnt;
	}

	public void setRectangleCnt(String rectangleCnt) {
		this.rectangleCnt = rectangleCnt;
	}

	public String getPolygonCnt() {
		return polygonCnt;
	}

	public void setPolygonCnt(String polygonCnt) {
		this.polygonCnt = polygonCnt;
	}

	public String getTaggingCnt() {
		return taggingCnt;
	}

	public void setTaggingCnt(String taggingCnt) {
		this.taggingCnt = taggingCnt;
	}

	public String getImageTaggingDataDicNm() {
		return imageTaggingDataDicNm;
	}

	public void setImageTaggingDataDicNm(String imageTaggingDataDicNm) {
		this.imageTaggingDataDicNm = imageTaggingDataDicNm;
	}

	public String getJpgAvg() {
		return jpgAvg;
	}

	public void setJpgAvg(String jpgAvg) {
		this.jpgAvg = jpgAvg;
	}

	public String getPngAvg() {
		return pngAvg;
	}

	public void setPngAvg(String pngAvg) {
		this.pngAvg = pngAvg;
	}

	public String getBmpAvg() {
		return bmpAvg;
	}

	public void setBmpAvg(String bmpAvg) {
		this.bmpAvg = bmpAvg;
	}

	public String getRectangleAvg() {
		return rectangleAvg;
	}

	public void setRectangleAvg(String rectangleAvg) {
		this.rectangleAvg = rectangleAvg;
	}

	public String getPolygonAvg() {
		return polygonAvg;
	}

	public void setPolygonAvg(String polygonAvg) {
		this.polygonAvg = polygonAvg;
	}

	public List<String> getImageTaggingDataDicNmList() {
		return imageTaggingDataDicNmList;
	}

	public void setImageTaggingDataDicNmList(List<String> imageTaggingDataDicNmList) {
		this.imageTaggingDataDicNmList = imageTaggingDataDicNmList;
	}

	public List<String> getTaggingCntList() {
		return taggingCntList;
	}

	public void setTaggingCntList(List<String> taggingCntList) {
		this.taggingCntList = taggingCntList;
	}

	public List<String> getTaggingCntPerList() {
		return taggingCntPerList;
	}

	public void setTaggingCntPerList(List<String> taggingCntPerList) {
		this.taggingCntPerList = taggingCntPerList;
	}

	public String getTotalPercentage() {
		return totalPercentage;
	}

	public void setTotalPercentage(String totalPercentage) {
		this.totalPercentage = totalPercentage;
	}

	public List<String> getImageTaggingDataDicIdSqList() {
		return imageTaggingDataDicIdSqList;
	}

	public void setImageTaggingDataDicIdSqList(List<String> imageTaggingDataDicIdSqList) {
		this.imageTaggingDataDicIdSqList = imageTaggingDataDicIdSqList;
	}

}
