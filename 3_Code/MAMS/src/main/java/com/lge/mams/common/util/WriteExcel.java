package com.lge.mams.common.util;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class WriteExcel {

	private final static Logger logger = LoggerFactory.getLogger(WriteExcel.class);

    @SuppressWarnings("unused")
	private void addDataNumber ( HSSFRow row, int columnIndex, Double value, HSSFWorkbook workbook ) {

    	HSSFCell cell1 = row.createCell(columnIndex);

		CellStyle style = workbook.createCellStyle();
		addBorder(style);
		style.setDataFormat(workbook.createDataFormat().getFormat("##.###0"));

		if (value != null) {
		    cell1.setCellValue(value);
		}

		cell1.setCellStyle(style);

    }

    public void addDataText(HSSFRow row, int columnIndex, String value, HSSFWorkbook workbook, CellStyle style) {

		addBorder(style);

		HSSFCell cell1 = row.createCell(columnIndex);
		cell1.setCellValue(value);
		cell1.setCellStyle(style);

    }

    @SuppressWarnings("deprecation")
	public void addHeader(HSSFRow row, int columnIndex, String value, HSSFWorkbook workbook) {

		CellStyle style = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style.setFont(font);

		style.setFillBackgroundColor(HSSFColor.WHITE.index);
		style.setFillForegroundColor(HSSFColor.YELLOW.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		addBorder(style);

		Cell cell1 = row.createCell(columnIndex);
		cell1.setCellValue(value);
		cell1.setCellStyle(style);

    }

    @SuppressWarnings("deprecation")
	public static void addBorder(CellStyle cellStyle) {

		if (cellStyle != null) {

		    //HSSFColor borderColor = new HSSFColor(Color.black);

		    //cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		    //((HSSFCellStyle) cellStyle).setBorderColor(BorderSide.RIGHT, borderColor);

		    //cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		    //((HSSFCellStyle) cellStyle).setBorderColor(BorderSide.LEFT, borderColor);

		    //cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		    //((HSSFCellStyle) cellStyle).setBorderColor(BorderSide.TOP, borderColor);

		    //cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		    //((HSSFCellStyle) cellStyle).setBorderColor(BorderSide.BOTTOM, borderColor);

			cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			cellStyle.setBottomBorderColor(HSSFColor.BLACK.index);
			cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			cellStyle.setLeftBorderColor(HSSFColor.GREEN.index);
			cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
			cellStyle.setRightBorderColor(HSSFColor.BLUE.index);
			cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
			cellStyle.setTopBorderColor(HSSFColor.BLACK.index);
		}

    }

    public HSSFSheet createSheet(HSSFWorkbook workbook, String type) {

		String sheetName = type;
		HSSFSheet sheet = workbook.createSheet(sheetName);
		return sheet;

    }

    public HSSFWorkbook createWorkBook() {

		HSSFWorkbook workbook = new HSSFWorkbook();
		return workbook;

    }

}
