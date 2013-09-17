package info.hexin.json.serialize.impl;

import info.hexin.json.JsonConfig;
import info.hexin.json.serialize.JsonSerialize;
import info.hexin.json.serialize.StringWrite;

public class ArrayIntSerialize implements JsonSerialize {
    public static ArrayIntSerialize instance = new ArrayIntSerialize();

    @Override
    public void render(Object object, StringWrite write, JsonConfig jsonConfig) {
        int[] array = (int[]) object;
        write.append(array);
    }
}
