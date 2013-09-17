package info.hexin.jmacs.json.serialize;

import info.hexin.jmacs.json.model.A;
import info.hexin.json.Json;
import info.hexin.lang.Assert;

import java.util.Date;

import org.junit.Test;

public class JsonSerializeTest {
    @Test
    public void testOject() {
        A a = new A();
        a.setName("aaaaa");
        a.setPassword("bbbbb");
        a.setDate(new Date());

        A u1 = new A();
        u1.setName("ccccc");
        u1.setPassword("ddddd");
        u1.setDate(new Date());

        a.setChildern(u1);
        String json = Json.toJson(a);

        A u = Json.fromJson(json, A.class);
        Assert.equals("ccccc", u.getChildern().getName());
        Assert.equals("aaaaa", u.getName());
    }
}
