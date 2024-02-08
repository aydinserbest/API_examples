package pojo;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;

public class GoogleMaps_Test {
    @Test
    public void addPlace(){
        AddPlace ap =new AddPlace();
        ap.setAddress("29, side layout, cohen 09");
        ap.setAccuracy(50);
        ap.setLanguage("French-IN");
        ap.setWebsite("http://google.com");
        ap.setPhone_number("(+91) 983 893 3937");
        ap.setName("Frontline house");

        List<String> myList = new ArrayList<>();
        myList.add("shoe park");
        myList.add("shop");
        ap.setTypes(myList);

        Location l = new Location();
        l.setLat(-38.383494);
        l.setLng(33.427362);

        ap.setLocation(l);

        RestAssured.baseURI = "https://rahulshettyacademy.com";
        Response response = given().queryParam("key", "qaclick123")
                .body(ap)
                .when().post("/maps/api/place/add/json")
                .then().assertThat().statusCode(200).extract().response();
        String responseString = response.asString();
        System.out.println(responseString);
    }
}
