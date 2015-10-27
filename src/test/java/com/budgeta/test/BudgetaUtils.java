package com.budgeta.test;

import java.util.ArrayList;
import java.util.List;

public class BudgetaUtils {

    static final String[] Month = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };

    public static List<String> getAllMonthsBetweenTwoMonths(String monthX, String yearX, String monthY, String yearY) {
	int indexX = 0;
	for (int i = 0; i < Month.length; i++) {
	    if (Month[i].equalsIgnoreCase(monthX.trim())) {
		indexX = i;
		break;
	    }
	}
	List<String> res = new ArrayList<String>();
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
	    if (Month[i].equals(month)) {
		if (i == 0)
		    return Month[11];
		return Month[i - 1];
	    }
	}
	return "";
    }

    public static int getIndexOfMonth(String month) {
	for (int i = 0; i < Month.length; i++) {
	    if (Month[i].equals(month))
		return i;
	}
	return -1;
    }

    public static String getMonthWithIndex(int index) {
	return Month[index - 1];
    }

    public static List<String> getBeginningOfQuaterlyMonths(String fiscal) {
	List<String> beginningOfQuaterly = new ArrayList<>();
	int index = getIndexOfMonth(fiscal);
	
	for(int i = 1 ; i <= 4 ; i++){
		beginningOfQuaterly.add(Month[index]);
		index += 3;
		if(index > 11)
			index -= 12;
	}
	return beginningOfQuaterly;
    }

    public static List<String> getEndOfQuaterlyMonths(String fiscal) {
	List<String> endOfQuaterly = new ArrayList<>();
	int index = getIndexOfMonth(getPreviousMonth(fiscal));
	
	for(int i = 1 ; i <= 4 ; i++){
		endOfQuaterly.add(Month[index]);
		index += 3;
		if(index > 11)
			index -= 12;
	}
	return endOfQuaterly;
    }

    public static int getNumOfMonthsBetweenTwoDate(String monthX, String yearX, String monthY, String yearY) {
	int year1 = Integer.parseInt(yearX);
	int year2 = Integer.parseInt(yearY);
	if (year1 < year2) {
	    return getAllMonthsBetweenTwoMonths(monthX, yearX, monthY, yearY).size();
	}
	if (year1 == year2) {
	    if (getIndexOfMonth(monthX) <= getIndexOfMonth(monthY))
		return getAllMonthsBetweenTwoMonths(monthX, yearX, monthY, yearY).size();
	    else
		return getAllMonthsBetweenTwoMonths(monthY, yearY, monthX, yearX).size() * -1;
	} else {
	    return getAllMonthsBetweenTwoMonths(monthY, yearY, monthX, yearX).size() * -1;
	}
    }

    public static String[] calculateValues_Once(String fromMonth, String fromYear, String toMonth, String toYear, String onDateMonth, String onDateYear,
	    int amount, int payAfter, int supportPercent, int supportPeriod) {
	List<String> months = getAllMonthsBetweenTwoMonths(fromMonth, fromYear, toMonth, toYear);
	String[] res = new String[months.size()];
	//int support = (int) Math.round(((double) supportPercent / 100) * amount);
	float support = (float) (((double) supportPercent / 100) * amount);
	int period = supportPeriod;
	int startIndex = 0;
	if (!onDateMonth.isEmpty()) {
	    startIndex = getAllMonthsBetweenTwoMonths(fromMonth, fromYear, onDateMonth, onDateYear).size() - 1;
	}
	startIndex += payAfter;
	for (int i = 0; i < startIndex; i++) {
	    res[i] = "-";
	}
	res[startIndex] = amount + support + "";
	for (int i = startIndex + 1; i < res.length; i++) {
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
	    int supportPercent, int supportPeriod, int growth) {
	List<String> months = getAllMonthsBetweenTwoMonths(fromMonth, fromYear, toMonth, toYear);
	String[] res = new String[months.size()];
	//int support = (int) Math.round(((double) supportPercent / 100) * amount);
	float support = (float) (((double) supportPercent / 100) * amount);
	int period = supportPeriod;
	int startIndex = payAfter;
	//int sum = amount + support;
	float sum = amount ;
	for (int i = 0; i < startIndex; i++) {
	    res[i] = "-";
	}
	res[startIndex] = sum + "";
	for (int i = startIndex + 1; i < res.length; i++) {
	    period--;
	    if (period == 0) {
		//sum = sum + support + (int) Math.round(((double) growth / 100) * sum);
	    sum = sum + support + (float) (((double) growth / 100) * sum);
		period = supportPeriod;
	    } else {
//		sum = sum + (int) Math.round(((double) growth / 100) * sum);
	    sum = sum + (float) (((double) growth / 100) * sum);
	    }
	    res[i] = sum + "";
	}
	return res;
    }

    public static String[] calculateValues_Quaterly(String fromMonth, String fromYear, String toMonth, String toYear, int amount, int payAfter, int growth,
	    String AtDate,String fiscal) {
	List<String> months = getAllMonthsBetweenTwoMonths(fromMonth, fromYear, toMonth, toYear);
	String[] res = new String[months.size()], finalRes =new String[months.size()];
	List<String> quaterly = new ArrayList<>();

	int startIndex = 0;
//	int sum = amount;
	float sum = amount;

	if (AtDate.equals("spread evenly")) {
		for (int i = 0; i < payAfter; i++) {
		    res[i] = "-";
		}
	    sum = sum / 3;
	    growth = growth / 3;
	    for (int i = startIndex; i < res.length; i++) {
		//sum = sum + (int) Math.round(((double) growth / 100) * sum);
	    	if(months.get(i).contains("Dec")){
	    		res[i] = sum + "";
	    		sum = sum + (float) (((double) growth / 100) * sum);
	    		continue;
	    	}
		res[i] = sum + "";
	    }
	} else {
	    int quater = 3;
	    if (AtDate.equals("at the beginning"))
		quaterly = getBeginningOfQuaterlyMonths(fiscal);
	    if (AtDate.equals("at the end"))
		quaterly = getEndOfQuaterlyMonths(fiscal);
	    while (!quaterly.contains(months.get(startIndex).split(" ")[0].trim())) {
		res[startIndex] = "-";
		startIndex++;
	    }
	    //sum = sum + (int) Math.round(((double) growth / 100) * sum);
	    //sum = sum + (float) (((double) growth / 100) * sum);
	    res[startIndex] = sum + "";
	    for (int i = startIndex + 1; i < res.length; i++) {
		quater--;
		if (quater == 0) {
		    //sum = sum + (int) Math.round(((double) growth / 100) * sum);
			sum = sum + (float) (((double) growth / 100) * sum);
		    res[i] = sum + "";
		    quater = 3;
		} else {
		    res[i] = "-";
		}
	    }
	}
	startIndex = 0;
	while(res[startIndex].equals("-")){
		finalRes[startIndex] = "-";
		startIndex++;
	}
	
	for(int i = startIndex ; i < res.length ; i++){
		if (i - payAfter < 0 )
			finalRes[i] = "-";
		else 
			finalRes[i] = res[i - payAfter];
	}
	return finalRes;
    }

    public static String[] calculateValues_Yearly(String fromMonth, String fromYear, String toMonth, String toYear, int amount, int payAfter, int growth,
	    String AtDate, String fiscal) {
	List<String> months = getAllMonthsBetweenTwoMonths(fromMonth, fromYear, toMonth, toYear);
	String[] res = new String[months.size()], finalRes =new String[months.size()];
	String startMonth = "";
	int startIndex = payAfter;
	//int sum = amount;
	float sum = amount;
	for (int i = 0; i < startIndex; i++) {
	    res[i] = "-";
	}
	if (AtDate.equals("spread evenly")) {
	    sum = sum / 12;
	    for (int i = startIndex; i < res.length; i++) {
		//sum = sum + (int) Math.round(((double) growth / 100) * sum);	    
	    	if(months.get(i).contains("Dec")){
	    		res[i] = sum + "";
	    		sum = sum + (float) (((double) growth / 100) * sum);
	    		continue;
	    	}
			res[i] = sum + "";
	    }
	} else {
	    int yearly = 12;
	    if (AtDate.equals("at the beginning"))
		startMonth = fiscal;
	    if (AtDate.equals("at the end"))
		startMonth = getPreviousMonth(fiscal);
	    while (!months.get(startIndex).contains(startMonth)) {
		res[startIndex] = "-";
		startIndex++;
	    }
	    //sum = sum + (int) Math.round(((double) growth / 100) * sum);
	   // sum = sum + (float) (((double) growth / 100) * sum);
	    res[startIndex] = sum + "";
	    for (int i = startIndex + 1; i < res.length; i++) {
		yearly--;
		if (yearly == 0) {
		    //sum = sum + (int) Math.round(((double) growth / 100) * sum);
			sum = sum + (float) (((double) growth / 100) * sum);
		    res[i] = sum + "";
		    yearly = 12;
		} else {
		    res[i] = "-";
		}
	    }
	}
	startIndex = 0;
	while(res[startIndex].equals("-")){
		finalRes[startIndex] = "-";
		startIndex++;
	}
	
	for(int i = startIndex ; i < res.length ; i++){
		if (i - payAfter < 0 )
			finalRes[i] = "-";
		else 
			finalRes[i] = res[i - payAfter];
	}
	return finalRes;
    }

    public static String[] calculateEmployeeValues_Monthly(String fromMonth, String fromYear, String toMonth, String toYear, String HireDateMonth,
	    String HireDateYear, String EndDateMonth, String EndDateYear, int baseSalary, int benefits, int bonus, String payment) {
	List<String> months = getAllMonthsBetweenTwoMonths(fromMonth, fromYear, toMonth, toYear);
	String[] res = new String[months.size()];
	int startIndex = 0, endIndex = res.length;
	int fromHireToView = getNumOfMonthsBetweenTwoDate(HireDateMonth, HireDateYear, fromMonth, fromYear);
	int fromEndHireToEndView = getNumOfMonthsBetweenTwoDate(EndDateMonth, EndDateYear, toMonth, toYear);
	if (getNumOfMonthsBetweenTwoDate(HireDateMonth, HireDateYear, toMonth, toYear) < 0
		|| getNumOfMonthsBetweenTwoDate(EndDateMonth, EndDateYear, fromMonth, fromYear) > 0) {
	    for (int i = 0; i < res.length; i++) {
		res[i] = "-";
	    }
	    return res;
	}
	int bonusMonths = 0;
	//int benefit = (int) Math.round(((double) benefits / 100) * baseSalary);
	float benefit =  (float) (((double) benefits / 100) * baseSalary);

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

	//int sum = baseSalary + benefit;
	float sum = baseSalary + benefit;

	for (int i = startIndex; i < endIndex; i++) {
	    bonusMonths++;
	    if (months.get(i).contains("Dec")) {
		//res[i] = sum + ((int) Math.round(((double) bonus / 100) * bonusMonths * baseSalary)) + "";
	    res[i] = sum + ((float)(((double) bonus / 100) * bonusMonths * baseSalary)) + "";
		bonusMonths = 0;
	    } else {
		res[i] = sum + "";
	    }
	}
	for (int i = endIndex; i < res.length; i++) {
	    res[i] = "-";
	}
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
	List<String> months = getAllMonthsBetweenTwoMonths(fromMonth, fromYear, toMonth, toYear);
	String[] res = new String[months.size()];
	int startIndex = 0, endIndex = res.length;
	int fromHireToView = getNumOfMonthsBetweenTwoDate(HireDateMonth, HireDateYear, fromMonth, fromYear);
	int fromEndHireToEndView = getNumOfMonthsBetweenTwoDate(EndDateMonth, EndDateYear, toMonth, toYear);
	int bonusMonths = 0;
	int sum = baseSalary / 12;
	//int benefit = (int) Math.round(((double) benefits / 100) * sum);
	float benefit = (float) (((double) benefits / 100) * sum);

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
	//	res[i] = sum + ((int) Math.round(((double) bonus / 100) * bonusMonths * sum)) + "";
	    res[i] = sum + ((float)(((double) bonus / 100) * bonusMonths * sum)) + "";
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

}
