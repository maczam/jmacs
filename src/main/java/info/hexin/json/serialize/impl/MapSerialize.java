package info.hexin.json.serialize.impl;

import info.hexin.json.JsonConfig;
import info.hexin.json.serialize.JsonSerialize;
import info.hexin.json.serialize.StringWrite;

import java.util.Map;

/**
 * 解析map
 * 
 * @author hexin
 * 
 */
public class MapSerialize implements JsonSerialize {
    public static MapSerialize instance = new MapSerialize();

    @Override
    public void render(Object object, StringWrite write, JsonConfig jsonConfig) {
        write.append('{');
        Map<?, ?> map = (Map<?, ?>) object;
        int i = 0;
        int sum = map.size();
        for (Object key : map.keySet()) {
            String keyString = (String) key;
            write.appendFiledNameWithQuotation(keyString);
            write.append(':');
            JsonSerialize jsonRender = jsonConfig.getSerialize(map.get(key).getClass());
            jsonRender.render(map.get(key), write, jsonConfig);
            if (++i < sum) {
                write.append(',');
            }
        }
        write.append('}');
    }
}
