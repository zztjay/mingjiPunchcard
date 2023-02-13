package com.tencent.wxcloudrun.util;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.*;

import static java.util.Calendar.DAY_OF_MONTH;

/**
 * @author 毕空
 * @email xianyu.hxy@alibaba-inc.com
 * 2019/1/7
 */
public class DateUtil {

    public static final String DATE_FORMAT_PATTERN = "yyyyMMdd";

    /**
     * 时间相差天数
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static Long subDate(Date startDate, Date endDate) {
        return startDate != null && endDate != null ? (endDate.getTime() - startDate.getTime()) / 86400000L : null;
    }

    public static String getNow() {
        return getDate2Str(new Date());
    }

    public static String getToday() {
        return DateTime.now().toString(DATE_FORMAT_PATTERN);
    }

    public static String getToday(String pattern) {
        return DateTime.now().toString(pattern);
    }

    public static String getYear() {
        return String.valueOf(DateTime.now().getYear());
    }

    public static String getYesterday() {
        return DateTime.now().minusDays(1).toString(DATE_FORMAT_PATTERN);
    }
    /**
     * 注意! 一般适用此方法都是tair调用 tair里面写0代表永久缓存，这里如果是0返回1
     * 当日剩余秒
     *
     * @return
     */
    public static int secendsLeftToday() {
        Date now = new Date();
        int secendsLeft = (int) (dayEnd(now).getTime() - now.getTime()) / (1000);
        return secendsLeft > 0 ? secendsLeft : 1;
    }

    /**
     * 当天准确剩余秒数
     *
     * @return
     */
    public static int secendsLeftTodayPrecise() {
        Date now = new Date();
        return (int) (dayEnd(now).getTime() - now.getTime()) / (1000);
    }

    /**
     * 取得某日期时间的特定表示格式的字符串
     *
     * @param format 时间格式
     * @param date   某日期（Date）
     * @return 某日期的字符串
     */
    public static String getDate2Str(String format, Date date) {
        if (date == null) {
            return "";
        }

        return SimpleDateFormatHolder.instance.getFormat(format).format(date);
    }

    /**
     * 将特定格式的时间字符串转化为Date类型
     *
     * @param format 时间格式
     * @param str    某日期的字符串
     * @return 某日期（Date）
     */
    public static Date getStrToDate(String format, String str) {
        ParsePosition parseposition = new ParsePosition(0);
        return SimpleDateFormatHolder.instance.getFormat(format).parse(str, parseposition);
    }

    /**
     * 将日期转换为长字符串（包含：年-月-日 时:分:秒）
     *
     * @param date 日期
     * @return 返回型如：yyyy-MM-dd HH:mm:ss 的字符串
     */
    public static String getDate2Str(Date date) {
        return getDate2Str("yyyy-MM-dd HH:mm:ss", date);
    }

