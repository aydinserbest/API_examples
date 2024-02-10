package trader;

import io.restassured.RestAssured;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.hasItems;

public class WhenGettingCompanyDetails {
    @Test
    public void test1(){
        String response = RestAssured.get("http://localhost:9000/api/stock/aapl/company")
                .asString();
        System.out.println(response);
    }
    @Test
    public void test2(){
        String response = given().get("http://localhost:9000/api/stock/aapl/company")
                .asString();
        System.out.println(response);
    }
    @Test
    public void test3(){
        RestAssured.baseURI = "http://localhost:9000/api";
        String response = given().get("/stock/aapl/company")
                .asString();
        System.out.println(response);
    }
    @Test
    public void retrieveList(){
        when().get("/tops/last")
                .then()
                .body("symbol", hasItems("PIN","PINE","TRS"));
    }
    /*
        DIREK when().get(...) ile de teste baslanabilir,
        parametre gondermiceksek mesela
     */
    @Test
    public void test5(){
        RestAssured.baseURI = "http://localhost:9000/api";
        String response = given().pathParams("stokid", "aapl")
                .when().get("/stock/{stokid}/company")
                .asString();
        System.out.println(response);
    }
    /*
    given-when-then john videolardan al yaz
     */
    /*
    RestAssured.get(".....") buraya baseURI tanimlamadan, tam urli yazabiliriz
    parametreler de icinde olabilir
    yani given().get
    ile RestAssured.get() ayni sey

    bir de  RestAssured.baseURI = "";
    get(/resource)
    dedigimizde
    metot
    once baseURI yi alir ,
    devamina resource i ekler

    ya da baseURI tanimlamadan tam adresi
    metot icine yazabiliriz
     */
    @Test
    public void test4(){
        RestAssured.baseURI = "https://bddtrader.herokuapp.com/api";
        String response = given().get("/stock/aapl/company")
                .asString();
        System.out.println(response);
    }
    /*
    The application we will be testing is the BDDTrader application,
    a simple (and simulated) trading application built for the purposes of these tests.
    If you want to view the online version of the app,
    the Swagger URL is now:
    https://bddtrader.herokuapp.com/swagger-ui/index.html
     */
    /*
    RestAssured.baseURI değerini değiştirerek
    testlerinizi hem yerel hem de uzaktaki uygulama örneklerine kolayca yönlendirebilirsiniz
     */
    /*
    In the world of test automation and software development,
    the terminology used to describe running applications in different environments typically includes:

    Local Environment / Localhost:

    Running on Localhost: This refers to running an application on the development machine
    or a developer's own computer. localhost or 127.0.0.1 points to the local machine itself.
    Running in the local environment is commonly used for fast development and testing cycles.
    Local Testing: The term used for tests conducted in the local environment.
    This means tests are run on the developer's own machine, usually using mock data
    or test databases instead of real user data.
    Remote Environment / Production-like Environment:

    Running in a Remote Environment / Deploying to a Remote Environment:
    This refers to running the application on a server or cloud service (like Heroku, AWS, GCP)
    outside the development machine. It often implies testing the application in conditions closer
    to live or with real user data.
    Remote Testing / Testing in a Production-like Environment:
    Testing the application outside the local environment.
    This is usually done to see how the application performs under conditions closer to live
    or under actual user load.
    Specific Terms for Your Case:
    Local Testing with Swagger UI:

    http://localhost:9000/swagger-ui/index.html#/stock/companyDetailsFor
    This URL is used for testing the application in the local environment through Swagger UI.
    It's referred to as "Local Testing."
    Remote Testing with Swagger UI:

    https://bddtrader.herokuapp.com/swagger-ui/index.html#/stock-controller/companyDetailsForUsingGET
    This URL is used for testing the application instance hosted on a cloud platform
    like Heroku through Swagger UI.
    Such tests are called "Remote Testing" or "Testing in a Production-like Environment."
    These terms are foundational concepts you can use while planning your tests
    and defining your testing strategy. Test automation, especially
    as part of CI/CD (Continuous Integration/Continuous Deployment) processes,
    helps develop strategies for testing applications both in local and remote environments.
     */


}
