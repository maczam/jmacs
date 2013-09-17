package info.hexin.json.serialize.impl;

import info.hexin.json.JsonConfig;
import info.hexin.json.serialize.JsonSerialize;
import info.hexin.json.serialize.StringWrite;

public class ArraySerialize implements JsonSerialize {
    public static ArraySerialize instance = new ArraySerialize();

    @Override
    public void render(Object object, StringWrite write, JsonConfig jsonConfig) {
        Object[] array = (Object[]) object;
        write.append('[');
        for (int i = 0, k = 0; i < array.length; i++) {
            Object o = array[i];
            if (o == null) {
                continue;
            }
            if (k++ > 0) {
                write.append(',');
            }
            JsonSerialize render = jsonConfig.getSerialize(o.getClass());
            render.render(o, write, jsonConfig);
        }
        write.append(']');
    }
}
