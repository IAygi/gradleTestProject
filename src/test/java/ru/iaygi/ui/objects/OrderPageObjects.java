package ru.iaygi.ui.objects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.iaygi.ui.data.Selectors;
import ru.iaygi.ui.data.TestData;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderPageObjects {

    @Step("Открыть галерею")
    public OrderPageObjects openGallery() {
        open("/gallery");
        return this;
    }

    @Step("Ввести логин и пароль")
    public OrderPageObjects setCredentials() {
        $(By.id("user_login")).setValue(TestData.userLogin); // Добавить парамс из пайплайна!!!!!!!!!!!!!!!!
        $(By.id("user_pass")).setValue(TestData.userPass);
        $(By.id("btn_login")).click();
        return this;
    }

    @Step("Закрыть модальное окно")
    public OrderPageObjects closeModalWindow() {
        $(By.id("wow-modal-close-1")).click();
        return this;
    }

    @Step("Выбрать фотографии")
    public OrderPageObjects checkPhotos() {
        $(By.id("al_2")).setSelected(true);
        $(By.id("fl_3")).setSelected(true);
        $(By.id("pr_4")).setSelected(true);
        return this;
    }

    @Step("Заполнить форму")
    public OrderPageObjects fillingForm() {
        $(By.id("user_name")).scrollIntoView(true).setValue("test_name");
        $(By.id("phone_number")).setValue(TestData.phoneNumber);
        $(By.id("e_mail")).setValue(TestData.Email);
        $(By.name("message")).setValue(TestData.message);
        return this;
    }

    @Step("Перейти на экран подтверждения выбора")
    public OrderPageObjects openApprovePage() {
        $(By.id(Selectors.btnIdMain)).shouldBe(visible).click();
        return this;
    }

    @Step("Подтвердить выбор")
    public OrderPageObjects approveChoice() {
        $(By.id(Selectors.btnGo)).scrollIntoView(true).shouldBe(visible).click();
        return this;
    }

    @Step("Проверка создания заказа")
    public OrderPageObjects checkOrderCreation() {
        String txt = $(By.id(Selectors.txtResult)).shouldBe(visible).getText().substring(0, 37);
        assertEquals(txt, TestData.checkOrder);
        return this;
    }
}
