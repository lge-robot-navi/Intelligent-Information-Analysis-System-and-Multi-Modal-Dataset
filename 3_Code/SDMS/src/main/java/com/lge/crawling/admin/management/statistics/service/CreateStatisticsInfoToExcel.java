package com.lge.crawling.admin.management.statistics.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lge.crawling.admin.common.util.CommonCode;
import com.lge.crawling.admin.common.util.WriteExcel;
import com.lge.crawling.admin.management.statistics.entity.ImageFileEntity;

@Component
public class CreateStatisticsInfoToExcel {

	private final static Logger logger = LoggerFactory.getLogger(CreateStatisticsInfoToExcel.class);

    /** Excel */
	@Autowired private WriteExcel excel;

    /** Service Code */
    private CommonCode commonCode = new CommonCode();

    DateTimeFormatter df1 = DateTimeFormat.forPattern("yyyyMMdd");

	//「############################################################################################################
	// 다운로드 경로별 이미지 수집 통계
	//#############################################################################################################

    // 다운로드 경로별 이미지 수집 통계 엑셀 작성
    public void writeGraphByDownloadPathReport(ServletOutputStream out, List<ImageFileEntity> barList, List<ImageFileEntity> pieList) throws IOException {

    	HSSFWorkbook workbook = excel.createWorkBook();
    	/*
    	 * Sheet 생성 - Bar Graph
    	 */
    	HSSFSheet barSheet = excel.createSheet(workbook, "BarGraphByDownloadPathInfoReport");
    	logger.debug("sheet Header Created.");

    	// Sheet 데이터 생성
    	createBarGraphByDownloadPathInfo(workbook, barList, barSheet);

    	/*
    	 * Sheet 생성 - Pie Graph
    	 */
    	HSSFSheet pieSheet = excel.createSheet(workbook, "PieGraphByDownloadPathInfoReport");
    	logger.debug("sheet Header Created.");

    	// Sheet 데이터 생성
    	createPieGraphByDownloadPathInfo(workbook, pieList, pieSheet);

    	logger.debug("sheet Data Created.");
    	workbook.write(out);

    }

    private void createBarGraphByDownloadPathInfo(HSSFWorkbook workbook,  List<ImageFileEntity> list,
    	    HSSFSheet sheet) {

    	//헤더 생성
    	createBarGraphByDownloadPathHeader(sheet, workbook);

    	// 데이터 생성
    	createBarGraphByDownloadPathData(sheet, workbook, list);
    	logger.debug("Create Excel GraphByDownloadPath Info Header & Data.");

    	// Set Auto Size column
    	for (int i = 0; i < 9; i++) {
    		sheet.autoSizeColumn(i);
    		int maxWidth = (sheet.getColumnWidth(i))+1024;
    		if (maxWidth > 255*256) {
				maxWidth = 255*256;
			}
    		sheet.setColumnWidth(i, maxWidth);
		}

    }

    private void createBarGraphByDownloadPathHeader(HSSFSheet sheet, HSSFWorkbook workbook) {

		HSSFRow row = sheet.createRow(0);
		int columnIndex = 0;

		excel.addHeader(row, columnIndex++, "등록상세일시", workbook);
		sheet.autoSizeColumn(columnIndex);
		excel.addHeader(row, columnIndex++, "Google개수", workbook);
		sheet.autoSizeColumn(columnIndex);
		excel.addHeader(row, columnIndex++, "Flickr개수", workbook);
		sheet.autoSizeColumn(columnIndex);
		excel.addHeader(row, columnIndex++, "Tumblr개수", workbook);
		sheet.autoSizeColumn(columnIndex);
		excel.addHeader(row, columnIndex++, "Twitter개수", workbook);
		sheet.autoSizeColumn(columnIndex);
		excel.addHeader(row, columnIndex++, "전체개수", workbook);
		sheet.autoSizeColumn(columnIndex);
    }

