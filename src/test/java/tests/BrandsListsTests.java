package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
public class BrandsListsTests {


    @Test
    public void test_getBrandsList() {
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
