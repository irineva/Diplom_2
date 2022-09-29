import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import requests.testsData.UserCredsData;
import steps.TestSteps;

public class LoginTests {
    TestSteps testSteps = new TestSteps();
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
    @DisplayName("Авторизация пользователем")
    public void loginUserTest() {
        testSteps.createUserStep(user);
        Response response = testSteps.loginUserStep(user);
        testSteps.checkAnswerForSuccsessStep(response);
    }

    @Test
    @DisplayName("Авторизация пользователем с неверным паролем")
    public void loginUserWithInvalidPassTest() {
        testSteps.createUserStep(user);
        user.setPassword("");
        Response response = testSteps.loginUserStep(user);
        testSteps.checkAnswerForLoginWithInvalidCredsStep(response);
    }
}
