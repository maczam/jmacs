package info.hexin.json.parser;

import info.hexin.json.parser.impl.ArrayParser;
import info.hexin.json.parser.impl.MapParser;
import info.hexin.json.parser.impl.StringParser;

import java.util.HashMap;
import java.util.Map;

/**
 * 配置反序列化
 * 
 * @author hexin
 * 
 */
public class JsonParserConfig {
    private static final char MapStart = '{';
    private static final char ListStart = '[';
    private static final char String1 = '"';
    private static final char String2 = '\'';
    private static Map<Character, JsonParser> jsonRenderMap = new HashMap<Character, JsonParser>();
    static {
        jsonRenderMap.put(MapStart, MapParser.instance);
        jsonRenderMap.put(ListStart, ArrayParser.instance);
        jsonRenderMap.put(String1, StringParser.instance);
        jsonRenderMap.put(String2, StringParser.instance);
    }

    public static JsonParser getParser(Character character) {
        return jsonRenderMap.get(character);
    }
}
