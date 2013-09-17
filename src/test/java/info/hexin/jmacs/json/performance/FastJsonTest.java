package info.hexin.jmacs.json.performance;

import info.hexin.jmacs.json.model.C;
import info.hexin.json.Json;
import info.hexin.lang.Stopwatch;

import java.util.Date;

import org.junit.Test;

import com.alibaba.fastjson.JSON;


public class FastJsonTest {
    
    @Test
    public void test(){
        C c = new C();
        c.setName("aaaaa");
        c.setPassword("bbbbb");
        c.setDate(new Date());

        C userC1 = new C();
        userC1.setName("cccc");
        userC1.setPassword("dddd");
        userC1.setDate(new Date());

        c.getChildren().add(userC1);

        Stopwatch stopwatch = Stopwatch.begin();
        for(int i = 0 ; i < 1 ; i ++){
            Json.toJson(c);
        }
        stopwatch.stop();
        System.out.println("json toJson时间 》》》 "+stopwatch.getDuration());
        
        stopwatch.reset();
        for(int i = 0 ; i < 1 ; i ++){
            JSON.toJSON(c);
        }
        stopwatch.stop();
        System.out.println("JSON toJSON时间 》》》 "+stopwatch.getDuration());
        
        Stopwatch stopwatch1 = Stopwatch.begin();
        for(int i = 0 ; i < 1 ; i ++){
            Json.toJson(c);
        }
        stopwatch1.stop();
        System.out.println("json toJson时间 》》》 "+stopwatch1.getDuration());
        
        stopwatch1.reset();
        for(int i = 0 ; i < 1 ; i ++){
            Json.toJson(c);
        }
        stopwatch1.stop();
        System.out.println("JSON toJSON时间 》》》 "+stopwatch1.getDuration());
    }
    
    
    @Test
    public void testParse() {
        String s = "{\"methods\":[{_public_:true,_static_:false,_name_:\"method1\"},{_public_:true,_static_:true,_name_:\"method1\"}],_fields_:[{_public_:false,_static_:false,name:\"fied2\",value:null},{_public_:false,_static_:false,name:\"fied1\",value:null}],_name_:\"123\",_classname_:\"com.dinglicom.cloud.jmp.type.jsonrpc.service.ServiceA\"}";
        Stopwatch stopwatch = Stopwatch.begin();
        for(int i = 0 ; i < 1 ; i ++){
            Object o = Json.fromJson(s);
            System.out.println(Json.toJson(o));
        }
        stopwatch.stop();
        System.out.println("json fromJson时间 》》》 "+stopwatch.getDuration());
        
        stopwatch.reset();
        for(int i = 0 ; i < 1 ; i ++){
           Object o =  JSON.parse(s);
           System.out.println(JSON.toJSON(o));
        }
        stopwatch.stop();
        System.out.println("JSON parse时间 》》》 "+stopwatch.getDuration());
    }
}
