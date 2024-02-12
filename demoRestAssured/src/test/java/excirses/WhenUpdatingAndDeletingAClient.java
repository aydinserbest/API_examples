package excirses;

import clients.Client;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class WhenUpdatingAndDeletingAClient {
    @BeforeMethod
    public void setupBaseUrl(){
        RestAssured.baseURI = "http://localhost:9000/api";
    }
    @Test
    public void add_a_client(){
        Client client = Client.withFirstName("Pam").andLastName("beileys").andEmail("pam@gmail.com");

        String id = given().contentType(ContentType.JSON)
                .body(client)
                .when().post("/client")
                .jsonPath().getString("id");
        System.out.println("client beileys id: " + id);

    }
    @Test
    public void refactor_add_a_client(){
        Client client = Client.withFirstName("John").andLastName("White").andEmail("john@gmail.com");
        String id = create_client_and_get_ID(client);
        System.out.println("client White id: " + id);
    }
    @Test
    public void check_client(){
        Client client = Client.withFirstName("John").andLastName("White").andEmail("john@gmail.com");
        String id = create_client_and_get_ID(client);
        given().pathParam("clientId",id)
                .when().get("/client/{clientId}")
                .then()
                .statusCode(200);
    }
    @Test
    public void refactor_check_client(){
        Client client = Client.withFirstName("John").andLastName("White").andEmail("john@gmail.com");
        String id = create_client_and_get_ID(client);
        when().get("/client/{clientId}", id)
                .then()
                .body("email", equalTo("john@gmail.com"));
    }
//    @Test
//    public void update(){
//        Map<String, >
//    }
    @Test
    public void update_client_email_info(){
        //create a client
        //we need data structure
        Client client = Client.withFirstName("John").andLastName("White").andEmail("john@gmail.com");

        //send this structure into body() and get ID
        //while updating , we will use this id
        String id = create_client_and_get_ID(client);
        System.out.println(id);

        //update email info
        Client johnWithUpdates = Client.withFirstName("John").andLastName("White").andEmail("john@white.com");
        //while updating, this query will return null id, but id will be the same
        //because we update other infos
        String string = given().contentType(ContentType.JSON)
                .pathParam("id", id)
                .and().body(johnWithUpdates)
                .when()
                .put("/client/{id}")
                .then()
                .body("email", equalTo("john@white.com")).extract().response().asString();
        //after updating, we can reach the same id
        System.out.println(getId(id));

    }
    @Test
    public void update_info_with_map(){
        //create a client
        //we need a data structure to use in body (...)
        Client client = Client.withFirstName("John").andLastName("White").andEmail("john@gmail.com");

        //send this structure into body() and get ID
        //while updating , we will use this id
        String id = create_client_and_get_ID(client);

        /*
        we will update email info,
        from john@gmail.com   to john@white.com
         */
        Map<String, Object> updates = new HashMap<>();
        updates.put("email", "john@white.com");

        given().contentType(ContentType.JSON)
                .body(updates)
                .when().put("/client/{id}", id)
                .then()
                .statusCode(200);


    }

    private static String create_client_and_get_ID(Client client) {
        return given().contentType(ContentType.JSON)
                .body(client)
                .when().post("/client")
                .jsonPath().getString("id");
    }
    private static String getId(String id){
        return when().get("/client/{id}", id).asString();
    }
}
