package com.budgeta.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;

import org.bouncycastle.crypto.modes.SICBlockCipher;
import org.testng.Assert;

import com.budgeta.pom.GeneralSection;
import com.galilsoftware.AF.core.utilities.EmailReader;
import com.galilsoftware.AF.core.utilities.WebdriverUtils;

public class BudgetaUtils {

	protected static final String IMAP_SERVER = "imap.gmail.com";

	private static String EMAIL = "galiltest123@gmail.com";

	private static String PASSWORD = "galil1234";

	static final String[] Month = { "JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC" };

	public static List<String> getAllMonthsBetweenTwoMonths(String monthX, String yearX, String monthY, String yearY, int payAfter, boolean isSpecificMonths) {
		System.out.println("get range from: " + monthX + "_" + yearX + " to: " + monthY + "_" + yearY);
		int indexX = 0;
		for (int i = 0; i < Month.length; i++) {
			if (Month[i].equalsIgnoreCase(monthX.trim())) {
				indexX = i;
				break;
			}
		}
		List<String> res = new ArrayList<String>();
		int year1 = Integer.parseInt(yearX);
		int year2 = Integer.parseInt(yearY);
		if (year1 > year2)
			return res;
		if (year1 == year2) {
			if (getIndexOfMonth(monthX) > getIndexOfMonth(monthY))
				return res;
		}
		while (!(Month[indexX].equalsIgnoreCase(monthY.trim()) && yearX.trim().equalsIgnoreCase(yearY.trim()))) {
			res.add(Month[indexX] + " " + yearX.trim());
			indexX++;
			if (indexX >= Month.length) {
				indexX = 0;
				yearX = (Integer.parseInt(yearX.trim()) + 1) + "";
			}
		}
		res.add(Month[indexX] + " " + yearX.trim());
		if (isSpecificMonths) {
			while (payAfter != 0) {
				indexX++;
				if (indexX >= Month.length) {
					indexX = 0;
					yearX = (Integer.parseInt(yearX.trim()) + 1) + "";
				}
				res.add(Month[indexX] + " " + yearX.trim());
				payAfter--;
			}
		}

		return res;
	}

	
	public static List<String> getAllMonthsOfDateRange(String monthX, String yearX, String monthY, String yearY) {
		System.out.println("get range from: " + monthX + "_" + yearX + " to: " + monthY + "_" + yearY);
		int indexX = 0;
		for (int i = 0; i < Month.length; i++) {
			if (Month[i].equalsIgnoreCase(monthX.trim())) {
				indexX = i;
				break;
			}
		}
		List<String> res = new ArrayList<String>();
		int year1 = Integer.parseInt(yearX);
		int year2 = Integer.parseInt(yearY);
		if (year1 > year2)
			return res;
		if (year1 == year2) {
			if (getIndexOfMonth(monthX) > getIndexOfMonth(monthY))
				return res;
		}
		while (!(Month[indexX].equalsIgnoreCase(monthY.trim()) && yearX.trim().equalsIgnoreCase(yearY.trim()))) {
			res.add(Month[indexX] + " " + yearX.trim());
			indexX++;
			if (indexX >= Month.length) {
				indexX = 0;
				yearX = (Integer.parseInt(yearX.trim()) + 1) + "";
			}
		}
		res.add(Month[indexX] + " " + yearX.trim());

		return res;
	}
	
	
	public static String getNextMonth(String month) {
		for (int i = 0; i < Month.length; i++) {
			if (Month[i].equals(month)) {
				if (i == 11)
					return Month[0];
				return Month[i + 1];
			}
		}
		return "";
	}

	public static String getNextDate(String month, String year) {
		String nextMonth = getNextMonth(month);
		String nextYear = Integer.parseInt(year) + 1 + "";
		if (nextMonth.equals("Jan"))
			return nextMonth + " " + nextYear;
		return nextMonth + " " + year;
	}

	public static String getPreviousMonth(String month) {
		for (int i = 0; i < Month.length; i++) {
			if (Month[i].equalsIgnoreCase(month)) {
				if (i == 0)
					return Month[11];
				return Month[i - 1];
			}
		}
		return "";
	}

