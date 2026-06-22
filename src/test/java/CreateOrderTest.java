import steps.OrderSteps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.After;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.*;

@Epic("Тестирование API Яндекс Самокат")
@Feature("Создание заказа")
@RunWith(Parameterized.class)
public class CreateOrderTest extends BaseApiTest {

    private final List<String> colors;
    private final String testDescription;
    private int trackNumber;

public  CreateOrderTest (List<String> colors, String testDescription) {
        this.colors = colors;
        this.testDescription = testDescription;
}

    @Parameterized.Parameters(name = "{1}")
    public static Collection<Object[]> testData() {
        return Arrays.asList(new Object[][]{
                {Arrays.asList("BLACK"), "Только BLACK"},
                {Arrays.asList("GREY"), "Только GREY"},
                {Arrays.asList("BLACK", "GREY"), "Оба цвета"},
                {null, "Без цвета (null)"},
                {Arrays.asList(), "Без цвета (пустой список)"}
        });
    }

    @After
    public void cleanUp() {
        if (trackNumber > 0) {
            System.out.println("Заказ создан, track: " + trackNumber + " (очистка отключена)");
            // Временно отключено из-за бага ручки /api/v1/orders/cancel (возвращает 404)
            //OrderSteps.cancelOrder(trackNumber)
                //    .then()
                //    .log().all()
                 //   .statusCode(SC_OK)
                  //  .body("ok", equalTo(true));
          //  System.out.println("Заказ отменён, track: " + trackNumber);
        }
    }

    @Test
    @Description("Проверка создания заказа с разными вариантами цветов")
    public void testCreateOrderWithColors() {
        trackNumber = OrderSteps.createOrderWithColors(colors)
                .then()
                .log().all()
                .statusCode(SC_CREATED)           // 201 Created
                .body("track", notNullValue())      // track есть
                .body("track", instanceOf(Integer.class)) // track это число
                .extract()
                .path("track");
    }

}
