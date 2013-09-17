package info.hexin.json.transform.impl;

import info.hexin.json.JsonConfig;
import info.hexin.json.transform.JsonTransform;

public class CharTransform implements JsonTransform<Character> {

    public static final CharTransform instance = new CharTransform();

    @Override
    public Character transform(Object value, Class<Character> clazz, JsonConfig config) {
        String valueString = value.toString();
        return valueString.charAt(0);
    }
}