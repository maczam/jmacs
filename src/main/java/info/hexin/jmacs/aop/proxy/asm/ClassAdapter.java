package info.hexin.jmacs.aop.proxy.asm;

import info.hexin.jmacs.aop.Aops;
import info.hexin.jmacs.aop.annotation.Point;
import info.hexin.jmacs.asm.ClassVisitor;
import info.hexin.jmacs.asm.ClassWriter;
import info.hexin.jmacs.asm.Label;
import info.hexin.jmacs.asm.MethodVisitor;
import info.hexin.jmacs.asm.Opcodes;
import info.hexin.jmacs.asm.Type;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 
 * @author hexin
 * 
 */
public class ClassAdapter extends ClassVisitor implements Opcodes {
    public static String AOP_PROXY_SUFFIX = "$_aop";
    public static String AOP_FIELD_NAME = "$$_aop_field_name";
    private Class<?> type;
    private String newClassName;
    private List<Method> aopmethods;
    private ClassWriter classWriter;

    public ClassAdapter(Class<?> type, ClassWriter cw) {
        super(ASM4, cw);
        this.classWriter = cw;
        this.type = type;
        this.aopmethods = Aops.getAopMethods(type);
    }

    /**
     * 类描述,为要描述的子类
     */
    @Override
    public void visit(final int version, final int access, final String name, final String signature,
            final String superName, final String[] interfaces) {
        this.newClassName = name + AOP_PROXY_SUFFIX;
        cv.visit(version, access, newClassName, signature, AsmHelper.toAsmCls(name), interfaces);
    }

    /**
     * 先不返回所有的方法， 在处理完之后统一调用
     */
    @Override
    public MethodVisitor visitMethod(final int access, final String name, final String desc, final String signature,
            final String[] exceptions) {
        return null;
    }

    @Override
    public void visitEnd() {
        constructor();

        // 获取所有方法，并重写(main方法 和 Object的方法除外)
        for (Method m : aopmethods) {

            Type[] argumentTypes = Type.getArgumentTypes(m);
            Type resultType = Type.getReturnType(m);
            String methodDesc = Type.getMethodDescriptor(m);
            MethodVisitor mv = classWriter.visitMethod(ACC_PUBLIC, m.getName(), methodDesc, null, null);

            mv.visitTypeInsn(NEW, "info/hexin/aop/advisor/MethodInvoke");
            mv.visitInsn(DUP);
            mv.visitMethodInsn(INVOKESPECIAL, "info/hexin/aop/advisor/MethodInvoke", "<init>", "()V");
            int sum = loadArguments(argumentTypes, mv);

            // add point
            addAdvisors(m, mv, sum);

            // // try
            Label start = new Label();
            Label end = new Label();
            Label handler = new Label();
            mv.visitTryCatchBlock(start, end, handler, "java/lang/Throwable");
            mv.visitLabel(start);
            // before
            sum = beforeWithoutReult(mv, ++sum);

            // l9
            // super
            execSuperMethod(m, argumentTypes, methodDesc, mv);
            if (resultType != Type.VOID_TYPE) {
                // sum 3storeCode
                mv.visitVarInsn(AsmHelper.storeCode(resultType), ++sum);
                if (argumentTypes.length > 0) {
                    mv.visitVarInsn(ALOAD, sum - 4);
                } else {
                    mv.visitVarInsn(ALOAD, sum - 3);
                }
                mv.visitVarInsn(AsmHelper.loadCode(resultType), sum);
                AsmHelper.packagePrivateData(resultType, mv);
                mv.visitMethodInsn(INVOKEVIRTUAL, "info/hexin/aop/advisor/MethodInvoke", "setReturnValue",
                        "(Ljava/lang/Object;)V");

                sum = afterWithReult(mv, sum, argumentTypes.length > 0);
                // 返回值
                sum = sum - 2;
                mv.visitVarInsn(AsmHelper.loadCode(resultType), sum);
                // AsmHelper.checkCast(resultType, mv);
                mv.visitInsn(AsmHelper.resultCode(resultType));

                mv.visitLabel(handler);
                sum = sum - 1; // 9
                mv.visitVarInsn(ASTORE, sum);
                if (sum >= 6) {
                    mv.visitVarInsn(ALOAD, sum - 3);
                } else {
                    mv.visitVarInsn(ALOAD, sum - 2);
                }
                mv.visitVarInsn(ALOAD, sum); // 9
                mv.visitMethodInsn(INVOKEVIRTUAL, "info/hexin/aop/advisor/MethodInvoke", "setThrowable",
                        "(Ljava/lang/Throwable;)V");
                // whenException
                whenExceptionWithReult(mv, sum);

                AsmHelper.returnExecption(resultType, mv);
                mv.visitJumpInsn(GOTO, end);

                mv.visitLabel(end);
                mv.visitInsn(AsmHelper.resultCode(resultType));

            } else {
                // after
                sum = afterWithoutReult(mv, sum);
                mv.visitJumpInsn(GOTO, end);

                // catch
                mv.visitLabel(handler);
                sum = sum - 2; // 9
                mv.visitVarInsn(ASTORE, sum);
                if (sum >= 6) {
                    mv.visitVarInsn(ALOAD, sum - 3);
                } else {
                    mv.visitVarInsn(ALOAD, sum - 2);
                }
                mv.visitVarInsn(ALOAD, sum); // 9
                mv.visitMethodInsn(INVOKEVIRTUAL, "info/hexin/aop/advisor/MethodInvoke", "setThrowable",
                        "(Ljava/lang/Throwable;)V");

                // whenException
                whenExceptionWithoutReult(mv, sum);
                mv.visitJumpInsn(GOTO, end);
                mv.visitLabel(end);
                mv.visitInsn(AsmHelper.resultCode(resultType));
            }
            mv.visitMaxs(1, 1);
            mv.visitEnd();
        }
        cv.visitEnd();
    }