    private void createBarGraphByDownloadPathData(HSSFSheet sheet, HSSFWorkbook workbook, List<ImageFileEntity> list) {

    	int rows = 0;

    	CellStyle style = workbook.createCellStyle();

		for (ImageFileEntity imageFileEntity : list) {
		    HSSFRow row = sheet.createRow(++rows);
		    int columnIndex = 0;

		    excel.addDataText(row, columnIndex++, DateTime.parse(imageFileEntity.getImageFileRegistDt(), df1).toString("yyyy-MM-dd"), workbook, style);
		    excel.addDataText(row, columnIndex++, imageFileEntity.getGoogleCnt(), workbook, style);
		    excel.addDataText(row, columnIndex++, imageFileEntity.getFlickrCnt(), workbook, style);
		    excel.addDataText(row, columnIndex++, imageFileEntity.getTumblrCnt(), workbook, style);
		    excel.addDataText(row, columnIndex++, imageFileEntity.getTwitterCnt(), workbook, style);
		    excel.addDataText(row, columnIndex++, imageFileEntity.getTotalCnt(), workbook, style);
		}

    }

    private void createPieGraphByDownloadPathInfo(HSSFWorkbook workbook,  List<ImageFileEntity> list,
    		HSSFSheet sheet) {

    	//헤더 생성
    	createPieGraphByDownloadPathHeader(sheet, workbook);

    	// 데이터 생성
    	createPieGraphByDownloadPathData(sheet, workbook, list);
    	logger.debug("Create Excel GraphByDownloadPath Info Header & Data.");

    	// Set Auto Size column
    	for (int i = 0; i < 9; i++) {
    		sheet.autoSizeColumn(i);
    		int maxWidth = (sheet.getColumnWidth(i))+1024;
    		if (maxWidth > 255*256) {
    			maxWidth = 255*256;
    		}
    		sheet.setColumnWidth(i, maxWidth);
    	}

    }

    private void createPieGraphByDownloadPathHeader(HSSFSheet sheet, HSSFWorkbook workbook) {

    	HSSFRow row = sheet.createRow(0);
    	int columnIndex = 0;

    	excel.addHeader(row, columnIndex++, "사이트명", workbook);
    	sheet.autoSizeColumn(columnIndex);
    	excel.addHeader(row, columnIndex++, "건수", workbook);
    	sheet.autoSizeColumn(columnIndex);
    	excel.addHeader(row, columnIndex++, "%", workbook);
    	sheet.autoSizeColumn(columnIndex);
    }

    private void createPieGraphByDownloadPathData(HSSFSheet sheet, HSSFWorkbook workbook, List<ImageFileEntity> list) {

    	int rows = 0;

    	CellStyle style = workbook.createCellStyle();

    	for (ImageFileEntity imageFileEntity : list) {
    		HSSFRow row = sheet.createRow(++rows);
    		int columnIndex = 0;
    		excel.addDataText(row, columnIndex++, "Google", workbook, style);
    		excel.addDataText(row, columnIndex++, imageFileEntity.getGoogleCnt(), workbook, style);
    		excel.addDataText(row, columnIndex++, imageFileEntity.getGoogleAvg()+"%", workbook, style);

    		row = sheet.createRow(++rows);
    		columnIndex = 0;
    		excel.addDataText(row, columnIndex++, "Flickr", workbook, style);
    		excel.addDataText(row, columnIndex++, imageFileEntity.getFlickrCnt(), workbook, style);
    		excel.addDataText(row, columnIndex++, imageFileEntity.getFlickrAvg()+"%", workbook, style);

    		row = sheet.createRow(++rows);
    		columnIndex = 0;
    		excel.addDataText(row, columnIndex++, "Tumblr", workbook, style);
    		excel.addDataText(row, columnIndex++, imageFileEntity.getTumblrCnt(), workbook, style);
    		excel.addDataText(row, columnIndex++, imageFileEntity.getTumblrAvg()+"%", workbook, style);

    		row = sheet.createRow(++rows);
    		columnIndex = 0;
    		excel.addDataText(row, columnIndex++, "Twitter", workbook, style);
    		excel.addDataText(row, columnIndex++, imageFileEntity.getTwitterCnt(), workbook, style);
    		excel.addDataText(row, columnIndex++, imageFileEntity.getTwitterAvg()+"%", workbook, style);

    		row = sheet.createRow(++rows);
    		columnIndex = 0;
    		excel.addDataText(row, columnIndex++, "합계", workbook, style);
    		excel.addDataText(row, columnIndex++, imageFileEntity.getTotalCnt(), workbook, style);
    		excel.addDataText(row, columnIndex++, "100%", workbook, style);
    	}

    }
    //」############################################################################################################

