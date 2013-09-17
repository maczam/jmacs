package info.hexin.jmacs.aop.model;

import info.hexin.jmacs.aop.advisor.Advice;
import info.hexin.jmacs.aop.advisor.MethodInvoke;
import info.hexin.jmacs.aop.config.AopConfig;
import info.hexin.jmacs.ioc.annotation.Bean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Bean
public class AA extends A {
    
//    public  long test2(String xx,double x) {
//        MethodInvoke invoke = new MethodInvoke();
//        Object[] params = new Object[] {xx,x};
//        invoke.setArgs(params);
//        List<Advice> localArrayList = new ArrayList<Advice>();
//        localArrayList.add(AopConfig.getAopBean("tx"));
//        try{
//            Iterator localIterator = localArrayList.iterator();
//            do {
//                Advice localAdvice = (Advice) localIterator.next();
//                localAdvice.before(invoke);
//            } while (localIterator.hasNext());
//            
//            long result = super.test2(xx,x);
//            invoke.setReturnValue(result);
//            
//            Iterator localIterator1 = localArrayList.iterator();
//            do {
//                Advice localAdvice = (Advice) localIterator1.next();
//                localAdvice.after(invoke);
//            } while (localIterator.hasNext());
//            return result;
//        } catch (Throwable e) {
//            invoke.setThrowable(e);
//            Iterator localIterator2 = localArrayList.iterator();
//            do {
//                Advice localAdvice = (Advice) localIterator2.next();
//                localAdvice.whenException(invoke);
//            } while (localIterator2.hasNext());
//            return 0;
//        }
//    }
    
    

//    public void test(String []param,int x,double k,String xx) {
//        MethodInvoke localMethodInvoke = new MethodInvoke();
//        Object[] params = new Object[] {param,x,k,xx};
//        localMethodInvoke.setArgs(params);
//        
//        List<Advice> localArrayList = new ArrayList<Advice>();
//        localArrayList.add(AopConfig.getAopBean("tx"));
//        try {
//            Iterator localIterator = localArrayList.iterator();
//            do {
//                Advice localAdvice = (Advice) localIterator.next();
//                localAdvice.before(localMethodInvoke);
//            } while (localIterator.hasNext());
//            super.test(param,x,k,xx);
//            
//            Iterator localIterator1 = localArrayList.iterator();
//            do {
//                Advice localAdvice = (Advice) localIterator1.next();
//                localAdvice.after(localMethodInvoke);
//            } while (localIterator.hasNext());
//        } catch (Throwable e) {
//            localMethodInvoke.setThrowable(e);
//            Iterator localIterator2 = localArrayList.iterator();
//            do {
//                Advice localAdvice = (Advice) localIterator2.next();
//                localAdvice.whenException(localMethodInvoke);
//            } while (localIterator2.hasNext());
//        }
//    }
    
    
//    public long test1() {
//        MethodInvoke invoke = new MethodInvoke();
//        List<Advice> localArrayList = new ArrayList<Advice>();
//        localArrayList.add(AopConfig.getAopBean("tx"));
//        try{
//            Iterator localIterator = localArrayList.iterator();
//            do {
//                Advice localAdvice = (Advice) localIterator.next();
//                localAdvice.before(invoke);
//            } while (localIterator.hasNext());
//            
//            long result = super.test1();
//            invoke.setReturnValue(result);
//            
//            Iterator localIterator1 = localArrayList.iterator();
//            do {
//                Advice localAdvice = (Advice) localIterator1.next();
//                localAdvice.after(invoke);
//            } while (localIterator.hasNext());
//            return result;
//        } catch (Throwable e) {
//            invoke.setThrowable(e);
//            Iterator localIterator2 = localArrayList.iterator();
//            do {
//                Advice localAdvice = (Advice) localIterator2.next();
//                localAdvice.whenException(invoke);
//            } while (localIterator2.hasNext());
//            return 0;
//        }
//    }
    
    
//    public void test() {
//        MethodInvoke localMethodInvoke = new MethodInvoke();
//        List<Advice> localArrayList = new ArrayList<Advice>();
//        localArrayList.add(AopConfig.getAopBean("tx"));
//        try {
//            Iterator localIterator = localArrayList.iterator();
//            do {
//                Advice localAdvice = (Advice) localIterator.next();
//                localAdvice.before(localMethodInvoke);
//            } while (localIterator.hasNext());
//            super.test();
//            Iterator localIterator2 = localArrayList.iterator();
//            do {
//                Advice localAdvice = (Advice) localIterator2.next();
//                localAdvice.after(localMethodInvoke);
//            } while (localIterator2.hasNext());
//        } catch (Throwable e) {
//            localMethodInvoke.setThrowable(e);
//            Iterator localIterator2 = localArrayList.iterator();
//            do {
//                Advice localAdvice = (Advice) localIterator2.next();
//                localAdvice.whenException(localMethodInvoke);
//            } while (localIterator2.hasNext());
//        }
//    }
}
