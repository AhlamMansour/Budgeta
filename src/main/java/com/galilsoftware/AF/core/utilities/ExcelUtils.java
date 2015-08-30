package com.galilsoftware.AF.core.utilities;
import java.util.Hashtable;

public class ExcelUtils {

		/**
		 * returns if a test was marked to be executed or not.
		 * @param testName
		 * @param xls - the excel file
		 * @return true if it is marked to be run, false otherwise.
		 */
		public static boolean isExecutable(String testName, Xls_Reader xls){
			
			for(int rowNum=2;rowNum<=xls.getRowCount("Test Cases");rowNum++){
				
				if(xls.getCellData("Test Cases", "TCID", rowNum).equals(testName)){
				
					if(xls.getCellData("Test Cases", "Runmode", rowNum).equals("Y"))
						return true;
					else 
						return false;
				}
				// print the test cases with RUnmode Y
			}
			
			return false;
		}
				
		public static Object[][] getExcelData(String testName, Xls_Reader xls, String sheet) {
			int testStartRowNum = 0;
			// find the row num from which test starts
			for (int rNum = 1; rNum <= xls.getRowCount(sheet); rNum++) {
				if (xls.getCellData(sheet, 0, rNum).equals(testName)) {
					testStartRowNum = rNum;
					break;
				}
			}
			System.out.println("Test " + testName + " starts from " + testStartRowNum);

			int colStartRowNum = testStartRowNum + 1;
			int totalCols = 0;
			while (!xls.getCellData(sheet, totalCols, colStartRowNum).equals("")) {
				totalCols++;
			}
			System.out.println("Total Cols in test " + testName + " are " + totalCols);

			// rows
			int dataStartRowNum = testStartRowNum + 2;
			int totalRows = 0;
			while (!xls.getCellData(sheet, 0, dataStartRowNum + totalRows).equals("")) {
				totalRows++;
			}
			System.out.println("Total Rows in test " + testName + " are " + totalRows);

			// extract data
			Object[][] data = new Object[totalRows][1];
			int index = 0;
			Hashtable<String, String> table = null;
			for (int rNum = dataStartRowNum; rNum < (dataStartRowNum + totalRows); rNum++) {
				table = new Hashtable<String, String>();
				for (int cNum = 0; cNum < totalCols; cNum++) {
					table.put(xls.getCellData(sheet, cNum, colStartRowNum), xls.getCellData(sheet, cNum, rNum));
					System.out.print(xls.getCellData(sheet, cNum, rNum) + " -- ");
				}
				data[index][0] = table;
				index++;
				System.out.println();
			}
			System.out.println("done");

			return data;
		}
		

}
