package org.sl.util.converter;

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * Created by hasee on 2017/8/8.
 * 定义日期类型转换器
 */
public class DateConverter implements Converter<String,Date> {
    /**
     * 转换日期
     * @param source 日期字符串
     * @return 日期对象
     */
    public Date convert(String source) {
        SimpleDateFormat format=getFormat(source);
        Date date=null;
        if (format!=null){
            try {
                date = format.parse(source);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return date;
    }
    /**
     * 定义具体可以转换的类型
     * @param source 日期字符串
     * @return 返回日期转换对象
     */
    private SimpleDateFormat getFormat(String source) {
        SimpleDateFormat format=null;
        if (Pattern.matches("\\d{4}/\\d{2}/\\d{2}",source)){
            format=new SimpleDateFormat("yyyy/MM/dd");
        }else if (Pattern.matches("\\d{4}-\\d{2}-\\d{2}",source)){
            format=new SimpleDateFormat("yyyy-MM-dd");
        }else if (Pattern.matches("\\d{4}\\d{2}\\d{2}",source)){
            format=new SimpleDateFormat("yyyyMMdd");
        }else if (Pattern.matches("\\d{2}/\\d{2}/\\d{4}",source)){
            format=new SimpleDateFormat("MM/dd/yyyy");
        }else if (Pattern.matches("\\d{2}-\\d{2}-\\d{4}",source)){
            format=new SimpleDateFormat("MM-dd-yyyy");
        }
        return format;
    }
}
