package data;

import model.Order;
import java.util.List;


public class OrderData {
    public static final String ORDER_PATH = "/api/v1/orders";

    // параметры запросов
    public static final String PARAM_COURIER_ID = "courierId";
    public static final String PARAM_NEAREST_STATION = "nearestStation";
    public static final String PARAM_LIMIT = "limit";
    public static final String PARAM_PAGE = "page";

    // тестовые данные
    public static final String DEFAULT_FIRST_NAME = "Naruto";
    public static final String DEFAULT_LAST_NAME = "Uchiha";
    public static final String DEFAULT_ADDRESS = "Konoha, 142 apt.";
    public static final Integer DEFAULT_METRO_STATION = 4;
    public static final String DEFAULT_PHONE = "+7 800 355 35 35";
    public static final Integer DEFAULT_RENT_TIME = 5;
    public static final String DEFAULT_DELIVERY_DATE = "2020-06-06";
    public static final String DEFAULT_COMMENT = "Saske, come back to Konoha";

    // станции
    public static final String STATION_1_AND_2 = "[\"1\", \"2\"]";
    public static final String STATION_KALUZHSKAYA = "[\"110\"]";

    // Базовые данные для заказа константы
    public static Order getBaseOrder() {
        return Order.builder()
                .firstName(DEFAULT_FIRST_NAME)
                .lastName(DEFAULT_LAST_NAME)
                .address(DEFAULT_ADDRESS)
                .metroStation(DEFAULT_METRO_STATION)
                .phone(DEFAULT_PHONE)
                .rentTime(DEFAULT_RENT_TIME)
                .deliveryDate(DEFAULT_DELIVERY_DATE)
                .comment(DEFAULT_COMMENT)
                .build();
    }

        // Создание заказа с цветами
        public static Order createOrderWithColors (List < String > colors) {
            Order order = getBaseOrder();
            order.setColor(colors);
            return order;
        }
    }

