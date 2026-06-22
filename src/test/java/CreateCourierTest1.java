import model.CourierModel;
import org.junit.Test;
import steps.CourierSteps;
import data.CourierData;
import org.junit.After;
import static org.apache.http.HttpStatus.*;


import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.containsString;

public class CreateCourierTest1 extends BaseApiTest {
    private String createdLogin;
    private String createdPassword;
    private int courierId;

    @After
    public void cleanUp() {
        if (courierId > 0) {
            CourierSteps.deleteCourier(courierId)
                    .then()
                    .log().all()
                    .statusCode(SC_OK);
            System.out.println("Курьер удалён, ID: " + courierId);
        } else if (createdLogin != null && createdPassword != null) {
            try {
                courierId = CourierSteps.loginCourier(createdLogin, createdPassword)
                        .then()
                        .statusCode(SC_OK)
                        .extract()
                        .path("id");
                if (courierId > 0) {
                    CourierSteps.deleteCourier(courierId)
                            .then()
                            .statusCode(SC_OK);
                    System.out.println("Курьер удалён, ID: " + courierId);
                }
            } catch (Exception e) {
                System.out.println("Курьер уже удалён или не найден");
            }
        }
    }

    @Test
    public void testCreateCourierSuccess() {
                CourierSteps.createRandomCourier()
                .then()
                .log().all()
                .statusCode(SC_CREATED)
                .body("ok", equalTo(true));
    }
    @Test
    public void testCannotCreateDuplicateCourier() {
        // Создаём первого курьера и сохраняем логин
        String login = CourierData.generateLogin();

        CourierSteps.createCourierWithLogin(login)
                .then()
                .statusCode(SC_CREATED);
        createdLogin = login;
        // Пытаемся создать второго с ТЕМ ЖЕ логином
        CourierSteps.createCourierWithLogin(login)
                .then()
                .log().all()
                .statusCode(SC_CONFLICT)
                .body("message", containsString("Этот логин уже используется"));
    }

    //  Нет пароля (ошибка)
    @Test
    public void testCreateCourierWithoutPassword() {
        CourierSteps.createCourierWithoutPassword()
                .then()
                .log().all()
                .statusCode(SC_BAD_REQUEST)
                .body("message", containsString("Недостаточно данных"));
    }

    //  Нет логина (ошибка)
    @Test
    public void testCreateCourierWithoutLogin() {
        CourierSteps.createCourierWithoutLogin()
                .then()
                .log().all()
                .statusCode(SC_BAD_REQUEST)
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
                .statusCode(SC_CREATED)
                .body("ok", equalTo(true));
        createdLogin = courier.getLogin();
        createdPassword = courier.getPassword();
    }

    //   Пустой логин (ошибка)
    @Test
    public void testCreateCourierWithEmptyLogin() {
        CourierSteps.createCourierWithEmptyLogin()
                .then()
                .log().all()
                .statusCode(SC_BAD_REQUEST);
    }
}





