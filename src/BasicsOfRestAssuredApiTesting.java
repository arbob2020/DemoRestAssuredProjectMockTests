import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import storedFiles.Payload;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class BasicsOfRestAssuredApiTesting {

	public static void main(String[] args) {
		

//  Validate if Add place API is working as expected
//	Given- all in put details. The Jason Body basically
//	When - Submit the API. The resource Basically goes here
//	Then - Validates the Response
//		Add place - > Update Place with new Address- > Get Place to validate if New Place is present in response

		RestAssured.baseURI="https://rahulshettyacademy.com";
	String response=given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json").body(Payload.AddPlace()).when().post("/maps/api/place/add/json")
		.then().assertThat().statusCode(200).body("scope", equalTo("APP"))
		.header("server", "Apache/2.4.18 (Ubuntu)").extract().response().asString();

	System.out.println(response);
	
	JsonPath js=new JsonPath(response); // for parsing jason
	String placeId=js.getString("place_id");
	System.out.println(placeId);
	
	//Update Place 
	
	given().log().all().queryParam("key", "qaclick123").head("Content-Type", "application/jason")
	.body("{\r\n" 
		+ "    \"status\": \"OK\",\r\n"
		+ "    \"place_id\": \"eba79d1aac3105f7a5d429bc059c4867\",\r\n"
		+ "    \"scope\": \"APP\",\r\n"
		+ "    \"reference\": \"7b3540ffc8d85c8259d6862616747c9d7b3540ffc8d85c8259d6862616747c9d\",\r\n"
		+ "    \"id\": \"7b3540ffc8d85c8259d6862616747c9d\"\r\n"
		+ "}")
	.when().put("maps/api/place/update/jason")
	.then().assertThat();
	
	
	
	}

}
