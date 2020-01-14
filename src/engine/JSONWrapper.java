package engine;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
class JSONObjectWrapper {

	JSONObject obj;
	JSONObjectWrapper(){
		obj = new JSONObject();
	}
	JSONObjectWrapper(JSONObject obj){
		this.obj = obj;
	}
	
	JSONObjectWrapper(String path) throws FileNotFoundException{
		File f = new File(path);
		InputStream is = new FileInputStream(f);
        JSONTokener tokener = new JSONTokener(is);
        this.obj = new JSONObject(tokener);
	}
	
	
	@Override
	public String toString() {
		return obj.toString();
	}
	public String toString(int indentation) {
		return obj.toString(indentation);
	}
}

class JSONArrayWrapper{
	JSONArray arr;
	
	JSONArrayWrapper(){
		arr = new JSONArray();
	}
	JSONArrayWrapper(JSONArray arr){
		this.arr = arr;
	}
	
	JSONArrayWrapper(String path) throws FileNotFoundException{
		File f = new File(path);
		InputStream is = new FileInputStream(f);
        JSONTokener tokener = new JSONTokener(is);
        this.arr = new JSONArray(tokener);
	}
	
	@Override
	public String toString() {
		return arr.toString();
	}
	public String toString(int indentation) {
		return arr.toString(indentation);
	}
}