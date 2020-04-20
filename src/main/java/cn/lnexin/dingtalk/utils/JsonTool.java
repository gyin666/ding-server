package cn.lnexin.dingtalk.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Jackson 工具类 禁止工具类内部捕获关键异常, 关键异常交由业务处理
 *
 * @author lnexin@foxmail.com
 **/
@SuppressWarnings({"unchecked", "rawtypes", "restriction"})
public class JsonTool {
    /**
     * private static final ObjectMapper mapper = new ObjectMapper();
     * <p>
     * static {
     * //反序列化  string >> entity
     * <p>
     * //允许值中换行
     * mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
     * //允许注释
     * //mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
     * <p>
     * //允许单引号
     * //mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
     * //允许使用未带引号的字段名
     * //mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
     * mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
     * <p>
     * //entity >> string
     * mapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
     *
     * @Override public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
     * gen.writeString("");
     * }
     * });
     * mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
     * // 解决序列化是没有继承serizliaze 接口导致的异常
     * mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
     * mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
     * }
     */
    private JsonTool() {
    }

    public static String toJSON(Object bean) {

        if (bean == null) {
            return "";
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
            @Override
            public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                gen.writeString("");
            }
        });
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        // 解决序列化是没有继承serizliaze 接口导致的异常
        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
        try {
            return mapper.writeValueAsString(bean);
        } catch (JsonGenerationException e) {
            Logger.getLogger(JsonTool.class.getName()).info("[toJSON]JsonGenerationException 异常," + e.getMessage());
        } catch (IOException e) {
            Logger.getLogger(JsonTool.class.getName()).info("[toJSON]JsonMappingException 异常," + e.getMessage());
        }
        return "";
    }

    public static String objToJSONString(Object obj) {
        if (obj != null) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
                @Override
                public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                    gen.writeString("");
                }
            });
            try {
                return mapper.writeValueAsString(obj);
            } catch (JsonGenerationException e) {
                Logger.getLogger(JsonTool.class.getName()).info("JsonGenerationException 异常," + e.getMessage());
            } catch (JsonMappingException e) {
                Logger.getLogger(JsonTool.class.getName()).info("JsonMappingException 异常," + e.getMessage());
            } catch (IOException e) {
                Logger.getLogger(JsonTool.class.getName()).info("IOException 异常," + e.getMessage());
            }

        }
        return "";
    }

    public static JsonNode getNode(String jsonStr) {
        if (jsonStr == null || jsonStr.equals("")) {
            return emptyNode();
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        mapper.configure(JsonGenerator.Feature.QUOTE_FIELD_NAMES, false);
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        //mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        try {
            return mapper.readTree(jsonStr);
        } catch (IOException e) {
            Logger.getLogger(JsonTool.class.getName()).info("json 转换出错!, 源字符串为:" + jsonStr + ", \n 异常原因:" + e);
            e.printStackTrace();
        }
        return emptyNode();
    }

    public static ObjectNode getNode(Object bean) {
        return bean != null ? new ObjectMapper().valueToTree(bean) : emptyNode();
    }

    public static ObjectNode emptyNode() {
        return new ObjectMapper().createObjectNode();
    }

    public static Map<String, Object> node2map(JsonNode node) {
        Map<String, Object> m = new LinkedHashMap<>();
        for (Iterator<Map.Entry<String, JsonNode>> it = node.fields(); it.hasNext(); ) {
            Map.Entry<String, JsonNode> next = it.next();
            String key = next.getKey();
            JsonNode val = node.get(key);
            m.put(next.getKey(), val.textValue() != null ? val.textValue() : next.getValue());
        }
        return m;
    }

    public static List parseArray(String departmentText) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(departmentText, List.class);
        } catch (JsonParseException e) {
            Logger.getLogger(JsonTool.class.getName()).info("parseArray::JsonParseException");
        } catch (JsonMappingException e) {
            Logger.getLogger(JsonTool.class.getName()).info("parseArray::JsonMappingException");
        } catch (IOException e) {
            Logger.getLogger(JsonTool.class.getName()).info("parseArray::IOException");
        }
        return null;
    }
}
