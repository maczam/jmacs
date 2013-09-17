package info.hexin.json.parser.impl;

import info.hexin.jmacs.log.Log;
import info.hexin.jmacs.log.Logs;
import info.hexin.json.parser.JSONTokener;
import info.hexin.json.parser.JsonParser;

import java.text.ParseException;

public class StringParser implements JsonParser {
    public static StringParser instance = new StringParser();
    Log log = Logs.get(); 

    @SuppressWarnings("unchecked")
    @Override
    public String parsing(JSONTokener tokener) {
        try {
//            log.info("tokener >>>> " +tokener);
            Object o = tokener.nextValue();
            return o.toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