	public static int getIndexOfMonth(String month) {
		for (int i = 0; i < Month.length; i++) {
			if (Month[i].equalsIgnoreCase(month))
				return i;
		}
		return -1;
	}
	
	public static int[] getIndexOfMonths(List<String> month) {
		int[] result = new int[month.size() +1];
		for (int i = 0; i < Month.length; i++) {
			if (Month[i].equalsIgnoreCase(month.get(i)))
			{
				result[i]=i;
			}
				
		}
		return result;
	}
	
	public static List<Integer> getIndexOfQuarterMonths(List<String> month,List<String> dates) {
		List<Integer> quarterIndex = new ArrayList<>();
		for (int j = 0; j < month.size(); j++){
			for (int i = 0; i < dates.size(); i++) {
				if (dates.get(i).contains(month.get(j)))
				{
					quarterIndex.add(i);
				}
					
			}
		}
//		int tmp = 0;
//		
//		for (int x = 0; x < quarterIndex.size(); x++){
//			if(quarterIndex.get(x) > quarterIndex.get(x+1)){
//				tmp = quarterIndex.get(x);
//				quarterIndex.add(quarterIndex.get(x +1));
//				
//				
//				
//			}
//		
//		
//		}
//		
		Collections.sort(quarterIndex);


		return quarterIndex;
	}
	

	public static List<Integer> getIndexOfFiscalMonth(String month,List<String> dates) {
		List<Integer> fiscalIndex = new ArrayList<>();
		
		for (int i = 0; i < dates.size(); i++) {
			if (dates.get(i).contains(month))
			{
				fiscalIndex.add(i);
			}
				
		}
		return fiscalIndex;
	}
	
	public static String getMonthWithIndex(int index) {
		return Month[index - 1];
	}

	public static List<String> getBeginningOfQuaterlyMonths(String fiscal) {
		List<String> beginningOfQuaterly = new ArrayList<>();
		int index = getIndexOfMonth(fiscal);

		for (int i = 1; i <= 4; i++) {
			beginningOfQuaterly.add(Month[index]);
			index += 3;
			if (index > 11)
				index -= 12;
		}
		return beginningOfQuaterly;
	}

	public static List<String> getEndOfQuaterlyMonths(String fiscal) {
		List<String> endOfQuaterly = new ArrayList<>();
		int index = getIndexOfMonth(getPreviousMonth(fiscal));

		for (int i = 1; i <= 4; i++) {
			endOfQuaterly.add(Month[index]);
			index += 3;
			if (index > 11)
				index -= 12;
		}
		return endOfQuaterly;
	}

	public static int getNumOfMonthsBetweenTwoDate(String monthX, String yearX, String monthY, String yearY) {
		int year1 = Integer.parseInt(yearX);
		int year2 = Integer.parseInt(yearY);
		if (year1 < year2) {
			return getAllMonthsBetweenTwoMonths(monthX, yearX, monthY, yearY, 0, false).size();
		}
		if (year1 == year2) {
			if (getIndexOfMonth(monthX) <= getIndexOfMonth(monthY))
				return getAllMonthsBetweenTwoMonths(monthX, yearX, monthY, yearY, 0, false).size();
			else
				return getAllMonthsBetweenTwoMonths(monthY, yearY, monthX, yearX, 0, false).size() * -1;
		} else {
			return getAllMonthsBetweenTwoMonths(monthY, yearY, monthX, yearX, 0, false).size() * -1;
		}
	}

