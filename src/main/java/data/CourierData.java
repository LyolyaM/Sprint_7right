package data;
import com.github.javafaker.Faker;
import model.CourierModel;


public class CourierData {
    public static final String BASE_URI = "https://qa-scooter.praktikum-services.ru";
    public static final String CREATE_COURIER_PATH = "/api/v1/courier";
    public static final String LOGIN_COURIER_PATH = "/api/v1/courier/login";

    private static final Faker faker = new Faker();

    public static String generateLogin() {
        return faker.name().lastName() + faker.regexify("[0-9]{4}");
    }

    public static String generatePassword() {
        return faker.regexify("[0-9]{4}");
    }

    public static String generateFirstName() {
        return faker.name().firstName() + faker.regexify("[0-9]{2}");
    }

    //  Готовый случайный курьер
    public static CourierModel generateRandomCourier() {
        return CourierModel.builder()
                .login(generateLogin())
                .password(generatePassword())
                .firstName(generateFirstName())
                .build();
    }

    // Курьер с заданным логином
    public static CourierModel generateCourierWithLogin(String login) {
        return CourierModel.builder()
                .login(login)
                .password(generatePassword())
                .firstName(generateFirstName())
                .build();
    }
    // Метод для формирования пути с id
    public static String getCourierPath(int courierId) {
        return CREATE_COURIER_PATH + "/" + courierId;
    }

    // Класс для хранения данных курьера
    public static class TestCourierData {
        public final String login;
        public final String password;

        public TestCourierData(String login, String password) {
            this.login = login;
            this.password = password;
        }
    }
}







