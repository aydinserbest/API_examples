package beginning;

import files.Payload;
import io.restassured.RestAssured;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Basics {
    public static void main(String[] args) {
        //validate if Add place Api is working as expected

        //given-input details
        //when-submit the Api resource + http method
        //then-validate the response

        RestAssured.baseURI = "https://rahulshettyacademy.com";
        String response = given().log().all().queryParam("key","qaclick123").header("Content-Type", "application/json")
                .body(Payload.addPlace()).when().post("maps/api/place/add/json")
                .then().assertThat().statusCode(200).body("scope", equalTo("APP"))
                .header("server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();

        System.out.println(response);


    }
    /*
    in the context of the RestAssured code, the extract().response().asString();
    statement is indeed the pivotal point. It tells RestAssured to get the response
    from the POST request and convert it into a String.

    Here's what's happening in more detail:

    extract(): This is used to get the raw response object from the API call.
    .response(): This method retrieves the response body from the raw response.
    .asString(): This converts the response body into a String format.
    The String you get from this is typically the raw JSON response from the server,
    which you can then use for further processing, such as parsing the JSON to extract specific values
    or simply logging the entire response.

    Therefore, extract().response().asString(); is the crucial step to take the server's response
    and work with it as a String within your Java code. After any validations done with assertThat(),
    this response String can be printed to the console, stored in a variable, or manipulated as needed.
     */

    /*
    The use of the assertThat() method in RestAssured is to check if the response satisfies certain conditions.
    These conditions could be the HTTP status code of the response,
    certain values within the response body,
    or values in the headers.

    Following the assertThat() method, statements like statusCode(200) instruct RestAssured
    to validate the received response against these conditions. If the conditions are not met, the test will fail,
    and an error message will be thrown.

    However, if you don't wish to perform any validation with assertThat()
    and just want to retrieve the content of the response as a String, you can remove the validation expressions.
    In this case, the extract().response().asString(); part will still retrieve the entire response as a String,
    and System.out.println(response); will print this response to the console.

    Therefore, whether you use the assertThat() method or not does not affect the content of the response variable;
    it's only used to validate that the response meets certain criteria.
    The content of the response (response) is always the server's response returned
    as the result of the when().post(...) statement.

    Thus, even if you remove the assertThat() statements, the content of the response will not change.
    The only difference is that the validation check for the response to meet certain conditions is removed,
    and any errors that occur will not be thrown during the test.
     */
}
