package helpers;

import data.CourierData;
import model.CourierModel;
import steps.CourierSteps;


public class CourierHelper {
    // Создаёт и регистрирует случайного курьера
    public static CourierData.TestCourierData createAndRegisterRandomCourier() {
        String login = CourierData.generateLogin();
        String password = CourierData.generatePassword();
        String firstName = CourierData.generateFirstName();

        CourierModel courier = CourierModel.builder()
                .login(login)
                .password(password)
                .firstName(firstName)
                .build();

        CourierSteps.createCourier(courier);


        return new CourierData.TestCourierData(login, password);
    }

    // Создаёт и регистрирует курьера с заданным логином
    public static CourierData.TestCourierData createAndRegisterCourierWithLogin(String login) {
        String password = CourierData.generatePassword();
        String firstName = CourierData.generateFirstName();

        CourierModel courier = CourierModel.builder()
                .login(login)
                .password(password)
                .firstName(firstName)
                .build();

        CourierSteps.createCourier(courier);


        return new CourierData.TestCourierData(login, password);
    }

    // Создаёт и регистрирует курьера с заданным логином и паролем
    public static CourierData.TestCourierData createAndRegisterCourierWithCredentials(String login, String password) {
        String firstName = CourierData.generateFirstName();

        CourierModel courier = CourierModel.builder()
                .login(login)
                .password(password)
                .firstName(firstName)
                .build();

        CourierSteps.createCourier(courier);


        return new CourierData.TestCourierData(login, password);
    }
}

