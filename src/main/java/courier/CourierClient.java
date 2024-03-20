package courier;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import restclient.Url;
import restclient.BaseApiClient;

import static io.restassured.RestAssured.given;

public class CourierClient extends BaseApiClient {

    @Step("Отправка запроса создания курьера POST /api/v1/courier")
    public ValidatableResponse courierCreate(Courier courier) {
        return given()
                .spec(getSpec())
                .body(courier)
                .when()
                .post(Url.CREATE_COURIER)
                .then();
    }

    @Step("Отправка запроса авторизации курьера POST /api/v1/courier/login")
    public ValidatableResponse courierLogin(CourierCredentials credentials) {
        return given()
                .spec(getSpec())
                .body(credentials)
                .when()
                .post(Url.COURIER_LOGIN)
                .then();
    }

    @Step("Отправка запроса удаления курьера DELETE /api/v1/courier/:id")
    public ValidatableResponse courierDelete(int id) {
        return given()
                .spec(getSpec())
                .body(id)
                .when()
                .delete(Url.COURIER_DELETE)
                .then();
    }
}
