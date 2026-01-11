package cooc.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeFormatType {
    public static final String YM = "yyyyMMddHHmmss";
    public static final String YM_s = "yyyyMMddHHmm";
    public static final String Y_M = "yyyy-MM-dd-HH-mm-ss";
    public static final String Y_M_d_H_m_s = "yyyy_MM_dd_HH_mm_ss";
    public static final String DEFAULT = "yyyy-MM-dd HH:mm:ss";


    public static Date getAfterMinutesTime(long minutes){
        final long s = minutes * 60;
        return getAfterSecondTime(s);
    }

    public static Date getAfterSecondTime(long second){
        return getAfterSecondTime(new Date(),second);
    }

    public static Date getAfterSecondTime(Date src, long second){
        final long ms = second * 1000;
        final long current = src.getTime();
        final long afterTime = current + ms;
        return new Date(afterTime);
    }

    public static boolean checkIsNextHour(Calendar src){
        int h = src.get(Calendar.HOUR);
        int n = Calendar.getInstance().get(Calendar.HOUR);
        return h > n;
    }

    public static long getNextHourSeconds() {
        Calendar now = Calendar.getInstance();
        Calendar next = Calendar.getInstance();
        next.set(Calendar.HOUR, now.get(Calendar.HOUR) + 1);
        next.set(Calendar.MINUTE, 0);
        next.set(Calendar.MINUTE, 0);
        return getRangeSeconds(now,next);
    }

    public static long getRangeSeconds(Calendar src,Calendar dst){
        long sleep = dst.getTimeInMillis()/1000 - src.getTimeInMillis()/1000;
        return sleep;
    }


    public static Date transFormat(String date,String format){
        Date result = null;
        try {
            result = new SimpleDateFormat(format).parse(date);
        }catch (ParseException e){
            System.out.println("日期转换失败，传入：" + date + "，转换格式：" + format + "\n" + e);
        }
        return result;
    }

    public static String transFormat(Date date,String format){
        return new SimpleDateFormat(format).format(date);
    }


}
