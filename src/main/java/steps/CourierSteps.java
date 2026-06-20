package steps;

import data.CourierData;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.CourierModel;
import static io.restassured.RestAssured.given;
import model.CourierCredentials;


public class CourierSteps {

    public static Response createCourier(CourierModel courier) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(courier)
                .when()
                .post(CourierData.CREATE_COURIER_PATH)
                .then()
                .extract().response();
    }

    // 1. Создание случайного курьера (было)
    public static Response createRandomCourier() {
        CourierModel courier = CourierModel.builder()
                .login(CourierData.generateLogin())
                .password(CourierData.generatePassword())
                .firstName(CourierData.generateFirstName())
                .build();
        return createCourier(courier);
    }

    // 2. Создание курьера с заданным логином
    public static Response createCourierWithLogin(String login) {
        CourierModel courier = CourierModel.builder()
                .login(login)
                .password(CourierData.generatePassword())
                .firstName(CourierData.generateFirstName())
                .build();
        return createCourier(courier);
    }

    // 3. Создание курьера без пароля (для негативных тестов)
    public static Response createCourierWithoutPassword() {
        CourierModel courier = CourierModel.builder()
                .login(CourierData.generateLogin())
                .firstName(CourierData.generateFirstName())
                .build();
        return createCourier(courier);
    }

    // 4. Создание курьера без логина (для негативных тестов)
    public static Response createCourierWithoutLogin() {
        CourierModel courier = CourierModel.builder()
                .password(CourierData.generatePassword())
                .firstName(CourierData.generateFirstName())
                .build();
        return createCourier(courier);
    }

    // 5. Создание курьера с пустым логином
    public static Response createCourierWithEmptyLogin() {
        CourierModel courier = CourierModel.builder()
                .login("")
                .password(CourierData.generatePassword())
                .firstName(CourierData.generateFirstName())
                .build();
        return createCourier(courier);
    }

    // Логин с объектом CourierCredentials
    public static Response loginCourier(CourierCredentials credentials) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(credentials)
                .when()
                .post(CourierData.LOGIN_COURIER_PATH)
                .then()
                .extract().response();
    }

    // Логин с логином и паролем (удобная обёртка)
    public static Response loginCourier(String login, String password) {
        CourierCredentials credentials = CourierCredentials.builder()
                .login(login)
                .password(password)
                .build();
        return loginCourier(credentials);
    }

    // БЕЗ ПАРОЛЯ (для негативных тестов)
    public static Response loginCourierWithoutPassword(String login) {
        CourierCredentials credentials = CourierCredentials.builder()
                .login(login)
                .password("")
                .build();
        return loginCourier(credentials);
    }

    // БЕЗ ЛОГИНА (для негативных тестов)
    public static Response loginCourierWithoutLogin(String password) {
        CourierCredentials credentials = CourierCredentials.builder()
                .password(password)
                .build();
        return loginCourier(credentials);
    }

    public static Response deleteCourier(int courierId) {
        return given()
                .log().all()
                .when()
                .delete(CourierData.getCourierPath(courierId))  // метод формирования id
                .then()
                .extract().response();
    }

    public static Response deleteCourierWithoutId() {
        return given()
                .log().all()
                .when()
                .delete(CourierData.CREATE_COURIER_PATH)  //  без id
                .then()
                .extract().response();
    }
}
