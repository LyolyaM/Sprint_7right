import data.CourierData;
import helpers.CourierHelper;
import steps.CourierSteps;
import org.junit.Test;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;

import static org.apache.http.HttpStatus.*;



import static org.hamcrest.Matchers.*;

@Epic("Тестирование API Яндекс Самокат")
@Feature("Удаление курьера")
public class CourierDeleteTest extends BaseApiTest {

    @Test
    @Description("Проверка успешного удаления курьера")
    @Story("Позитивные тесты")
    public void testDeleteCourierSuccess() {
        // Создаём курьера
        CourierData.TestCourierData courierData = CourierHelper.createAndRegisterRandomCourier();
        String login = courierData.login;
        String password = courierData.password;

        // Логинимся и получаем id
        int courierId = CourierSteps.loginCourier(login, password)
                .then()
                .statusCode(SC_OK)
                .extract()
                .path("id");

        // Удаляем курьера
        CourierSteps.deleteCourier(courierId)
                .then()
                .log().all()
                .statusCode(SC_OK)
                .body("ok", equalTo(true));
    }

    //  Удаление курьера без id (ошибка)
    @Test
    @Description("Проверка ошибки при удалении курьера без ID")
    @Story("Негативные тесты")
    public void testDeleteCourierWithoutId() {
        CourierSteps.deleteCourierWithoutId()
                .then()
                .log().all()
                .statusCode(SC_NOT_FOUND)
                .body("message", containsString("Not Found"));
    }


    @Test
    @Description("Проверка ошибки при удалении несуществующего курьера")
    @Story("Негативные тесты")
    public void testDeleteNonexistentCourier() {
        int nonexistentId = 999999;

        CourierSteps.deleteCourier(nonexistentId)
                .then()
                .log().all()
                .statusCode(SC_NOT_FOUND)
                .body("message", containsString("Курьера с таким id нет."));
    }


    @Test
    @Description("Проверка, что после удаления курьер не может авторизоваться")
    @Story("Негативные тесты")
    public void testDeletedCourierCannotLogin() {
        // Создаём курьера
        CourierData.TestCourierData courierData = CourierHelper.createAndRegisterRandomCourier();
        String login = courierData.login;
        String password = courierData.password;

        // Логинимся и получаем id
        int courierId = CourierSteps.loginCourier(login, password)
                .then()
                .statusCode(SC_OK)
                .extract()
                .path("id");

        // Удаляем курьера
        CourierSteps.deleteCourier(courierId)
                .then()
                .statusCode(SC_OK);

        // Пытаемся снова авторизоваться
        CourierSteps.loginCourier(login, password)
                .then()
                .log().all()
                .statusCode(SC_NOT_FOUND);
    }
}

