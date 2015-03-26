package com.uttara.gtmanager;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Test 
{
	public static void main(String[] args) {
		JSONArray array= new JSONArray();
		JSONObject jsonObject= new JSONObject();
		jsonObject.put("name", "Doddanna");
		jsonObject.put("age", "10");
		JSONObject jsonObject1= new JSONObject();
		jsonObject1.put("name", "Shankar");
		jsonObject1.put("age", "10");
		JSONObject jsonObject2= new JSONObject();
		jsonObject2.put("name", "Rohit");
		jsonObject2.put("age", "9");
		array.add(jsonObject);
		array.add(jsonObject1);
		array.add(jsonObject2);
		System.out.println(array.toString());
	}
}
