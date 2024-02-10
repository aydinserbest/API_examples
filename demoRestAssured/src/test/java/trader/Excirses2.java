package trader;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Excirses2 {
    @BeforeMethod
    public void prepare_rest_config(){
        RestAssured.baseURI = "http://localhost:9000/api";
    }
    @Test
    public void check_first_element_of_array(){
        given().pathParam("symbol", "aapl")
                .when().get("/stock/{symbol}/book")
                .then().log().all()
                .body("trades[0].price", equalTo(319.59f));
    }
    @Test
    public void check_last_element_of_array(){
        given().pathParam("symbol", "aapl")
                .when().get("/stock/{symbol}/book")
                .then().log().all()
                .body("trades[-1].price", equalTo(319.54f));
    }
    @Test
    public void find_the_number_of_trades(){
        given().pathParam("symbol", "aapl")
                .when().get("/stock/{symbol}/book")
                .then()
                .body("trades.size()", equalTo(20));
    }
    @Test
    public void find_the_minimum_trade(){
        given().pathParam("symbol", "aapl")
                .when().get("/stock/{symbol}/book")
                .then()
                .body("trades.price.min()", equalTo(319.38f));
    }
    @Test
    public void find_the_size_of_minimum_trade(){
        given().pathParam("symbol", "aapl")
                .when().get("/stock/{symbol}/book")
                .then()
                .body("trades.min { trade -> trade.price }.size", equalTo(100f));
        //instead of  .body("trades.min { trade -> trade.price }.size", equalTo(100f));
        //we can use this:
        //.body("trades.min { it.price }.size", equalTo(100f));
    }
    @Test
    public void test6(){
        given().pathParam("symbol", "aapl")
                .when().get("/stock/{symbol}/book")
                .then()
                .body("trades.findAll { it.price > 319.50 }.size()", equalTo(13));
    }
}
