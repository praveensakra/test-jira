package com.praveen.jira.plugin;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AppCache {
	
	private static Map<String, Object> cache = new HashMap<String, Object>();
	static{
		InputStream io = AppCache.class.getClassLoader().getResourceAsStream("config.json");
		ObjectMapper mapper = new ObjectMapper();
		try {
			
			cache.putAll(mapper.readValue(io, Map.class));
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Object get(String key){
		return cache.get(key);
	}
	
	
	public static void main(String[] args) {
		System.out.println(AppCache.get("log"));
	}

}
