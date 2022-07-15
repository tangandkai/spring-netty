package com.tang.boot.cache;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * json工具类
 * @author tangwenkai
 * @date 2021-09-22 16:50 2021-09-22 20:04
 */
@Slf4j
public class JsonUtil {

    /**
     * 将对象序列化为字符串
     * @param obj
     * @return
     */
    public static String serialize(Object obj){
        //为空对象，不需要序列化，直接返回即可
        if (null==obj){
            return null;
        }
        return JSONObject.toJSONString(obj);
    }

    /**
     * 反序列化
     * @param jsonStr
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> T deserialize(String jsonStr,Class<T> tClass){
        if (null==jsonStr){
            throw new IllegalArgumentException("src should not be null");
        }
        if("{}".equals(jsonStr.trim())) {
            return null;
        }
        return JSON.parseObject(jsonStr, tClass);
    }

    public static void main(String[] args) {
        List<Map> list = new ArrayList<>();
        Map map = new HashMap();
        map.put("1","nice");
        map.put("2","good");
        list.add(map);
        final String serialize = serialize(list);
        log.info("serialize {}",serialize);

        final List<Map> deserialize = deserialize(serialize, List.class);
        log.info("deserialize {}",deserialize);
        final Map map1 = deserialize.get(0);
        log.info("map1 {}",map1);
    }
}
