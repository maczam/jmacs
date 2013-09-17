package info.hexin.json.serialize.impl;

import info.hexin.json.JsonConfig;
import info.hexin.json.serialize.JsonSerialize;
import info.hexin.json.serialize.StringWrite;

import java.util.List;

public class ListSerialize implements JsonSerialize {

    public static final ListSerialize instance = new ListSerialize();;

    @Override
    public void render(Object object, StringWrite write,JsonConfig jsonConfig) {
        List<?> list = (List<?>) object;
        write.append('[');
        for (int i = 0,k = 0; i < list.size(); i++) {
            Object o = list.get(i);
            if (o == null) {
                continue;
            }
            if (k++ > 0) {
                write.append(',');
            }
            JsonSerialize render = jsonConfig.getSerialize(o.getClass());
            render.render(o, write,jsonConfig);
        }
        write.append(']');
    }
}
