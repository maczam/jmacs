package info.hexin.json.serialize.impl;

import info.hexin.json.JsonConfig;
import info.hexin.json.serialize.JsonSerialize;
import info.hexin.json.serialize.StringWrite;

public class BooleanSerialize implements JsonSerialize {
    public static BooleanSerialize instance = new BooleanSerialize();

    @Override
    public void render(Object object, StringWrite write, JsonConfig jsonConfig) {
        Boolean boolean1 = Boolean.parseBoolean(object.toString());
        write.appendString(boolean1.toString());
    }
}
