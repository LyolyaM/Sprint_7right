package steps;

import data.OrderData;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.Order;
import java.util.List;

import static io.restassured.RestAssured.given;

public class OrderSteps {

    public static Response createOrder(Order order) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(order)
                .when()
                .post(OrderData.ORDER_PATH)
                .then()
                .extract().response();
    }

    public static Response createOrderWithColors(List<String> colors) {
        Order order = OrderData.createOrderWithColors(colors);
        return createOrder(order);
    }
    // получение списка заказов
    public static Response getOrdersList() {
        return given()
                .log().all()
                .get(OrderData.ORDER_PATH)
                .then()
                .extract().response();
    }

    public static Response getOrdersList(Integer courierId, Integer limit, Integer page) {
        return given()
                .log().all()
                .queryParam(OrderData.PARAM_COURIER_ID, courierId)
                .queryParam(OrderData.PARAM_LIMIT, limit)
                .queryParam(OrderData.PARAM_PAGE, page)
                .when()
                .get(OrderData.ORDER_PATH)
                .then()
                .extract().response();
    }

    public static Response getOrdersListWithStation(String stationJson) {
        return given()
                .log().all()
                .queryParam(OrderData.PARAM_NEAREST_STATION, stationJson)
                .when()
                .get(OrderData.ORDER_PATH)
                .then()
                .extract().response();
    }

    public static Response getOrdersListWithCourierIdAndStation(Integer courierId, String stationJson) {
        return given()
                .log().all()
                .queryParam(OrderData.PARAM_COURIER_ID, courierId)
                .queryParam(OrderData.PARAM_NEAREST_STATION, stationJson)
                .when()
                .get(OrderData.ORDER_PATH)
                .then()
                .extract().response();
    }

    public static Response getOrdersListWithLimitPageAndStation(Integer limit, Integer page, String stationJson) {
        return given()
                .log().all()
                .queryParam(OrderData.PARAM_LIMIT, limit)
                .queryParam(OrderData.PARAM_PAGE, page)
                .queryParam(OrderData.PARAM_NEAREST_STATION, stationJson)
                .when()
                .get(OrderData.ORDER_PATH)
                .then()
                .extract().response();
    }
}