	public static String[] calculateValues_Once(String fromMonth, String fromYear, String toMonth, String toYear, String onDateMonth, String onDateYear,
			int amount, int payAfter, int supportPercent, int supportPeriod, String toExactMonth, String toExactYear) {
		List<String> months = getAllMonthsBetweenTwoMonths(fromMonth, fromYear, toMonth, toYear, payAfter, false);
		List<String> speceficMonth = getAllMonthsBetweenTwoMonths(fromMonth, fromYear, toExactMonth, toExactYear, payAfter, true);
		String[] res;
		if (months.size() > speceficMonth.size())
			res = new String[months.size()];
		else
			res = new String[speceficMonth.size()];
		int support = (int) Math.round(((double) supportPercent / 100) * amount);
		// float support = (float) (((double) supportPercent / 100) * amount);
		int period = supportPeriod;
		int endIndex = res.length;
		int startIndex = 0;
		if (!onDateMonth.isEmpty()) {
			startIndex = getAllMonthsBetweenTwoMonths(fromMonth, fromYear, onDateMonth, onDateYear, payAfter, false).size() - 1;
		}
		startIndex += payAfter;
		for (int i = 0; i < endIndex; i++) {
			res[i] = "-";
		}

		endIndex = speceficMonth.size();

		res[startIndex] = amount + support + "";
		for (int i = startIndex + 1; i < endIndex; i++) {
			period--;
			if (period == 0) {
				res[i] = support + "";
				period = supportPeriod;
			} else {
				res[i] = "-";
			}
		}
		return res;
	}

	public static String[] calculateValues_Monthly(String fromMonth, String fromYear, String toMonth, String toYear, int amount, int payAfter,
			int supportPercent, int supportPeriod, int growth, String toExactMonth, String toExactYear) {
		List<String> months = getAllMonthsBetweenTwoMonths(fromMonth, fromYear, toMonth, toYear, payAfter, false);
		List<String> speceficMonth = getAllMonthsBetweenTwoMonths(fromMonth, fromYear, toExactMonth, toExactYear, payAfter, true);

		String[] res;
		if (months.size() > speceficMonth.size())
			res = new String[months.size()];
		else
			res = new String[speceficMonth.size()];

		int support = (int) Math.round(((double) supportPercent / 100) * amount);

		int period = supportPeriod;
		int startIndex = payAfter;
		int endIndex = res.length;
		float sum = amount + support;

		for (int i = 0; i < endIndex; i++) {
			res[i] = "-";
		}

		endIndex = speceficMonth.size();
		res[startIndex] = (int) Math.round(sum) + "";
		for (int i = startIndex + 1; i < endIndex; i++) {
			period--;
			if (period == 0) {
				sum = (float) (sum + support + ((double) growth / 100) * sum);
				period = supportPeriod;
			} else {
				sum = (float) (sum + ((double) growth / 100) * sum);

			}
			res[i] = (int) Math.round(sum) + "";
		}
		return res;
	}

	public static String[] calculateValues_Quaterly(String fromMonth, String fromYear, String toMonth, String toYear, int amount, int payAfter, int growth,
			String AtDate, String fiscal, String toExactMonth, String toExactYear) {
		List<String> actualMonths;
		List<String> months = getAllMonthsBetweenTwoMonths(fromMonth, fromYear, toMonth, toYear, payAfter, false);
		List<String> speceficMonth = getAllMonthsBetweenTwoMonths(fromMonth, fromYear, toExactMonth, toExactYear, payAfter, true);

		String[] res, finalRes;
		if (months.size() > speceficMonth.size()) {
			actualMonths = months;
		} else {
			actualMonths = speceficMonth;
		}
		res = new String[actualMonths.size()];
		finalRes = new String[actualMonths.size()];
		List<String> quaterly = new ArrayList<>();

		// int sum = amount;
		float sum = amount;

		if (AtDate.equals("spread evenly")) {
			sum = sum / 3;
			int quater = 3;
			for (int i = 0; i < res.length; i++) {
				// sum = sum + (int) Math.round(((double) growth / 100) * sum);
				res[i] = (int) Math.round(sum) + "";
				quater--;
				if (quater == 0) {
					sum = (float) (sum + ((double) growth / 100) * sum);
					quater = 3;
				}
			}
		} else {
			if (AtDate.equals("at the beginning"))
				quaterly = getBeginningOfQuaterlyMonths(fiscal);
			if (AtDate.equals("at the end"))
				quaterly = getEndOfQuaterlyMonths(fiscal);

			for (int i = 0; i < res.length; i++) {
				if (quaterly.contains(actualMonths.get(i).split(" ")[0].trim())) {
					res[i] = (int) Math.round(sum) + "";
					sum = (float) (sum + ((double) growth / 100) * sum);
				} else {
					res[i] = "-";
				}
			}

		}

		for (int i = 0; i < res.length; i++) {
			if (i - payAfter < 0)
				finalRes[i] = "-";
			else
				finalRes[i] = res[i - payAfter];
		}

		for (int i = speceficMonth.size(); i < actualMonths.size(); i++) {
			finalRes[i] = "-";
		}
		return finalRes;
	}

