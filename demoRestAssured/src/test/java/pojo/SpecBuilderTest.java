package pojo;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class SpecBuilderTest {
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

        RequestSpecification requestSpecification = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addQueryParam("key", "qaclick123")
                .setContentType(ContentType.JSON)
                .build();
        ResponseSpecification responseSpecBuilder = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();


         RequestSpecification request = given().spec(requestSpecification)
                .body(ap);


         Response response = request.when().post("/maps/api/place/add/json")
                .then().spec(responseSpecBuilder).extract().response();
        String responseString = response.asString();
        System.out.println(responseString);
    }
}