    //「############################################################################################################
    // 이미지 파일 타입별 수집 통계
    //#############################################################################################################

    // 이미지 파일 타입별 수집 통계 엑셀 작성
    public void writeGraphByImageFileTypeReport(ServletOutputStream out, List<ImageFileEntity> barList, List<ImageFileEntity> pieList) throws IOException {

    	HSSFWorkbook workbook = excel.createWorkBook();
    	/*
    	 * Sheet 생성
    	 */
    	HSSFSheet barSheet = excel.createSheet(workbook, "BarGraphByImageFileTypeInfoReport");
    	logger.debug("sheet Header Created.");

    	// Sheet 데이터 생성
    	createBarGraphByImageFileTypeInfo(workbook, barList, barSheet);
    	/*
    	 * Sheet 생성
    	 */
    	HSSFSheet pieSheet = excel.createSheet(workbook, "PieGraphByImageFileTypeInfoReport");
    	logger.debug("sheet Header Created.");

    	// Sheet 데이터 생성
    	createPieGraphByImageFileTypeInfo(workbook, pieList, pieSheet);

    	logger.debug("sheet Data Created.");
    	workbook.write(out);

    }

    private void createBarGraphByImageFileTypeInfo(HSSFWorkbook workbook, List<ImageFileEntity> list,
    		HSSFSheet sheet) {

    	//헤더 생성
    	createBarGraphByImageFileTypeHeader(sheet, workbook);

    	// 데이터 생성
    	createBarGraphByImageFileTypeData(sheet, workbook, list);
    	logger.debug("Create Excel GraphByImageFileType Info Header & Data.");

    	// Set Auto Size column
    	for (int i = 0; i < 9; i++) {
    		sheet.autoSizeColumn(i);
    		int maxWidth = (sheet.getColumnWidth(i))+1024;
    		if (maxWidth > 255*256) {
    			maxWidth = 255*256;
    		}
    		sheet.setColumnWidth(i, maxWidth);
    	}

    }

    private void createBarGraphByImageFileTypeHeader(HSSFSheet sheet, HSSFWorkbook workbook) {

    	HSSFRow row = sheet.createRow(0);
    	int columnIndex = 0;

		excel.addHeader(row, columnIndex++, "등록상세일시", workbook);
		sheet.autoSizeColumn(columnIndex);
    	excel.addHeader(row, columnIndex++, "JPG개수", workbook);
    	sheet.autoSizeColumn(columnIndex);
    	excel.addHeader(row, columnIndex++, "PNG개수", workbook);
    	sheet.autoSizeColumn(columnIndex);
    	excel.addHeader(row, columnIndex++, "BMP개수", workbook);
    	sheet.autoSizeColumn(columnIndex);
    	excel.addHeader(row, columnIndex++, "전체개수", workbook);
    	sheet.autoSizeColumn(columnIndex);
    }

    private void createBarGraphByImageFileTypeData(HSSFSheet sheet, HSSFWorkbook workbook, List<ImageFileEntity> list) {

    	int rows = 0;

    	CellStyle style = workbook.createCellStyle();

    	for (ImageFileEntity imageFileEntity : list) {
    		HSSFRow row = sheet.createRow(++rows);
    		int columnIndex = 0;

    		excel.addDataText(row, columnIndex++, DateTime.parse(imageFileEntity.getImageFileRegistDt(), df1).toString("yyyy-MM-dd"), workbook, style);
    		excel.addDataText(row, columnIndex++, imageFileEntity.getJpgCnt(), workbook, style);
    		excel.addDataText(row, columnIndex++, imageFileEntity.getPngCnt(), workbook, style);
    		excel.addDataText(row, columnIndex++, imageFileEntity.getBmpCnt(), workbook, style);
    		excel.addDataText(row, columnIndex++, imageFileEntity.getTotalCnt(), workbook, style);
    	}

    }

