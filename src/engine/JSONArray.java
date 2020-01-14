package engine;
import java.io.FileNotFoundException;

import org.json.JSONException;


public class JSONArray {
	JSONArrayWrapper json;
	
	public JSONArray(){
		json = new JSONArrayWrapper();
	}
	
	JSONArray(JSONArrayWrapper jsaw){
		json = jsaw;
	}
	
	JSONArray(String path) throws FileNotFoundException{
		json = new JSONArrayWrapper(path);
	}
	
	public String getString(int index) {
		return json.arr.getString(index);
	}
	
	public String getString(int index, String defaultValue) {
		try {
			String t = json.arr.getString(index);
			return t;
		}catch(JSONException e) {
			return defaultValue;
		}
	}
	
	public int getInt(int index) {
		return json.arr.getInt(index);
	}
	
	public int getInt(int index, int defaultValue) {
		try {
			int t = json.arr.getInt(index);
			return t;
		}catch(JSONException e) {
			return defaultValue;
		}
	}
	
	public float getFloat(int index) {
		return json.arr.getFloat(index);
	}
	
	public float getFloat(int index, float defaultValue) {
		try {
			float t = json.arr.getFloat(index);
			return t;
		}catch(JSONException e) {
			return defaultValue;
		}
	}
	
	public boolean getBoolean(int index) {
		return json.arr.getBoolean(index);
	}
	
	
	public boolean getBoolean(int index, boolean defaultValue) {
		try {
			boolean t = json.arr.getBoolean(index);
			return t;
		}catch(JSONException e) {
			return defaultValue;
		}
	}

	public JSONArray getJSONArray(int index) {
		return new JSONArray(new JSONArrayWrapper(json.arr.getJSONArray(index)));
	}
	
	public JSONObject getJSONObject(int index) {
		return new JSONObject(new JSONObjectWrapper(json.arr.getJSONObject(index)));
	}
	
	public boolean isNull(int index) {
		return json.arr.isNull(index);
	}
	
	public void setString(int index, String value) {
		json.arr.put(index, value);
	}
	
	public void setInt(int index, int value) {
		json.arr.put(index, Integer.valueOf(value));
	}
	public void setFloat(int index, double value) {
		json.arr.put(index, (float) value);
	}
	
	public void setBoolean(int index, boolean value) {
		json.arr.put(index, Boolean.valueOf(value));
	}
	
	public void setJSONObject(int index, JSONObject value) {
		json.arr.put(index, value.json.obj);
	}
	
	public void setJSONArray(int index, JSONArray value) {
		json.arr.put(index, value.json.arr);
	}
	
	@Override
	public String toString() {
		return json.toString();
	}
	
	public String toString(int indentation) {
		return json.toString(indentation);
	}
}
