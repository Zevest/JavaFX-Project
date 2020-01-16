package engine;
import java.io.FileNotFoundException;
import java.util.Set;

import org.json.JSONException;


public class JSONObject {
	JSONObjectWrapper json;
	public JSONObject(){
		json = new JSONObjectWrapper();
	}
	
	JSONObject(JSONObjectWrapper jsow){
		json = jsow;
	}
	
	JSONObject(String path) throws FileNotFoundException{
		json = new JSONObjectWrapper(path);
	}
	

	public String getString(String key) {
		return json.obj.getString(key);
	}
	
	public String getString(String key, String defaultValue) {
		try {
			String t = json.obj.getString(key);
			return t;
		}catch(JSONException e) {
			return defaultValue;
		}
	}
	
	public int getInt(String key) {
		return json.obj.getInt(key);
	}
	
	public int getInt(String key, int defaultValue) {
		try {
			int t = json.obj.getInt(key);
			return t;
		}catch(JSONException e) {
			return defaultValue;
		}
	}
	
	public float getFloat(String key) {
		return json.obj.getFloat(key);
	}
	
	public float getFloat(String key, float defaultValue) {
		try {
			float t = json.obj.getFloat(key);
			return t;
		}catch(JSONException e) {
			return defaultValue;
		}
	}
	
	public boolean getBoolean(String key) {
		return json.obj.getBoolean(key);
	}
	
	
	public boolean getBoolean(String key, boolean defaultValue) {
		try {
			boolean t = json.obj.getBoolean(key);
			return t;
		}catch(JSONException e) {
			return defaultValue;
		}
	}

	public JSONArray getJSONArray(String key) {
		return new JSONArray(new JSONArrayWrapper(json.obj.getJSONArray(key)));
	}
	
	public JSONObject getJSONObject(String key) {
		return new JSONObject(new JSONObjectWrapper(json.obj.getJSONObject(key)));
	}
	
	public boolean isNull(String key) {
		return json.obj.isNull(key);
	}
	
	public void setString(String key, String value) {
		json.obj.put(key, value);
	}
	
	public void setInt(String key, int value) {
		json.obj.put(key, Integer.valueOf(value));
	}
	public void setFloat(String key, double value) {
		json.obj.put(key, (float) value);
	}
	
	public void setBoolean(String key, boolean value) {
		json.obj.put(key, Boolean.valueOf(value));
	}
	
	public void setJSONObject(String key, JSONObject value) {
		json.obj.put(key, value.json.obj);
	}
	
	public void setJSONArray(String key, JSONArray value) {
		json.obj.put(key, value.json.arr);
	}
	
	public Set<String> keys() {
		return json.obj.keySet();
	}
	
	
	@Override
	public String toString() {
		return json.toString();
	}
	public String toString(int indentation) {
		return json.toString(indentation);
	}
	
}
