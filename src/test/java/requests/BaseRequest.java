package requests;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

public class BaseRequest {
    RequestSpecification requestSpecification = RestAssured.given()
            .baseUri("https://stellarburgers.nomoreparties.site")
            .header("Content-type", "application/json");;
}
