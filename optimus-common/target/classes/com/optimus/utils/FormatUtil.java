package com.optimus.utils;

import com.optimus.common.exception.BizException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 格式化工具类
 * @author caoawei
 * Created on 2017/12/5.
 */
public class FormatUtil {

    private static final int PHONE_LENGTH = 11;
    private static final int ID_NUMBER_LEN = 18;

    private static final String PHONE_REGEX = "\\d{11}";
    private static final String PHONE_FORMAT_REGEX = "(\\d{3})\\d{4}(\\d{4})";
    private static final String ID_REGEX = "^[0-9]{17}(x|\\d)$";
    private static final String ID_FORMAT_REGEX = "(\\d{6})\\d{8}(\\d{4})";

    private static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";

    public static String format(Date date){
        if(Utils.isNull(date)){
            return "";
        }
        DateFormat df = new SimpleDateFormat(DEFAULT_DATE_PATTERN);
        return df.format(date);
    }

    public static String format(Date date,String pattern){
        if(Utils.isNull(date)){
            return "";
        }
        if(Utils.isEmpty(pattern)){
            return format(date);
        }
        DateFormat df = new SimpleDateFormat(pattern);
        return df.format(date);
    }

    public static Date parseDate(String input){
        if(Utils.isEmpty(input)){
            return null;
        }

        DateFormat df = new SimpleDateFormat(DEFAULT_DATE_PATTERN);
        try {
            return df.parse(input);
        }catch (Exception e){
            throw new BizException(e);
        }
    }

    public static Date parseDate(String input,String pattern){
        if(Utils.isEmpty(input)){
            return null;
        }
        if(Utils.isEmpty(pattern)){
            return parseDate(input);
        }

        DateFormat df = new SimpleDateFormat(pattern);
        try {
            return df.parse(input);
        }catch (Exception e){
            throw new BizException(e);
        }
    }
}
