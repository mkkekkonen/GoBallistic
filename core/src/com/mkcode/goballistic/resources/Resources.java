package com.mkcode.goballistic.resources;

import java.util.HashMap;
import java.util.Map;

public class Resources {

	public static Language lang = Language.FI;
	
	private static Map<Language, Map<String, String>> resourceMap;
	
	private Map<String, String> fiResources;
	private Map<String, String> enResources;
	
	public Resources() {
	
		fiResources = new HashMap<String, String>();
		enResources = new HashMap<String, String>();
		
		initFiResources();
		initEnResources();
		
		resourceMap = new HashMap<Language, Map<String, String>>();
		
		resourceMap.put(Language.FI, fiResources);
		resourceMap.put(Language.EN, enResources);
	}
	
	public static String tr(String key) {
		
		if(!resourceMap.get(lang).containsKey(key))
			return "";
		
		return resourceMap.get(lang).get(key);
	}
	
	private void initFiResources() {
		fiResources.put("angle", "Kulma °");
		fiResources.put("inputAngle", "Syötä kulma");
		fiResources.put("force", "Voima N");
		fiResources.put("inputForce", "Syötä voima");
	}
	
	private void initEnResources() {
		enResources.put("angle", "Angle °");
		enResources.put("inputAngle", "Input an angle");
		enResources.put("force", "Force N");
		enResources.put("inputForce", "Input a force");
	}
}