    public static Date asDate(java.time.LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
    public static String getDate2ymdStr(Date date) {
        if (date == null) {
            return "";
        }
        return getDate2Str("yyyy-MM-dd", date);
    }

    public static String getDate2yymmddStr(Date date) {
        if (date == null) {
            return "";
        }
        return getDate2Str("yyyyMMdd", date);
    }

    /**
     * 将某指定的字符串转换为型如：yyyy-MM-dd HH:mm:ss的时间
     *
     * @param str 将被转换为Date的字符串
     * @return 转换后的Date
     */
    public static Date getStr2SDate(String str) {
        return getStrToDate("yyyy-MM-dd HH:mm:ss", str);
    }

    /**
     * 将某指定的字符串转换为型如：yyyy-MM-dd的时间
     *
     * @param str 将被转换为Date的字符串
     * @return 转换后的Date
     */
    public static Date getymdStr2SDate(String str) {
        return getStrToDate("yyyy-MM-dd", str);
    }

    /**
     * 去掉时间部分
     *
     * @param date
     * @return
     */
    public static Date trunc(Date date) {
        String str = DateUtil.getDate2ymdStr(date);
        return DateUtil.getymdStr2SDate(str);
    }


    /**
     * 检测字符串是否为日期
     *
     * @return
     */
    private static boolean isDateTime(String dateTime) {
        String pattern = "yyyy-MM-dd HH:mm:ss";
        DateFormat df = new SimpleDateFormat(pattern);
        ParsePosition pos = new ParsePosition(0);
        Date dt = df.parse(dateTime, pos);

        if (dt == null) {
            return false;
        }

        return true;
    }

    /**
     * 添加几天
     *
     * @param date
     * @param days
     * @return
     */
    public static Date addDays(final Date date, int days) {
        return new Date(date.getTime() + days * 24 * 60 * 60 * 1000L);
    }

    /**
     * 添加几天
     *
     * @param date
     * @param days
     * @return
     */
    public static String addDays(final String date, int days) {
        Date date2Str = getStr2SDate(date);
        Date finalDate = new Date(date2Str.getTime() + days * 24 * 60 * 60 * 1000L);
        return getDate2Str(finalDate);
    }

    /**
     * 添加几天 格式yyyyMMdd
     *
     * @param date
     * @param days
     * @return
     */
    public static String addDays2yymmddStr(final Date date, int days) {
        Date finalDate = new Date(date.getTime() + days * 24 * 60 * 60 * 1000L);
        return getDate2yymmddStr(finalDate);
    }

    /**
     * 添加几天 到当天23:59:59
     *
     * @param date
     * @param days
     * @return
     */
    public static String addDaysToDayEnd(final String date, int days) {
        Date date2Str = getStr2SDate(date);
        Date finalDate = new Date(date2Str.getTime() + days * 24 * 60 * 60 * 1000L);
        Date dateEnd = dayEnd(finalDate);
        return getDate2Str(dateEnd);
    }

    /**
     * 添加多少秒
     *
     * @param date
     * @param seconds
     * @return
     */
    public static Date addSeconds(final Date date, Long seconds) {
        return new Date(date.getTime() + seconds * 1000L);
    }

    /**
     * 添加多少秒
     *
     * @param date
     * @param seconds
     * @return
     */
    public static String addSeconds(final String date, Long seconds) {
        Date date2Str = getStr2SDate(date);
        Date finalDate = new Date(date2Str.getTime() + seconds * 1000L);
        return getDate2Str(finalDate);
    }

    public static String descSeconds(final String date, Long seconds) {
        Date date2Str = getStr2SDate(date);
        Date finalDate = new Date(date2Str.getTime() - seconds * 1000L);
        return getDate2Str(finalDate);
    }

    /**
     * 添加多少秒 到当天23:59:59
     *
     * @param date
     * @param seconds
     * @return
     */
    public static String addSecondsToDayEnd(final String date, Long seconds) {
        Date date2Str = getStr2SDate(date);
        Date finalDate = new Date(date2Str.getTime() + seconds * 1000L);
        Date dateEnd = dayEnd(finalDate);
        return getDate2Str(dateEnd);
    }


    /**
     * 获取指定时间的那天 00:00:00.000 的时间
     *
     * @param date
     * @return
     */
    public static Date dayBegin(final Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 获取指定时间的那天 23:59:59.999 的时间
     *
     * @param date
     * @return
     */
    public static Date dayEnd(final Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * SimpleDateFormat持有，线程安全.<br/>
     * keyu by 2015-07-01
     */
    private static class SimpleDateFormatHolder {

        /**
         * 默认日期模板格式
         */
        private static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";

        /**
         * 锁对象
         */
        private static Object lockObj = new Object();

        /**
         * 单例对象
         */
        public static SimpleDateFormatHolder instance = new SimpleDateFormatHolder();

        /**
         * 存放不同的日期模板格式的SimpleDateFormat的Map
         */
        private static Map<String, ThreadLocal<SimpleDateFormat>> sdfMap = new HashMap<String, ThreadLocal<SimpleDateFormat>>();

        public SimpleDateFormat getFormat(final String pattern) {
            String p = pattern;
            if (StringUtils.isBlank(p)) {
                p = DEFAULT_FORMAT;
            }

            ThreadLocal<SimpleDateFormat> locals = sdfMap.get(p);
            if (locals == null) {
                synchronized (lockObj) {
                    locals = sdfMap.get(pattern);
                    if (locals == null) {
                        locals = new ThreadLocal<SimpleDateFormat>() {
                            @Override
                            protected SimpleDateFormat initialValue() {
                                return new SimpleDateFormat(pattern);
                            }
                        };
                        sdfMap.put(pattern, locals);
                    }
                }
            }
            return locals.get();
        }
    }

    /**
     * Java将Unix时间戳转换成指定格式日期字符串
     * @param timestampString 时间戳 如："1473048265";
     * @param formats 要格式化的格式 默认："yyyy-MM-dd HH:mm:ss";
     *
     * @return 返回结果 如："2016-09-05 16:06:42";
     */
    public static String TimeStamp2Date(String timestampString, String formats) {
        if (StringUtils.isEmpty(formats)) {
            formats = "yyyy-MM-dd HH:mm:ss";
        }
        Long timestamp = Long.parseLong(timestampString) * 1000;
        return new SimpleDateFormat(formats, Locale.CHINA).format(new Date(timestamp));
    }

    /**
     * 计算两个date跨越的天数 相同天的时间点算1.0天 20190903 23:00:00 -- 20190904 01:00:00 跨越2.0天
     * @param start 开始时间
     * @param end 开始时间
     * @return
     */
    public static long getBetweenDays(Date start, Date end) {

        if (end.before(start)) {
            return 0;
        }

        Date startDateTrunk = DateUtils.truncate(start, DAY_OF_MONTH);
        Date endTrunk = DateUtils.truncate(end, DAY_OF_MONTH);
        long dayDiff = (endTrunk.getTime() - startDateTrunk.getTime())/86400000L;
        return dayDiff + 1;
    }

    public static List<String> getThisWeekDate(String date, String format) {

        LocalDateTime localDateTime = LocalDateTime.parse(date, DateTimeFormat.forPattern(format));

        int dayOfWeek = localDateTime.getDayOfWeek();

        LocalDateTime thisWeekStart = localDateTime.minusDays(dayOfWeek - 1);

        List<String> result = Lists.newArrayList();

        for (int i = 0; i < 7; i++) {
            result.add(thisWeekStart.plusDays(i).toString(format));
        }
        return result;
    }
}




