package ru.iaygi.ui.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.junit5.TextReportExtension;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.remote.RemoteWebDriver;
import ru.iaygi.ui.data.Selectors;
import ru.iaygi.ui.objects.MainPageObjects;
import ru.iaygi.ui.objects.OrderPageObjects;

import java.io.IOException;
import java.net.URL;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;
import static io.qameta.allure.SeverityLevel.CRITICAL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.iaygi.ui.data.EndPoints.baseUrl;

@ExtendWith({TextReportExtension.class})
@Severity(CRITICAL)
@Tag("ui_test")
@Tag("regression")
@Epic("WebSite")
@Feature("Основная функциональность")
public class WebsiteTest extends TestBaseUi {

    private static RemoteWebDriver driver;
    private static MainPageObjects mainPageObjects;
    private static OrderPageObjects orderPageObjects;

    @BeforeAll
    public static void setUp() {
        Configuration.baseUrl = baseUrl;
        mainPageObjects = new MainPageObjects();
        orderPageObjects = new OrderPageObjects();
    }

    @BeforeEach
    public void init() throws IOException {
        initDriver();
        driver = new RemoteWebDriver(new URL("http://194.67.119.85:4444/wd/hub"), options);
        driver.manage().window().setSize(new Dimension(1920, 1080));
        WebDriverRunner.setWebDriver(driver);
    }

    @AfterEach
    public void stopDriver() {
        driver.quit();
    }

    @RepeatedTest(value = 2, name = "{displayName}Проверка главной страницы {currentRepetition}/{totalRepetitions}")
    @Tag("smoke")
    @DisplayName(" ")
    @Description("Проверить отображение на главной странице заголовка, галереи и подгалереи")
    public void mainPage() {

        mainPageObjects.openMainPage();
        mainPageObjects.checkPageTitle("Фотограф Татьяна Айги");
        mainPageObjects.checkMainGallery();
        mainPageObjects.checkSubGallery();
    }

    @Test
    @Tag("smoke")
    @DisplayName("Проверка страницы Портфолио")
    @Description("Проверить отображение заголовка на странице Портфолио")
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
    @Tag("smoke")
    @DisplayName("Проверка страницы Обо мне")
    @Description("Проверить отображение заголовка на странице Обо мне")
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
    @Tag("smoke")
    @DisplayName("Проверка страницы Контакты")
    @Description("Проверить отображение заголовка на странице Контакты")
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
    @Tag("smoke")
    @DisplayName("Проверка создания заказа")
    @Description("Проверить корректное создание заказа на странице Галерея")
    public void createOrder() {

        orderPageObjects
                .openGallery()
                .setCredentials()
                .closeModalWindow()
                .checkPhotos()
                .fillingForm()
                .openApprovePage()
                .approveChoice()
                .checkOrderCreation();
    }
}
