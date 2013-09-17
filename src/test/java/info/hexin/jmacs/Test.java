package info.hexin.jmacs;

import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class Test {
    public static void main(String[] args) {
        
        SerializerFeature[] serializerFeature = {SerializerFeature.WriteDateUseDateFormat};

        System.out.println(JSON.toJSONString(new A(new Date()),serializerFeature));

    }
}

class A {
    public A(Date date2) {
        this.date = date2;
    }

    Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
