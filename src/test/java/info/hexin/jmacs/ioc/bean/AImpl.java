package info.hexin.jmacs.ioc.bean;

import info.hexin.jmacs.ioc.annotation.Bean;

@Bean
public class AImpl implements IA {
    @Override
    public String say() {
        return "AImpl";
    }
}
