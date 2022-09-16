package requests;

import io.restassured.response.Response;
import requests.testsData.UserCredsData;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class UserRequest extends BaseRequest {
    private static String accessToken = null;
    private final static String USER_PATH = "/api/auth/";

    public Response createUser(UserCredsData user) {
        Response response =
                given()
                        .spec(requestSpecification)
                        .and()
                        .body(user)
                        .when()
                        .post(USER_PATH + "register");
        if (accessToken == null) {
            saveAccessToken(response);
        }
        return response;
    }

    public void deleteUser() {
        if (accessToken != null) {
            given()
                    .spec(requestSpecification)
                    .header("Authorization", accessToken)
                    .when()
                    .delete(USER_PATH + "user");
        }
    }

    public Response loginUser(UserCredsData user) {
        Response response =
                given()
                        .spec(requestSpecification)
                        .and()
                        .body(user)
                        .when()
                        .post(USER_PATH + "login");
        return response;
    }

    public Response updateUser(UserCredsData user, boolean authorised) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-type", "application/json");
        if (authorised) {
            headers.put("Authorization", accessToken);
        }
        Response response =
                given()
                        .spec(requestSpecification)
                        .headers(headers)
                        .and()
                        .body(user)
                        .when()
                        .patch(USER_PATH + "user");
        return response;
    }

    public void saveAccessToken(Response response) {
        accessToken = response.then().extract().body().path("accessToken");
    }

    public String getToken() {
        return accessToken;
    }

    public void clearTokens() {
        accessToken = null;
    }
}
