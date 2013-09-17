package info.hexin.jmacs.lang.reflect;

import info.hexin.jmacs.lang.reflect.model.ArrayA;
import info.hexin.jmacs.lang.reflect.model.BooleanA;
import info.hexin.jmacs.lang.reflect.model.IntA;
import info.hexin.json.Json;

import org.junit.Test;

public class WrapTest {

    // @Test
    public void testInt() {
        IntA intA = new IntA(1, 2);
        IntA intA1 = Json.fromJson(Json.toJson(intA), IntA.class);
        System.out.println(intA1.getInta());
        System.out.println(intA1.getIntb());
    }

    // @Test
    public void testBooleanA() {
        BooleanA booleanA = new BooleanA(true, true);
        print(booleanA);
        BooleanA booleanb = Json.fromJson(Json.toJson(booleanA), BooleanA.class);
        print(booleanb);
    }

    @Test
    public void testArray() {
        ArrayA arrayA = new ArrayA();
//        arrayA.setArrayByteA(new Byte[] { 'x', 'y' });
//        arrayA.setArrayIntA(new int[] { -1, 2,-2147483648 });
//        arrayA.setArrayShortA(new short[] { 1, 2 });
        arrayA.setArrayStringA(new String[] { "aaaaa", "bbbbb" });
        print(arrayA);
    }

    private void print(Object o) {
        System.out.println(Json.toJson(o));
    }
}
