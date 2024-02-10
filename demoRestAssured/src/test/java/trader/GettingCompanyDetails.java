package trader;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class GettingCompanyDetails {
    @BeforeMethod
    public void prepare_rest_config(){
        RestAssured.baseURI = "http://localhost:9000/api";
    }
    @Test
    public void test1(){
        RestAssured.get("/stock/aapl/company")
                .then()
                .body("companyName", equalTo("Apple, Inc."))
                .body("sector", equalTo("Electronic Technology"));
    }
    @Test
    public void test3(){
        RestAssured.baseURI = "http://localhost:9000/api";
        String response = given().get("/stock/aapl/company")
                .asString();
        System.out.println(response);
    }
    @Test
    public void test4(){
        RestAssured.baseURI = "http://localhost:9000/api";
        String response = given().pathParams("stokid", "aapl")
                .when().get("/stock/{stokid}/company")
                .asString();
        System.out.println(response);
    }
    @Test
    public void test2(){
        String response = given().log().all().get("/stock/aapl/company")
                .then().log().all().body("companyName", equalTo("Apple, Inc."))
                .body("sector", equalTo("Electronic Technology")).extract().response().asString();
        System.out.println(response);
    }
}
