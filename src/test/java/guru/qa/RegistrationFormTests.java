package guru.qa;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;

import com.codeborne.selenide.ElementsCollection;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class RegistrationFormTests extends TestBase {

    @Test
    void successfulFillFormTest() {
        open("/automation-practice-form");

        $("#firstName").setValue("Ivan");
        $("#lastName").setValue("Ivanov");
        $("#userEmail").setValue("Ivanov@example.com");
        $("#gender-radio-1").click();
        $("#userNumber").setValue("9298564111");
        $("#dateOfBirthInput").click();
        $(".react-datepicker__month-select").selectOption("February");
        $(".react-datepicker__year-select").selectOption("2005");
        $(".react-datepicker__day--021").click();
        $("#subjectsInput").setValue("Arts");
        $$("div[id*='option']").findBy(text("Arts")).click();
        $("#hobbies-checkbox-1").click();
        $("#hobbies-checkbox-3").click();
        $("#uploadPicture").uploadFromClasspath("test-image.jpg");
        $("#currentAddress").setValue("Jaipur, Rajasthan, India");
        $("#state").click();
        $$("div[id*='option']").findBy(text("Rajasthan")).click();
        $("#city").click();
        $$("div[id*='option']").findBy(text("Jaipur")).click();
        $("#submit").click();

        $("#example-modal-sizes-title-lg").shouldBe(text("Thanks for submitting the form"));
    }

    @Test
    void successfulFillRequiredFieldsTest() {
        open("/automation-practice-form");

        $("#firstName").setValue("Ivan");
        $("#lastName").setValue("Ivanov");
        $("#gender-radio-1").click();
        $("#userNumber").setValue("9298564111");
        $("#submit").click();

        $("#example-modal-sizes-title-lg").shouldBe(text("Thanks for submitting the form"));
    }

    @Test
    void submitEmptyFormTest() {
        open("/automation-practice-form");

        $("#submit").click();

        $("#firstName").shouldHave(attribute("required")).shouldBe(empty);
        $("#lastName").shouldHave(attribute("required")).shouldBe(empty);
        $("#userNumber").shouldHave(attribute("required")).shouldBe(empty);
        ElementsCollection genders = $$("input[name='gender']");
        for (SelenideElement gender : genders) {
            gender.shouldHave(attribute("required"));
        }
        $(".modal-content").shouldNotBe(visible);
        $("#userForm").shouldHave(cssClass("was-validated"));
    }

    @Test
    void invalidMobileNumberTest() {
        open("/automation-practice-form");

        $("#firstName").setValue("Ivan");
        $("#lastName").setValue("Ivanov");
        $("#gender-radio-1").click();
        $("#userNumber").setValue("12345");
        $("#submit").click();

        $(".modal-content").shouldNotBe(visible);
        $("#userForm").shouldHave(cssClass("was-validated"));
    }

    @Test
    void invalidEmailFormatTest() {
        open("/automation-practice-form");

        $("#firstName").setValue("Ivan");
        $("#lastName").setValue("Ivanov");
        $("#userEmail").setValue("Ivanov");
        $("#gender-radio-1").click();
        $("#userNumber").setValue("9298564111");
        $("#submit").click();

        $(".modal-content").shouldNotBe(visible);
        $("#userForm").shouldHave(cssClass("was-validated"));
    }

    @Test
    void successfulFillSimpleFormTest() {
        open("/text-box");

        $("#userName").setValue("Ivan Ivanov");
        $("#userEmail").setValue("Ivanov@example.com");
        $("#currentAddress").setValue("Jaipur, Rajasthan, India");
        $("#permanentAddress").setValue("address matches the current one");
        $("#submit").click();

        $("#output #name").shouldHave(text("Ivan Ivanov"));
        $("#output #email").shouldHave(text("Ivanov@example.com"));
        $("#output #currentAddress").shouldHave(text("Jaipur, Rajasthan, India"));
        $("#output #permanentAddress").shouldHave(text("address matches the current one"));
    }

    @Test
    void invalidEmailFormatSimpleFormTest() {
        open("/text-box");

        $("#userEmail").setValue("Ivanov");
        $("#submit").click();

        $("#output #email").shouldNotBe(visible);
        $("#userEmail").shouldHave(cssClass("field-error"));
    }
}
