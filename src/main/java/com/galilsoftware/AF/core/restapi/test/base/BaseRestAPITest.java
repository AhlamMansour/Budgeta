package com.galilsoftware.AF.core.restapi.test.base;

import java.lang.reflect.Method;
import java.util.Hashtable;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

import com.galilsoftware.AF.core.SelTestProps;
import com.galilsoftware.AF.core.listeners.DataProviderParams;
import com.galilsoftware.AF.core.restapi.RestCall;
import com.galilsoftware.AF.core.restapi.RestObject;
import com.galilsoftware.AF.core.restapi.RestResponse;
import com.galilsoftware.AF.core.restapi.client.RestClient;
import com.galilsoftware.AF.core.restapi.test.utils.RestParser;
import com.galilsoftware.AF.core.restapi.test.utils.RestValidator;
import com.galilsoftware.AF.core.utilities.Xls_Reader;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

/**
 * Base Test Class for Rest API Tests.
 * 
 * @author Amir Najjar
 */
public abstract class BaseRestAPITest {

	protected RestClient restClient; //waseem
	
	protected RestValidator restValidator; //waseem

	protected String rootRestUrl;

	protected String nameOfXls;

	protected static Xls_Reader xls;
	
	protected final int RESPONSE = 1;
	
	protected final int CALL = 0;


	/**
	 * Maps flows to invocation methods note: the invocation methods are
	 * represented as a string - using reflection API, the relevant method will
	 * be invoked.
	 */
	protected Multimap<String, RestObject> keywordsMap;

	@BeforeClass
	public void init() {
		rootRestUrl = SelTestProps.get("common.rest.base.url");
		restClient = new RestClient(rootRestUrl);
		restValidator = new RestValidator(restClient);
		nameOfXls = SelTestProps.get("common.rest.excel.name");
		xls = new Xls_Reader(System.getProperty("user.dir") + "\\src\\main\\java\\resources\\" + nameOfXls);
		initializeKeywords();
	}

	@SuppressWarnings("unchecked")
	private void initializeKeywords() {
		Object[][] restData = getRestData("Keywords", xls, "Keywords");
		// TODO: validate rest Data is not empty

		keywordsMap = ArrayListMultimap.create();
		
		String currentAction = "";

		for (int i = 0; i < restData.length; i++) {

			Hashtable<String, String> rowData = (Hashtable<String, String>) restData[i][0];
			String action = rowData.get("Action");

			if (action.equals("EOFLOW"))
				continue;
			else if (action.equals("N/A") || action.isEmpty()) {
				keywordsMap.put(currentAction, createRestObject(rowData));
			} else {
				currentAction = action;
				keywordsMap.put(currentAction, createRestObject(rowData));
			}
		}
	}

	private RestObject createRestObject(Hashtable<String, String> rowData) {
		
		RestCall call = RestParser.parseCall(rowData);
		RestResponse response = RestParser.parseResponse(rowData);
		return new RestObject(call, response);
	}

	public static Object[][] getRestData(String area, Xls_Reader xls, String sheet) {
		int testStartRowNum = 0;
		// find the row num from which test starts
		for (int rNum = 1; rNum <= xls.getRowCount(sheet); rNum++) {
			if (xls.getCellData(sheet, 0, rNum).equals(area)) {
				testStartRowNum = rNum;
				break;
			}
		}
		System.out.println("Test " + area + " starts from " + testStartRowNum);

		int colStartRowNum = testStartRowNum + 1;
		int totalCols = 0;
		while (!xls.getCellData(sheet, totalCols, colStartRowNum).equals("")) {
			totalCols++;
		}
		System.out.println("Total Cols in test " + area + " are " + totalCols);

		// rows
		int dataStartRowNum = testStartRowNum + 2;
		int totalRows = 0;
		while (!xls.getCellData(sheet, 0, dataStartRowNum + totalRows).equals("")) {
			totalRows++;
		}
		System.out.println("Total Rows in test " + area + " are " + totalRows);

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

	@DataProvider(name = "ExcelFileLoader")
	public static Object[][] getDataFromXmlFile(final Method testMethod) {
		DataProviderParams parameters = testMethod.getAnnotation(DataProviderParams.class);

		if (parameters != null) {
			String sheet = parameters.sheet();
			String area = parameters.area();
			return getRestData(area, xls, sheet);
		} else {
			throw new RuntimeException("Couldn't find the annotation");
		}
	}

}
