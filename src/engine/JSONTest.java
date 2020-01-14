package engine;

public class JSONTest {
	public static void main(String[] args) {
		JSONObject test = new JSONObject();
		test.setFloat("PI", 3.141);
		test.setString("Name" ,"Steve");
		test.setInt("num", 50);
		test.setBoolean("Is", false);
		JSONArray arr = new JSONArray();
		arr.setJSONObject(0, test);
		System.out.println(arr);
		
	}
}
