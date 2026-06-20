import data.CourierData;
import steps.CourierSteps;
import helpers.CourierHelper;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;

import static java.net.HttpURLConnection.*;
import static org.hamcrest.Matchers.*;

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
    public void testCourierCanLogin() {
        CourierSteps.loginCourier(testLogin, testPassword)
                .then()
                .log().all()
                .statusCode(HTTP_OK)
                .body("id", notNullValue())
                .body("id", instanceOf(Integer.class));
    }

    @Test
    public void testSuccessfulLoginReturnsId() {
        int id = CourierSteps.loginCourier(testLogin, testPassword)
                .then()
                .statusCode(HTTP_OK)
                .extract()
                .path("id");

        assertThat(id, greaterThan(0));
    }

    @Test
    public void testLoginWithWrongLogin() {
        String wrongLogin = "wrong_" + System.currentTimeMillis();

        CourierSteps.loginCourier(wrongLogin, testPassword)
                .then()
                .log().all()
                .statusCode(HTTP_NOT_FOUND)
                .body("message", containsString("Учетная запись не найдена"));
    }

    @Test
    public void testLoginWithWrongPassword() {
        String wrongPassword = "wrong123";

        CourierSteps.loginCourier(testLogin, wrongPassword)
                .then()
                .log().all()
                .statusCode(HTTP_NOT_FOUND)
                .body("message", containsString("Учетная запись не найдена"));
    }

    @Test
    public void testLoginWithoutPassword() {
        CourierSteps.loginCourierWithoutPassword(testLogin)
                .then()
                .log().all()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message", containsString("Недостаточно данных для входа"));
    }

    @Test
    public void testLoginWithoutLogin() {
        CourierSteps.loginCourierWithoutLogin(testPassword)
                .then()
                .log().all()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message", containsString("Недостаточно данных"));
    }

    @Test
    public void testLoginNonexistentUser() {
        String nonexistentLogin = "nonexistent_" + System.currentTimeMillis();
        String nonexistentPassword = "nonexistent123";

        CourierSteps.loginCourier(nonexistentLogin, nonexistentPassword)
                .then()
                .log().all()
                .statusCode(HTTP_NOT_FOUND)
                .body("message", containsString("Учетная запись не найдена"));
    }

    @Test
    public void testLoginWithEmptyFields() {
        CourierSteps.loginCourier("", "")
                .then()
                .log().all()
                .statusCode(HTTP_BAD_REQUEST);
    }
}


