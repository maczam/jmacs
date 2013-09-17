package info.hexin.jmacs.json;

import info.hexin.json.Json;

import org.junit.Test;

public class JsonTest {
    
    @Test
    public void test1(){
        System.out.println(Json.fromJson("12", int.class));
    }
}
