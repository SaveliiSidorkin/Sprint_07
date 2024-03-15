package order;

import constants.Constants;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderClient extends Constants {
    @Step("Создание заказа")
    public ValidatableResponse createOrder(Order order) {
        return given()
                .spec(getSpec())
                .body(order)
                .when()
                .post(Constants.CREATE_ORDER)
                .then();
    }

    @Step("Получение списка заказов")
    public ValidatableResponse getListOrder() {
        return given()
                .spec(getSpec())
                .when()
                .get(Constants.ORDER_LIST)
                .then();
    }

    @Step("Отмена заказа")
    public ValidatableResponse canselOrder(int track) {
        return given()
                .spec(getSpec())
                .when()
                .put(Constants.CANCEL_ORDER)
                .then();
    }
}