	public static String[] calculateValues_Yearly(String fromMonth, String fromYear, String toMonth, String toYear, int amount, int payAfter, int growth,
			String AtDate, String fiscal, String toExactMonth, String toExactYear) {
		List<String> actualMonths;
		List<String> months = getAllMonthsBetweenTwoMonths(fromMonth, fromYear, toMonth, toYear, payAfter, false);
		List<String> speceficMonth = getAllMonthsBetweenTwoMonths(fromMonth, fromYear, toExactMonth, toExactYear, payAfter, true);

		String[] res, finalRes;
		if (months.size() > speceficMonth.size()) {
			actualMonths = months;
		} else {
			actualMonths = speceficMonth;
		}
		res = new String[actualMonths.size()];
		finalRes = new String[actualMonths.size()];

		String startMonth = "";
		int startIndex = payAfter;
		float sum = amount;
		// int sum = amount;
		for (int i = 0; i < startIndex; i++) {
			res[i] = "-";
		}
		if (AtDate.equals("spread evenly")) {
			int yearly = 13;
			sum = sum / 12;
			for (int i = startIndex; i < res.length; i++) {
				// sum = sum + (int) Math.round(((double) growth / 100) * sum);
				yearly--;
				if (yearly == 0) {
					sum = (float) (sum + ((double) growth / 100) * sum);
					yearly = 12;
				}
				res[i] = (int) Math.round(sum) + "";
			}
		} else {
			if (AtDate.equals("at the beginning"))
				startMonth = fiscal.toUpperCase();
			if (AtDate.equals("at the end"))
				startMonth = getPreviousMonth(fiscal);

			for (int i = 0; i < res.length; i++) {
				if (actualMonths.get(i).contains(startMonth)) {
					res[i] = (int) Math.round(sum) + "";
					sum = (float) (sum + ((double) growth / 100) * sum);
				} else {
					res[i] = "-";
				}
			}
		}

		for (int i = 0; i < res.length; i++) {
			if (i - payAfter < 0)
				finalRes[i] = "-";
			else
				finalRes[i] = res[i - payAfter];
		}
		for (int i = speceficMonth.size(); i < actualMonths.size(); i++) {
			finalRes[i] = "-";
		}

		return finalRes;
	}

