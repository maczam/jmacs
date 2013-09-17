package info.hexin.jmacs.mvc.annotation;

/**
 * web 请求的method， 不能用ALL来equal别的。 ALL使用equal不对称
 * 
 * @author ydhexin@gmail.com
 * 
 */
public enum Method {
    GET(1), POST(2), PUT(3), DELETE(4), PATCH(5), HEAD(6), OPTIONS(7), ALL(15);
    private int value;

    private Method(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public boolean equal(Method other) {
        return (this.getValue() & other.getValue()) == this.getValue();
    }

    public static Method typeOf(String type) {
        if ("GET".equalsIgnoreCase(type)) {
            return Method.GET;
        } else if ("POST".equalsIgnoreCase(type)) {
            return Method.POST;
        } else if ("PUT".equalsIgnoreCase(type)) {
            return Method.PUT;
        } else if ("DELETE".equalsIgnoreCase(type)) {
            return Method.DELETE;
        } else if ("PATCH".equalsIgnoreCase(type)) {
            return Method.PATCH;
        } else if ("HEAD".equalsIgnoreCase(type)) {
            return Method.HEAD;
        } else if ("OPTIONS".equalsIgnoreCase(type)) {
            return Method.OPTIONS;
        } else {
            return Method.GET;
        }
    }
}