    private void createPieGraphByImageFileTypeInfo(HSSFWorkbook workbook, List<ImageFileEntity> list,
    		HSSFSheet sheet) {

    	//헤더 생성
    	createPieGraphByImageFileTypeHeader(sheet, workbook);

    	// 데이터 생성
    	createPieGraphByImageFileTypeData(sheet, workbook, list);
    	logger.debug("Create Excel GraphByImageFileType Info Header & Data.");

    	// Set Auto Size column
    	for (int i = 0; i < 9; i++) {
    		sheet.autoSizeColumn(i);
    		int maxWidth = (sheet.getColumnWidth(i))+1024;
    		if (maxWidth > 255*256) {
    			maxWidth = 255*256;
    		}
    		sheet.setColumnWidth(i, maxWidth);
    	}

    }

    private void createPieGraphByImageFileTypeHeader(HSSFSheet sheet, HSSFWorkbook workbook) {

    	HSSFRow row = sheet.createRow(0);
    	int columnIndex = 0;

    	excel.addHeader(row, columnIndex++, "사이트명", workbook);
    	sheet.autoSizeColumn(columnIndex);
    	excel.addHeader(row, columnIndex++, "건수", workbook);
    	sheet.autoSizeColumn(columnIndex);
    	excel.addHeader(row, columnIndex++, "%", workbook);
    	sheet.autoSizeColumn(columnIndex);
    }

    private void createPieGraphByImageFileTypeData(HSSFSheet sheet, HSSFWorkbook workbook, List<ImageFileEntity> list) {

    	int rows = 0;

    	CellStyle style = workbook.createCellStyle();

    	for (ImageFileEntity imageFileEntity : list) {
    		HSSFRow row = sheet.createRow(++rows);
    		int columnIndex = 0;
    		excel.addDataText(row, columnIndex++, "JPG", workbook, style);
    		excel.addDataText(row, columnIndex++, imageFileEntity.getJpgCnt(), workbook, style);
    		excel.addDataText(row, columnIndex++, imageFileEntity.getJpgAvg()+"%", workbook, style);

    		row = sheet.createRow(++rows);
    		columnIndex = 0;
    		excel.addDataText(row, columnIndex++, "PNG", workbook, style);
    		excel.addDataText(row, columnIndex++, imageFileEntity.getPngCnt(), workbook, style);
    		excel.addDataText(row, columnIndex++, imageFileEntity.getPngAvg()+"%", workbook, style);

    		row = sheet.createRow(++rows);
    		columnIndex = 0;
    		excel.addDataText(row, columnIndex++, "BMP", workbook, style);
    		excel.addDataText(row, columnIndex++, imageFileEntity.getBmpCnt(), workbook, style);
    		excel.addDataText(row, columnIndex++, imageFileEntity.getBmpAvg()+"%", workbook, style);

    		row = sheet.createRow(++rows);
    		columnIndex = 0;
    		excel.addDataText(row, columnIndex++, "합계", workbook, style);
    		excel.addDataText(row, columnIndex++, imageFileEntity.getTotalCnt(), workbook, style);
    		excel.addDataText(row, columnIndex++, "100%", workbook, style);
    	}

    }
    //」############################################################################################################

    //「############################################################################################################
    // 이미지 태깅 유형별 수집 통계
    //#############################################################################################################

