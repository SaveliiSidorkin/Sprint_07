package constants;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class Constants {
    protected static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";
    protected static final String CREATE_COURIER = "/api/v1/courier";
    protected static final String COURIER_LOGIN = "/api/v1/courier/login";
    protected static final String COURIER_DELETE = "/api/v1/courier/:id";
    protected static final String ORDER_LIST = "/api/v1/orders";
    protected static final String CREATE_ORDER = "/api/v1/orders";
    protected static final String CANCEL_ORDER = "/api/v1/orders/cancel";

    protected RequestSpecification getSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setContentType(ContentType.JSON)
                .build();
    }
}
