package info.hexin.jmacs.lang.reflect.model;

public class BooleanA {
    private boolean boolean1;
    private Boolean boolean2;

    public BooleanA() {
    }

    public BooleanA(boolean boolean1, Boolean boolean2) {
        this.boolean1 = boolean1;
        this.boolean2 = boolean2;
    }

    public boolean isBoolean1() {
        return boolean1;
    }

    public void setBoolean1(boolean boolean1) {
        this.boolean1 = boolean1;
    }

    public Boolean getBoolean2() {
        return boolean2;
    }

    public void setBoolean2(Boolean boolean2) {
        this.boolean2 = boolean2;
    }
}
