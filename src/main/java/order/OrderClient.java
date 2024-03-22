package order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import restclient.Url;
import restclient.BaseApiClient;

import static io.restassured.RestAssured.given;

public class OrderClient extends BaseApiClient {

    @Step("Создание заказа POST /api/v1/orders")
    public ValidatableResponse createOrder(Order order) {
        return given()
                .spec(getSpec())
                .body(order)
                .when()
                .post(Url.CREATE_ORDER)
                .then();
    }

    @Step("Получение списка заказов GET /api/v1/orders")
    public ValidatableResponse getListOrder() {
        return given()
                .spec(getSpec())
                .when()
                .get(Url.ORDER_LIST)
                .then();
    }

    @Step("Отмена заказа PUT /api/v1/orders/cancel")
    public ValidatableResponse cancelOrder(int track) {
        return given()
                .spec(getSpec())
                .when()
                .put(Url.CANCEL_ORDER)
                .then();
    }
}
