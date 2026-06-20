import model.CourierModel;
import org.junit.Test;
import steps.CourierSteps;
import data.CourierData;


import static java.net.HttpURLConnection.HTTP_CREATED;
import static org.hamcrest.Matchers.equalTo;
import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_CONFLICT;
import static org.hamcrest.Matchers.containsString;

public class CreateCourierTest1 extends BaseApiTest {

    @Test
    public void testCreateCourierSuccess() {
                CourierSteps.createRandomCourier()
                .then()
                .log().all()
                .statusCode(HTTP_CREATED)
                .body("ok", equalTo(true));
    }
    @Test
    public void testCannotCreateDuplicateCourier() {
        // Создаём первого курьера и сохраняем логин
        String login = CourierData.generateLogin();

        CourierSteps.createCourierWithLogin(login)
                .then()
                .statusCode(HTTP_CREATED);

        // Пытаемся создать второго с ТЕМ ЖЕ логином
        CourierSteps.createCourierWithLogin(login)
                .then()
                .log().all()
                .statusCode(HTTP_CONFLICT)
                .body("message", containsString("Этот логин уже используется"));
    }

    //  Нет пароля (ошибка)
    @Test
    public void testCreateCourierWithoutPassword() {
        CourierSteps.createCourierWithoutPassword()
                .then()
                .log().all()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message", containsString("Недостаточно данных"));
    }

    //  Нет логина (ошибка)
    @Test
    public void testCreateCourierWithoutLogin() {
        CourierSteps.createCourierWithoutLogin()
                .then()
                .log().all()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message", containsString("Недостаточно данных"));
    }

    // Нет имени (успех, так как имя не обязательно)
    @Test
    public void testCreateCourierWithoutFirstName() {
        // Используем основной метод, так как имя не обязательно
        CourierModel courier = CourierModel.builder()
                .login(CourierData.generateLogin())
                .password(CourierData.generatePassword())
                .build();

        CourierSteps.createCourier(courier)
                .then()
                .log().all()
                .statusCode(HTTP_CREATED)
                .body("ok", equalTo(true));
    }

    //   Пустой логин (ошибка)
    @Test
    public void testCreateCourierWithEmptyLogin() {
        CourierSteps.createCourierWithEmptyLogin()
                .then()
                .log().all()
                .statusCode(HTTP_BAD_REQUEST);
    }
}