	public static String[] calculateEmployeeValues_Monthly(String fromMonth, String fromYear, String toMonth, String toYear, String HireDateMonth,
			String HireDateYear, String EndDateMonth, String EndDateYear, int baseSalary, int benefits, int bonus, String payment, int yearlyVacationDays,
			int AvgAccruedVacation, int YearlyIncrease, String fiscal) {
		List<String> months = getAllMonthsBetweenTwoMonths(fromMonth, fromYear, toMonth, toYear, 0, false);
		String[] res = new String[months.size()];
		int startIndex = 0, endIndex = res.length;
		int fromHireToView = getNumOfMonthsBetweenTwoDate(HireDateMonth, HireDateYear, fromMonth, fromYear);
		int fromEndHireToEndView = getNumOfMonthsBetweenTwoDate(EndDateMonth, EndDateYear, toMonth, toYear);
		int indexOfExtra = months.indexOf(EndDateMonth + " " + EndDateYear);
		int indexBonusMonth = getIndexOfMonth(getPreviousMonth(fiscal));
		String BonusMonth = getPreviousMonth(fiscal);
		int fromHireDateToFiscal = (getNumOfMonthsBetweenTwoDate(HireDateMonth, HireDateYear, fiscal, EndDateYear)) - 1;
		if (getNumOfMonthsBetweenTwoDate(HireDateMonth, HireDateYear, toMonth, toYear) < 0
				|| getNumOfMonthsBetweenTwoDate(EndDateMonth, EndDateYear, fromMonth, fromYear) > 0) {
			for (int i = 0; i < res.length; i++) {
				res[i] = "-";
			}
			return res;
		}
		int bonusMonths = 0;
		int benefit = (int) Math.round(((double) benefits / 100) * baseSalary);
		// float benefit = (float) (((double) benefits / 100) * baseSalary);

		if (fromHireToView < 0) {
			for (int i = 0; i > fromHireToView + 1; i--) {
				res[-i] = "-";
			}
			startIndex = (-1 * fromHireToView) - 1;
		} else {
			bonusMonths += fromHireToView;
		}

		if (fromEndHireToEndView > 0) {
			endIndex = res.length - fromEndHireToEndView + 1;
		}

		int sum = baseSalary + benefit;
		double yearlyVacation = (double) yearlyVacationDays / 12;
		int worksMonth = getNumOfMonthsBetweenTwoDate(HireDateMonth, HireDateYear, EndDateMonth, EndDateYear);
		double avgAccural = (((double) AvgAccruedVacation / 100) * (worksMonth * yearlyVacation));
		double valueOfvactationDays = (double) baseSalary / 21.75;
		double Extra = avgAccural * valueOfvactationDays;

		int Bonus = (int) Math.round(((double) bonus / 100) * baseSalary) * fromHireDateToFiscal;

		int BonusForEmployee = baseSalary + Bonus + benefit;

		// float sum = baseSalary + benefit;

		// for (int i = startIndex; i < endIndex; i++) {
		// bonusMonths++;
		// if (months.get(i).contains("Dec")) {
		// res[i] = sum + ((int) Math.round(((double) bonus / 100) * bonusMonths
		// * baseSalary)) + "";
		// //res[i] = sum + ((float)(((double) bonus / 100) * bonusMonths *
		// baseSalary)) + "";
		// bonusMonths = 0;
		// } else {
		// res[i] = sum + "";
		// }
		// }

		for (int i = startIndex; i < endIndex; i++) {
			bonusMonths++;
			if (months.get(i).contains(BonusMonth)) {
				res[i] = BonusForEmployee + "";
				// res[i] = sum + ((float)(((double) bonus / 100) * bonusMonths
				// * baseSalary)) + "";
				bonusMonths = 0;
			} else if (months.get(i).contains(fiscal) && !HireDateMonth.equals(fiscal)) {
				sum = sum + ((int) Math.round(((double) YearlyIncrease / 100) * sum));
				res[i] = sum + "";
			} else {
				res[i] = sum + "";
			}

		}

		for (int i = endIndex; i < res.length; i++) {
			res[i] = "-";
		}

		res[indexOfExtra] = ((int) Math.round((double) Extra + sum)) + "";
		if (payment.equals("Same month"))
			return res;

		String[] fetchRes = new String[res.length + 1];
		fetchRes[0] = "-";
		for (int i = 1; i < res.length + 1; i++) {
			fetchRes[i] = res[i - 1];
		}
		return fetchRes;
	}

	public static String[] calculateEmployeeValues_Yearly(String fromMonth, String fromYear, String toMonth, String toYear, String HireDateMonth,
			String HireDateYear, String EndDateMonth, String EndDateYear, int baseSalary, int benefits, int bonus, String payment) {
		List<String> months = getAllMonthsBetweenTwoMonths(fromMonth, fromYear, toMonth, toYear, 0, false);
		String[] res = new String[months.size()];
		int startIndex = 0, endIndex = res.length;
		int fromHireToView = getNumOfMonthsBetweenTwoDate(HireDateMonth, HireDateYear, fromMonth, fromYear);
		int fromEndHireToEndView = getNumOfMonthsBetweenTwoDate(EndDateMonth, EndDateYear, toMonth, toYear);
		int bonusMonths = 0;
		int sum = baseSalary / 12;
		// int benefit = (int) Math.round(((double) benefits / 100) * sum);
		// float benefit = (float) (((double) benefits / 100) * sum);

		if (fromHireToView < 0) {
			for (int i = 0; i > fromHireToView + 1; i--) {
				res[-i] = "-";
			}
			startIndex = (-1 * fromHireToView) - 1;
		} else {
			bonusMonths += fromHireToView;
		}

		if (fromEndHireToEndView > 0) {
			endIndex = res.length - fromEndHireToEndView + 1;
		}

		for (int i = startIndex; i < endIndex; i++) {
			bonusMonths++;
			if (months.get(i).contains("Dec")) {
				res[i] = sum + ((int) Math.round(((double) bonus / 100) * bonusMonths * sum)) + "";
				// res[i] = sum + ((float)(((double) bonus / 100) * bonusMonths
				// * sum)) + "";
				bonusMonths = 0;
			} else {
				res[i] = sum + "";
			}
		}
		if (payment.equals("Same month"))
			return res;

		String[] fetchRes = new String[res.length + 1];
		fetchRes[0] = "-";
		for (int i = 1; i < res.length; i++) {
			fetchRes[i] = res[i - 1];
		}
		return fetchRes;
	}

