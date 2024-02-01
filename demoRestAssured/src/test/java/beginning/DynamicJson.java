package beginning;

import files.Payload;
import files.ReUsableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class DynamicJson {
    @Test(dataProvider = "BooksData")
    public void addBook(String isbn, String aisle){
        RestAssured.baseURI = "http://216.10.245.166";
        String resp = given().header("Content-Type", "application/json")
                .body(Payload.addBook(isbn, aisle))
                .when().post("/Library/Addbook.php")
                .then().log().all().assertThat().statusCode(200)
                .extract().response().asString();
        JsonPath js = ReUsableMethods.rawToJson(resp);
        String id = js.get("ID");
        System.out.println(id);
    }
    @DataProvider(name="BooksData")
    public Object [][] getData(){
        return new Object[][]{{"aaaaaa","111"},{"bbbbbbb", "222"},{"cccccc", "333"}};
    }
    //parameterize the API test with multiple data set
}
