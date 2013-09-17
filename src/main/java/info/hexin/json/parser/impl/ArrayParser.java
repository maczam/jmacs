package info.hexin.json.parser.impl;

import info.hexin.json.parser.JSONTokener;
import info.hexin.json.parser.JsonParser;
import info.hexin.json.parser.JsonParserConfig;
import info.hexin.lang.Exceptions;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author hexin
 * 
 */
public class ArrayParser implements JsonParser {

    public static ArrayParser instance = new ArrayParser();

    @SuppressWarnings("unchecked")
    @Override
    public List<Object> parsing(JSONTokener tokener) {
        List<Object> list = new ArrayList<Object>();
        tokener.next();
        while (true) {
            try {
                char tmpTokener = tokener.nextClean();
                JsonParser tmpJsonParser = JsonParserConfig.getParser(tmpTokener);
                Object value = null;
                tokener.back();
                if (tmpJsonParser != null) {
                    value = tmpJsonParser.parsing(tokener);
                    list.add(value);
                } else {
                    value = tokener.nextValue();
                    if (value != null) {
                        list.add(value);
                    }
                }
            } catch (ParseException e) {
                throw Exceptions.make(e);
            }

            switch (tokener.nextClean()) {
            case ',':
                if (tokener.nextClean() == ']') {
                    return list;
                }
                tokener.back();
                break;
            case ']':
            default:
                return list;
            }
        }
    }
}