    // 이미지 태깅 유형별 수집 통계 엑셀 작성
    public void writeGraphByImageTaggingTypeReport(ServletOutputStream out, List<ImageFileEntity> barList, List<ImageFileEntity> pieList) throws IOException {

    	HSSFWorkbook workbook = excel.createWorkBook();
    	/*
    	 * Sheet 생성
    	 */
    	HSSFSheet barSheet = excel.createSheet(workbook, "BarGraphByImageTaggingTypeInfoReport");
    	logger.debug("sheet Header Created.");

    	// Sheet 데이터 생성
    	createBarGraphByImageTaggingTypeInfo(workbook, barList, barSheet);
    	/*
    	 * Sheet 생성
    	 */
    	HSSFSheet pieSheet = excel.createSheet(workbook, "PieGraphByImageTaggingTypeInfoReport");
    	logger.debug("sheet Header Created.");

    	// Sheet 데이터 생성
    	createPieGraphByImageTaggingTypeInfo(workbook, pieList, pieSheet);

    	logger.debug("sheet Data Created.");
    	workbook.write(out);

    }

    private void createBarGraphByImageTaggingTypeInfo(HSSFWorkbook workbook, List<ImageFileEntity> list,
    		HSSFSheet sheet) {

    	//헤더 생성
    	createBarGraphByImageTaggingTypeHeader(sheet, workbook);

    	// 데이터 생성
    	createBarGraphByImageTaggingTypeData(sheet, workbook, list);
    	logger.debug("Create Excel GraphByImageTaggingType Info Header & Data.");

    	// Set Auto Size column
    	for (int i = 0; i < 9; i++) {
    		sheet.autoSizeColumn(i);
    		int maxWidth = (sheet.getColumnWidth(i))+1024;
    		if (maxWidth > 255*256) {
    			maxWidth = 255*256;
    		}
    		sheet.setColumnWidth(i, maxWidth);
    	}

    }

    private void createBarGraphByImageTaggingTypeHeader(HSSFSheet sheet, HSSFWorkbook workbook) {

    	HSSFRow row = sheet.createRow(0);
    	int columnIndex = 0;

		excel.addHeader(row, columnIndex++, "등록상세일시", workbook);
		sheet.autoSizeColumn(columnIndex);
    	excel.addHeader(row, columnIndex++, "Rectangle개수", workbook);
    	sheet.autoSizeColumn(columnIndex);
    	excel.addHeader(row, columnIndex++, "Polygon개수", workbook);
    	sheet.autoSizeColumn(columnIndex);
    	excel.addHeader(row, columnIndex++, "전체개수", workbook);
    	sheet.autoSizeColumn(columnIndex);
    }

    private void createBarGraphByImageTaggingTypeData(HSSFSheet sheet, HSSFWorkbook workbook, List<ImageFileEntity> list) {

    	int rows = 0;

    	CellStyle style = workbook.createCellStyle();

    	for (ImageFileEntity imageFileEntity : list) {
    		HSSFRow row = sheet.createRow(++rows);
    		int columnIndex = 0;

    		excel.addDataText(row, columnIndex++, DateTime.parse(imageFileEntity.getImageFileRegistDt(), df1).toString("yyyy-MM-dd"), workbook, style);
    		excel.addDataText(row, columnIndex++, imageFileEntity.getRectangleCnt(), workbook, style);
    		excel.addDataText(row, columnIndex++, imageFileEntity.getPolygonCnt(), workbook, style);
    		excel.addDataText(row, columnIndex++, imageFileEntity.getTotalCnt(), workbook, style);
    	}

    }

    private void createPieGraphByImageTaggingTypeInfo(HSSFWorkbook workbook, List<ImageFileEntity> list,
    		HSSFSheet sheet) {

    	//헤더 생성
    	createPieGraphByImageTaggingTypeHeader(sheet, workbook);

    	// 데이터 생성
    	createPieGraphByImageTaggingTypeData(sheet, workbook, list);
    	logger.debug("Create Excel GraphByImageTaggingType Info Header & Data.");

    	// Set Auto Size column
    	for (int i = 0; i < 9; i++) {
    		sheet.autoSizeColumn(i);
    		int maxWidth = (sheet.getColumnWidth(i))+1024;
    		if (maxWidth > 255*256) {
    			maxWidth = 255*256;
    		}
    		sheet.setColumnWidth(i, maxWidth);
    	}

    }

