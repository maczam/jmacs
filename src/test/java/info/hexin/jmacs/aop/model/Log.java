package info.hexin.jmacs.aop.model;

import info.hexin.jmacs.aop.advisor.Advice;
import info.hexin.jmacs.aop.advisor.MethodInvoke;
import info.hexin.jmacs.aop.annotation.Aop;

@Aop
public class Log implements Advice {

    @Override
    public void before(MethodInvoke invoke) {
        System.out.println("in log xxxxxxxxxxxxxxx");
    }

    @Override
    public void after(MethodInvoke invoke) {
        // TODO Auto-generated method stub

    }

    @Override
    public void whenException(MethodInvoke invoke) {
        // TODO Auto-generated method stub
    }
}
