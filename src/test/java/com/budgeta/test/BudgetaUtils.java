package com.budgeta.test;

import java.util.ArrayList;
import java.util.List;

public class BudgetaUtils {

	static final String[] Month = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
	
	public static List<String> getAllMonthsBetweenTwoMonths(String monthX, String yearX, String monthY, String yearY){
		int indexX = 0;
		for(int i = 0 ; i < Month.length ; i++){
			if(Month[i].equalsIgnoreCase(monthX.trim())){
				indexX = i;
				break;
			}
		}
		List<String> res = new ArrayList<String>();
		while(!(Month[indexX].equalsIgnoreCase(monthY.trim()) && yearX.trim().equalsIgnoreCase(yearY.trim()))){
			res.add(Month[indexX]+" "+yearX.trim());
			indexX++;
			if(indexX >= Month.length){
				indexX = 0;
				yearX = (Integer.parseInt(yearX.trim())+1)+"";
			}
		}
		res.add(Month[indexX]+" "+yearX.trim());
		return res;
	}
	
	
	public static String getNextMonth(String month){
		for(int i = 0 ; i < Month.length ; i++){
			if(Month[i].equals(month)){
				if(i == 11)
					return Month[0];
				return Month[i+1];
			}
		}
		return "";
	}
	
	public static String getPreviousMonth(String month){
		for(int i = 0 ; i < Month.length ; i++){
			if(Month[i].equals(month)){
				if(i == 0)
					return Month[11];
				return Month[i-1];
			}
		}
		return "";
	}
	
	public static int getIndexOfMonth(String month){
		for(int i = 0 ; i < Month.length ; i++){
			if(Month[i].equals(month))
				return i;
		}
		return -1;
	}
	
	public static String getMonthWithIndex(int index){
		return Month[index-1];
	}
	
	public static List<String> getBeginningOfQuaterlyMonths(){
		List<String> beginningOfQuaterly = new ArrayList<>();
		beginningOfQuaterly.add("Feb");
		beginningOfQuaterly.add("May");
		beginningOfQuaterly.add("Aug");
		beginningOfQuaterly.add("Nov");
		return beginningOfQuaterly;
	}
	
	public static List<String> getEndOfQuaterlyMonths(){
		List<String> endOfQuaterly = new ArrayList<>();
		endOfQuaterly.add("Jan");
		endOfQuaterly.add("Apr");
		endOfQuaterly.add("Jul");
		endOfQuaterly.add("Oct");
		return endOfQuaterly;
	}
	
	public static String[] calculateValues_Once(String fromMonth, String fromYear, String toMonth, String toYear, String onDateMonth, String onDateYear, int amount, int payAfter, int supportPercent, int supportPeriod){
		List<String> months = getAllMonthsBetweenTwoMonths(fromMonth,fromYear,toMonth,toYear);
		String[] res = new String[months.size()];
		int support = (int) Math.round(((double)supportPercent/100) * amount);
		int period = supportPeriod;
		int startIndex = 0;
		if(!onDateMonth.isEmpty()){
			startIndex = getAllMonthsBetweenTwoMonths(fromMonth, fromYear, onDateMonth, onDateYear).size() - 1;
		}
		startIndex += payAfter;
		for(int i = 0; i < startIndex; i++){
			res[i] = "-";
		}
		res[startIndex] = amount + support+"";
		for(int i = startIndex + 1 ; i < res.length; i++){
			period--;
			if(period == 0){
				res[i] = support+"";
				period = supportPeriod;
			}
			else{
				res[i] = "-";
			}
		}
		return res;
	}
	
	public static String[] calculateValues_Monthly(String fromMonth, String fromYear, String toMonth, String toYear, int amount, int payAfter, int supportPercent, int supportPeriod, int growth){
		List<String> months = getAllMonthsBetweenTwoMonths(fromMonth,fromYear,toMonth,toYear);
		String[] res = new String[months.size()];
		int support = (int) Math.round(((double)supportPercent/100) * amount);
		int period = supportPeriod;
		int startIndex = payAfter;
		int sum = amount + support;
		for(int i = 0; i < startIndex; i++){
			res[i] = "-";
		}
		res[startIndex] = sum+"";
		for(int i = startIndex + 1 ; i < res.length; i++){
			period--;
			if(period == 0){
				sum = sum + support + (int) Math.round(((double)growth/100) * sum);
				period = supportPeriod;
			}
			else{
				sum = sum + (int) Math.round(((double)growth/100) * sum);
			}
			res[i] = sum + "";
		}
		return res;
	}
	
	
	public static String[] calculateValues_Quaterly(String fromMonth, String fromYear, String toMonth, String toYear, int amount, int payAfter, int growth,String AtDate){
		List<String> months = getAllMonthsBetweenTwoMonths(fromMonth,fromYear,toMonth,toYear);
		String[] res = new String[months.size()];
		List<String> quaterly = new ArrayList<>();
		
		int startIndex = payAfter;
		int sum = amount;
		for(int i = 0; i < startIndex; i++){
			res[i] = "-";
		}
		if(AtDate.equals("spread evenly")){
			sum = sum/3;
			growth = growth/3;
			for(int i =startIndex; i<res.length; i++){
				sum = sum + (int) Math.round(((double)growth/100) * sum);
				res[i] = sum + "";
			}
		}
		else{
			int quater = 3;
			if(AtDate.equals("at the beginning"))
				quaterly = getBeginningOfQuaterlyMonths();	
			if(AtDate.equals("at the end"))
				quaterly = getEndOfQuaterlyMonths();
			while(!quaterly.contains(months.get(startIndex).split(" ")[0].trim())){
				res[startIndex] = "-";
				startIndex++;
			}
			sum = sum + (int) Math.round(((double)growth/100) * sum);
			res[startIndex] = sum + "";
			for(int i = startIndex + 1 ; i<res.length; i++){
				quater--;
				if(quater == 0){
					sum = sum + (int) Math.round(((double)growth/100) * sum);
					res[i] = sum + "";
					quater = 3;
				}
				else{
					res[i] = "-";
				}
			}
		}
		return res;
	}
	
	
	public static String[] calculateValues_Yearly(String fromMonth, String fromYear, String toMonth, String toYear, int amount, int payAfter, int growth,String AtDate,String fiscal){
		List<String> months = getAllMonthsBetweenTwoMonths(fromMonth,fromYear,toMonth,toYear);
		String[] res = new String[months.size()];
		String startMonth="";
		int startIndex = payAfter;
		int sum = amount;
		for(int i = 0; i < startIndex; i++){
			res[i] = "-";
		}
		if(AtDate.equals("spread evenly")){
			sum = sum/12;
			growth = growth/12;
			for(int i = startIndex; i<res.length; i++){
				sum = sum + (int) Math.round(((double)growth/100) * sum);
				res[i] = sum + "";
			}
		}
		else{
			int yearly = 12;
			if(AtDate.equals("at the beginning"))
				startMonth = fiscal;
			if(AtDate.equals("at the end"))
				startMonth = getPreviousMonth(fiscal);
			while(!months.get(startIndex).contains(startMonth)){
				res[startIndex] = "-";
				startIndex++;
			}
			sum = sum + (int) Math.round(((double)growth/100) * sum);
			res[startIndex] = sum + "";
			for(int i = startIndex + 1 ; i<res.length; i++){
				yearly--;
				if(yearly == 0){
					sum = sum + (int) Math.round(((double)growth/100) * sum);
					res[i] = sum + "";
					yearly = 12;
				}
				else{
					res[i] = "-";
				}
			}
		}
		return res;
	}
}
