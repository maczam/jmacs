package info.hexin.json.transform.impl;

import info.hexin.json.JsonConfig;
import info.hexin.json.transform.JsonTransform;
import info.hexin.lang.Exceptions;

import java.text.ParseException;
import java.util.Date;

/**
 * 日期转化 多种日志类型
 * 
 * @author hexin
 * 
 */
public class DateTransform implements JsonTransform<Date> {
    public static final DateTransform instance = new DateTransform();

    @Override
    public Date transform(Object value, Class<Date> clazz, JsonConfig config) {
        try {
            String valueString = value.toString();
            return config.sdf.parse(valueString);
        } catch (ParseException e) {
            throw Exceptions.make(e);
        }
    }
}
