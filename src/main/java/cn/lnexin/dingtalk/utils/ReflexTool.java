package cn.lnexin.dingtalk.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * 通过反射的手段 进行一些操作
 * @className: ReflexTool
 * @Description: TODO
 * @author: lnexin@aliyun.com
 * @Date: 2016/12/3 16:47
 */
@SuppressWarnings({"rawtypes", "unused", "unchecked"})
public class ReflexTool {

    /**
     * 根据属性名获取属性值
     */
    public static Object getVal(String fieldName, Object o) {
        Object result = null;
        try {
            Field field = o.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            result = field.get(o);
        } catch (NoSuchFieldException e) {
            Logger.getLogger(ReflexTool.class.getName()).info("不存在此字段:[" + fieldName + "],NoSuchFieldException, " + e.getMessage());
        } catch (IllegalAccessException e) {
            Logger.getLogger(ReflexTool.class.getName()).info("私有成员变量权限变更错误:[" + fieldName + "], IllegalAccessException, " + e.getMessage());
        }

        return result;
    }

    /**
     * 获取属性名数组
     */
    public static String[] fieldNames(Object o) {
        Field[] fields = o.getClass().getDeclaredFields();
        String[] fieldNames = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            // System.out.println(fields[i].getType());
            fieldNames[i] = fields[i].getName();
        }
        return fieldNames;
    }

    /**
     * 获取属性类型(type)，属性名(name)，属性值(value)的map组成的list
     */

    public static List<Map> filedMapList(Object o) {
        Field[] fields = o.getClass().getDeclaredFields();
        String[] fieldNames = new String[fields.length];
        List list = new ArrayList();
        Map infoMap = null;
        for (int i = 0; i < fields.length; i++) {
            infoMap = new HashMap();
            infoMap.put("type", fields[i].getType().toString());
            infoMap.put("name", fields[i].getName());
            infoMap.put("value", getVal(fields[i].getName(), o));
            list.add(infoMap);
        }
        return list;
    }

    /**
     * 将bean 转换为 map
     * 获取 属性名(name):属性值(value) map
     */
    public static Map filedMap(Object o) {
        Map<String, Object> result = new HashMap<>();
        Field[] fields = o.getClass().getDeclaredFields();
        for (Field field : fields) {
            String name = field.getName();
            result.put(name, getVal(name, o));
        }
        return result;
    }

}
