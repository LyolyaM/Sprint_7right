import steps.OrderSteps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static java.net.HttpURLConnection.HTTP_CREATED;
import static org.hamcrest.Matchers.*;

@RunWith(Parameterized.class)
public class CreateOrderTest extends BaseApiTest {

    private final List<String> colors;
    private final String testDescription;


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

    @Test
    public void testCreateOrderWithColors() {
        OrderSteps.createOrderWithColors(colors)
                .then()
                .log().all()
                .statusCode(HTTP_CREATED)           // 201 Created
                .body("track", notNullValue())      // track есть
                .body("track", instanceOf(Integer.class)); // track это число
    }
}
