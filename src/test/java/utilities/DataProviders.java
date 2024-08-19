package utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders {

	@DataProvider(name="LoginData")
	public String[][] getData() throws IOException {
		String filePath = ".\\testData\\Opencart_LoginData.xlsx";
		//creating an object for ExcelUtility class
		ExcelUtility excelUtility = new ExcelUtility(filePath);

		int totalRows = excelUtility.getRowCount("LoginData");
		int totalCols = excelUtility.getCellCount("LoginData", 1);
		//created for two dimension array
		String loginData[][] = new String[totalRows][totalCols];

		for (int i = 1; i <= totalRows; i++) {
			for (int j = 0; j < totalCols; j++) {
				loginData[i - 1][j] = excelUtility.getCellData("LoginData", i, j);
			}
		}
		return loginData;
	}
}
