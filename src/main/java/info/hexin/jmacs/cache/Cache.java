package info.hexin.jmacs.cache;

public interface Cache {

    String getName();

    Object getNativeCache();

    void put(Object key, Object value);

    void evict(Object key);

    void clear();
}
