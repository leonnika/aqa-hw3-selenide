package ru.netology;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class SelenideTest {

   @BeforeAll
    static void setUpAll() {
       open("http://localhost:9999");
   }

    @Nested
    public class NameValidPhoneValidCheckIs {
        private String expected;

        @BeforeEach
        void init() {
            $("[data-test-id=phone] input").setValue("+79270000000");
            $("[data-test-id=agreement]").click();
            expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        }
        @Test
    void shouldSentFormAllValid()
    {
            $("[data-test-id=name] input").setValue("Иванов Василий");
            $("[role='button']").click();
            $("[data-test-id=order-success]").shouldHave(exactText(expected));
    }

        @Test
        void shouldSentFormDableName() {
            $("[data-test-id=name] input").setValue("Иванов Петр Иван");
            $("[role='button']").click();
            $("[data-test-id=order-success]").shouldHave(exactText(expected));
        }

        void shouldSentFormDableNameSeparator() {
            $("[data-test-id=name] input").setValue("Иванов Петр-Иван");
            $("[role='button']").click();
            $("[data-test-id=order-success]").shouldHave(exactText(expected));
        }

        @Test
        void shouldSentFormDableNameLong() {
            $("[data-test-id=name] input").setValue("Иванова-Перевозкина-Пограничникова Александра Виктория Валентина");
            $("[role='button']").click();
            $("[data-test-id=order-success]").shouldHave(exactText(expected));
        }

}}
