package info.hexin.jmacs.ioc.loader.impl;

import info.hexin.jmacs.config.PropertyFiles;
import info.hexin.jmacs.ioc.IocBean;
import info.hexin.jmacs.ioc.Iocs;
import info.hexin.jmacs.ioc.Property;
import info.hexin.jmacs.ioc.annotation.Scope;
import info.hexin.jmacs.log.Log;
import info.hexin.jmacs.log.Logs;
import info.hexin.lang.Exceptions;
import info.hexin.lang.string.Strings;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class XmlLoader extends AbstractLoader {

    static Log log = Logs.get();
    static Pattern pattern = Pattern.compile("^\\$\\{([\\w.]+)\\}");
    Map<String, String> defaultValueMap = new HashMap<String, String>();
    static boolean DEBUG = true;

    public XmlLoader(String... xPath) {
        loadXpath(xPath);
        injectDefaultValue();
    }

    /**
     *
     */
    private void injectDefaultValue() {
        for (Map.Entry<String, IocBean> entry : beanMap.entrySet()) {
            IocBean iocBean = entry.getValue();
            for (Property property : iocBean.getPropertiesList()) {
                String value = property.getValue();
                if (Strings.isNotBlank(value)) {
                    Matcher matcher = pattern.matcher(value);
                    if (matcher.find() && matcher.groupCount() == 1) {
                        String propertyName = matcher.group(1);
                        String defaultValue = defaultValueMap.get(propertyName);
                        if (Strings.isNotNull(defaultValue)) {
                            property.setValue(defaultValue.trim());
                        } else {
                            throw Exceptions.make(String.format("请查看property配置文件中是否存在%s的值", propertyName));
                        }
                    }
                }
            }
        }
    }

    private Class<?> getPropertiesLoadClass() {
        return PropertyFiles.class;
    }

    void loadXpath(String... xPath) {
        String propertiesClassName = getPropertiesLoadClass().getName();
        for (String path : xPath) {
            path = path.replaceAll("\\.", "/").replace("/xml", ".xml");
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path);
            if (inputStream == null) {
                throw Exceptions.make(String.format("配置文件 %s 没有找到", path));
            }
            try {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document document = builder.parse(inputStream);
                Element root = document.getDocumentElement();
                NodeList beanList = root.getElementsByTagName("bean");
                for (int i = 0; i < beanList.getLength(); i++) {
                    Element beanNode = (Element) beanList.item(i);
                    // 基本信息
                    String id = beanNode.getAttribute("id");
                    String className = beanNode.getAttribute("class");
                    String scope = beanNode.getAttribute("scope");
                    NodeList properties = beanNode.getElementsByTagName("property");

                    log.info("load id >>>> {} , className >>>> {}", id, className);

                    // load properties
                    if (propertiesClassName.equals(className)) {
                        for (int j = 0; j < properties.getLength(); j++) {
                            Element propNode = (Element) properties.item(j);
                            String name = propNode.getAttribute("name");
                            String value = propNode.getAttribute("value");

                            if (DEBUG) {
                                log.debug("load propertiesFile >>> {}", value);
                            }
                            if ("locations".equals(name) && Strings.isNotBlank(value)) {
                                loadProperties(value);
                            }
                        }
                    }
                    // bean
                    else {
                        String simpleName = null;
                        if (className.indexOf(".") >= 0) {
                            simpleName = className.substring(className.lastIndexOf(".") + 1);
                        } else {
                            simpleName = className;
                        }

                        id = Strings.isNotBlank(id) ? id : Strings.lowerFirst(simpleName);
                        Class<?> klass = Class.forName(className);

                        IocBean iocBean = new IocBean();
                        iocBean.setName(id);
                        iocBean.setScope(Scope.of(scope));
                        iocBean.setClassName(className);
                        iocBean.setClazz(klass);

                        List<Property> propertiesList = new ArrayList<Property>();
                        for (int j = 0; j < properties.getLength(); j++) {
                            Property property = new Property();
                            Element propNode = (Element) properties.item(j);

                            String name = propNode.getAttribute("name");
                            if (Strings.isNotBlank(name)) {
                                property.setName(name);
                            }

                            String ref = propNode.getAttribute("ref");
                            if (Strings.isNotBlank(ref)) {
                                property.setRef(ref);
                            }

                            String value = propNode.getAttribute("value");
                            if (Strings.isNotBlank(value)) {
                                property.setValue(value);
                                Field field = Iocs.findClassFiled(klass, name);
                                if (field != null) {
                                    property.setField(field);
                                }
                            }
                            propertiesList.add(property);
                        }
                        iocBean.setPropertiesList(propertiesList);
                        loadInterFace(klass);
                        log.debug(iocBean.getPropertiesList());
                        beanMap.put(id, iocBean);
                    }
                }
            } catch (Exception e) {
                throw Exceptions.make(e);
            } finally {
                try {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void loadProperties(String path) {
        String[] paths = path.split(";");
        for (String xx : paths) {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(xx);
            Properties properties = new Properties();
            try {
                properties.load(inputStream);
                for (Entry<Object, Object> entry : properties.entrySet()) {
                    Object key = entry.getKey();
                    Object value = entry.getValue();
                    if (key != null && value != null) {
                        if (defaultValueMap.containsKey(key.toString())) {
                            throw Exceptions.make("");
                        } else {
                            defaultValueMap.put(key.toString(), value.toString());
                        }
                    }
                }
            } catch (IOException e) {
                throw Exceptions.make(e);
            } finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
