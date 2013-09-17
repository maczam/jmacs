package info.hexin.json.serialize.impl;

import info.hexin.json.JsonConfig;
import info.hexin.json.serialize.JsonSerialize;
import info.hexin.json.serialize.StringWrite;

import java.util.Set;

public class SetSerialize implements JsonSerialize {
    public static SetSerialize instance = new SetSerialize();

    @Override
    public void render(Object object, StringWrite write, JsonConfig jsonConfig) {
        write.append('[');
        Set<?> set = (Set<?>) object;
        int i = 0;
        int sum = set.size();
        for (Object value : set) {
            JsonSerialize jsonRender = jsonConfig.getSerialize(value.getClass());
            jsonRender.render(value, write, jsonConfig);
            if (++i < sum) {
                write.append(',');
            }
        }
        write.append(']');
    }
}
