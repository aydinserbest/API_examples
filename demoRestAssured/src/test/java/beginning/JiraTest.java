package beginning;

import io.restassured.RestAssured;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class JiraTest {
    @Test
    public void commentAdd(){
        RestAssured.baseURI="http://localhost:8080";
        given().pathParam("id","10007").header("Content-Type","application/json")
                .body("{\n" +
                "    \"body\": \"I am adding comment from intellij.\",\n" +
                "    \"visibility\": {\n" +
                "        \"type\": \"role\",\n" +
                "        \"value\": \"Administrators\"\n" +
                "    }\n" +
                "}").post("/rest/api/2/issue/{id}/comment");

    }
}
