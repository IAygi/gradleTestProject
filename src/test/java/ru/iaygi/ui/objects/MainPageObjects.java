package ru.iaygi.ui.objects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.iaygi.ui.data.Selectors;

import static com.codeborne.selenide.Condition.image;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainPageObjects {

    @Step("Открыть главную страницу")
    public void openMainPage() {
        open("/");
    }

    @Step("Проверить заголовок страницы")
    public void checkPageTitle(String value) {
        String title = $(By.className(Selectors.pageTitle)).shouldBe(visible).getText();
        assertEquals(title, value);
    }

    @Step("Проверить наличие изображения в главной галерее")
    public void checkMainGallery() {
        $(By.className(Selectors.swiperSlideImage)).shouldHave(image);
    }

    @Step("Проверить наличие подгалереи")
    public void checkSubGallery() {
        assert ($(By.className(Selectors.fooGallery)).scrollIntoView(true).isDisplayed());
    }
}
