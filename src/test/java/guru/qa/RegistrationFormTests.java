package guru.qa;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;

import com.codeborne.selenide.ElementsCollection;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class RegistrationFormTests extends TestBase {

    @Test
    void successfulFillFormTest() {
        open("/automation-practice-form");

        $("#firstName").setValue("Ivan");
        $("#lastName").setValue("Ivanov");
        $("#userEmail").setValue("Ivanov@example.com");
        $$("#genterWrapper label").findBy(text("Male")).click();
        $("#userNumber").setValue("9298564111");
        $("#dateOfBirthInput").click();
        $(".react-datepicker__month-select").selectOption("February");
        $(".react-datepicker__year-select").selectOption("2005");
        $(".react-datepicker__day--021").click();
        $("#subjectsInput").setValue("Arts");
        $$("div[id*='option']").findBy(text("Arts")).click();
        $$("#hobbiesWrapper label").findBy(text("Sports")).click();
        $$("#hobbiesWrapper label").findBy(text("Music")).click();
        $("#uploadPicture").uploadFromClasspath("test-image.jpg");
        $("#currentAddress").setValue("Jaipur, Rajasthan, India");
        $("#state").click();
        $$("div[id*='option']").findBy(text("Rajasthan")).click();
        $("#city").click();
        $$("div[id*='option']").findBy(text("Jaipur")).click();
        $("#submit").click();

        $(".modal-content").shouldBe(visible);
        $("#example-modal-sizes-title-lg").shouldBe(text("Thanks for submitting the form"));
        $(".table-responsive").$(byText("Student Name")).parent().shouldHave(text("Ivan Ivanov"));
        $(".table-responsive").$(byText("Student Email")).parent().shouldHave(text("Ivanov@example.com"));
        $(".table-responsive").$(byText("Gender")).parent().shouldHave(text("Male"));
        $(".table-responsive").$(byText("Mobile")).parent().shouldHave(text("9298564111"));
        $(".table-responsive").$(byText("Date of Birth")).parent().shouldHave(text("21 February,2005"));
        $(".table-responsive").$(byText("Subjects")).parent().shouldHave(text("Arts"));
        $(".table-responsive").$(byText("Hobbies")).parent().shouldHave(text("Sports, Music"));
        $(".table-responsive").$(byText("Picture")).parent().shouldHave(text("test-image.jpg"));
        $(".table-responsive").$(byText("Address")).parent().shouldHave(text("Jaipur, Rajasthan, India"));
        $(".table-responsive").$(byText("State and City")).parent().shouldHave(text("Rajasthan Jaipur"));
    }

    @Test
    void successfulFillRequiredFieldsTest() {
        open("/automation-practice-form");

        $("#firstName").setValue("Ivan");
        $("#lastName").setValue("Ivanov");
        $$("#genterWrapper label").findBy(text("Male")).click();
        $("#userNumber").setValue("9298564111");
        $("#submit").click();

        $(".modal-content").shouldBe(visible);
        $("#example-modal-sizes-title-lg").shouldBe(text("Thanks for submitting the form"));
        $(".table-responsive").$(byText("Student Name")).parent().shouldHave(text("Ivan Ivanov"));
        $(".table-responsive").$(byText("Gender")).parent().shouldHave(text("Male"));
        $(".table-responsive").$(byText("Mobile")).parent().shouldHave(text("9298564111"));
        $(".table-responsive").$(byText("Student Email")).sibling(0).shouldHave(exactText(""));
        $(".table-responsive").$(byText("Subjects")).sibling(0).shouldHave(exactText(""));
        $(".table-responsive").$(byText("Hobbies")).sibling(0).shouldHave(exactText(""));
        $(".table-responsive").$(byText("Picture")).sibling(0).shouldHave(exactText(""));
        $(".table-responsive").$(byText("Address")).sibling(0).shouldHave(exactText(""));
        $(".table-responsive").$(byText("State and City")).sibling(0).shouldHave(exactText(""));
        $(".table-responsive").$(byText("Date of Birth")).sibling(0).shouldHave(exactText(""));
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
        $$("#genterWrapper label").findBy(text("Male")).click();
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
        $$("#genterWrapper label").findBy(text("Male")).click();
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
