package beginning;

import files.Payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Basics2 {
    public static void main(String[] args) {

        //Add place -> update place with new address -> get place  to validate if new address is present in response

        RestAssured.baseURI = "https://rahulshettyacademy.com";
        String response = given().log().all().queryParam("key","qaclick123").header("Content-Type", "application/json")
                .body(Payload.addPlace()).when().post("maps/api/place/add/json")
                .then().assertThat().statusCode(200).body("scope", equalTo("APP"))
                .header("server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();

        System.out.println(response);
        //JsonPath takes String and converts it to JSON
        //and it will help to parse json
        JsonPath jp = new JsonPath(response); //for parsing json
        String placeId = jp.getString("place_id");
        //now value are stored in this JSON variable
        System.out.println(placeId);

        //update place
        String newAdress = "70 Summer walk, USA";
        given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
                .body("{\n" +
                        "\"place_id\":\""+placeId+"\",\n" +
                        "\"address\":\""+newAdress+"\",\n" +
                        "\"key\":\"qaclick123\"\n" +
                        "}\n").when().put("maps/api/place/update/json")
                .then().assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));

        //get place
        given().log().all().queryParam("key","qaclick123").queryParam("place_id", placeId)
                .when().get("maps/api/place/get/json")
                .then().assertThat().log().all().statusCode(200).body("address", equalTo(newAdress));

        /*
        assertte body icersinde equalTo ile assert yaptik
        JsonPath ile de assert yapabilirdik ,
        ----> Basics3 isimli classa bak
         */





    }
}
