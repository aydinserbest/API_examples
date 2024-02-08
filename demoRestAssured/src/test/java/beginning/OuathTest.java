package beginning;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class OuathTest {
    @Test
    public void get_Access_Token(){
        RestAssured.baseURI = "https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token";
        String response = given().formParam("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .formParam("client_secret","erZOWM9g3UtwNRj340YYaK_W")
                .formParam("grant_type","client_credentials")
                .formParam("scope","trust")
                .when().log().all().post()
                .asString();

        System.out.println(response);

        JsonPath js = new JsonPath(response);
        String accessToken = js.getString("access_token");

        String responseWithToken = given().queryParam("access_token", accessToken)
                .when().log().all().get("https://rahulshettyacademy.com/oauthapi/getCourseDetails")
                .asString();
        System.out.println(responseWithToken);


    }

}
