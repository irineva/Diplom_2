import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import requests.testsData.UserCredsData;
import steps.TestSteps;

public class UpdateUserDataTests {
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
    @DisplayName("Изменение почты пользователя с авторизацией")
    public void updateEmailUserTest() {
        testSteps.createUserStep(user);
        String updatedEmail = "newemail@gmail.ru";
        user.setEmail(updatedEmail);
        Response response = testSteps.updateUserStep(user, true);
        testSteps.checkAnswerForUpdatedAuthorisedUserStep(response, updatedEmail, "user.email");
    }

    @Test
    @DisplayName("Изменение почты пользователя без авторизации")
    public void updateEmailNotAuthorisedUserTest() {
        testSteps.createUserStep(user);
        String updatedEmail = "newemail@gmail.ru";
        user.setEmail(updatedEmail);
        Response response = testSteps.updateUserStep(user, false);
        testSteps.checkAnswerForUpdatedNotAuthorisedUserStep(response);
    }

}
