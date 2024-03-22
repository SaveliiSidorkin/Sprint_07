package ordertest;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import order.Order;
import order.OrderClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertNotNull;

@RunWith(Parameterized.class)
public class OrderCreateTest {
    private static int track;
    private final List<String> color;
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

    @Test
    @DisplayName("Создание заказа с самокатами разных цветов")
    @Description("Проверяем, что заказ создается")
    public void orderCreateWithDifferentColor() {
        ValidatableResponse createOrder = orderClient.createOrder(order);
        createOrder.statusCode(SC_CREATED);
        track = createOrder.extract().path("track");
        assertNotNull(track);
    }

    @Test
    @DisplayName("Отмена заказа")
    @Description("Проверяем, что созданный заказ отменяется")
    public void orderCansel() {
        ValidatableResponse cancelOrder = orderClient.cancelOrder(track);
        cancelOrder.statusCode(SC_OK);
    }
}
