package e_commerce;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class EcommerceAPITest {
    @Test
    public void e2etest(){
        RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .setContentType(ContentType.JSON)
                .build();
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserEmail("aydinserbest34@gmail.com");
        loginRequest.setUserPassword("Sa21342134");
        RequestSpecification reqLogin = given().log().all().spec(req).body(loginRequest);
        LoginResponse loginResponse = reqLogin.when().post("/api/ecom/auth/login").then().log().all().extract().response().as(LoginResponse.class);
        System.out.println(loginResponse.getToken());
        String token = loginResponse.getToken();
        System.out.println(loginResponse.getUserId());
        String userId = loginResponse.getUserId();

        //Add a product
        RequestSpecification addProductBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .addHeader("Authorization", token)
                .build();
        RequestSpecification reqAddProduct = given().log().all().spec(addProductBaseReq)
                .param("productName", "technology")
                .param("productDescription", "television")
                .param("productAddedBy", userId)
                .param("productCategory", "fashion")
                .param("productSubCategory", "shirts")
                .param("productPrice", "11500")
                .param("productFor", "women")
                .multiPart("productImage", new File("/Users/gebruiker/Desktop/free-nature-images.jpeg"));

        String responseAddedProduct = reqAddProduct.when().post("/api/ecom/product/add-product").then().log().all()
                .extract().response().asString();
        JsonPath js = new JsonPath(responseAddedProduct);
        String productId = js.get("productId");

        //Create order
        RequestSpecification orderBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .setContentType(ContentType.JSON).addHeader("authorization", token)
                .build();
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setProductOrderedId(productId);
        orderDetails.setCountry("India");
        List<OrderDetails> orderDetailsList = new ArrayList<>();
        orderDetailsList.add(orderDetails);

        Orders orders = new Orders();
        orders.setOrderDetails(orderDetailsList);

        RequestSpecification createOrderReq = given().log().all().spec(orderBaseReq).body(orders);
        String ordersAdded = createOrderReq.when().post("/api/ecom/order/create-order")
                .then().log().all().extract().response().asString();
        System.out.println(ordersAdded);

        //delete product
        RequestSpecification deleteBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
               .addHeader("authorization", token)
                .build();
        RequestSpecification deleteProductReq =given().spec(deleteBaseReq).pathParam("productId", productId);

        String deletedProduct = deleteProductReq.when().delete("/api/ecom/product/delete-product/{productId}").then().log().all().extract().response().asString();
        JsonPath jsonPath = new JsonPath(deletedProduct);
        assertEquals("Product Deleted Successfully", jsonPath.get("message"));
    }
}
