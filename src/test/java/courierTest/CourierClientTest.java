package courierTest;

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
    public void cleanup() {
        courierClient.delete(id);
    }

    @Test
    @DisplayName("Создание курьера")
    @Description("Создаем курьера и логинимся")
    public void CourierCanBeCreateTest() {
        ValidatableResponse response = courierClient.create(courier);
        response.statusCode(201);
        boolean status = response.extract().path("ok");
        assertTrue(status);
        ValidatableResponse loginResponce = courierClient.login(new CourierCredentials(courier.getLogin(), courier.getPassword()));
        loginResponce.statusCode(200);
        id = loginResponce.extract().path("id");
        assertNotNull(id);

    }

    @Test
    @DisplayName("Пустой логин")
    @Description("Проверяем, что нельзя создать курьера с пустым логином")
    public void CourierCanNotBeCreateWithoutLoginTest() {
        courier.setLogin("");
        ValidatableResponse response = courierClient.create(courier);
        response.statusCode(400);
        String message = response.extract().path("message");
        assertEquals("Недостаточно данных для создания учетной записи", message);
    }

    @Test
    @DisplayName("Пустой пароль")
    @Description("Проверем, что нельз создать курьера без пароля")
    public void CourierCanNotBeCreatedWithoutPasswordTest() {
        courier.setPassword("");
        ValidatableResponse response = courierClient.create(courier);
        response.statusCode(400);
        String message = response.extract().path("message");
        assertEquals("Недостаточно данных для создания учетной записи", message);
    }

    @Test
    @DisplayName("Пустой пароль и логин")
    @Description("Проверяем, что нельзя создать курьера без логина и пароля")
    public void CourierCanNotBeCreatedWithoutPasswordAndLoginTest() {
        courier.setPassword("");
        courier.setLogin("");
        ValidatableResponse response = courierClient.create(courier);
        response.statusCode(400);
        String message = response.extract().path("message");
        assertEquals("Недостаточно данных для создания учетной записи", message);
    }

    @Test
    @DisplayName("Нельзя создать двух курьеров с одним логином")
    @Description("Проверяем, что нельзя создать 2ух курьеров с одинаковым логином")
    public void CourierCanNotBeCreatedWithSameLogin() {
        courierClient.create(courier);
        Courier duplicateCourier = new Courier(courier.getLogin(), "new password", "New Name");
        ValidatableResponse response = courierClient.create(duplicateCourier);
        response.statusCode(409);
        String message = response.extract().path("message");
        assertEquals("Этот логин уже используется. Попробуйте другой.", message);

    }
}