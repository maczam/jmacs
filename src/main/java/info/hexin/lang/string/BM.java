package info.hexin.lang.string;


/**
 * 字符串bm算法查找
 * 
 * @author hexin
 * 
 */
public class BM {

    private byte[] boundary;

    private int[] charTable;

    private int[] offsetTable;

    public BM(byte[] boundary) {
        this.boundary = boundary;
        charTable = makeCharTable(boundary);
        offsetTable = makeOffsetTable(boundary);
    }

    public int indexOf(byte[] buffer) {
        return indexOf(buffer, 0);
    }

    public int indexOf(byte[] buffer, int start) {
        return indexOf(buffer, start, buffer.length);
    }

    public int indexOf(byte[] buffer, int start, int end) {
        if (boundary.length == 0) {
            return 0;
        }
        for (int i = boundary.length - 1 + start, j; i < end;) {
            for (j = boundary.length - 1; boundary[j] == buffer[i]; --i, --j) {
                if (j == 0) {
                    return i;
                }
            }
            // i += needle.length - j; // For naive method
            int offset = Math.max(offsetTable[boundary.length - 1 - j], charTable[buffer[i] < 0 ? buffer[i] + 256
                    : buffer[i]]);
            i += offset;
        }
        return -1;
    }

    public int getBoundaryLength() {
        return this.boundary.length;
    }

    private int[] makeCharTable(byte[] needle) {
        final int ALPHABET_SIZE = 256;
        int[] table = new int[ALPHABET_SIZE];
        for (int i = 0; i < table.length; ++i) {
            table[i] = needle.length;
        }
        for (int i = 0; i < needle.length - 1; ++i) {
            table[needle[i]] = needle.length - 1 - i;
        }
        return table;
    }

    private int[] makeOffsetTable(byte[] needle) {
        int[] table = new int[needle.length];
        int lastPrefixPosition = needle.length;
        for (int i = needle.length - 1; i >= 0; --i) {
            if (isPrefix(needle, i + 1)) {
                lastPrefixPosition = i + 1;
            }
            table[needle.length - 1 - i] = lastPrefixPosition - i + needle.length - 1;
        }
        for (int i = 0; i < needle.length - 1; ++i) {
            int slen = suffixLength(needle, i);
            table[slen] = needle.length - 1 - i + slen;
        }
        return table;
    }

    /**
     * Is needle[p:end] a prefix of needle?
     */
    private boolean isPrefix(byte[] needle, int p) {
        for (int i = p, j = 0; i < needle.length; ++i, ++j) {
            if (needle[i] != needle[j]) {
                return false;
            }
        }
        return true;
    }

    private int suffixLength(byte[] needle, int p) {
        int len = 0;
        for (int i = p, j = needle.length - 1; i >= 0 && needle[i] == needle[j]; --i, --j) {
            len += 1;
        }
        return len;
    }

    public static void main1(String[] args) {
        byte[] bb = new byte[] { 45, 45, 45, 45, 45, 45, 87, 101, 98, 75, 105, 116, 70, 111, 114, 109, 66, 111, 117,
                110, 100, 97, 114, 121, 121, 90, 76, 55, 84, 115, 105, 108, 69, 83, 107, 54, 80, 98, 101, 53, 13, 10,
                67, 111, 110, 116, 101, 110, 116, 45, 68, 105, 115, 112, 111, 115, 105, 116, 105, 111, 110, 58, 32,
                102, 111, 114, 109, 45, 100, 97, 116, 97, 59, 32, 110, 97, 109, 101, 61, 34, 110, 97, 109, 101, 34, 13,
                10, 13, 10, 49, 50, 51, 13, 10, 45, 45, 45, 45, 45, 45, 87, 101, 98, 75, 105, 116, 70, 111, 114, 109,
                66, 111, 117, 110, 100, 97, 114, 121, 121, 90, 76, 55, 84, 115, 105, 108, 69, 83, 107, 54, 80, 98, 101,
                53, 13, 10, 67, 111, 110, 116, 101, 110, 116, 45, 68, 105, 115, 112, 111, 115, 105, 116, 105, 111, 110,
                58, 32, 102, 111, 114, 109, 45, 100, 97, 116, 97, 59, 32, 110, 97, 109, 101, 61, 34, 102, 105, 108,
                101, 34, 59, 32, 102, 105, 108, 101, 110, 97, 109, 101, 61, 34, 49, 46, 116, 120, 116, 34, 13, 10, 67,
                111, 110, 116, 101, 110, 116, 45, 84, 121, 112, 101, 58, 32, 116, 101, 120, 116, 47, 112, 108, 97, 105,
                110, 13, 10, 13, 10, 104, 101, 108, 108, 111, 32, 119, 111, 114, 100, 120, 120, 120, 13, 10, 45, 45,
                45, 45, 45, 45, 87, 101, 98, 75, 105, 116, 70, 111, 114, 109, 66, 111, 117, 110, 100, 97, 114, 121,
                121, 90, 76, 55, 84, 115, 105, 108, 69, 83, 107, 54, 80, 98, 101, 53, 45, 45, 13, 10 };
        byte[] index = new byte[] { 45, 45, 45, 45, 45, 45, 87, 101, 98, 75, 105, 116, 70, 111, 114, 109, 66, 111, 117,
                110, 100, 97, 114, 121, 121, 90, 76, 55, 84, 115, 105, 108, 69, 83, 107, 54, 80, 98, 101, 53 };
        BM bm = new BM(bb);
        
        System.out.println(bm.indexOf(index, 0));
    }
    
    public static void main(String[] args) {
        BM bm = new BM("nihao".getBytes());
        System.out.println(bm.indexOf("nihao xxxxxxxx".getBytes()));
    }
    
    public static void main3(String[] args) {
        byte[] boundary = "---------------------------194760164713951335551783200939".getBytes();
        BM bf = new BM(boundary);
        byte[] text= ("-----------------------------194760164713951335551783200939\r\n" +
                "Content-Disposition: form-data; name=\"_text1.text1\"\r\n" +
                "123123123123dfdfsdfsdafsdaffdafasfa0939ttttttttttttttttttttertertqweqweqwrrrrr9391112235551783200939\r\n" +
                "-----------------------------194760164713951335551783200939--").getBytes();
        int i = bf.indexOf(text, 20);
        byte[] boundary2 = new byte[boundary.length];
        System.arraycopy(text, i, boundary2, 0, boundary.length);
        System.out.println("index: " + i  + " " + new String(boundary2) );
    }
}
