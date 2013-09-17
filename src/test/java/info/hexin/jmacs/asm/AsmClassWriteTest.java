package info.hexin.jmacs.asm;

import static info.hexin.jmacs.asm.Opcodes.ACC_PUBLIC;

import info.hexin.jmacs.asm.ClassWriter;
import info.hexin.jmacs.asm.MethodVisitor;
import info.hexin.jmacs.asm.Opcodes;

import java.lang.reflect.Method;

import org.junit.Test;

public class AsmClassWriteTest {

    @Test
    public void testWriteClass() throws Exception {
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        cw.visit(Opcodes.V1_5, Opcodes.ACC_PUBLIC, "HelloWorld", null, "java/lang/Object", null);
        
        
        // 返回值描述(Ljava/lang/reflect/Type;)V
        // 抛出异常描述 new String[] { "java/io/IOException"}
        //构造函数
        MethodVisitor mw = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
        //visitCode visitEnd 两个方法基本上空格方法
        mw.visitCode();
        mw.visitVarInsn(Opcodes.ALOAD, 0);
        mw.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V");
        mw.visitInsn(Opcodes.RETURN);
        mw.visitMaxs(1, 1);
        mw.visitEnd();
        
        mw = cw.visitMethod(ACC_PUBLIC, "main", "()V", null, null);
        mw.visitCode();
        mw.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out","Ljava/io/PrintStream;");
        mw.visitLdcInsn("test12321312");
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println","(Ljava/lang/String;)V");
        mw.visitVarInsn(Opcodes.ALOAD, 0);
        mw.visitInsn(Opcodes.RETURN);
        //需要最后一个调用来这个方法的执行帧中局部变量和操作数栈的大小
        mw.visitMaxs(2, 1);
        mw.visitEnd();

        // 生成字节码形式的类
        byte[] code = cw.toByteArray();
        ASMClassLoader asmClassLoader = new ASMClassLoader();
        Class<?> class1 = asmClassLoader.defineClassPublic("HelloWorld", code);
        System.out.println(class1.getName());
        
        Object object = class1.newInstance();
        Method method =  class1.getMethod("main", null);
        Object o = method.invoke(object, null);
    }
}
