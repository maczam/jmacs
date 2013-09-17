package info.hexin.jmacs.asm;

import java.security.PrivilegedAction;

public class ASMClassLoader extends ClassLoader {

    /**
     * class文件存放的默认路径
     */
    private static java.security.ProtectionDomain PD;

    static {
        PD = (java.security.ProtectionDomain) java.security.AccessController
                .doPrivileged(new PrivilegedAction<Object>() {
                    public Object run() {
                        return ASMClassLoader.class.getProtectionDomain();
                    }
                });
    }

    public Class<?> defineClassPublic(String name, byte[] code) {
        return this.defineClass(name, code, 0, code.length);
    }

    public Class<?> defineClassPublic(String name, byte[] code, int off, int len) throws ClassFormatError {
        return defineClass(name, code, off, len, PD);
    }
}
