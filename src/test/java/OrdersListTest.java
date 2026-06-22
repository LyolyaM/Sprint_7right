import data.CourierData;
import data.OrderData;
import steps.CourierSteps;
import steps.OrderSteps;
import org.junit.Test;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;

import java.util.List;
import helpers.CourierHelper;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.*;

@Epic("Тестирование API Яндекс Самокат")
@Feature("Список заказов")

public class OrdersListTest extends BaseApiTest{
    @Test
    @Description("Проверка, что список заказов возвращается")
    @Story("Позитивные тесты")
    public void testOrdersListReturnsList() {
        OrderSteps.getOrdersList()
                .then()
                .log().all()
                .statusCode(SC_OK)
                .body("orders", notNullValue())
                .body("orders", instanceOf(List.class));
    }

    @Test
    @Description("Проверка получения заказов по ID курьера")
    @Story("Позитивные тесты")
    public void testOrdersListWithCourierId() {
        //  Используем Helper
        CourierData.TestCourierData courierData = CourierHelper.createAndRegisterRandomCourier();
        String login = courierData.login;
        String password = courierData.password;

        //  Логинимся с тем же паролем
        int courierId = CourierSteps.loginCourier(login, password)
                .then()
                .statusCode(SC_OK)
                .extract()
                .path("id");

        OrderSteps.getOrdersList(courierId, null, null)
                .then()
                .log().all()
                .statusCode(SC_OK)
                .body("orders", notNullValue());
    }

    @Test
    @Description("Проверка получения заказов по ID курьера и станции")
    @Story("Позитивные тесты")
    public void testOrdersListWithCourierIdAndStation() {

        CourierData.TestCourierData courierData = CourierHelper.createAndRegisterRandomCourier();
        String login = courierData.login;
        String password = courierData.password;

        // Логинимся и получаем ID
        int courierId = CourierSteps.loginCourier(login, password)
                .then()
                .statusCode(SC_OK)
                .extract()
                .path("id");

        // Получаем заказы курьера с фильтром по станции
        OrderSteps.getOrdersListWithCourierIdAndStation(courierId, OrderData.STATION_1_AND_2)
                .then()
                .log().all()
                .statusCode(SC_OK)
                .body("orders", notNullValue());
    }

    @Test
    @Description("Проверка получения заказов с пагинацией (limit и page)")
    @Story("Позитивные тесты")
    public void testOrdersListWithLimitAndPage() {
        OrderSteps.getOrdersList(null, 10, 0)
                .then()
                .log().all()
                .statusCode(SC_OK)
                .body("orders", notNullValue())
                .body("orders.size()", lessThanOrEqualTo(10));
    }

    @Test
    @Description("Проверка получения заказов с пагинацией и фильтром по станции")
    @Story("Позитивные тесты")
    public void testOrdersListWithLimitPageAndStation() {
        OrderSteps.getOrdersListWithLimitPageAndStation(10, 0, OrderData.STATION_KALUZHSKAYA)
                .then()
                .log().all()
                .statusCode(SC_OK)
                .body("orders", notNullValue())
                .body("orders.size()", lessThanOrEqualTo(10));
    }

    @Test
    @Description("Проверка получения заказов с фильтром по станции")
    @Story("Позитивные тесты")
    public void testOrdersListWithStationOnly() {
        OrderSteps.getOrdersListWithStation(OrderData.STATION_1_AND_2)
                .then()
                .log().all()
                .statusCode(SC_OK)
                .body("orders", notNullValue());
    }
}

