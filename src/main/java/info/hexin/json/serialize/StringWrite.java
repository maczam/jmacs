package info.hexin.json.serialize;

import info.hexin.json.util.JsonUtil;

import java.lang.ref.SoftReference;

/**
 * 编写json
 * 
 * @author ydhexin@gmail.com
 * 
 */
public class StringWrite {

    protected char buf[];

    /**
     * 当前buf使用的总量
     */
    protected int count;

    private final static ThreadLocal<SoftReference<char[]>> bufLocal = new ThreadLocal<SoftReference<char[]>>();

    public StringWrite() {
        SoftReference<char[]> ref = bufLocal.get();

        if (ref != null) {
            buf = ref.get();
            bufLocal.set(null);
        }

        if (buf == null) {
            buf = new char[1024];
        }
    }

    public StringWrite(int capacity) {
        SoftReference<char[]> ref = bufLocal.get();
        if (ref != null) {
            buf = ref.get();
            bufLocal.set(null);
        }

        if (buf == null) {
            buf = new char[capacity];
        }
    }

    /**
     * Writes a character to the buffer.
     */
    public void append(int c) {
        int newcount = count + 1;
        if (newcount > buf.length) {
            expandCapacity(newcount);
        }
        buf[count] = (char) c;
        count = newcount;
    }

    public void append(char c) {
        int newcount = count + 1;
        if (newcount > buf.length) {
            expandCapacity(newcount);
        }
        buf[count] = c;
        count = newcount;
    }

    /**
     * 扩展容量
     * 
     * @param minimumCapacity
     */
    private void expandCapacity(int minimumCapacity) {
        int newCapacity = (buf.length * 3) / 2 + 1;

        if (newCapacity < minimumCapacity) {
            newCapacity = minimumCapacity;
        }
        char newValue[] = new char[newCapacity];
        System.arraycopy(buf, 0, newValue, 0, count);
        buf = newValue;
    }

    /**
     * 
     * @param extraLength
     * @return
     */
    private int checkCapacity(int extraLength) {
        int newcount = count + extraLength;
        if (newcount > buf.length) {
            expandCapacity(newcount);
        }
        return newcount;
    }

    public void appendString(String string) {
        int newcount = checkCapacity(string.length());
        for (int i = 0; i < string.length(); i++) {
            buf[count + i] = string.charAt(i);
        }
        count = newcount;
    }

    public void appendFiledNameWithQuotation(String name) {
        int newcount = checkCapacity(name.length() + 2);
        buf[count] = '\"';
        for (int i = 1; i <= name.length(); i++) {
            buf[count + i] = name.charAt(i - 1);
        }
        buf[newcount - 1] = '\"';
        count = newcount;
    }

    public int length() {
        return count;
    }

    public void append(int[] array) {
        int totalLength = array.length + 1; // 逗号分隔符 和 []
        int[] eachIntStringLength = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            // 将Integer.toString(i) 方法扣出来
            int itemInt = array[i];
            int size = 0;
            if (itemInt == Integer.MIN_VALUE) {
                size = JsonUtil.intStringMinSize();
            } else {
                size = (itemInt < 0) ? JsonUtil.intStringSize(-itemInt) + 1 : JsonUtil.intStringSize(itemInt);
            }
            eachIntStringLength[i] = size;
            totalLength += size;
        }

        int newcount = checkCapacity(totalLength);
        buf[count++] = '[';
        for (int i = 0; i < eachIntStringLength.length; i++) {
            if (i > 0) {
                buf[count++] = ',';
            }
            JsonUtil.getChars(array[i], count += eachIntStringLength[i], buf);
        }
        buf[newcount - 1] = ']';
        count = newcount;
    }

    public String toString() {
        return new String(buf, 0, count);
    }
}
