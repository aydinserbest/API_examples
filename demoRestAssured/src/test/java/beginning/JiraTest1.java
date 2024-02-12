package beginning;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class JiraTest1 {
    @Test
    public void commentAdd(){
        RestAssured.baseURI="http://localhost:8080";

        String response = given().header("Content-Type", "application/json")
                .body("{ \"username\": \"aydinserbest34\", \"password\": \"Sa21342134\" }")
                .log().all()
                .when().post("/rest/auth/1/session").then().extract().response().asString();

        JsonPath js = new JsonPath(response);
        String sessionId = js.get("session.value");

       String issue =  given().log().all().header("Content-Type", "application/json").header("Cookie", "JSESSIONID="+sessionId+"").body("{\n" +
                "    \"fields\": {\n" +
                "        \"project\": {\n" +
                "            \"key\": \"RES\"\n" +
                "        },\n" +
                "        \"summary\": \"Debit Card Defect from intellij\",\n" +
                "        \"description\": \"Creating bug\",\n" +
                "        \"issuetype\": {\n" +
                "            \"name\": \"Bug\"\n" +
                "        }\n" +
                "    }\n" +
                "}").when().post("/rest/api/2/issue").then().extract().response().asString();

       JsonPath js2 = new JsonPath(issue);
        String key = js2.get("key");
        System.out.println(key);

        String commentResponse=given().pathParam("key",key).header("Content-Type","application/json").header("Cookie", "JSESSIONID="+sessionId+"")
                .body("{\n" +
                "    \"body\": \"I am adding comment from intellij.\",\n" +
                "    \"visibility\": {\n" +
                "        \"type\": \"role\",\n" +
                "        \"value\": \"Administrators\"\n" +
                "    }\n" +
                "}").when().post("/rest/api/2/issue/{key}/comment").then().extract().response().asString();
        JsonPath js3 = new JsonPath(commentResponse);
        String comment=js3.get("body");

        System.out.println(comment);

    }
    @Test
    public void refactor_2header_in_commentAdd_method(){
        RestAssured.baseURI="http://localhost:8080";

        String response = given().header("Content-Type", "application/json")
                .body("{ \"username\": \"aydinserbest34\", \"password\": \"Sa21342134\" }")
                .log().all()
                .when().post("/rest/auth/1/session").then().extract().response().asString();

        JsonPath js = new JsonPath(response);
        String sessionId = js.get("session.value");

        String issue =  given().log().all().headers("Content-Type", "application/json;Cookie", "JSESSIONID="+sessionId+"").body("{\n" +
                "    \"fields\": {\n" +
                "        \"project\": {\n" +
                "            \"key\": \"RES\"\n" +
                "        },\n" +
                "        \"summary\": \"Debit Card Defect from intellij\",\n" +
                "        \"description\": \"Creating bug\",\n" +
                "        \"issuetype\": {\n" +
                "            \"name\": \"Bug\"\n" +
                "        }\n" +
                "    }\n" +
                "}").when().post("/rest/api/2/issue").then().extract().response().asString();

        JsonPath js2 = new JsonPath(issue);
        String key = js2.get("key");
        System.out.println(key);

        String commentResponse=given().pathParam("key",key).header("Content-Type","application/json").header("Cookie", "JSESSIONID="+sessionId+"")
                .body("{\n" +
                        "    \"body\": \"I am adding comment from intellij.\",\n" +
                        "    \"visibility\": {\n" +
                        "        \"type\": \"role\",\n" +
                        "        \"value\": \"Administrators\"\n" +
                        "    }\n" +
                        "}").when().post("/rest/api/2/issue/{key}/comment").then().extract().response().asString();
        JsonPath js3 = new JsonPath(commentResponse);
        String comment=js3.get("body");

        System.out.println(comment);

    }
}
