package info.hexin.jmacs.mvc.handler.upload;

import info.hexin.lang.string.BM;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.HashMap;

/**
 * 
 * @author hexin
 * 
 */
public class BMChunk {

    private static final int PRE_SKIP = 40;
    private int pos;
    private byte[] buffer;
    private int length;

    private int boundStart = -1;
    private int boundEnd = -1;
    private int contentStart;

    private BM bm;

    public BMChunk(BM bm) {
        this.bm = bm;
    }

    public BMChunk(byte[] buffer, int pos, int length, BM bm) {
        this.pos = pos;
        this.buffer = buffer;
        this.length = length;
        this.bm = bm;
    }

    /**
     * convenience method set whole <em>buff</em> as current chunk buffer
     * 
     * @param buff
     */
    public void setBuffer(byte[] buff) {
        setBuffer(buff, 0, buff.length);
    }

    public void setBuffer(byte[] buff, int pos, int len) {
        this.buffer = buff;
        this.pos = pos;
        this.length = len;
    }

    /**
     * 增加字符串的长度
     * 
     * @param buff
     * @param pos
     * @param len
     */
    public void append(byte[] buff, int pos, int len) {
        ByteBuffer bb = ByteBuffer.allocate(this.length + len);
        bb.put(this.buffer, 0, this.length);
        bb.put(buff, 0, len);
        this.buffer = bb.array();
        this.length += len;
    }

    public boolean find() {
        boundStart = bm.indexOf(buffer, pos);
        if (boundStart != -1) {
            pos = boundStart + bm.getBoundaryLength() + PRE_SKIP;
            if (pos >= length - 1) {// if out of bound index
                boundEnd = -1;
                return false;
            } else {
                boundEnd = bm.indexOf(buffer, pos);
                if (boundEnd != -1) {
                    pos = boundEnd;
                    return true;
                } else
                    return false;
            }
        }
        return boundStart >= 0 && boundEnd > 0;
    }

    /**
     * 读取每个file的头部信息
     * 
     * @return
     */
    public FieldHeadContext readContentHeader() {
        int s = boundStart + bm.getBoundaryLength() + 2;
        int p = readLine(s);

        if (p == -1 || p - s < PRE_SKIP) {
            return null;
        }

        String encoding = "utf-8";
        HashMap<String, String> describeMap = parseLine(substitute(buffer, s, p, encoding));
        FieldHeadContext fileHeadContext = new FieldHeadContext();
        fileHeadContext.setDescribeMap(describeMap);
        if (fileHeadContext.isFile()) {
            s = p;
            p = readLine(s + 2);
            if (p == -1) {
                return null;
            }
            HashMap<String, String> type = this.parseLine(this.substitute(buffer, s, p, encoding));
            fileHeadContext.setDescribeMap(type);
        }

        contentStart = p + 3;
        return fileHeadContext;
    }

    private int readLine(int pos) {
        for (int i = pos; i < length; i++) {
            if (buffer[i] == 0x0a)
                return i;
        }
        return -1;
    }

    /**
     * Content-Disposition: form-data; name="file"; filename="1.txt"
     * 
     * @param line
     * @return
     */
    public HashMap<String, String> parseLine(String line) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        String[] sections = line.split(";\\s");
        for (String sec : sections) {
            String[] vals = sec.split(":\\s|(=\")");
            if (vals.length > 1) {
                hashMap.put(vals[0].trim(), vals[1].trim().replaceAll("\"", ""));
            }
        }
        return hashMap;
    }

    private String substitute(byte[] buffer, int start, int end, String charset) {
        try {
            return new String(buffer, start, end - start, charset);
        } catch (UnsupportedEncodingException e) {
            return new String(buffer, start, end - start);
        }
    }

    public int getContentStart() {
        return contentStart;
    }

    public int getBoundEnd() {
        return boundEnd;
    }

    public byte[] getBuffer() {
        return buffer;
    }

}
