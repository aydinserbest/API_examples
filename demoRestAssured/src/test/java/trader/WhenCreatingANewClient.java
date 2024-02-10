package trader;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.swing.plaf.PanelUI;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

public class WhenCreatingANewClient {
    @BeforeMethod
    public void setupBaseUrl(){
        RestAssured.baseURI = "http://localhost:9000/api";
    }
    @Test
    public void checkId(){
        String newClient = "{\n" +
                "  \"firstName\": \"John\",\n" +
                "  \"lastName\": \"White\",\n" +
                "  \"email\": \"john@gmail.com\"\n" +
                "}";
        given()
               // .header("Content-Type", ContentType.JSON)
                //or
                //.header("Content-Type", "application/json")
                //.contentType("application/json")
                    //or
                .contentType(ContentType.JSON)
                .body(newClient)
                .when()
                .post("/client")
                .then().statusCode(200)
                .and().body("id", not(equalTo(0)))
                .and().body("firstName",equalTo("John" ))
                .and().body("lastName",equalTo("White" ))
                .and().body("email",equalTo("john@gmail.com" ));
    }
}
