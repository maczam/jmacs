package info.hexin.json.serialize.impl;

import info.hexin.json.JsonConfig;
import info.hexin.json.serialize.JsonSerialize;
import info.hexin.json.serialize.StringWrite;

import java.util.Date;

public class DateSerialize implements JsonSerialize {
    public static DateSerialize instance = new DateSerialize();

    @Override
    public void render(Object object, StringWrite write, JsonConfig jsonConfig) {
        Date date = (Date) object;
        String time = jsonConfig.sdf.format(date);
        write.appendFiledNameWithQuotation(time);
    }
}
