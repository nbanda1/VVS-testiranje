package tests;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class ProductsApiTest {

    @BeforeClass
    public void setup() {
        // Base URI for all tests in this class
        RestAssured.baseURI = "https://automationexercise.com/api";
    }

    @Test
    public void test_getAllProducts() {

        given()
                .log().all() // Log request
                .when()
                .get("/productsList") // GET request
                .then()
                .log().all() // Log response
                .statusCode(200) // Validate status code
                .time(lessThan(3000L)) // Validate response time < 3 seconds
                .body("products", notNullValue()) // Check JSON key exists
                .body("products.size()", greaterThan(0)); // Ensure array is not empty
    }

}
