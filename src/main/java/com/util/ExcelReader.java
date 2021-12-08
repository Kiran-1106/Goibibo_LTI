package com.util;

import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {

	static XSSFWorkbook workbook;
	static XSSFSheet sheet;

	public ExcelReader(String excelPath, String sheetName) {
		try {
			workbook = new XSSFWorkbook(excelPath);
			sheet = workbook.getSheet(sheetName);
		} catch (IOException e) {
			System.out.println("File not present");
		}
	}

	public  int getRowCount() {
		int rowCount = 0;
		rowCount= sheet.getPhysicalNumberOfRows();
		return rowCount;

	}
 
	public  int getColCount() {
		int colCount = 0;
		colCount= sheet.getRow(0).getLastCellNum();
		return colCount;
	}

	public  String getStringCellData(int rowNum, int colNum) {
		String cellData = null;
		cellData= sheet.getRow(rowNum).getCell(colNum).getStringCellValue();
		return cellData;
	}

	public Object[][] getTestData() {
		Object data[][] = null;
		int rowCount = getRowCount();
		int colCount = getColCount();
		data = new Object[rowCount-1][colCount];
		for (int row = 1; row < rowCount; row++) {
			for (int col = 0; col < colCount; col++) {
				data[row-1][col] = getStringCellData(row, col);
			}
		}
		return data;
	}
	
}
