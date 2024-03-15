package orderTest;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import order.OrderClient;
import org.junit.Test;

import static org.hamcrest.Matchers.notNullValue;

public class OrderListTest {
    @Test
    @DisplayName("Получение списка заказов")
    @Description("Проверяем, что получен список заказов")
    public void getOrderList() {
        OrderClient orderClient = new OrderClient();
        ValidatableResponse getListOrderResponse = orderClient.getListOrder();
        getListOrderResponse.statusCode(200)
                .body("orders", notNullValue());
    }
}