    private void addAdvisors(Method m, MethodVisitor mv, int sum) {
        mv.visitTypeInsn(NEW, "java/util/ArrayList");
        mv.visitInsn(DUP);
        mv.visitMethodInsn(INVOKESPECIAL, "java/util/ArrayList", "<init>", "()V");
        mv.visitVarInsn(ASTORE, sum + 1);

        Point point = m.getAnnotation(Point.class);
        if (point != null && point.value() != null) {
            for (String pointName : point.value()) {
                mv.visitVarInsn(ALOAD, sum + 1); // 7
                mv.visitLdcInsn(pointName);
                mv.visitMethodInsn(INVOKESTATIC, "info/hexin/aop/config/AopConfig", "getAopBean",
                        "(Ljava/lang/String;)Linfo/hexin/aop/advisor/Advice;");
                mv.visitMethodInsn(INVOKEINTERFACE, "java/util/List", "add", "(Ljava/lang/Object;)Z");
                mv.visitInsn(POP);
            }
        }
    }

    /**
     * 带有返回值的whenException 方法调用
     * 
     * @param mv
     * @param sum
     */
    private void whenExceptionWithReult(MethodVisitor mv, int sum) {
        // 2-4-4-5-5-1-4 //double
        Label label = new Label();
        mv.visitVarInsn(ALOAD, sum - 1);
        mv.visitMethodInsn(INVOKEINTERFACE, "java/util/List", "iterator", "()Ljava/util/Iterator;");
        mv.visitVarInsn(ASTORE, ++sum);
        mv.visitLabel(label);
        mv.visitVarInsn(ALOAD, sum);
        mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Iterator", "next", "()Ljava/lang/Object;");
        mv.visitTypeInsn(CHECKCAST, "info/hexin/aop/advisor/Advice");
        mv.visitVarInsn(ASTORE, ++sum);
        mv.visitVarInsn(ALOAD, sum);
        if (sum >= 6) {
            mv.visitVarInsn(ALOAD, sum - 5);
        } else {
            mv.visitVarInsn(ALOAD, sum - 4);
        }
        mv.visitMethodInsn(INVOKEINTERFACE, "info/hexin/aop/advisor/Advice", "whenException",
                "(Linfo/hexin/aop/advisor/MethodInvoke;)V");
        mv.visitVarInsn(ALOAD, --sum);
        mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Iterator", "hasNext", "()Z");
        mv.visitJumpInsn(IFNE, label);
    }

