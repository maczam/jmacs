package info.hexin.json.serialize;

import info.hexin.json.JsonConfig;

/**
 * JSON序列化实现类的超累，所有需要序列话对象都要实现此接口
 * 
 * @author hexin
 * 
 */
public interface JsonSerialize {



    /**
     * 描述object如何进行序列化，并将序列话后的char写入到StringWrite中
     * 
     * @param object
     * @param write
     */
    void render(Object object, StringWrite write,JsonConfig jsonConfig);
}
