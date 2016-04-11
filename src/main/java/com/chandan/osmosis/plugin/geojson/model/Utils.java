package com.chandan.osmosis.plugin.geojson.model;

import java.lang.reflect.Type;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils {

	private static ObjectMapper objectMapper = new ObjectMapper();
	
    public static String jsonEncode(Object o) throws JsonProcessingException {
        if (o == null)
            return "";
        return objectMapper.writeValueAsString(o);
    }
    
    public static Object jsonDecode(String s, Class<?> c) {
        Object u = null;
        if(s != null && !s.equals("")) {
            try {
                u = objectMapper.reader(c).readValue(s);
            } catch (Exception e) {
                return null;
            }
        }
        return u;
    }

    public static <T> T jsonDecode(byte[] data) throws Exception {
    	if (data == null) {
    		return null;
    	}
 		return (T)objectMapper.readValue(data, new TypeReference<T>() {});
    }
}
