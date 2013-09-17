package info.hexin.jmacs.log;

/**
 * log适配器。适配不通的log
 * 
 * @author hexin
 * 
 */
public interface LogAdapter {
    
    Log getLogger(String className);
}
