package ru.iaygi.ui.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;
import ru.iaygi.ui.data.Selectors;
import ru.iaygi.ui.data.TestData;

import java.io.IOException;
import java.net.URL;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;
import static io.qameta.allure.SeverityLevel.CRITICAL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.iaygi.ui.data.EndPoints.baseUrl;

@Severity(CRITICAL)
@Tag("ui_test")
@Tag("regression")
@Epic("WebSite")
@Feature("Основная функциональность")
public class WebsiteTest extends TestBaseUi {

    private static RemoteWebDriver driver;

    @BeforeAll
    public static void setUp() {
        Configuration.baseUrl = baseUrl;
    }

    @BeforeEach
    public void init() throws IOException {
        initDriver();
        driver = new RemoteWebDriver(new URL("http://194.67.119.85:4444/wd/hub"), options);
        WebDriverRunner.setWebDriver(driver);
    }

    @AfterEach
    public void stopDriver() {
        driver.quit();
    }

    @Test
    @Tag("smoke")
    @DisplayName("Проверка главной страницы")
    public void mainPage() {

        step("Открыть главную страницу", () -> {
            open("/");
        });

        step("Проверить заголовок страницы", () -> {
            String title = $(By.className(Selectors.pageTitle)).shouldBe(visible).getText();
            assertEquals(title, "Фотограф Татьяна Айги");
        });

//        step("Проверить наличие изображения в главной галерее", () -> {
//            $(By.className(Selectors.swiperSlideImage)).shouldHave(image);
//        });
//
//        step("Проверить наличие подгалереи", () -> {
//            assert ($(By.className(Selectors.fooGallery)).isDisplayed());
//        });
    }

    @Test
    @DisplayName("Проверка страницы Портфолио")
    public void portfolioPage() {

        step("Открыть страницу Портфолио", () -> {
            open("/portfolio/");
        });

        step("Проверить заголовок страницы", () -> {
            String title = $(By.className(Selectors.pageTitle)).shouldBe(visible).getText();
            assertEquals(title, "Портфолио");
        });
    }

    @Test
    @DisplayName("Проверка страницы Обо мне")
    public void aboutPage() {

        step("Открыть страницу Обо мне", () -> {
            open("/about/");
        });

        step("Проверить заголовок страницы", () -> {
            String title = $(By.className(Selectors.pageTitle)).shouldBe(visible).getText();
            assertEquals(title, "Обо мне");
        });
    }

    @Test
    @DisplayName("Проверка страницы Контакты")
    public void contactsPage() {

        step("Открыть страницу Контакты", () -> {
            open("/contacts/");
        });

        step("Проверить заголовок страницы", () -> {
            String title = $(By.className(Selectors.pageTitle)).shouldBe(visible).getText();
            assertEquals(title, "Контакты");
        });
    }

    @Test
    @DisplayName("Проверка создания заказа")
    public void createOrder() {

        step("Открыть галерею", () -> {
            open("/gallery");
        });

        step("Ввести логин и пароль", () -> {
            $(By.id("user_login")).setValue(TestData.userLogin); // Добавить парамс из пайплайна!!!!!!!!!!!!!!!!
            $(By.id("user_pass")).setValue(TestData.userPass);
            $(By.id("btn_login")).click();
        });

        step("Закрыть модальное окно", () -> {
            $(By.id("wow-modal-close-1")).click();
        });

        step("Выбрать фотографии", () -> {
            $(By.id("al_2")).setSelected(true);
            $(By.id("fl_3")).setSelected(true);
            $(By.id("pr_4")).setSelected(true);
        });

        step("Заполнить форму", () -> {
            $(By.id("user_name")).scrollIntoView(true).setValue("test_name");
            $(By.id("phone_number")).setValue(TestData.phoneNumber);
            $(By.id("e_mail")).setValue(TestData.Email);
            $(By.name("message")).setValue(TestData.message);
        });

        step("Перейти на экран подтверждения выбора", () -> {
            $(By.id(Selectors.btnIdMain)).click();
        });

        step("Подтвердить выбор", () -> {
            $(By.id(Selectors.btnGo)).click();
        });

        step("Проверка создания заказа", () -> {
            String txt = $(By.id(Selectors.txtResult)).getText().substring(0, 37);
            assertEquals(txt, TestData.checkOrder);
        });
    }
}
