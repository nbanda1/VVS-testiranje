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
        RestAssured.baseURI = "https://automationexercise.com/api";
    }

    @Test
    public void test_getAllProducts() {

        given()
                .log().all()
        .when()
                .get("/productsList")
        .then()
                .log().all()
                .statusCode(200)
                .time(lessThan(3000L))
                .body("products", notNullValue())
                .body("products.size()", greaterThan(0));
    }


}
