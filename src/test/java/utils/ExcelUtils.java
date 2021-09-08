package utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;


public class ExcelUtils {
	private static XSSFWorkbook ExcelWBook;
	private static XSSFSheet ExcelWSheet;
	private static XSSFRow row;
	private static Cell Cell;
	public static final int nFirstDataRowNum = 1;
	public static final int nHeaderRowNum = 0;
	public static final String sTestCaseSheet = "TestCases";
	private static final int nTestCaseIdCol = 0;
	private static final int nTestCaseCol = 1;
	public ExcelUtils() {
		System.out.println("In ExcelUtil Constructor");
	}

	public static String getTestCaseId(String sTestCase) {
		String sTestCaseId = "";
		try {
			int nRowNum = getRowNumberOf(nTestCaseCol, sTestCaseSheet, sTestCase);
			sTestCaseId = getStringCellData(nRowNum, nTestCaseIdCol, sTestCaseSheet);
			System.out.println("Test Case Id: " + sTestCaseId);
		} catch (Exception e) {
			System.out.println("Error while fetching test case details\n" + e.getMessage());
		}
		return sTestCaseId;
	}
	
	public static int getTestCaseIdRowNum(String sTestCaseId) {
		int nRowNum = -1;
		try {
			nRowNum = getRowNumberOf(nTestCaseIdCol, sTestCaseSheet, sTestCaseId);
		} catch (Exception e) {
			System.out.println("Error while fething Test Case ID\n" + e.getMessage());			
		}
		return nRowNum;
	}
	
	public static void setExcelFile(String sPath) {
		try {
			System.out.println("Initializing Excel Data File: applicationData.xlsx");
			FileInputStream ExcelFile = new FileInputStream(sPath);
			ExcelWBook = new XSSFWorkbook(ExcelFile);
			System.out.println("Excel File Initialized");
		} catch (Exception e) {
			System.out.println("Failed to initialize Excel Data File: applicationData.xlsx\n" + e.getMessage());
			Assert.fail();
		}
	}

	public static int getRowCount(String sSheetName) {
		int iNumber = 0;
		try {
			ExcelWSheet = ExcelWBook.getSheet(sSheetName);
			iNumber = ExcelWSheet.getLastRowNum() + 1;
		} catch (Exception e) {
			System.out.println("Error while getting row count of sheet: " + sSheetName + "\n" + e.getMessage());
		}
		return iNumber;
	}

	public static int getRowNumberOf(int nColNum, String sSheetName, String sValue) {
		int nRowNum = 0;
		try {
			ExcelWSheet = ExcelWBook.getSheet(sSheetName);
			for (int i = 0; i <= ExcelWSheet.getLastRowNum(); i++) {
				row = ExcelWSheet.getRow(i);
				if (row.getCell(nColNum).getStringCellValue().contentEquals(sValue)) {
					nRowNum = i;
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("Error while getting row Number of: " + sValue + "\n" + e.getMessage());
		}
		return nRowNum;
	}

	public static int getColumnNumberOf(int nRowNum, String sSheetName, String sValue) {
		int nColNum = 0;
		try {
			ExcelWSheet = ExcelWBook.getSheet(sSheetName);
			row = ExcelWSheet.getRow(nRowNum);
			for (int i = 0; i < row.getLastCellNum(); i++) {
				if (row.getCell(i).getStringCellValue().contentEquals(sValue)) {
					nColNum = i;
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("Error while getting Column Number of: " + sValue + "\n" + e.getMessage());
		}
		return nColNum;
	}

	public static int getNumericCellData(int RowNum, int ColNum, String SheetName) {
		int CellData;
		try {
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
			Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
			CellData = (int) Cell.getNumericCellValue();
		} catch (Exception e) {
			System.out.println("Class Utils | Method getCellData | Exception desc : " + e.getMessage());
			return 0;
		}
		return CellData;
	}

	public static String getStringCellData(int RowNum, int ColNum, String SheetName) {
		String CellData = "";
		try {
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
			Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
			CellData = Cell.getStringCellValue();
		} catch (Exception e) {
			System.out.println("Class Utils | Method getCellData | Exception desc : " + e.getMessage());
			return "";
		}
		return CellData;
	}

	public static void setStringCellData(String sValue, int RowNum, int ColNum, String SheetName) throws Exception {
		try {

			ExcelWSheet = ExcelWBook.getSheet(SheetName);
			row = ExcelWSheet.getRow(RowNum);
			Cell = row.getCell(ColNum, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
			if (Cell == null) {
				Cell = row.createCell(ColNum);
				Cell.setCellValue(sValue);
			} else {
				Cell.setCellValue(sValue);
			}
			FileOutputStream fileOut = new FileOutputStream(BaseUtil.configProp.getProperty("excel_file"));
			ExcelWBook.write(fileOut);
			fileOut.flush();
			fileOut.close();
			ExcelWBook = new XSSFWorkbook(new FileInputStream(BaseUtil.configProp.getProperty("excel_file")));
		} catch (Exception e) {

		}
	}

	public static void setIntegerCellData(int nValue, int RowNum, int ColNum, String SheetName) throws Exception {
		try {

			ExcelWSheet = ExcelWBook.getSheet(SheetName);
			row = ExcelWSheet.getRow(RowNum);
			Cell = row.getCell(ColNum, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
			if (Cell == null) {
				Cell = row.createCell(ColNum);
				Cell.setCellValue(nValue);
			} else {
				Cell.setCellValue(nValue);
			}
			FileOutputStream fileOut = new FileOutputStream(BaseUtil.configProp.getProperty("excel_file"));
			ExcelWBook.write(fileOut);
			fileOut.flush();
			fileOut.close();
			ExcelWBook = new XSSFWorkbook(new FileInputStream(BaseUtil.configProp.getProperty("excel_file")));
		} catch (Exception e) {

		}
	}

}