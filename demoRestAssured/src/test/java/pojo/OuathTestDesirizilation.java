package pojo;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertTrue;

public class OuathTestDesirizilation {
    @Test
    public void get_Access_Token(){
        String[] expectedTitles = {"Selenium Webdriver Java","Cypress","Protractor"};
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

        GetCourse responseWithToken = given().queryParam("access_token", accessToken)
                .when().log().all().get("https://rahulshettyacademy.com/oauthapi/getCourseDetails")
                .as(GetCourse.class);
        System.out.println(responseWithToken.getUrl());
        System.out.println(responseWithToken.getInstructor());
        System.out.println(responseWithToken.getLinkedIn());
        System.out.println(responseWithToken.getCourses().getApi().get(1).getCourseTitle());

        List<Api> api = responseWithToken.getCourses().getApi();

        for (int i=0; i< api.size(); i++){
            if (api.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")){
                System.out.println(api.get(i).getPrice());
            }
        }
        List<WebAutomation> webAutomation = responseWithToken.getCourses().getWebAutomation();
        ArrayList<String> actualTitles = new ArrayList<>();

        for (WebAutomation automation : webAutomation) {
            actualTitles.add(automation.getCourseTitle());
        }
        List<String> expectedCourseTitles = Arrays.asList(expectedTitles);
        assertTrue(actualTitles.equals(expectedCourseTitles));


    }

}
