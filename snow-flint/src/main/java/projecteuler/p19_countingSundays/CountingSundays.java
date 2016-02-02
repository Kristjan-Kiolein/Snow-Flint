package projecteuler.p19_countingSundays;

import java.util.Arrays;
import java.util.List;

/**
 * <h1>Counting Sundays</h1>
 * <br>
 * You are given the following information, but you may prefer to do some research for yourself.<br>
 * <br>
 * 1 Jan 1900 was a Monday.<br>
 * Thirty days has September,<br>
 * April, June and November.<br>
 * All the rest have thirty-one,<br>
 * Saving February alone,<br>
 * Which has twenty-eight, rain or shine.<br>
 * And on leap years, twenty-nine.<br>
 * A leap year occurs on any year evenly divisible by 4, but not on a century unless it is divisible by 400.<br>
 * <br>
 * <i>How many Sundays fell on the first of the month during the twentieth century (1 Jan 1901 to 31 Dec 2000)?</i><br>
 */
public class CountingSundays {

	private static final int MONTHS_IN_YEAR = 12;                     //jan feb mar apr may jun jul aug sep oct nov dec
	private static final List<Integer> DAYS_IN_MONTH = Arrays.asList(-1, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
	private static final int FROM_YEAR = 1901;
	private static final int TO_YEAR = 2000;
	
	
	public static void main(String[] args) {
		int day = 1;
		int month = 1;
		int year = FROM_YEAR;
		int sundayCount = solve(day, month, year);
		System.out.println("Sundays on first of month : " + sundayCount);
	}

	private static int solve(int day, int month, int year) {

		int sundayCount = 0;
		int daysPast = 1;
		int currentDay = day;
		int currentMonth = month;
		int currentYear = year;
		
		while(currentYear < TO_YEAR) {
			
			if(daysPast % 7 == 0 && currentDay == 1) {
				sundayCount++;
			}
			
			currentDay++;
			daysPast++;
			
			if(currentDay > getDaysInMonth(currentMonth, currentYear)) {
				currentDay = 1;
				currentMonth++;
				if(currentMonth > MONTHS_IN_YEAR) {
					currentMonth = 1;
					currentYear++;
				}
			}
		}
		
		return sundayCount;
	}
	
	
	
	private static int getDaysInMonth(int month, int year) {
		if(month == 2 && isLeapYear(year)) return DAYS_IN_MONTH.get(month) + 1;
		return DAYS_IN_MONTH.get(month);
		
	}
	
	private static boolean isLeapYear(int year) {
		return year % 100 == 0 ? year % 400 == 0 : year % 4 == 0;
	}
}