    private void createPieGraphByImageTaggingTypeHeader(HSSFSheet sheet, HSSFWorkbook workbook) {

    	HSSFRow row = sheet.createRow(0);
    	int columnIndex = 0;

    	excel.addHeader(row, columnIndex++, "사이트명", workbook);
    	sheet.autoSizeColumn(columnIndex);
    	excel.addHeader(row, columnIndex++, "건수", workbook);
    	sheet.autoSizeColumn(columnIndex);
    	excel.addHeader(row, columnIndex++, "%", workbook);
    	sheet.autoSizeColumn(columnIndex);
    }

    private void createPieGraphByImageTaggingTypeData(HSSFSheet sheet, HSSFWorkbook workbook, List<ImageFileEntity> list) {

    	int rows = 0;

    	CellStyle style = workbook.createCellStyle();

    	for (ImageFileEntity imageFileEntity : list) {
    		HSSFRow row = sheet.createRow(++rows);
    		int columnIndex = 0;
    		excel.addDataText(row, columnIndex++, "Rectangle", workbook, style);
    		excel.addDataText(row, columnIndex++, imageFileEntity.getRectangleCnt(), workbook, style);
    		excel.addDataText(row, columnIndex++, imageFileEntity.getRectangleAvg()+"%", workbook, style);

    		row = sheet.createRow(++rows);
    		columnIndex = 0;
    		excel.addDataText(row, columnIndex++, "Polygon", workbook, style);
    		excel.addDataText(row, columnIndex++, imageFileEntity.getPolygonCnt(), workbook, style);
    		excel.addDataText(row, columnIndex++, imageFileEntity.getPolygonAvg()+"%", workbook, style);

    		row = sheet.createRow(++rows);
    		columnIndex = 0;
    		excel.addDataText(row, columnIndex++, "합계", workbook, style);
    		excel.addDataText(row, columnIndex++, imageFileEntity.getTotalCnt(), workbook, style);
    		excel.addDataText(row, columnIndex++, "100%", workbook, style);
    	}

    }
    //」############################################################################################################

    //「############################################################################################################
    // 이미지 라벨별 수집 통계
    //#############################################################################################################

    // 이미지 라벨별 수집 통계 엑셀 작성
    public void writeGraphByLabelReport(ServletOutputStream out, List<ImageFileEntity> barList, List<ImageFileEntity> pieList) throws IOException {

    	HSSFWorkbook workbook = excel.createWorkBook();
    	/*
    	 * Sheet 생성
    	 */
    	HSSFSheet barSheet = excel.createSheet(workbook, "BarGraphByLabelInfoReport");
    	logger.debug("sheet Header Created.");

    	// Sheet 데이터 생성
    	createBarGraphByLabelInfo(workbook, barList, barSheet);
    	/*
    	 * Sheet 생성
    	 */
    	HSSFSheet pieSheet = excel.createSheet(workbook, "PieGraphByLabelInfoReport");
    	logger.debug("sheet Header Created.");

    	// Sheet 데이터 생성
    	createPieGraphByLabelInfo(workbook, pieList, pieSheet);

    	logger.debug("sheet Data Created.");
    	workbook.write(out);

    }

    private void createBarGraphByLabelInfo(HSSFWorkbook workbook, List<ImageFileEntity> list,
    		HSSFSheet sheet) {

    	//헤더 생성
    	createBarGraphByLabelHeader(sheet, workbook, list);

    	// 데이터 생성
    	createBarGraphByLabelData(sheet, workbook, list);
    	logger.debug("Create Excel GraphByLabel Info Header & Data.");

    	// Set Auto Size column
    	for (int i = 0; i < 9; i++) {
    		sheet.autoSizeColumn(i);
    		int maxWidth = (sheet.getColumnWidth(i))+1024;
    		if (maxWidth > 255*256) {
    			maxWidth = 255*256;
    		}
    		sheet.setColumnWidth(i, maxWidth);
    	}

    }

