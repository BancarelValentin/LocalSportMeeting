package com.iutclermont.lpmobile.localsportmeeting.backend.Metier;

import java.util.Calendar;
import java.util.Comparator;
import java.util.GregorianCalendar;

/**
 * Created by Thomas on 01/12/2014.
 */
public class MyDate implements Comparable<Calendar> {

    private int year;
    private int month;
    private int day;
    private int hour;
    private int min;

    public MyDate() {
    }

    public MyDate(int year, int month, int day, int hour, int min) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.min = min;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public String dateToString() {
        return year+"-"+String.format("%02d", month)+"-"+String.format("%02d", day);
    }
    public String heureToString() {
        return String.format("%02d", hour)+":"+String.format("%02d", min);
    }
    
    @Override
    public int compareTo(Calendar dateDuJour) {
        if (this.getYear() != dateDuJour.get(Calendar.YEAR))
            return this.getYear() - dateDuJour.get(Calendar.YEAR);
        else
        {
            if (this.getMonth() != dateDuJour.get(Calendar.MONTH))
                return this.getMonth() -dateDuJour.get(Calendar.MONTH);
            else
                return  this.getDay() - dateDuJour.get(Calendar.DAY_OF_MONTH);
        }
    }
}
