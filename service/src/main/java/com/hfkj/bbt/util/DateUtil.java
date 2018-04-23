package com.hfkj.bbt.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2017-07-04.
 */
public class DateUtil {
    private static final Logger LOG= LoggerFactory.getLogger(DateUtil.class);
    private DateUtil(){

    }


    public static Timestamp tranTimestamp(String format,String dateStr)  {
        SimpleDateFormat sdf=new SimpleDateFormat(format);
        Date date=new Date();
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            LOG.error(e.getMessage());
        }
        return new Timestamp(date.getTime());
    }

    public static String tranDateToString(String format,Date date){
        if (date!=null){
            SimpleDateFormat sdf=new SimpleDateFormat(format);
            return sdf.format(date);
        }else {
            return "";
        }
    }

    public static String getCurrentWeekDay(){
        Calendar calendar=Calendar.getInstance();
        int i = calendar.get(Calendar.DAY_OF_WEEK)-1;

        return String.valueOf(0+""+i);
    }


    public static Date tranStringToDate(String format,String dateStr){
        SimpleDateFormat sdf=new SimpleDateFormat(format);
        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            LOG.error("日期转换错误!");
        }
        return null;
    }

}
