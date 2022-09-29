import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import requests.testsData.Ingredients;
import requests.testsData.UserCredsData;
import steps.TestSteps;

public class CreateOrderTests {
    TestSteps testSteps = new TestSteps();
    Ingredients ingredients = new Ingredients();
    UserCredsData user;

    @Before
    public void setUser() {
        user = testSteps.setUserDataStep();
    }

    @After
    public void deleteTestData() {
        testSteps.deleteUserStep();
        testSteps.clearCashStep();
    }

    @Test
    @DisplayName("Создание заказа с ингредиентами")
    public void createOrderWithIngredientsTest() {
        ingredients.setIngredients(testSteps.getIngredientStep());

        testSteps.createUserStep(user);
        Response response = testSteps.createOrderStep(ingredients, true);
        testSteps.checkAnswerForCreateSuccessOrderStep(response);
    }

    @Test
    @DisplayName("Создание заказа без ингредиентами")
    public void createOrderWithoutIngredientsTest() {
        testSteps.createUserStep(user);
        Response response = testSteps.createOrderStep(ingredients, true);
        testSteps.checkAnswerForCreateOrderWithoutIngredientsStep(response);
    }

    @Test
    @DisplayName("Создание заказа без ингредиентами")
    public void createOrderWithInvalidIngredientsTest() {
        ingredients.setIngredients(null);
        testSteps.createUserStep(user);
        Response response = testSteps.createOrderStep(ingredients, true);
        testSteps.checkAnswerForCreateOrderInvalidIngredientsStep(response);
    }

    @Test
    @DisplayName("Создание заказа без авторизации")
    public void createOrderNotAuthorisedTest() {
        ingredients.setIngredients(testSteps.getIngredientStep());
        Response response = testSteps.createOrderStep(ingredients, false);
        testSteps.checkAnswerForCreateSuccessOrderStep(response);
    }

    @Test
    @DisplayName("Получение заказа с авторизацией")
    public void getOrderWithAuthTest() {
        testSteps.createUserStep(user);
        ingredients.setIngredients(testSteps.getIngredientStep());
        testSteps.createOrderStep(ingredients, true);
        Response response = testSteps.getOrderStep(true);
        testSteps.checkAnswerForGetOrderWithAuthStep(response, ingredients.getIngredients().get(0));
    }

    @Test
    @DisplayName("Получение заказа без авторизации")
    public void getOrderWithoutAuthTest() {
        ingredients.setIngredients(testSteps.getIngredientStep());
        testSteps.createOrderStep(ingredients, true);
        Response response = testSteps.getOrderStep(false);
        testSteps.checkAnswerForGetOrderWithoutAuthStep(response);
    }

}
