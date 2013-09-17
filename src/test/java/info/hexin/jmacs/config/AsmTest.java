package info.hexin.jmacs.config;

import info.hexin.jmacs.asm.AnnotationVisitor;
import info.hexin.jmacs.asm.ClassReader;
import info.hexin.jmacs.asm.ClassVisitor;
import info.hexin.jmacs.asm.MethodVisitor;
import info.hexin.jmacs.asm.Opcodes;
import info.hexin.jmacs.config.servlet.ActionTest;

import java.io.IOException;

import org.junit.Test;

public class AsmTest {
    @Test
    public void test() {
        Class<?> clazz = ActionTest.class;
        final String clazzName = clazz.getName();
        System.out.println("clazzName >>>> " + clazzName);

        ClassReader cr = null;
        try {
            cr = new ClassReader(clazzName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        cr.accept(new ClassVisitor(Opcodes.ASM4) {
            @Override
            public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
                System.out.println("visitAnnotation : desc >>>> " + desc);
                return super.visitAnnotation(desc, visible);
            }

            @Override
            public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
                System.out.println("   visitMethod : desc >>> " + desc + " signature >>> " + signature);
                MethodVisitor methodVisitor = super.visitMethod(access, name, desc, signature, exceptions);
                return methodVisitor;
            }

        }, Opcodes.ASM4);
    }
}
