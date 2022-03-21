package dataReader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Read/write data from TestData (.xls/.xlsx) file
 */
public class DataReader{
	static Workbook book;
	static Sheet sheet;
	
	public static String TESTDATA_SHEET_PATH = PropertyReader.read("test.dataPath");
	
	public static Object[][] getDataFromSheet(){
		FileInputStream file = null;
		try {
			file = new FileInputStream(TESTDATA_SHEET_PATH);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			book = WorkbookFactory.create(file);
		} catch (IOException e) {
			e.printStackTrace();
		} 
		sheet = book.getSheet(sheetName);
		Object[][] data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
		for (int i = 0; i < sheet.getLastRowNum(); i++) {
			for (int k = 0; k < sheet.getRow(0).getLastCellNum(); k++) {
				data[i][k] = sheet.getRow(i + 1).getCell(k).toString().replace(".0", "");
			}
		}
		return data;
	}
	
//	##################################################################################
	private static String sheetName = "Sheet1";
	private static String file = PropertyReader.read("test.dataPath");
	private static Map<String, String> map = new HashMap<String, String>();

	/**
	 * Get the testData of a input testcase
	 * @param testName
	 * @param columnName
	 * @return testData of a input testcase
	 * @throws IOException
	 */
	public static String get(String testName, String columnName) throws IOException {
		FileInputStream fis;
		int targetCol = 0;
		
		fis = new FileInputStream(file);
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet ws = wb.getSheet(sheetName);
		int rows = ws.getPhysicalNumberOfRows();
		for (int rowNum = 0; rowNum < rows; rowNum++) {
			int cols = ws.getRow(0).getPhysicalNumberOfCells();
			for (int colNum = 0; colNum < cols; colNum++) {
				if (ws.getRow(rowNum).getCell(colNum, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString().replace(".0", "").equalsIgnoreCase(columnName)) {
					targetCol = colNum;
				}
				map.put(ws.getRow(rowNum).getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString().replace(".0", ""),
						ws.getRow(rowNum).getCell(targetCol, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).toString().replace(".0", ""));
			}
		}
		wb.close();
		return map.get(testName);
	}
}
