package beginning;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class JiraTest2 {
    @Test
    public void commentAdd(){
        RestAssured.baseURI="http://localhost:8080";
        SessionFilter session = new SessionFilter();

        String response = given().header("Content-Type", "application/json")
                .body("{ \"username\": \"aydinserbest34\", \"password\": \"Sa21342134\" }")
                .filter(session)
                .log().all()
                .when().post("/rest/auth/1/session").then().extract().response().asString();


       String issue =  given().log().all().header("Content-Type", "application/json").body("{\n" +
                "    \"fields\": {\n" +
                "        \"project\": {\n" +
                "            \"key\": \"RES\"\n" +
                "        },\n" +
                "        \"summary\": \"Debit Card Defect\",\n" +
                "        \"description\": \"Creating bug\",\n" +
                "        \"issuetype\": {\n" +
                "            \"name\": \"Bug\"\n" +
                "        }\n" +
                "    }\n" +
                "}").filter(session).when().post("/rest/api/2/issue").then().extract().response().asString();

       JsonPath js2 = new JsonPath(issue);
        String key = js2.get("key");
        System.out.println("issue key: "+key);


        String commentResponse=given().log().all().pathParam("key",key).header("Content-Type","application/json")
                .body("{\n" +
                "    \"body\": \"I am adding comment from intellij.\",\n" +
                "    \"visibility\": {\n" +
                "        \"type\": \"role\",\n" +
                "        \"value\": \"Administrators\"\n" +
                "    }\n" +
                "}").filter(session).when().post("/rest/api/2/issue/{key}/comment")
                .then().log().all().assertThat().statusCode(201).extract().response().asString();
        JsonPath js3 = new JsonPath(commentResponse);
        String commentId=js3.get("id");

        System.out.println(commentId);

        given().header("X-Atlassian-Token", " no-check").filter(session)
                .pathParam("key", key)
                .header("Content-Type", "multipart/form-data")
                .multiPart("file",new File("jira.txt"))
                .when().post("/rest/api/2/issue/{key}/attachments")
                .then().log().all().assertThat().statusCode(200);

        //get issue

        String issueDetails = given().filter(session).pathParam("key",key).log().all().when().get("/rest/api/2/issue/{key}")
                .then().log().all().extract().response().asString();

        System.out.println(issueDetails);

        //get issue filtered- with queryParam

        String issueDetailsFiltered = given().filter(session).pathParam("key",key)
                .queryParam("fields","comment").log().all().when().get("/rest/api/2/issue/{key}")
                .then().log().all().extract().response().asString();

        System.out.println(issueDetailsFiltered);

    }
}
