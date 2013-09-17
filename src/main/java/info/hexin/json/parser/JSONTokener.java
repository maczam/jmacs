package info.hexin.json.parser;

import info.hexin.json.serialize.StringWrite;
import info.hexin.lang.Exceptions;

import java.text.ParseException;

/**
 * 
 * @author hexin
 * 
 */
public class JSONTokener {

    private int index;

    private String source;

    public JSONTokener(String json) {
        this.index = 0;
        this.source = json;
    }

    public void back() {
        if (index > 0) {
            index -= 1;
        }
    }

    public static int dehexchar(char c) {
        if (c >= '0' && c <= '9') {
            return c - '0';
        }
        if (c >= 'A' && c <= 'F') {
            return c + 10 - 'A';
        }
        if (c >= 'a' && c <= 'f') {
            return c + 10 - 'a';
        }
        return -1;
    }

    public boolean more() {
        return index < source.length();
    }

    /**
     * 获取下一个字符，当前索引增加
     * 
     * @return
     */
    public char next() {
        char c = more() ? source.charAt(index) : 0;
        index += 1;
        return c;
    }

    public char next(char c) {
        char n = next();
        if (n != c) {
            throw Exceptions.make("Expected '" + c + "' and instead saw '" + n + "'.");
        }
        return n;
    }

    public String next(int n) throws ParseException {
        int i = index;
        int j = i + n;
        if (j >= source.length()) {
            throw Exceptions.make("Substring bounds error");
        }
        index += n;
        return source.substring(i, j);
    }

    /**
     * 获取一个干净的char，过滤掉注释
     * 
     * @return
     */
    public char nextClean() {
        while (true) {
            char c = next();
            if (c == '/') {
                switch (next()) {
                case '/':
                    do {
                        c = next();
                    } while (c != '\n' && c != '\r' && c != 0);
                    break;
                case '*':
                    while (true) {
                        c = next();
                        if (c == 0) {
                            throw Exceptions.make("Unclosed comment.");
                        }
                        if (c == '*') {
                            if (next() == '/') {
                                break;
                            }
                            back();
                        }
                    }
                    break;
                default:
                    back();
                    return '/';
                }
            } else if (c == 0 || c > ' ') {
                return c;
            }
        }
    }

    public String nextString(char quote) throws ParseException {
        char c;
        StringWrite stringWrite = new StringWrite(32); 
        while (true) {
            c = next();
            switch (c) {
            case 0:
            case 0x0A:
            case 0x0D:
                throw Exceptions.make("Unterminated string");
            case '\\':
                c = next();
                switch (c) {
                case 'b':
                    stringWrite.append('\b');
                    break;
                case 't':
                    stringWrite.append('\t');
                    break;
                case 'n':
                    stringWrite.append('\n');
                    break;
                case 'f':
                    stringWrite.append('\f');
                    break;
                case 'r':
                    stringWrite.append('\r');
                    break;
                case 'u':
                    stringWrite.append((char) Integer.parseInt(next(4), 16));
                    break;
                case 'x':
                    stringWrite.append((char) Integer.parseInt(next(2), 16));
                    break;
                default:
                    stringWrite.append(c);
                }
                break;
            default:
                if (c == quote) {
                    return stringWrite.toString();
                }
                stringWrite.append(c);
            }
        }
    }

    public String nextTo(char d) {
        StringBuffer sb = new StringBuffer();
        while (true) {
            char c = next();
            if (c == d || c == 0 || c == '\n' || c == '\r') {
                if (c != 0) {
                    back();
                }
                return sb.toString().trim();
            }
            sb.append(c);
        }
    }

    public String nextTo(String delimiters) {
        char c;
        StringBuffer sb = new StringBuffer();
        while (true) {
            c = next();
            if (delimiters.indexOf(c) >= 0 || c == 0 || c == '\n' || c == '\r') {
                if (c != 0) {
                    back();
                }
                return sb.toString().trim();
            }
            sb.append(c);
        }
    }

    /**
     * 对于[] 中值为null ， 
     * @return
     * @throws ParseException
     */
    public Object nextValue() throws ParseException {
        char c = nextClean();
        if (c == '"' || c == '\'') {
            return nextString(c);
        }
//        StringBuilder sb = new StringBuilder();
        StringWrite stringWrite = new StringWrite(32);
        char b = c;
        while (c >= ' ' && c != ':' && c != ',' && c != ']' && c != '}' && c != '/' && c != '\'' && c != '"') {
            stringWrite.append(c);
            c = next();
        }
        back();
        if (stringWrite.length() == 0) {
            return null;
        }
        String s = stringWrite.toString();
        if (s.equals("true")) {
            return Boolean.TRUE;
        }
        if (s.equals("false")) {
            return Boolean.FALSE;
        }
        if (s.equals("null")) {
            return "null";
        }
        if ((b >= '0' && b <= '9') || b == '.' || b == '-' || b == '+') {
            try {
                return new Integer(s);
            } catch (Exception e) {
            }
            try {
                return new Double(s);
            } catch (Exception e) {
            }
        }
        // if (s.equals("")) {
        // throw Exceptions.make("Missing value.");
        // }
        return s;
    }
}
