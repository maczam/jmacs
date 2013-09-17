package info.hexin.jmacs.json.parser;

import static org.junit.Assert.assertEquals;
import info.hexin.json.Json;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class JsonParserTest {
     @Test
    public void testParser1() {
        String ss = "[\"123\"]";
        Object o = Json.fromJson(ss);
        System.out.println(o);
        assertEquals(ArrayList.class, o.getClass());
        List<String> l = (List<String>) o;
        assertEquals(1, l.size());
    }

     @Test
    public void testParser2() {
        String ss = "[\"123\",234]";
        Object o = Json.fromJson(ss);
        System.out.println(o);
        assertEquals(ArrayList.class, o.getClass());
        List<String> l = (List<String>) o;
        assertEquals(2, l.size());
    }

     @Test
    public void testObject1() {
        String s1 = "{name:\"123\",password:\"234\"}";
        Object o1 = Json.fromJson(s1);
        System.out.println(o1);
    }

     @Test
    public void testObject2() {
        String s1 = "{\"name\":\"123\",password:\"234\"}";
        Object o1 = Json.fromJson(s1);
        System.out.println(o1);
    }

     @Test
    public void testObject3() {
        String s2 = "{\"name\":\"123\",\"password\":{name:\"456\"}}";
        Object o2 = Json.fromJson(s2);
        System.out.println(o2);
    }

     @Test
    public void testObject4() {
        String s3 = "{\"name\":\"123\",password:{name:\"456\"}}";
        Object o3 = Json.fromJson(s3);
        System.out.println(o3);
    }

//    @Test
    public void testArrayList() {
        String s = "[{\"name\":\"aaa\",\"password\":{\"name\":\"bbb\"}},{\"name\":\"ccc\",\"password\":{\"name\":\"dddd\"}}]";
        Object o3 = Json.fromJson(s);
        System.out.println(o3);
    }
    
//    @Test
    public void testArray2(){
        String s = "{\"name\":\"aaa\",\"password\":{\"name\":\"bbb\"},array:[\"cccc\",\"dddd\"]}";
        Object o3 = Json.fromJson(s);
        System.out.println(o3);
    }
    
//    @Test
    public void testArray3(){
        String s = "{_fields_:[{_public_:false,_static_:false,name:\"fied2\",value:null}]}";
        Object o3 = Json.fromJson(s);
        System.out.println(o3);
    }
//    @Test
    public void testArray4(){
        String s = "{\"methods\":[{_public_:true,_static_:false,_name_:\"method1\"},{_public_:true,_static_:true,_name_:\"method1\"}],_fields_:[{_public_:false,_static_:false,name:\"fied2\",value:null},{_public_:false,_static_:false,name:\"fied1\",value:null}],_name_:\"123\",_classname_:\"com.dinglicom.cloud.jmp.type.jsonrpc.service.ServiceA\"}";
        Object o3 = Json.fromJson(s);
        System.out.println(o3);
    }
    
    @Test
    public void testArray5(){
        String ss = "[{\"_methods_\":[{\"_public_\":true,\"_static_\":false,\"_name_\":\"find\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"save\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"delete\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"update\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"findAll\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"findAllByRoleName\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"findById\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"setMenuDao\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"setRoleDao\"}],\"_fields_\":[],\"_name_\":\"MenuServiceImpl\",\"_classname_\":\"com.dinglicom.cloud.jmp.persistence.service.impl.MenuServiceImpl\"},{\"_methods_\":[{\"_public_\":true,\"_static_\":false,\"_name_\":\"delete\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"update\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"setMongoDao\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"findAll\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"saveAndUpdate\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"findFields\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"findToolbar\"}],\"_fields_\":[],\"_name_\":\"BarServiceImpl\",\"_classname_\":\"com.dinglicom.cloud.jmp.persistence.service.impl.BarServiceImpl\"},{\"_methods_\":[{\"_public_\":true,\"_static_\":false,\"_name_\":\"setMongoDao\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"viewBalanceData\"}],\"_fields_\":[],\"_name_\":\"SystemServiceImpl\",\"_classname_\":\"com.dinglicom.cloud.jmp.persistence.service.impl.SystemServiceImpl\"},{\"_methods_\":[{\"_public_\":true,\"_static_\":false,\"_name_\":\"save\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"delete\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"update\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"setMongoDao\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"findAll\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"findByName\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"initializedAdminUser\"}],\"_fields_\":[],\"_name_\":\"UserServiceImpl\",\"_classname_\":\"com.dinglicom.cloud.jmp.persistence.service.impl.UserServiceImpl\"},{\"_methods_\":[{\"_public_\":true,\"_static_\":false,\"_name_\":\"destroyed\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"initialized\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"setMongoDao\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"setUserService\"}],\"_fields_\":[],\"_name_\":\"SiteServiceImpl\",\"_classname_\":\"com.dinglicom.cloud.jmp.persistence.service.impl.SiteServiceImpl\"},{\"_methods_\":[{\"_public_\":true,\"_static_\":true,\"_name_\":\"method1\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"method1\"}],\"_fields_\":[],\"_name_\":\"JsonServiceTest\",\"_classname_\":\"com.dinglicom.cloud.jmp.persistence.service.impl.JsonServiceTest\"},{\"_methods_\":[{\"_public_\":true,\"_static_\":false,\"_name_\":\"save\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"delete\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"create\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"setMongoDao\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"findAll\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"findAllByHostId\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"updateUserId\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"updateJvm\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"releaseUserId\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"findNoUse\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"jvmLog\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"jvmAddUserId\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"saveJVM\"}],\"_fields_\":[],\"_name_\":\"JvmServiceImpl\",\"_classname_\":\"com.dinglicom.cloud.jmp.persistence.service.impl.JvmServiceImpl\"},{\"_methods_\":[{\"_public_\":true,\"_static_\":false,\"_name_\":\"find\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"delete\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"insert\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"update\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"setPageDefineDao\"}],\"_fields_\":[],\"_name_\":\"PageDefineService\",\"_classname_\":\"com.dinglicom.cloud.jmp.persistence.service.page.PageDefineService\"},{\"_methods_\":[{\"_public_\":true,\"_static_\":false,\"_name_\":\"find\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"save\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"delete\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"release\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"update\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"setMongoDao\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"findAll\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"finadApps\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"startUseVhost\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"setVhostDao\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"setAppVhostDao\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"setAppJvmDao\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"stopUseVhost\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"addJvm\"}],\"_fields_\":[],\"_name_\":\"VhostServiceImpl\",\"_classname_\":\"com.dinglicom.cloud.jmp.persistence.service.impl.VhostServiceImpl\"},{\"_methods_\":[{\"_public_\":true,\"_static_\":false,\"_name_\":\"setMongoDao\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"clusterSave\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"clusterFindAll\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"hostFindAll\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"clusterDelete\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"updateDelete\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"hostSave\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"hostDelete\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"hostUpdate\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"hostFindOne\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"getClusterDao\"}],\"_fields_\":[],\"_name_\":\"ClusterServiceImpl\",\"_classname_\":\"com.dinglicom.cloud.jmp.persistence.service.impl.ClusterServiceImpl\"},{\"_methods_\":[{\"_public_\":true,\"_static_\":false,\"_name_\":\"delete\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"update\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"findAll\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"saveAndUpdate\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"setRoleDao\"}],\"_fields_\":[],\"_name_\":\"RoleServiceImpl\",\"_classname_\":\"com.dinglicom.cloud.jmp.persistence.service.impl.RoleServiceImpl\"},{\"_methods_\":[{\"_public_\":true,\"_static_\":false,\"_name_\":\"save\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"delete\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"deployAndStartApp\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"setMongoDao\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"changeAppStatus\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"verifyApp\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"findVhosts\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"balance\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"downResultResult\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"getJvms\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"findUserFile\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"deployApp\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"startApp\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"stopApp\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"setVhostService\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"uploadFile\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"deployCheckSystem_old\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"getBalanceUrl\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"findAppByAppName\"}],\"_fields_\":[],\"_name_\":\"AppServiceImpl\",\"_classname_\":\"com.dinglicom.cloud.jmp.persistence.service.impl.AppServiceImpl\"},{\"_methods_\":[{\"_public_\":true,\"_static_\":false,\"_name_\":\"getFields\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"addField\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"getAllViewTypes\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"getViewTypesByID\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"getRootViewTypes\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"getViewTypesByName\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"getInstacesDefine\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"getDisplays\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"getOperatesFilter\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"getStaticFields\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"getOperatesDefine\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"getOperatesByType\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"addViewType\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"deleteViewType\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"updateViewType\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"getViewTypeChildren\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"getInheritViewTypes\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"updateField\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"deleteField\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"addOperate\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"updateOperate\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"deleteOperate\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"setOperatesFilter\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"addDisplay\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"updateDisplay\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"deleteDisplay\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"setInstancesDefine\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"getOperateByID\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"addStaticField\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"updateStaticField\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"deleteStaticField\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"addStaticOperate\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"updateStaticOperate\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"deleteStaticOperate\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"getStaticOperates\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"getStaticOperatesByType\"},{\"_public_\":true,\"_static_\":false,\"_name_\":\"getOperateDisplay\"}],\"_fields_\":[],\"_name_\":\"ViewTypeServiceMongoImpl\",\"_classname_\":\"com.dinglicom.cloud.jmp.persistence.service.manager.impl.ViewTypeServiceMongoImpl\"}]";
        Object o = Json.fromJson(ss);
        System.out.println("objct >> "+o);
        String json =  Json.toJson(o);
        System.out.println(json);
    }
    
    @Test
    public void test6(){
        A a = new A();
        B b = new B();
        String [] ss = new String[0];
        System.out.println(Json.toJson(a));
        System.out.println(Json.toJson(b));
        System.out.println(Json.toJson(ss));
    }
}

class A{
    String aa = "";
}

class B{
    List<String> list = new ArrayList<String>();
}

