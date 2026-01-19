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
                .log().all()
        .when()
                .get("/brandsList")
        .then()
                .log().all()
                .statusCode(200)
                .time(lessThan(3000L))
                .body("brands", notNullValue());
    }
}
