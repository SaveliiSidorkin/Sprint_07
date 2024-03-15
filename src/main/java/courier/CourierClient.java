package courier;

import constants.Constants;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierClient extends Constants {
    @Step("Отправка запроса создания курьера POST /api/v1/courier")
    public ValidatableResponse create(Courier courier) {
        return given()
                .spec(getSpec())
                .body(courier)
                .when()
                .post(Constants.CREATE_COURIER)
                .then();
    }

    @Step("Отправка запроса авторизации курьера POST /api/v1/courier/login")
    public ValidatableResponse login(CourierCredentials credentials) {
        return given()
                .spec(getSpec())
                .body(credentials)
                .when()
                .post(Constants.COURIER_LOGIN)
                .then();
    }

    @Step("Отправка запроса удаления курьера DELETE /api/v1/courier/:id")
    public ValidatableResponse delete(int id) {
        return given()
                .spec(getSpec())
                .body(id)
                .when()
                .delete(Constants.COURIER_DELETE)
                .then();
    }
}
