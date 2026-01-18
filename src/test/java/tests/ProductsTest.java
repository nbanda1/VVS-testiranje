package tests;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class ProductsTest {

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
    @Test(priority = 4)
    public void test_postAllProductsList_shouldFail() {

        Response response = given()
                .log().all()
                .header("Accept", "*/*")
                .when()
                .post("/productsList")
                .then()
                .log().all()
                .statusCode(405)   // OČEKUJEMO DA PADNE
                .extract().response();

        // Extract body
        String bodyString = response.getBody().asString();
        String jsonString = bodyString.replaceAll("(?s)<.*?>", ""); // ukloni HTML
        JsonPath json = new JsonPath(jsonString);

        // Assert da je poruka greške ispravna
        assert json.getString("message").equals("This request method is not supported.");
    }

}