	public static String checkEmail(String[] innerWords, String subLink) throws Exception {

		EmailReader email = new EmailReader(EMAIL, PASSWORD, IMAP_SERVER);
		Message msg = email.getEmail(innerWords);
		Assert.assertTrue(msg != null, "No Mail was recieved to the new account, or arrived in spam");
		if (subLink != null) {
			Object content = msg.getContent();
			if (msg.getContentType().contains("multipart")){
				try {
	                Multipart mp = (Multipart) msg.getContent();
	                WebdriverUtils.sleep(1000);
	                content = mp.getBodyPart(0).getContent();
	            } catch (Exception ex) {
	                System.out.println("Exception arise at get Content");
	            }
			}

			ArrayList<String> links = email.pullLinks(content.toString());
			if (links != null) {
				ArrayList<String> allLinks = new ArrayList<String>();
				for (String link : links) {
					if (link.contains(subLink)) {
						allLinks.add(link);
					}
				}
				try {
					return allLinks.get(4);
				} catch (Exception e) {
					return allLinks.get(0);
				}
			}
		}
		return "the link is null";
	}
	
	
	public static String[] calculateSheetValues_Quaterly(List<String> expectedValues, List<Integer> indexOfquartermont){
		List<String> res = new ArrayList<>();
		//	int[] values = new int[expectedValues.size()];
			String[] fiscal = new String[indexOfquartermont.size() + 1];
			String[] result = new String[indexOfquartermont.size() + 1];
			int index = 0,end,sum = 0;
			
			for(int i=0; i<fiscal.length;i++){
				int start = index;
				if(i==fiscal.length -1){
					end = expectedValues.size();
				}else
					end = indexOfquartermont.get(i);
				for(int j=start; j<end;j++){
					if(expectedValues.get(j).equals("-")){
						expectedValues.add(j, "0");
						expectedValues.remove(j+1);
					}
					sum += Integer.parseInt(expectedValues.get(j));
				}
				index = end;
				result[i] = sum + "";
				sum = 0;
				
			}
			
			
			return result;
			

		
	
		
	}
	
	
	public static String[] calculateSheetValues_Yearly(List<String> expectedValues, List<Integer> indexOfFiscal){
		List<String> res = new ArrayList<>();
	//	int[] values = new int[expectedValues.size()];
		String[] fiscal = new String[indexOfFiscal.size() + 1];
		String[] result = new String[indexOfFiscal.size() + 1];
		int index = 0,end,sum = 0;
		
		for(int i=0; i<fiscal.length;i++){
			int start = index;
			if(i==fiscal.length -1){
				end = expectedValues.size();
			}else
				end = indexOfFiscal.get(i);
			for(int j=start; j<end;j++){
				if(expectedValues.get(j).equals("-")){
					expectedValues.add(j, "0");
					expectedValues.remove(j+1);
				}
				sum += Integer.parseInt(expectedValues.get(j));
			}
			index = end;
			result[i] = sum + "";
			sum = 0;
			
		}
		
		
		return result;
		
	
//		for (int i = 0; i< values.length; i++){
//			values[i] = Integer.parseInt(expectedValues.get(i));
//			System.out.println(values);
//		}
//		
//		for (int j = 0; j<index.length; j++){
//			index[j] = indexOfFiscal.get(j);
//		}

	}

}