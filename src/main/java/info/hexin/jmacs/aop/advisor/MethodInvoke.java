package info.hexin.jmacs.aop.advisor;

/**
 * 
 * @author hexin
 * 
 */
public class MethodInvoke {

    private Object[] args;
    private Object returnValue;
    private Throwable throwable;

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public Object getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(Object returnValue) {
        this.returnValue = returnValue;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }
}
