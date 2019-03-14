package simpleioc;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Copyright (C), 2015-2019, XXX有限公司
 * FileName: SimpleIOC
 * Author:   copywang
 * Date:     2019/3/13 13:08
 * Description: IOC实现类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

public class SimpleIOC {

  /**
   * 加载 xml 配置文件，遍历其中的标签
   * 获取标签中的 id 和 class 属性，加载 class 属性对应的类，并创建 bean
   * 遍历标签中的标签，获取属性值，并将属性值填充到 bean 中
   * 将 bean 注册到 bean 容器中
   */
  private Map<String, Object> beanMap = new HashMap<>();

  public SimpleIOC(String location) throws Exception {
    loadBeans(location);
  }

  public Object getBean(String name) {
    Object bean = beanMap.get(name);
    if (bean == null) {
      throw new IllegalArgumentException("there is no bean with name " + name);
    }

    return bean;
  }

  private void loadBeans(String location) throws Exception {
    // 加载 xml 配置文件
    InputStream inputStream = new FileInputStream(location);
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder docBuilder = factory.newDocumentBuilder();
    Document doc = docBuilder.parse(inputStream);
    Element root = doc.getDocumentElement();
    NodeList nodes = root.getChildNodes();

    // 遍历 <bean> 标签
    for (int i = 0; i < nodes.getLength(); i++) {
      Node node = nodes.item(i);
      if (node instanceof Element) {
        Element ele = (Element) node;
        String id = ele.getAttribute("id");
        String className = ele.getAttribute("class");

        // 加载 beanClass
        Class beanClass = null;
        try {
          beanClass = Class.forName(className);
        } catch (ClassNotFoundException e) {
          e.printStackTrace();
          return;
        }

        // 创建 bean
        Object bean = beanClass.newInstance();

        // 遍历 <property> 标签
        NodeList propertyNodes = ele.getElementsByTagName("property");
        for (int j = 0; j < propertyNodes.getLength(); j++) {
          Node propertyNode = propertyNodes.item(j);
          if (propertyNode instanceof Element) {
            Element propertyElement = (Element) propertyNode;
            String name = propertyElement.getAttribute("name");
            String value = propertyElement.getAttribute("value");

            // 利用反射将 bean 相关字段访问权限设为可访问
            Field declaredField = bean.getClass().getDeclaredField(name);
            declaredField.setAccessible(true);

            if (value != null && value.length() > 0) {
              // 将属性值填充到相关字段中
              declaredField.set(bean, value);
            } else {
              String ref = propertyElement.getAttribute("ref");
              if (ref == null || ref.length() == 0) {
                throw new IllegalArgumentException("ref config error");
              }

              // 将引用填充到相关字段中
              declaredField.set(bean, getBean(ref));
            }

            // 将 bean 注册到 bean 容器中
            registerBean(id, bean);
          }
        }
      }
    }
  }

  private void registerBean(String id, Object bean) {
    beanMap.put(id, bean);
  }
}
