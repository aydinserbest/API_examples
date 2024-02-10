package trader;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Excersise1 {
    @BeforeMethod
    public void prepare_rest_config(){
        RestAssured.baseURI = "http://localhost:9000/api";
    }
    @Test
    public void retrieveField(){
        given().get("/stock/aapl/company")
                .then()
                .body("industry", equalTo("Telecommunications Equipment"));
    }
    @Test
    public void retrieveField2(){
        given().pathParam("symbol", "AAPL")
                .when().get("/stock/{symbol}/company")
                .then()
                .body("industry", equalTo("Telecommunications Equipment"));
    }
    @Test
    public void retrieveField3(){
        given().pathParam("symbol", "AAPL")
                .when().get("/stock/{symbol}/company")
                .then()
                .body("description", containsString("smartphones"));
    }
    @Test
    public void retrieveNestedJson(){
        given().pathParam("symbol", "AAPL")
                .when().get("/stock/{symbol}/book")
                .then()
                .body("quote.symbol", containsString("AAPL"));
    }
    @Test
    public void retrieveList(){
        when().get("/tops/last")
                .then()
                .body("symbol", hasItems("PIN","PINE","TRS"));
    }
    @Test
    public void checkListCondition(){
        when().get("/tops/last")
                .then()
                .body("price", hasItems(greaterThan("100.0f")));
    }
}
