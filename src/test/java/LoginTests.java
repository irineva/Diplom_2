import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Test;
import requests.testsData.UserCredsData;
import steps.TestSteps;

public class LoginTests {
    static String email = "neva@yandex.ru";
    static String password = "1234";
    static String name = "Kate";

    TestSteps testSteps = new TestSteps();

    @After
    public void deleteTestData() {
        testSteps.deleteUserStep();
        testSteps.clearCashStep();
    }

    @Test
    @DisplayName("Авторизация пользователем")
    public void loginUserTest() {
        UserCredsData user = new UserCredsData(email, password, name);
        testSteps.createUserStep(user);
        Response response = testSteps.loginUserStep(user);
        testSteps.checkAnswerForSuccsessStep(response);
    }

    @Test
    @DisplayName("Авторизация пользователем с неверным паролем")
    public void loginUserWithInvalidPassTest() {
        UserCredsData user = new UserCredsData(email, password, name);
        testSteps.createUserStep(user);
        user.setPassword("");
        Response response = testSteps.loginUserStep(user);
        testSteps.checkAnswerForLoginWithInvalidCredsStep(response);
    }
}
