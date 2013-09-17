package info.hexin.jmacs.aop.proxy.asm;

import info.hexin.jmacs.asm.MethodVisitor;
import info.hexin.jmacs.asm.Opcodes;
import info.hexin.jmacs.asm.Type;

/**
 * asm 帮助类
 * 
 * @author hexin
 * 
 */
class AsmHelper implements Opcodes {

    /**
     * 自动封包
     * 
     * @param type
     * @param mv
     */
    static void packagePrivateData(Type type, MethodVisitor mv) {
        if (type.equals(Type.BOOLEAN_TYPE)) {
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/Boolean", "valueOf", "(Z)Ljava/lang/Boolean;");
        } else if (type.equals(Type.BYTE_TYPE)) {
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;");
        } else if (type.equals(Type.CHAR_TYPE)) {
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/Character", "valueOf", "(C)Ljava/lang/Character;");
        } else if (type.equals(Type.SHORT_TYPE)) {
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/Short", "valueOf", "(S)Ljava/lang/Short;");
        } else if (type.equals(Type.INT_TYPE)) {
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
        } else if (type.equals(Type.LONG_TYPE)) {
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;");
        } else if (type.equals(Type.FLOAT_TYPE)) {
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/Float", "valueOf", "(F)Ljava/lang/Float;");
        } else if (type.equals(Type.DOUBLE_TYPE)) {
            mv.visitMethodInsn(INVOKESTATIC, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;");
        }
    }

    /**
     * 自动解包
     * 
     * @param type
     * @param mv
     */
    static void unpackagePrivateData(Type type, MethodVisitor mv) {
        if (type.equals(Type.BOOLEAN_TYPE)) {
            mv.visitMethodInsn(INVOKESTATIC, "info/hexin/aop/proxy/asm/Helper", "valueOf", "(Ljava/lang/Boolean;)Z");
        } else if (type.equals(Type.BYTE_TYPE)) {
            mv.visitMethodInsn(INVOKESTATIC, "info/hexin/aop/proxy/asm/Helper", "valueOf", "(Ljava/lang/Byte;)B");
        } else if (type.equals(Type.CHAR_TYPE)) {
            mv.visitMethodInsn(INVOKESTATIC, "info/hexin/aop/proxy/asm/Helper", "valueOf", "(Ljava/lang/Character;)C");
        } else if (type.equals(Type.SHORT_TYPE)) {
            mv.visitMethodInsn(INVOKESTATIC, "info/hexin/aop/proxy/asm/Helper", "valueOf", "(Ljava/lang/Short;)S");
        } else if (type.equals(Type.INT_TYPE)) {
            mv.visitMethodInsn(INVOKESTATIC, "info/hexin/aop/proxy/asm/Helper", "valueOf", "(Ljava/lang/Integer;)I");
        } else if (type.equals(Type.LONG_TYPE)) {
            mv.visitMethodInsn(INVOKESTATIC, "info/hexin/aop/proxy/asm/Helper", "valueOf", "(Ljava/lang/Long;)J");
        } else if (type.equals(Type.FLOAT_TYPE)) {
            mv.visitMethodInsn(INVOKESTATIC, "info/hexin/aop/proxy/asm/Helper", "valueOf", "(Ljava/lang/Float;)F");
        } else if (type.equals(Type.DOUBLE_TYPE)) {
            mv.visitMethodInsn(INVOKESTATIC, "info/hexin/aop/proxy/asm/Helper", "valueOf", "(Ljava/lang/Double;)D");
        }
    }

    /**
     * 程序出现异常，但是抓住后，需要返回
     * 
     * @param resultType
     * @param mv
     */
    static void returnExecption(Type resultType, MethodVisitor mv) {
        // DCONST_0
        switch (resultType.getSort()) {
        case Type.DOUBLE:
            mv.visitInsn(DCONST_0);
            break;
        case Type.FLOAT:
            mv.visitInsn(FCONST_0);
            break;
        case Type.BOOLEAN:
        case Type.INT:
        case Type.CHAR:
        case Type.BYTE:
        case Type.SHORT:
            mv.visitInsn(ICONST_0);
            break;
        case Type.LONG:
            mv.visitInsn(LCONST_0);
            break;
        case Type.ARRAY:
        case Type.OBJECT:
            mv.visitInsn(ACONST_NULL);
            break;
        default:
            // VOID
            break;
        }
    }

    /**
     * 将返回值将强转
     * 
     * @param type
     * @param mv
     */
    static void checkCast(Type type, MethodVisitor mv) {
        if (type.getSort() == Type.ARRAY) {
            mv.visitTypeInsn(CHECKCAST, type.getDescriptor());
            return;
        }
        if (type != Type.getType(Object.class)) {
            if (type.getOpcode(IRETURN) != ARETURN) {
                checkCast2(type, mv);
                unpackagePrivateData(type, mv);
            } else {
                mv.visitTypeInsn(CHECKCAST, type.getClassName().replace('.', '/'));
            }
        }
    }

    static void checkCast2(Type type, MethodVisitor mv) {
        if (type.equals(Type.BOOLEAN_TYPE)) {
            mv.visitTypeInsn(CHECKCAST, "java/lang/Boolean");
        } else if (type.equals(Type.BYTE_TYPE)) {
            mv.visitTypeInsn(CHECKCAST, "java/lang/Byte");
        } else if (type.equals(Type.CHAR_TYPE)) {
            mv.visitTypeInsn(CHECKCAST, "java/lang/Character");
        } else if (type.equals(Type.SHORT_TYPE)) {
            mv.visitTypeInsn(CHECKCAST, "java/lang/Short");
        } else if (type.equals(Type.INT_TYPE)) {
            mv.visitTypeInsn(CHECKCAST, "java/lang/Integer");
        } else if (type.equals(Type.LONG_TYPE)) {
            mv.visitTypeInsn(CHECKCAST, "java/lang/Long");
        } else if (type.equals(Type.FLOAT_TYPE)) {
            mv.visitTypeInsn(CHECKCAST, "java/lang/Float");
        } else if (type.equals(Type.DOUBLE_TYPE)) {
            mv.visitTypeInsn(CHECKCAST, "java/lang/Double");
        }
    }

    /**
     * 
     * <p>
     * get StoreCode(Opcodes#xStore)
     * </p>
     * 
     * 
     * @param type
     * @return
     */
    public static int storeCode(Type type) {

        int sort = type.getSort();
        switch (sort) {
        case Type.ARRAY:
            sort = ASTORE;
            break;
        case Type.BOOLEAN:
            sort = ISTORE;
            break;
        case Type.BYTE:
            sort = ISTORE;
            break;
        case Type.CHAR:
            sort = ISTORE;
            break;
        case Type.DOUBLE:
            sort = DSTORE;
            break;
        case Type.FLOAT:
            sort = FSTORE;
            break;
        case Type.INT:
            sort = ISTORE;
            break;
        case Type.LONG:
            sort = LSTORE;
            break;
        case Type.OBJECT:
            sort = ASTORE;
            break;
        case Type.SHORT:
            sort = ISTORE;
            break;
        default:
            break;
        }
        return sort;
    }

    /**
     * 
     * <p>
     * get StoreCode(Opcodes#xLOAD)
     * </p>
     * 
     * @param type
     * @return
     */
    public static int loadCode(Type type) {
        int sort = type.getSort();
        switch (sort) {
        case Type.ARRAY:
            sort = ALOAD;
            break;
        case Type.BOOLEAN:
            sort = ILOAD;
            break;
        case Type.BYTE:
            sort = ILOAD;
            break;
        case Type.CHAR:
            sort = ILOAD;
            break;
        case Type.DOUBLE:
            sort = DLOAD;
            break;
        case Type.FLOAT:
            sort = FLOAD;
            break;
        case Type.INT:
            sort = ILOAD;
            break;
        case Type.LONG:
            sort = LLOAD;
            break;
        case Type.OBJECT:
            sort = ALOAD;
            break;
        case Type.SHORT:
            sort = ILOAD;
            break;
        default:
            break;
        }
        return sort;
    }

    /**
     * 根据不同的返回值类型获返回编码
     * 
     * @param type
     * @return
     */
    public static int resultCode(Type type) {
        int sort = type.getSort();
        switch (sort) {
        case Type.DOUBLE:
            sort = DRETURN;
            break;
        case Type.FLOAT:
            sort = FRETURN;
            break;
        case Type.LONG:
            sort = LRETURN;
            break;
        case Type.ARRAY:
        case Type.OBJECT:
            sort = ARETURN;
            break;
        case Type.BOOLEAN:
        case Type.BYTE:
        case Type.CHAR:
        case Type.INT:
        case Type.SHORT:
            sort = IRETURN;
            break;
        default:
            // VOID
            sort = RETURN;
            break;
        }
        return sort;
    }

    /**
     * 把类名中的.替换为/
     * 
     * @param className
     * @return
     */
    public static String toAsmCls(String className) {
        return className.replace('.', '/');
    }
}
