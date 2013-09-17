package info.hexin.json.serialize.impl;

import info.hexin.json.JsonConfig;
import info.hexin.json.serialize.JsonSerialize;
import info.hexin.json.serialize.StringWrite;

public class StringSerialize implements JsonSerialize {
    public static StringSerialize instance = new StringSerialize();

    @Override
    public void render(Object object, StringWrite write, JsonConfig jsonConfig) {
        String tmp = object.toString();
        if (null != tmp) {
            write.appendFiledNameWithQuotation(object.toString());
        }
    }
}
