package info.hexin.json.parser.impl;

import info.hexin.json.parser.JSONTokener;
import info.hexin.json.parser.JsonParser;
import info.hexin.json.parser.JsonParserConfig;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

public class MapParser implements JsonParser {
    public static MapParser instance = new MapParser();

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> parsing(JSONTokener tokener) {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            while (true) {
                // 由于前面存在{ nextValu 是可以忽略引号 key
                tokener.next();
                Object key = tokener.nextValue();

                // 去掉 ":"
                tokener.next();
                char tmpTokener = tokener.nextClean();
                JsonParser tmpJsonParser = JsonParserConfig.getParser(tmpTokener);
                Object value = null;
                tokener.back();
                if (tmpJsonParser != null) {
                    value = tmpJsonParser.parsing(tokener);
                } else {
                    value = tokener.nextValue();
                }

                map.put(key.toString(), value);
                switch (tokener.nextClean()) {
                case ',':
                    tokener.back();
                    if (tokener.nextClean() == '}') {
                        return map;
                    }
                    tokener.back();
                    break;
                case '}':
                default:
                    return map;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
