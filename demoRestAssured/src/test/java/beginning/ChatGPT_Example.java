package beginning;

import io.restassured.RestAssured;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ChatGPT_Example {
    /*
    Path Parameter için Test Örneği
    Bu örnekte, bir kullanıcının detaylarını getiren bir API endpoint'i test edeceğiz.
    Kullanıcı ID'si bir path parameter olarak geçirilecek.
     */
    @Test
    public void getUserDetailsWithPathParameter() {
        RestAssured.baseURI = "http://example.com/api";

        given().
                pathParam("userId", "123").
                when().
                get("/users/{userId}").
                then().
                statusCode(200).
                body("id", equalTo(123),
                        "name", equalTo("John Doe"));
    }
    /*
    Query Parameter için Test Örneği
    Bu örnekte, belirli bir şehirde yaşayan kullanıcıları listelemek için kullanılan bir API endpoint'ini test edeceğiz.
    Şehir bilgisi bir query parameter olarak geçirilecek.
     */
    @Test
    public void getUsersByCityWithQueryParameter() {
        RestAssured.baseURI = "http://example.com/api";

        given().
                queryParam("city", "Istanbul").
                when().
                get("/users").
                then().
                statusCode(200).
                body("users", hasSize(greaterThan(0)),
                        "users.city", everyItem(equalTo("Istanbul")));
    }

    /*
    Path ve Query Parameter Birleşimi için Test Örneği
    Bu örnekte, bir kullanıcının blog gönderilerini,
    belirli bir tarihten sonraki gönderileri filtreleyerek getiren bir API endpoint'ini test edeceğiz.
    Kullanıcı ID'si path parameter olarak,
    tarih ise query parameter olarak geçirilecek.
     */
    @Test
    public void getUserPostsAfterDateWithPathAndQueryParameter() {
        RestAssured.baseURI = "http://example.com/api";

        given().
                pathParam("userId", "123").
                queryParam("after", "2021-01-01").
                when().
                get("/users/{userId}/posts").
                then().
                statusCode(200).
                body("posts", hasSize(greaterThan(0)),
                        "posts.date", everyItem(greaterThan("2021-01-01")));
    }
    /*
    usttekine benzer,
    iki parameter alma ornegi, udemy'den:
     */
    /*
    String getPlaceResponse = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeId)
            .when().get("maps/api/place/get/json")
            .then().assertThat().log().all().statusCode(200).extract().response().asString();
    */

}