    /**
     * 带有返回值执行after
     * 
     * @param mv
     * @param sum
     * @return
     */
    private int afterWithReult(MethodVisitor mv, int sum, boolean hasArguments) {
        // 2 5 5 6 6 1 3
        // 2-6-6-7-7-1-3 double
        // 6 10 10 11 11 4 7
        sum = sum - 1;
        Label label = new Label();
        mv.visitVarInsn(ALOAD, sum - 1); // 7
        mv.visitMethodInsn(INVOKEINTERFACE, "java/util/List", "iterator", "()Ljava/util/Iterator;");
        mv.visitVarInsn(ASTORE, ++sum + 2);// 9
        mv.visitLabel(label);
        mv.visitVarInsn(ALOAD, ++sum + 1); // 9
        mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Iterator", "next", "()Ljava/lang/Object;");
        mv.visitTypeInsn(CHECKCAST, "info/hexin/aop/advisor/Advice");
        mv.visitVarInsn(ASTORE, ++sum + 1);// 10
        mv.visitVarInsn(ALOAD, sum + 1);
        if (sum >= 6) {
            if (hasArguments) {
                mv.visitVarInsn(ALOAD, sum - 6);
            } else {
                mv.visitVarInsn(ALOAD, sum - 5);
            }
            mv.visitMethodInsn(INVOKEINTERFACE, "info/hexin/aop/advisor/Advice", "after",
                    "(Linfo/hexin/aop/advisor/MethodInvoke;)V");
            mv.visitVarInsn(ALOAD, sum - 3);
        } else {
            mv.visitVarInsn(ALOAD, 1);
            mv.visitMethodInsn(INVOKEINTERFACE, "info/hexin/aop/advisor/Advice", "after",
                    "(Linfo/hexin/aop/advisor/MethodInvoke;)V");
            mv.visitVarInsn(ALOAD, sum - 1);
        }
        mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Iterator", "hasNext", "()Z");
        mv.visitJumpInsn(IFNE, label);
        return sum;
    }

    /**
     * 没有返回值执行before方法
     * 
     * @param mv
     * @param sum
     * @return
     */
    private int beforeWithoutReult(MethodVisitor mv, int sum) {
        // 2-3-3-4-4-1-3
        Label label = new Label();
        mv.visitVarInsn(ALOAD, sum); // 7
        mv.visitMethodInsn(INVOKEINTERFACE, "java/util/List", "iterator", "()Ljava/util/Iterator;");
        mv.visitVarInsn(ASTORE, ++sum); // 8
        mv.visitLabel(label);
        mv.visitVarInsn(ALOAD, sum); // 8
        mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Iterator", "next", "()Ljava/lang/Object;");
        mv.visitTypeInsn(CHECKCAST, "info/hexin/aop/advisor/Advice");
        mv.visitVarInsn(ASTORE, ++sum);
        mv.visitVarInsn(ALOAD, sum);
        if (sum >= 6) {
            mv.visitVarInsn(ALOAD, sum - 4);
        } else {
            mv.visitVarInsn(ALOAD, sum - 3);
        }
        mv.visitMethodInsn(INVOKEINTERFACE, "info/hexin/aop/advisor/Advice", "before",
                "(Linfo/hexin/aop/advisor/MethodInvoke;)V");
        mv.visitVarInsn(ALOAD, --sum); // 8
        mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Iterator", "hasNext", "()Z");
        mv.visitJumpInsn(IFNE, label);
        return sum;
    }

    /**
     * 生成after处代码
     * 
     * @param mv
     * @param sum
     * @return
     */
    private int afterWithoutReult(MethodVisitor mv, int sum) {
        // 2-4-4-5-5-1-4
        Label l7 = new Label();
        mv.visitVarInsn(ALOAD, --sum); // 7
        mv.visitMethodInsn(INVOKEINTERFACE, "java/util/List", "iterator", "()Ljava/util/Iterator;");
        mv.visitVarInsn(ASTORE, ++sum + 1);// 9
        mv.visitLabel(l7);
        mv.visitVarInsn(ALOAD, ++sum); // 9
        mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Iterator", "next", "()Ljava/lang/Object;");
        mv.visitTypeInsn(CHECKCAST, "info/hexin/aop/advisor/Advice");
        mv.visitVarInsn(ASTORE, ++sum);// 10
        mv.visitVarInsn(ALOAD, sum);
        if (sum >= 6) {
            mv.visitVarInsn(ALOAD, sum - 5);
            mv.visitMethodInsn(INVOKEINTERFACE, "info/hexin/aop/advisor/Advice", "after",
                    "(Linfo/hexin/aop/advisor/MethodInvoke;)V");
            mv.visitVarInsn(ALOAD, sum - 2);
        } else {
            mv.visitVarInsn(ALOAD, sum - 4);
            mv.visitMethodInsn(INVOKEINTERFACE, "info/hexin/aop/advisor/Advice", "after",
                    "(Linfo/hexin/aop/advisor/MethodInvoke;)V");
            mv.visitVarInsn(ALOAD, sum - 1);
        }
        mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Iterator", "hasNext", "()Z");
        mv.visitJumpInsn(IFNE, l7);
        return sum;
    }

