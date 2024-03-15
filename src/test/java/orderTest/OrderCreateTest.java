package orderTest;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import order.Order;
import order.OrderClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static org.junit.Assert.assertNotNull;

@RunWith(Parameterized.class)
public class OrderCreateTest {
    private final List<String> color;
    private int track;
    private OrderClient orderClient;
    private Order order;

    public OrderCreateTest(List<String> color) {
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] getColor() {
        return new Object[][]{
                {List.of("BLACK")},
                {List.of("GRAY")},
                {List.of("BLACK, GRAY")},
                {List.of()}
        };
    }

    @Before
    @Step("Создаем тестовые данные")
    public void createTestData() {
        orderClient = new OrderClient();
        order = new Order(color);
    }

    @After
    @Step("Удаляем тестовые данные")
    public void cleanUp() {
        orderClient.canselOrder(track);
    }

    @Test
    @DisplayName("Создание заказа с самокатами разных цветов")
    public void createOrderWithDifferentColor() {
        ValidatableResponse createOrder = orderClient.createOrder(order);
        createOrder.statusCode(201);
        track = createOrder.extract().path("track");
        assertNotNull(track);
    }


}
