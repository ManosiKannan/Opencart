package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility {

	public FileInputStream fis;
	public FileOutputStream fos;
	public XSSFWorkbook workbook;
	public XSSFSheet sheet;
	public XSSFRow row;
	public XSSFCell cell;
	public String filePath;

	public ExcelUtility(String filePath) {
		this.filePath = filePath;
	}

	public int getRowCount(String sheetName) throws IOException {
		fis = new FileInputStream(filePath);
		workbook = new XSSFWorkbook(fis);
		sheet = workbook.getSheet(sheetName);
		int rowCount = sheet.getLastRowNum();
		workbook.close();
		fis.close();
		return rowCount;
	}

	public int getCellCount(String sheetName, int rowNum) throws IOException {
		fis = new FileInputStream(filePath);
		workbook = new XSSFWorkbook(fis);
		sheet = workbook.getSheet(sheetName);
		row = sheet.getRow(rowNum);
		int cellCount = row.getLastCellNum();
		workbook.close();
		fis.close();
		return cellCount;
	}

	public String getCellData(String sheetName, int rowNum, int colNum) throws IOException {
		fis = new FileInputStream(filePath);
		workbook = new XSSFWorkbook(fis);
		sheet = workbook.getSheet(sheetName);
		row = sheet.getRow(rowNum);
		cell = row.getCell(colNum);

		DataFormatter formater = new DataFormatter();
		String data;

		try {
			// Return the formatted value of a cell as a String
			data = formater.formatCellValue(cell);
		} catch (Exception e) {
			data = "";
		}
		workbook.close();
		fis.close();
		return data;
	}

	public void setCellData(String sheetName, int rowNum, int colNum, String data) throws IOException {
		File file = new File(filePath);
		// If file not exists then create new file
		if (!file.exists()) {
			workbook = new XSSFWorkbook();
			fos = new FileOutputStream(filePath);
			workbook.write(fos);
		}

		fis = new FileInputStream(filePath);
		workbook = new XSSFWorkbook(fis);

		// If sheet not exists then create new sheet
		if (workbook.getSheetIndex(sheetName) == -1) {
			workbook.createSheet(sheetName);
			sheet = workbook.getSheet(sheetName);
			// If row not exists then create new row
			if (sheet.getRow(rowNum) == null) {
				sheet.createRow(rowNum);
				row = sheet.getRow(rowNum);
				cell = row.createCell(colNum);
				cell.setCellValue(data);
				fos = new FileOutputStream(filePath);
				workbook.write(fos);
				workbook.close();
				fis.close();
				fos.close();
			}
		}
	}

}
