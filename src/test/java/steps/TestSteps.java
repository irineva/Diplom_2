package steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import requests.OrderRequest;
import requests.UserRequest;
import requests.testsData.Ingredients;
import requests.testsData.UserCredsData;

import java.util.Random;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;

public class TestSteps {

    UserRequest userRequest = new UserRequest();
    OrderRequest orderRequest = new OrderRequest();

    @Step("Задаем уникальные данные нового пользователя")
    public UserCredsData setUserDataStep() {
        int random = new Random().nextInt(10);
        UserCredsData user;
        user = new UserCredsData("test_email_" + random + "@ya.ru",
                "test_pass",
                "test_name_" + random);
        return user;
    }

    @Step("Создаем нового пользователя")
    public Response createUserStep(UserCredsData user) {
        return userRequest.createUser(user);
    }

    @Step("Авторизация пользователем")
    public Response loginUserStep(UserCredsData user) {
        return userRequest.loginUser(user);
    }

    @Step("Изменение данных пользователя")
    public Response updateUserStep(UserCredsData user, boolean authorized) {
        return userRequest.updateUser(user, authorized);
    }

    @Step("Проверяем тело и статус код на некорректный запрос авторизации")
    public void checkAnswerForLoginWithInvalidCredsStep(Response response) {
        response.then().assertThat().body("success", equalTo(false),
                        "message", equalTo("email or password are incorrect"))
                .and()
                .statusCode(SC_UNAUTHORIZED);
    }

    @Step("Проверяем тело и статус код на created request")
    public void checkAnswerForSuccsessStep(Response response) {
        response.then().assertThat().body("success", equalTo(true))
                .and()
                .statusCode(SC_OK);
    }

    @Step("Проверяем тело и статус для существующего пользователя")
    public void checkAnswerForExistingUserCreateStep(Response response) {
        response.then().assertThat().body("success", equalTo(false),
                        "message", equalTo("User already exists"))
                .and()
                .statusCode(SC_FORBIDDEN);
    }

    @Step("Проверяем тело и статус код на некорректный запрос создания пользователся")
    public void checkAnswerForInvalidCreateRequestStep(Response response) {
        response.then().assertThat().body("success", equalTo(false),
                        "message", equalTo("Email, password and name are required fields"))
                .and()
                .statusCode(SC_FORBIDDEN);
    }

    @Step("Проверяем тело и статус код для изменения данных авторизованного пользователя")
    public void checkAnswerForUpdatedAuthorisedUserStep(Response response, String expectedValue, String path) {
        response.then().assertThat().body("success", equalTo(true),
                        path, equalTo(expectedValue))
                .and()
                .statusCode(SC_OK);
    }

    @Step("Проверяем тело и статус код для изменения данных не авторизованного пользователя")
    public void checkAnswerForUpdatedNotAuthorisedUserStep(Response response) {
        response.then().assertThat().body("success", equalTo(false),
                        "message", equalTo("You should be authorised"))
                .and()
                .statusCode(SC_UNAUTHORIZED);
    }

    @Step("Удаление пользователя")
    public void deleteUserStep() {
        userRequest.deleteUser();
    }

    @Step("Очистка кэша")
    public void clearCashStep() {
        userRequest.clearTokens();
    }

    @Step("Создаем новый заказ")
    public Response createOrderStep(Ingredients ingredients, boolean authorized) {
        return orderRequest.createOrder(ingredients, authorized);
    }

    @Step("Получаем заказ")
    public Response getOrderStep(boolean withAuth) {
        return orderRequest.getOrder(withAuth);
    }

    @Step("Получаем игредиенты")
    public String getIngredientStep() {
        return orderRequest.getIngredient();
    }

    @Step("Проверяем тело и статус код успешно созданного заказа")
    public void checkAnswerForCreateSuccessOrderStep(Response response) {
        response.then().assertThat().body("success", equalTo(true))
                .and()
                .statusCode(SC_OK);
    }

    @Step("Проверяем тело и статус код заказа без ингридиента")
    public void checkAnswerForCreateOrderWithoutIngredientsStep(Response response) {
        response.then().assertThat().body("success", equalTo(false),
                        "message", equalTo("Ingredient ids must be provided"))
                .and()
                .statusCode(SC_BAD_REQUEST);
    }

    @Step("Проверяем тело и статус код заказа c некорректным ингридиентом")
    public void checkAnswerForCreateOrderInvalidIngredientsStep(Response response) {
        response.then().assertThat().body("success", equalTo(false),
                        "message", equalTo("One or more ids provided are incorrect"))
                .and()
                .statusCode(SC_BAD_REQUEST);
    }

    @Step("Проверяем тело и статус код для получения заказа с авторизацией")
    public void checkAnswerForGetOrderWithAuthStep(Response response, String ingredient) {
        response.then().assertThat().body("success", equalTo(true),
                        "orders[0].ingredients[0]", equalTo(ingredient))
                .and()
                .statusCode(SC_OK);
    }

    @Step("Проверяем тело и статус код для получения заказа без авторизации")
    public void checkAnswerForGetOrderWithoutAuthStep(Response response) {
        response.then().assertThat().body("success", equalTo(false),
                        "message", equalTo("You should be authorised"))
                .and()
                .statusCode(SC_UNAUTHORIZED);
    }
}
