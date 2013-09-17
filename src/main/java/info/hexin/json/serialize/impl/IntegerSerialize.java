package info.hexin.json.serialize.impl;

import info.hexin.json.JsonConfig;
import info.hexin.json.serialize.JsonSerialize;
import info.hexin.json.serialize.StringWrite;

public class IntegerSerialize implements JsonSerialize {
    public static IntegerSerialize instance = new IntegerSerialize();

    @Override
    public void render(Object object, StringWrite write, JsonConfig jsonConfig) {
        Number number = (Number) object;
        write.appendString(number.toString());
    }
}
