package info.hexin.jmacs.aop.proxy.asm;

import info.hexin.jmacs.aop.proxy.AopProxy;
import info.hexin.jmacs.asm.ClassReader;
import info.hexin.jmacs.asm.ClassWriter;
import info.hexin.lang.Exceptions;
import info.hexin.lang.classloader.JmacsClassLoader;

import java.io.IOException;

/**
 * 代理对象的class转化。
 * 
 * @author hexin
 * 
 */
public class AsmAopProxy implements AopProxy {

    private JmacsClassLoader classLoader = new JmacsClassLoader(AsmAopProxy.class.getClassLoader());

    public Class<?> getProxy(Class<?> type) {
        try {
            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
            ClassReader cr = new ClassReader(type.getName());
            cr.accept(new ClassAdapter(type, cw), 0);
            byte[] bytes = cw.toByteArray();
            return classLoader.defineClassFromClassFile(type.getName() + "$_aop", bytes);
        } catch (IOException e) {
            throw Exceptions.make(e);
        }
    }
}
