package steps;

import data.CourierData;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.CourierModel;
import static io.restassured.RestAssured.given;
import model.CourierCredentials;


public class CourierSteps {
    @Step("Создание курьера с логином: {courier.login}")
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
    @Step("Создание случайного курьера")
    public static Response createRandomCourier() {
        CourierModel courier = CourierModel.builder()
                .login(CourierData.generateLogin())
                .password(CourierData.generatePassword())
                .firstName(CourierData.generateFirstName())
                .build();
        return createCourier(courier);
    }

    // 2. Создание курьера с заданным логином
    @Step("Создание курьера с логином: {login}")
    public static Response createCourierWithLogin(String login) {
        CourierModel courier = CourierModel.builder()
                .login(login)
                .password(CourierData.generatePassword())
                .firstName(CourierData.generateFirstName())
                .build();
        return createCourier(courier);
    }

    // 3. Создание курьера без пароля (для негативных тестов)
    @Step("Создание курьера без пароля")
    public static Response createCourierWithoutPassword() {
        CourierModel courier = CourierModel.builder()
                .login(CourierData.generateLogin())
                .firstName(CourierData.generateFirstName())
                .build();
        return createCourier(courier);
    }

    // 4. Создание курьера без логина (для негативных тестов)
    @Step("Создание курьера без логина")
    public static Response createCourierWithoutLogin() {
        CourierModel courier = CourierModel.builder()
                .password(CourierData.generatePassword())
                .firstName(CourierData.generateFirstName())
                .build();
        return createCourier(courier);
    }

    // 5. Создание курьера с пустым логином
    @Step("Создание курьера с пустым логином")
    public static Response createCourierWithEmptyLogin() {
        CourierModel courier = CourierModel.builder()
                .login("")
                .password(CourierData.generatePassword())
                .firstName(CourierData.generateFirstName())
                .build();
        return createCourier(courier);
    }

    // Логин с объектом CourierCredentials
    @Step("Логин курьера с логином: {credentials.login}")
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
    @Step("Логин курьера с логином: {login}")
    public static Response loginCourier(String login, String password) {
        CourierCredentials credentials = CourierCredentials.builder()
                .login(login)
                .password(password)
                .build();
        return loginCourier(credentials);
    }

    // БЕЗ ПАРОЛЯ (для негативных тестов)
    @Step("Попытка логина без пароля")
    public static Response loginCourierWithoutPassword(String login) {
        CourierCredentials credentials = CourierCredentials.builder()
                .login(login)
                .password("")
                .build();
        return loginCourier(credentials);
    }

    // БЕЗ ЛОГИНА (для негативных тестов)
    @Step("Попытка логина без логина")
    public static Response loginCourierWithoutLogin(String password) {
        CourierCredentials credentials = CourierCredentials.builder()
                .password(password)
                .build();
        return loginCourier(credentials);
    }

    @Step("Удаление курьера с ID: {courierId}")
    public static Response deleteCourier(int courierId) {
        return given()
                .log().all()
                .when()
                .delete(CourierData.getCourierPath(courierId))  // метод формирования id
                .then()
                .extract().response();
    }

    @Step("Попытка удаления курьера без ID")
    public static Response deleteCourierWithoutId() {
        return given()
                .log().all()
                .when()
                .delete(CourierData.CREATE_COURIER_PATH)  //  без id
                .then()
                .extract().response();
    }
}
