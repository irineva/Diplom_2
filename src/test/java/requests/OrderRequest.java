package requests;

import io.restassured.response.Response;
import requests.testsData.Ingredients;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class OrderRequest extends BaseRequest {

    private final static String ORDER_PATH = "/api/orders";
    UserRequest userRequest = new UserRequest();

    public Response createOrder(Ingredients ingredients, boolean withAuth) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-type", "application/json");
        if (withAuth) {
            headers.put("Authorization", userRequest.getToken());
        }
        Response response =
                given()
                        .spec(requestSpecification)
                        .headers(headers)
                        .and()
                        .body(ingredients)
                        .when()
                        .post(ORDER_PATH);
        return response;
    }

    public Response getOrder(boolean withAuth) {
        Map<String, String> headers = new HashMap<>();
        if (withAuth) {
            headers.put("Authorization", userRequest.getToken());
        }
        Response response =
                given()
                        .spec(requestSpecification)
                        .headers(headers)
                        .when()
                        .get(ORDER_PATH);
        return response;
    }

    public String getIngredient() {
        String ingredientId =
                given()
                        .spec(requestSpecification)
                        .when()
                        .get("/api/ingredients")
                        .then()
                        .extract()
                        .body()
                        .path("data[0]._id");
        return ingredientId;
    }

}
