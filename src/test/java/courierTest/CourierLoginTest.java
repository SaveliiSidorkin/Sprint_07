package courierTest;

import courier.Courier;
import courier.CourierClient;
import courier.CourierCredentials;
import courier.CourierGenerator;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CourierLoginTest {
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
        courierClient.delete(id);
    }

    @Test
    @DisplayName("Успешная авторизация")
    public void courierLoginSuccess() {
        courierClient.create(courier);
        ValidatableResponse loginResponce = courierClient.login(new CourierCredentials(courier.getLogin(), courier.getPassword()));
        loginResponce.statusCode(200);
        id = loginResponce.extract().path("id");
        assertNotNull(id);
    }

    @Test
    @DisplayName("Авторизация с пустым логином")
    public void courierLoginWithEmptyLogin() {
        courierClient.create(courier);
        ValidatableResponse loginResponce = courierClient.login(new CourierCredentials("", courier.getPassword()));
        loginResponce.statusCode(400);
        String message = loginResponce.extract().path("message");
        assertEquals("Недостаточно данных для входа", message);

    }

    @Test
    @DisplayName("Авторизация с пустым паролем")
    public void courierLoginWithEmptyPassword() {
        courierClient.create(courier);
        ValidatableResponse loginResponce = courierClient.login(new CourierCredentials(courier.getLogin(), ""));
        loginResponce.statusCode(400);
        String message = loginResponce.extract().path("message");
        assertEquals("Недостаточно данных для входа", message);
    }

    @Test
    @DisplayName("Авторизация с пустыми логином и паролем")
    public void courierLoginWithEmptyLoginAndPassword() {
        courierClient.create(courier);
        ValidatableResponse loginResponce = courierClient.login(new CourierCredentials("", ""));
        String message = loginResponce.extract().path("message");
        assertEquals("Недостаточно данных для входа", message);
    }

    @Test
    @DisplayName("Авторизоваться с под несуществующим пользователем")
    public void courierLoginNonExistentUser() {
        ValidatableResponse loginResponce = courierClient.login(new CourierCredentials("test", "test"));
        loginResponce.statusCode(404);
        String message = loginResponce.extract().path("message");
        assertEquals("Учетная запись не найдена", message);
    }
}
