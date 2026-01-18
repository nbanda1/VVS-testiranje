package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
public class Test1 {

    @Test
    public void test_1() {
        Response response = RestAssured.get("https://automationexercise.com/api/brandsList");

        System.out.println("Status: " + response.getStatusCode());
        System.out.println("Time: " + response.getTime());

        Assert.assertEquals(response.getStatusCode(), 200, "Status code is not 200!");
        Assert.assertTrue(response.getTime() < 3000, "Response is too slow!");
    }
    @Test
    public void test_1_given_when_then() {
        RestAssured.baseURI = "https://automationexercise.com/api";

        given()
                .log().all()        // Log request details
        .when()
                .get("/brandsList") // Send GET request
        .then()
                .log().all()        // Log response details
                .statusCode(200)    // Assert status code
                .time(lessThan(3000L)) // Assert response time < 3 seconds
                .body("brands", notNullValue()); // Assert JSON has "brands" key
    }
}
