import data.CourierData;
import data.OrderData;
import steps.CourierSteps;
import steps.OrderSteps;
import org.junit.Test;
import java.util.List;
import helpers.CourierHelper;

import static java.net.HttpURLConnection.HTTP_OK;
import static org.hamcrest.Matchers.*;

public class OrdersListTest extends BaseApiTest{
    @Test
    public void testOrdersListReturnsList() {
        OrderSteps.getOrdersList()
                .then()
                .log().all()
                .statusCode(HTTP_OK)
                .body("orders", notNullValue())
                .body("orders", instanceOf(List.class));
    }

    @Test
    public void testOrdersListWithCourierId() {
        //  Используем Helper
        CourierData.TestCourierData courierData = CourierHelper.createAndRegisterRandomCourier();
        String login = courierData.login;
        String password = courierData.password;

        //  Логинимся с тем же паролем
        int courierId = CourierSteps.loginCourier(login, password)
                .then()
                .statusCode(200)
                .extract()
                .path("id");

        OrderSteps.getOrdersList(courierId, null, null)
                .then()
                .log().all()
                .statusCode(HTTP_OK)
                .body("orders", notNullValue());
    }

    @Test
    public void testOrdersListWithCourierIdAndStation() {
        // Создаём курьера через Helper
        CourierData.TestCourierData courierData = CourierHelper.createAndRegisterRandomCourier();
        String login = courierData.login;
        String password = courierData.password;

        // Логинимся и получаем ID
        int courierId = CourierSteps.loginCourier(login, password)
                .then()
                .statusCode(200)
                .extract()
                .path("id");

        // Получаем заказы курьера с фильтром по станции
        OrderSteps.getOrdersListWithCourierIdAndStation(courierId, OrderData.STATION_1_AND_2)
                .then()
                .log().all()
                .statusCode(HTTP_OK)
                .body("orders", notNullValue());
    }

    @Test
    public void testOrdersListWithLimitAndPage() {
        OrderSteps.getOrdersList(null, 10, 0)
                .then()
                .log().all()
                .statusCode(HTTP_OK)
                .body("orders", notNullValue())
                .body("orders.size()", lessThanOrEqualTo(10));
    }

    @Test
    public void testOrdersListWithLimitPageAndStation() {
        OrderSteps.getOrdersListWithLimitPageAndStation(10, 0, OrderData.STATION_KALUZHSKAYA)
                .then()
                .log().all()
                .statusCode(HTTP_OK)
                .body("orders", notNullValue())
                .body("orders.size()", lessThanOrEqualTo(10));
    }

    @Test
    public void testOrdersListWithStationOnly() {
        OrderSteps.getOrdersListWithStation(OrderData.STATION_1_AND_2)
                .then()
                .log().all()
                .statusCode(HTTP_OK)
                .body("orders", notNullValue());
    }
}

