import data.CourierData;
import steps.CourierSteps;
import helpers.CourierHelper;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.MatcherAssert.assertThat;


import static org.hamcrest.Matchers.*;

@Epic("Тестирование API Яндекс Самокат")
@Feature("Авторизация курьера")

public class CourierLoginTest extends BaseApiTest {
    private String testLogin;
    private String testPassword;


    @Before
    public void createTestCourier() {
        CourierData.TestCourierData courierData = CourierHelper.createAndRegisterRandomCourier();
        testLogin = courierData.login;
        testPassword = courierData.password;
    }

    @Test
    @Description("Проверка успешной авторизации курьера")
    @Story("Позитивные тесты")
    public void testCourierCanLogin() {
        CourierSteps.loginCourier(testLogin, testPassword)
                .then()
                .log().all()
                .statusCode(SC_OK)
                .body("id", notNullValue())
                .body("id", instanceOf(Integer.class));
    }

    @Test
    @Description("Проверка, что авторизация возвращает ID курьера")
    @Story("Позитивные тесты")
    public void testSuccessfulLoginReturnsId() {
        int id = CourierSteps.loginCourier(testLogin, testPassword)
                .then()
                .statusCode(SC_OK)
                .extract()
                .path("id");

        assertThat(id, greaterThan(0));
    }

    @Test
    @Description("Проверка ошибки при авторизации с неверным логином")
    @Story("Негативные тесты")
    public void testLoginWithWrongLogin() {
        String wrongLogin = "wrong_" + System.currentTimeMillis();

        CourierSteps.loginCourier(wrongLogin, testPassword)
                .then()
                .log().all()
                .statusCode(SC_NOT_FOUND)
                .body("message", containsString("Учетная запись не найдена"));
    }

    @Test
    @Description("Проверка ошибки при авторизации с неверным паролем")
    @Story("Негативные тесты")
    public void testLoginWithWrongPassword() {
        String wrongPassword = "wrong123";

        CourierSteps.loginCourier(testLogin, wrongPassword)
                .then()
                .log().all()
                .statusCode(SC_NOT_FOUND)
                .body("message", containsString("Учетная запись не найдена"));
    }

    @Test
    @Description("Проверка ошибки при авторизации без пароля")
    @Story("Негативные тесты")
    public void testLoginWithoutPassword() {
        CourierSteps.loginCourierWithoutPassword(testLogin)
                .then()
                .log().all()
                .statusCode(SC_BAD_REQUEST)
                .body("message", containsString("Недостаточно данных для входа"));
    }

    @Test
    @Description("Проверка ошибки при авторизации без логина")
    @Story("Негативные тесты")
    public void testLoginWithoutLogin() {
        CourierSteps.loginCourierWithoutLogin(testPassword)
                .then()
                .log().all()
                .statusCode(SC_BAD_REQUEST)
                .body("message", containsString("Недостаточно данных"));
    }

    @Test
    @Description("Проверка ошибки при авторизации несуществующего пользователя")
    @Story("Негативные тесты")
    public void testLoginNonexistentUser() {
        String nonexistentLogin = "nonexistent_" + System.currentTimeMillis();
        String nonexistentPassword = "nonexistent123";

        CourierSteps.loginCourier(nonexistentLogin, nonexistentPassword)
                .then()
                .log().all()
                .statusCode(SC_NOT_FOUND)
                .body("message", containsString("Учетная запись не найдена"));
    }

    @Test
    @Description("Проверка ошибки при авторизации с пустыми полями")
    @Story("Негативные тесты")
    public void testLoginWithEmptyFields() {
        CourierSteps.loginCourier("", "")
                .then()
                .log().all()
                .statusCode(SC_BAD_REQUEST);
    }
}


