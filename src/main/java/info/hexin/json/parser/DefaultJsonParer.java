package info.hexin.json.parser;

/**
 * 构造默认json解析入口
 * 
 * @author hexin
 * 
 */
public class DefaultJsonParer {
    private JSONTokener jsonTokener;

    public DefaultJsonParer(JSONTokener jsonTokener) {
        this.jsonTokener = jsonTokener;
    }

    public <T> T parser() {
        char char1 = jsonTokener.next();
        JsonParser jsonParser = JsonParserConfig.getParser(char1);
        jsonTokener.back();
        return jsonParser.parsing(jsonTokener);
    }
}
