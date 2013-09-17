package info.hexin.lang.classloader;

/**
 * 类装载器
 * 
 * @author hexin
 * 
 */
public class JmacsClassLoader extends ClassLoader {

    public JmacsClassLoader() {
        super();
    }

    public JmacsClassLoader(ClassLoader parent) {
        super(parent);
    }

    public Class<?> defineClassFromClassFile(String className, byte[] classFile) throws ClassFormatError {
        return defineClass(className, classFile, 0, classFile.length);
    }
}
