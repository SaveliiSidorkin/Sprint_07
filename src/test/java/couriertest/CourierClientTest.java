package couriertest;

import courier.Courier;
import courier.CourierClient;
import courier.CourierCredentials;
import courier.CourierGenerator;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;

public class CourierClientTest {
    private CourierClient courierClient;
    private Courier courier;
    private int id;

    @Before
    @Step("Создаем тестовые данные")
    public void createTestData() {
        courierClient = new CourierClient();
        courier = CourierGenerator.random();
    }

    @After
    @Step("Удаляем тестовые данные")
    public void cleanUp() {
        courierClient.courierDelete(id);
    }

    @Test
    @DisplayName("Создание курьера")
    @Description("Создаем курьера и логинимся")
    public void courierCanBeCreateTest() {
        ValidatableResponse response = courierClient.courierCreate(courier);
        response.statusCode(SC_CREATED);
        boolean status = response.extract().path("ok");
        assertTrue(status);
        ValidatableResponse loginResponce = courierClient.courierLogin(new CourierCredentials(courier.getLogin(), courier.getPassword()));
        loginResponce.statusCode(SC_OK);
        id = loginResponce.extract().path("id");
        assertNotNull(id);

    }

    @Test
    @DisplayName("Пустой логин")
    @Description("Проверяем, что нельзя создать курьера с пустым логином")
    public void courierCanNotBeCreateWithoutLoginTest() {
        courier.setLogin("");
        ValidatableResponse response = courierClient.courierCreate(courier);
        response.statusCode(SC_BAD_REQUEST);
        String message = response.extract().path("message");
        assertEquals("Недостаточно данных для создания учетной записи", message);
    }

    @Test
    @DisplayName("Пустой пароль")
    @Description("Проверем, что нельз создать курьера без пароля")
    public void courierCanNotBeCreatedWithoutPasswordTest() {
        courier.setPassword("");
        ValidatableResponse response = courierClient.courierCreate(courier);
        response.statusCode(SC_BAD_REQUEST);
        String message = response.extract().path("message");
        assertEquals("Недостаточно данных для создания учетной записи", message);
    }

    @Test
    @DisplayName("Пустой пароль и логин")
    @Description("Проверяем, что нельзя создать курьера без логина и пароля")
    public void courierCanNotBeCreatedWithoutPasswordAndLoginTest() {
        courier.setPassword("");
        courier.setLogin("");
        ValidatableResponse response = courierClient.courierCreate(courier);
        response.statusCode(SC_BAD_REQUEST);
        String message = response.extract().path("message");
        assertEquals("Недостаточно данных для создания учетной записи", message);
    }

    @Test
    @DisplayName("Нельзя создать двух курьеров с одним логином")
    @Description("Проверяем, что нельзя создать 2ух курьеров с одинаковым логином")
    public void courierCanNotBeCreatedWithSameLogin() {
        courierClient.courierCreate(courier);
        Courier duplicateCourier = new Courier(courier.getLogin(), "new password", "New Name");
        ValidatableResponse response = courierClient.courierCreate(duplicateCourier);
        response.statusCode(SC_CONFLICT);
        String message = response.extract().path("message");
        assertEquals("Этот логин уже используется. Попробуйте другой.", message);

    }
}