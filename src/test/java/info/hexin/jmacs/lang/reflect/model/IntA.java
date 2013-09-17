package info.hexin.jmacs.lang.reflect.model;

public class IntA {
    int inta;
    Integer intb;
    
    public IntA() {
        super();
    }

    public IntA(int inta, Integer intb) {
        this.inta = inta;
        this.intb = intb;
    }

    public int getInta() {
        return inta;
    }

    public void setInta(int inta) {
        this.inta = inta;
    }

    public Integer getIntb() {
        return intb;
    }

    public void setIntb(Integer intb) {
        this.intb = intb;
    }
}
