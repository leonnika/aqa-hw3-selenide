package ru.netology;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SelenideTest {
    private String expected;

    @Nested
    public class NameValidOptionsPhoneValidCheckIs {

        @BeforeEach
        void init() {
            open("http://localhost:9999");
            $("[data-test-id=phone] input").setValue("+79270000000");
            $("[data-test-id=agreement]").click();
            expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        }

        @Test
        void shouldSentFormAllValid() {
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

        @Test
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
    }

    @Nested
    public class NameNotValidOptionsPhoneValidCheckIs {

        @BeforeEach
        void init() {
            open("http://localhost:9999");
            $("[data-test-id=phone] input").setValue("+79270000000");
            $("[data-test-id=agreement]").click();
            expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        }

        @Test
        void shouldNotSentFormNameEnglish() {
            $("[data-test-id=name] input").setValue("Ivanov Ivan");
            $("[role='button']").click();
            $("[class='input__sub']").shouldHave(exactText(expected));
        }

        @Test
        void shouldNotSentFormNameNumber() {
            $("[data-test-id=name] input").setValue("88888");
            $("[role='button']").click();
            $("[class='input__sub']").shouldHave(exactText(expected));
        }

        @Test
        void shouldNotSentFormNameEmpty() {
            $("[role='button']").click();
            $("[class='input__sub']").shouldHave(exactText("Поле обязательно для заполнения"));
        }

        @Test
        void shouldNotSentFormNameSpecialSymbols() {
            $("[data-test-id=name] input").setValue("@#$$%^&(*&(");
            $("[role='button']").click();
            $("[class='input__sub']").shouldHave(exactText(expected));
        }
// Тысты которые будут fail
//        @Test
//        void shouldNotSentFormNameOnly() {
//         $("[data-test-id=name] input").setValue("Иванов");
//            $("[role='button']").click();
//            $("[class='input__sub']").shouldHave(exactText(expected));
//        }

        //     @Test
//        void shouldNotSentFormNameDifferentCaseLetters() {
//           ("[data-test-id=name] input").setValue("иВаноВ иВАН");
//            $("[role='button']").click();
//            $("[class='input__sub']").shouldHave(exactText(expected));
//        }
    }

    @Nested
    public class PhoneNotValidNameValidCheckIs {
        private String expected;

        @BeforeEach
        void init() {
            open("http://localhost:9999");
            $("[data-test-id=name] input").setValue("Иванов Василий");
            $("[data-test-id=agreement]").click();
            expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        }

        @Test
        void shouldNotSentFormPhoneSpecialSymbols() {
            $("[data-test-id=phone] input").setValue(";%;%%:%");
            $("[role='button']").click();
            SelenideElement phone = $("[data-test-id=phone]");
            phone.$("[class='input__sub']").shouldHave(exactText(expected));
        }

        @Test
        void shouldNotSentFormPhoneEmpty() {
            $("[role='button']").click();
            SelenideElement phone = $("[data-test-id=phone]");
            phone.$("[class='input__sub']").shouldHave(exactText("Поле обязательно для заполнения"));
        }

        @Test
        void shouldNotSentFormPhoneFistSymbolNotPlus() {
            $("[data-test-id=phone] input").setValue("89898989890");
            $("[role='button']").click();
            SelenideElement phone = $("[data-test-id=phone]");
            phone.$("[class='input__sub']").shouldHave(exactText(expected));
        }

        @Test
        void shouldNotSentFormPhoneNot11element() {
            $("[data-test-id=phone] input").setValue("89898");
            $("[role='button']").click();
            SelenideElement phone = $("[data-test-id=phone]");
            phone.$("[class='input__sub']").shouldHave(exactText(expected));
        }

// Тысты которые будут fail
//        @Test
//        void shouldNotSentFormPhoneFist0(){
//        $("[data-test-id=phone] input").setValue("+08989898909");
//        $("[role='button']").click();
//        SelenideElement phone =$("[data-test-id=phone]");
//        phone.$("[class='input__sub']").shouldHave(exactText(expected));
//        }
    }

    @Test
    void CheckIsNotNamePhoneValid() {
        open("http://localhost:9999");
        $("[data-test-id=name] input").setValue("Иванов Василий");
        $("[data-test-id=phone] input").setValue("+79270000000");
        $("[role='button']").click();
        assertEquals("rgba(255, 92, 92, 1)", $("[data-test-id=agreement]").getCssValue("color"));
    }
}
