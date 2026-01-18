package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.annotations.AfterClass;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class UserAccountTests {

    private String email;
    private final String password = "Test@123";

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://automationexercise.com/api";
        // Generate unique email to avoid conflicts
        email = "user" + System.currentTimeMillis() + "@example.com";
    }

    @Test(priority = 1)
    public void test_createUser() {
        Response response = given()
                .log().all()
                .header("Accept", "*/*")
                .formParam("name", "John Doe")
                .formParam("email", email)
                .formParam("password", password)
                .formParam("title", "Mr")
                .formParam("birth_date", "01")
                .formParam("birth_month", "01")
                .formParam("birth_year", "1990")
                .formParam("firstname", "John")
                .formParam("lastname", "Doe")
                .formParam("company", "TestCompany")
                .formParam("address1", "123 Test Street")
                .formParam("address2", "Suite 100")
                .formParam("country", "United States")
                .formParam("zipcode", "12345")
                .formParam("state", "California")
                .formParam("city", "Los Angeles")
                .formParam("mobile_number", "1234567890")
                .when()
                .post("/createAccount")
                .then()
                .log().all()
                .statusCode(200)
                .extract().response();

        String bodyString = response.getBody().asString();
        String jsonString = bodyString.replaceAll("(?s)<.*?>", "");
        JsonPath json = new JsonPath(jsonString);

        assert json.getInt("responseCode") == 201;
        assert json.getString("message").equals("User created!");
    }

    @Test(priority = 2)
    public void test_verifyLogin() {
        Response response = given()
                .log().all()
                .header("Accept", "*/*")
                .formParam("email", email)
                .formParam("password", password)
                .when()
                .post("/verifyLogin")
                .then()
                .log().all()
                .statusCode(200)
                .extract().response();

        String bodyString = response.getBody().asString();
        String jsonString = bodyString.replaceAll("(?s)<.*?>", "");
        JsonPath json = new JsonPath(jsonString);

        assert json.getInt("responseCode") == 200;
        assert json.getString("message").equals("User exists!");
    }

    @Test(priority = 3)
    public void test_updateUser() {
        Response response = given()
                .log().all()
                .header("Accept", "*/*")
                .formParam("name", "John Updated")
                .formParam("email", email)
                .formParam("password", password)
                .formParam("title", "Mr")
                .formParam("birth_date", "02")
                .formParam("birth_month", "02")
                .formParam("birth_year", "1991")
                .formParam("firstname", "John")
                .formParam("lastname", "Updated")
                .formParam("company", "UpdatedCompany")
                .formParam("address1", "456 Updated Street")
                .formParam("address2", "Suite 200")
                .formParam("country", "United States")
                .formParam("zipcode", "54321")
                .formParam("state", "California")
                .formParam("city", "Los Angeles")
                .when()
                .put("/updateAccount")
                .then()
                .log().all()
                .statusCode(200)
                .extract().response();

        String bodyString = response.getBody().asString();
        String jsonString = bodyString.replaceAll("(?s)<.*?>", "");
        JsonPath json = new JsonPath(jsonString);

        assert json.getInt("responseCode") == 200;
        assert json.getString("message").equals("User updated!");
    }

    @Test(priority = 4)
    public void test_getUserDetailByEmail() {
        Response response = given()
                .log().all()
                .header("Accept", "*/*")
                .queryParam("email", email)
                .when()
                .get("/getUserDetailByEmail")
                .then()
                .log().all()
                .statusCode(200)
                .extract().response();

        String bodyString = response.getBody().asString();
        String jsonString = bodyString.replaceAll("(?s)<.*?>", "");
        JsonPath json = new JsonPath(jsonString);

        // Sada dohvatamo podatke unutar "user" objekta
        String name = json.getString("user.name");
        String lastName = json.getString("user.last_name");
        String company = json.getString("user.company");
        String address1 = json.getString("user.address1");
        String emailResponse = json.getString("user.email");
        String birthDate = json.getString("user.birth_day");
        String birthMonth = json.getString("user.birth_month");
        String birthYear = json.getString("user.birth_year");

        // Assert
        assert name.equals("John Updated");
        assert lastName.equals("Updated");
        assert company.equals("UpdatedCompany");
        assert address1.equals("456 Updated Street");
        assert emailResponse.equals(email);
        assert birthDate.equals("02");
        assert birthMonth.equals("02");
        assert birthYear.equals("1991");
    }

    @Test(priority = 5)
    public void test_deleteUser() {
        Response response = given()
                .log().all()
                .header("Accept", "*/*")
                .formParam("email", email)
                .formParam("password", password)
                .when()
                .delete("/deleteAccount")
                .then()
                .log().all()
                .statusCode(200)
                .extract().response();

        String bodyString = response.getBody().asString();
        String jsonString = bodyString.replaceAll("(?s)<.*?>", "");
        JsonPath json = new JsonPath(jsonString);

        assert json.getInt("responseCode") == 200;
        assert json.getString("message").equals("Account deleted!");
    }
}
