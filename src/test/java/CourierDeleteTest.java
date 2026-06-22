import data.CourierData;
import helpers.CourierHelper;
import steps.CourierSteps;
import org.junit.Test;
import static org.apache.http.HttpStatus.*;


import static java.net.HttpURLConnection.HTTP_OK;
import static org.hamcrest.Matchers.*;

public class CourierDeleteTest extends BaseApiTest {
    //Успешное удаление курьера
    @Test
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
                .statusCode(HTTP_OK)
                .body("ok", equalTo(true));
    }

    //  Удаление курьера без id (ошибка)
    @Test
    public void testDeleteCourierWithoutId() {
        CourierSteps.deleteCourierWithoutId()
                .then()
                .log().all()
                .statusCode(SC_NOT_FOUND)
                .body("message", containsString("Not Found"));
    }

    // Удаление несуществующего курьера
    @Test
    public void testDeleteNonexistentCourier() {
        int nonexistentId = 999999;

        CourierSteps.deleteCourier(nonexistentId)
                .then()
                .log().all()
                .statusCode(SC_NOT_FOUND)
                .body("message", containsString("Курьера с таким id нет."));
    }

    // Проверка, что курьер удалён (нельзя авторизоваться)
    @Test
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
                .statusCode(HTTP_OK);

        // Пытаемся снова авторизоваться
        CourierSteps.loginCourier(login, password)
                .then()
                .log().all()
                .statusCode(SC_NOT_FOUND);
    }
}