    /**
     * 生成whenException处代码
     * 
     * @param mv
     * @param sum
     */
    private void whenExceptionWithoutReult(MethodVisitor mv, int sum) {
        // 2-4-4-5-5-1-4
        // 8-10-10-11-11-6-10
        Label label = new Label();
        mv.visitVarInsn(ALOAD, sum - 1);
        mv.visitMethodInsn(INVOKEINTERFACE, "java/util/List", "iterator", "()Ljava/util/Iterator;");
        mv.visitVarInsn(ASTORE, ++sum);
        mv.visitLabel(label);
        mv.visitVarInsn(ALOAD, sum);
        mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Iterator", "next", "()Ljava/lang/Object;");
        mv.visitTypeInsn(CHECKCAST, "info/hexin/aop/advisor/Advice");
        mv.visitVarInsn(ASTORE, ++sum);
        mv.visitVarInsn(ALOAD, sum);
        if (sum >= 6) {
            mv.visitVarInsn(ALOAD, sum - 5);
        } else {
            mv.visitVarInsn(ALOAD, sum - 4);
        }
        mv.visitMethodInsn(INVOKEINTERFACE, "info/hexin/aop/advisor/Advice", "whenException",
                "(Linfo/hexin/aop/advisor/MethodInvoke;)V");
        mv.visitVarInsn(ALOAD, --sum);
        mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Iterator", "hasNext", "()Z");
        mv.visitJumpInsn(IFNE, label);
    }

    private void execSuperMethod(Method m, Type[] argumentTypes, String methodDesc, MethodVisitor mv) {
        int argumentTypesLength = argumentTypes.length;
        mv.visitVarInsn(ALOAD, 0);
        String declaringCls = AsmHelper.toAsmCls(m.getDeclaringClass().getName());
        for (int k = 0, index = 1; k < argumentTypesLength; k++) {
            Type argType = argumentTypes[k];
            mv.visitVarInsn(AsmHelper.loadCode(argType), index);
            index += argType.getSize();
        }
        mv.visitMethodInsn(INVOKESPECIAL, declaringCls, m.getName(), methodDesc);
    }

    /**
     * 装载方法的输入参数
     * 
     * @param argumentTypes
     * @param mv
     * @return
     */
    private int loadArguments(Type[] argumentTypes, MethodVisitor mv) {
        int sum = 1;
        int argumentTypesLength = argumentTypes.length;
        if (argumentTypesLength > 0) {
            for (int k = 0; k < argumentTypes.length; k++) {
                sum += argumentTypes[k].getSize();
            }
            mv.visitVarInsn(ASTORE, sum);

            mv.visitInsn(ICONST_0 + argumentTypesLength);
            mv.visitTypeInsn(ANEWARRAY, "java/lang/Object");

            for (int k = 0, index = 1; k < argumentTypes.length; k++) {
                Type argType = argumentTypes[k];
                mv.visitInsn(DUP);
                mv.visitInsn(ICONST_0 + k);
                mv.visitVarInsn(AsmHelper.loadCode(argType), index);
                index += argType.getSize();
                AsmHelper.packagePrivateData(argType, mv);
                mv.visitInsn(AASTORE);
            }

            mv.visitVarInsn(ASTORE, ++sum); // 6
            mv.visitVarInsn(ALOAD, --sum); // 5
            mv.visitVarInsn(ALOAD, ++sum); // 6
            mv.visitMethodInsn(INVOKEVIRTUAL, "info/hexin/aop/advisor/MethodInvoke", "setArgs",
                    "([Ljava/lang/Object;)V");
        } else {
            mv.visitVarInsn(ASTORE, sum);
        }
        return sum;
    }

    /**
     * 修改构造函数并给新增加的属性赋值
     */
    private void constructor() {
        MethodVisitor mvInit = classWriter.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
        mvInit.visitVarInsn(ALOAD, 0);
        mvInit.visitMethodInsn(INVOKESPECIAL, AsmHelper.toAsmCls(type.getName()), "<init>", "()V");

        mvInit.visitInsn(RETURN);
        mvInit.visitMaxs(1, 1);
        mvInit.visitEnd();
    }
}
