package info.hexin.json.transform.impl;

import info.hexin.json.JsonConfig;
import info.hexin.json.transform.JsonTransform;

public class BooleanTransform implements JsonTransform<Boolean> {

    public static final BooleanTransform instance = new BooleanTransform();

    @Override
    public Boolean transform(Object value, Class<Boolean> clazz, JsonConfig config) {
        return Boolean.valueOf(value.toString());
    }
}
