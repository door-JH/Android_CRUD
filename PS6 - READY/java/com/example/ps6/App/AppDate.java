package com.example.ps6.App;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AppDate {

    public AppDate() {
    }

    public long getDDay(String begin, String end) {

        long diffDays = 0;
        Date beginDate, endDate;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            beginDate = formatter.parse(begin);
            endDate = formatter.parse(end);

            long diff = endDate.getTime() - beginDate.getTime();
            diffDays = diff / (24 * 60 * 60 * 1000);
        } catch (ParseException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }

        return diffDays;
    }

    public String getTodayString() {

        //timestamp "yyyy-MM-dd"
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
        Date currentTime = new Date();//Calendar currentDate = Calendar.getInstance();
        String sDay = formatter.format(currentTime);

        return sDay;
    }

    public Date getTodayDate() {

        //timestamp "yyyy-MM-dd HH:mm:ss"
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
        Date currentTime = new Date();//Calendar currentDate = Calendar.getInstance();
        String sTime = formatter.format(currentTime);
        Date dDay = null;
        try {
            dDay = formatter.parse(sTime);
        } catch (ParseException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }

        return dDay;
    }

    public String getTodayTimeString() {

        //timestamp "yyyy-MM-dd HH:mm:ss"
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
        Date currentTime = new Date();//Calendar currentDate = Calendar.getInstance();
        String sDayTime = formatter.format(currentTime);

        return sDayTime;
    }

    public Date getTodayDayTimeDate() {

        //timestamp "yyyy-MM-dd HH:mm:ss"
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
        Date currentTime = new Date();//Calendar currentDate = Calendar.getInstance();
        String sTime = formatter.format(currentTime);
        Date dDayTime = null;
        try {
            dDayTime = formatter.parse(sTime);
        } catch (ParseException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }

        return dDayTime;
    }

    public String getDate2String(Date date) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
        String sDay = formatter.format(date);

        return sDay;
    }

    public String getDateTime2String(Date date) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
        String sDay = formatter.format(date);

        return sDay;
    }

}
