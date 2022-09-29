import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import requests.testsData.UserCredsData;
import steps.TestSteps;


public class CreateUserTests {
    static String password = "1234";
    static String name = "kate";
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
    @DisplayName("Создание пользователя")
    public void createUserTest() {
        Response response = testSteps.createUserStep(user);
        testSteps.checkAnswerForSuccsessStep(response);
    }

    @Test
    @DisplayName("Попытка создания уже существующего пользователя")
    public void createDublicateUserTest() {
        testSteps.createUserStep(user);
        Response response = testSteps.createUserStep(user);
        testSteps.checkAnswerForExistingUserCreateStep(response);
    }

    @Test
    @DisplayName("Попытка создать пользователя без email")
    public void createUserWithoutEmailTest() {
        Response response = testSteps.createUserStep(new UserCredsData("", password, name));
        testSteps.checkAnswerForInvalidCreateRequestStep(response);
    }

}