    private void createBarGraphByLabelHeader(HSSFSheet sheet, HSSFWorkbook workbook, List<ImageFileEntity> list) {

    	HSSFRow row = sheet.createRow(0);
    	int columnIndex = 0;

    	if (list != null) {
    		excel.addHeader(row, columnIndex++, "등록상세일시", workbook);
    		sheet.autoSizeColumn(columnIndex);
			List<String> names = list.get(0).getImageTaggingDataDicNmList();
			for (String name : names) {
				excel.addHeader(row, columnIndex++, name, workbook);
				sheet.autoSizeColumn(columnIndex);
			}
		}
    }

    private void createBarGraphByLabelData(HSSFSheet sheet, HSSFWorkbook workbook, List<ImageFileEntity> list) {

    	int rows = 0;

    	CellStyle style = workbook.createCellStyle();

    	for (ImageFileEntity imageFileEntity : list) {
    		HSSFRow row = sheet.createRow(++rows);
    		int columnIndex = 0;
    		excel.addDataText(row, columnIndex++, DateTime.parse(imageFileEntity.getImageFileRegistDt(), df1).toString("yyyy-MM-dd"), workbook, style);
    		List<String> cnts = imageFileEntity.getTaggingCntList();
    		for (String cnt : cnts) {
    			excel.addDataText(row, columnIndex++, cnt, workbook, style);
			}
    	}

    }

    private void createPieGraphByLabelInfo(HSSFWorkbook workbook, List<ImageFileEntity> list,
    		HSSFSheet sheet) {

    	//헤더 생성
    	createPieGraphByLabelHeader(sheet, workbook, list);

    	// 데이터 생성
    	createPieGraphByLabelData(sheet, workbook, list);
    	logger.debug("Create Excel GraphByLabel Info Header & Data.");

    	// Set Auto Size column
    	for (int i = 0; i < 9; i++) {
    		sheet.autoSizeColumn(i);
    		int maxWidth = (sheet.getColumnWidth(i))+1024;
    		if (maxWidth > 255*256) {
    			maxWidth = 255*256;
    		}
    		sheet.setColumnWidth(i, maxWidth);
    	}

    }

    private void createPieGraphByLabelHeader(HSSFSheet sheet, HSSFWorkbook workbook, List<ImageFileEntity> list) {

    	HSSFRow row = sheet.createRow(0);
    	int columnIndex = 0;

		excel.addHeader(row, columnIndex++, "사이트명", workbook);
		sheet.autoSizeColumn(columnIndex);
		excel.addHeader(row, columnIndex++, "건수", workbook);
		sheet.autoSizeColumn(columnIndex);
		excel.addHeader(row, columnIndex++, "%", workbook);
		sheet.autoSizeColumn(columnIndex);
    }

    private void createPieGraphByLabelData(HSSFSheet sheet, HSSFWorkbook workbook, List<ImageFileEntity> list) {

    	int rows = 0;

    	CellStyle style = workbook.createCellStyle();

    	if (list != null) {
			List<String> taggingNames = list.get(0).getImageTaggingDataDicNmList();
			List<String> cnts = list.get(0).getTaggingCntList();
			List<String> perList = list.get(0).getTaggingCntPerList();
			for (int i = 0; i < taggingNames.size(); i++) {
				HSSFRow row = sheet.createRow(++rows);
				int columnIndex = 0;
				excel.addDataText(row, columnIndex++, taggingNames.get(0), workbook, style);
				excel.addDataText(row, columnIndex++, cnts.get(i), workbook, style);
				excel.addDataText(row, columnIndex++, perList.get(i)+"%", workbook, style);
			}

			HSSFRow row = sheet.createRow(++rows);
			int columnIndex = 0;
			excel.addDataText(row, columnIndex++, "합계", workbook, style);
			excel.addDataText(row, columnIndex++, list.get(0).getTotalCnt(), workbook, style);
			excel.addDataText(row, columnIndex++, list.get(0).getTotalPercentage()+"%", workbook, style);
    	}

    }
    //」############################################################################################################
}

