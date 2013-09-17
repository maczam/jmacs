package info.hexin.json.parser;


/**
 * json解析父类接口
 * 
 * @author hexin
 * 
 */
public interface JsonParser {
    
    <T> T parsing(JSONTokener tokener);
}
