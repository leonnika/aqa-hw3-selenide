package ru.netology;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class SelenideTest {
    @Test
    void shouldSubmitRequest()
    {
        String expected;

        open("http://localhost:9999");
            $("[data-test-id=phone] input").setValue("+79270000000");
            $("[data-test-id=agreement]").click();
            expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
            $("[data-test-id=name] input").setValue("Иванов Василий");
            $("[role='button']").click();
            $("[data-test-id=order-success]").shouldHave(exactText(expected));
    }

}
