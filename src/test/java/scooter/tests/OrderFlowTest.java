package scooter.tests;

import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import scooter.pageobject.MainPage;
import scooter.pageobject.OrderPage;
import static org.hamcrest.CoreMatchers.containsString;

@RunWith(Parameterized.class)
public class OrderFlowTest {
    private WebDriver driver;

    private final String buttonType; // "top" или "bottom"
    private final String name;
    private final String surname;
    private final String address;
    private final String metro;
    private final String phone;
    private final String date;
    private final String period;
    private final String color;
    private final String comment;

    public OrderFlowTest(String buttonType, String name, String surname, String address, String metro, String phone, String date, String period, String color, String comment) {
        this.buttonType = buttonType;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.metro = metro;
        this.phone = phone;
        this.date = date;
        this.period = period;
        this.color = color;
        this.comment = comment;
    }

    @Parameterized.Parameters
    public static Object[][] getOrderData() {
        return new Object[][] {
                {
                        "top", "Иван", "Иванов", "Москва, ул. Ленина 5", "Черкизовская", "79991112233",
                        "25.05.2026", "сутки", "black", "Позвонить за час"
                },
                {
                        "bottom", "Петр", "Петров", "Москва, ул. Мира 12", "Сокольники", "79994445566",
                        "26.05.2026", "двое суток", "grey", "Оставить у двери"
                }
        };
    }

    @Before
    public void setUp() {
        driver = new ChromeDriver();
        driver.get("https://qa-scooter.praktikum-services.ru/");
    }

    @Test
    public void checkOrderFlow() {
        MainPage mainPage = new MainPage(driver);
        OrderPage orderPage = new OrderPage(driver);

        mainPage.acceptCookies();

        // Выбираем точку входа в зависимости от параметров
        if ("top".equals(buttonType)) {
            mainPage.clickTopOrderButton();
        } else {
            mainPage.clickBottomOrderButton();
        }

        // Оформляем заказ
        orderPage.fillFirstForm(name, surname, address, metro, phone);
        orderPage.fillSecondForm(date, period, color, comment);

        // Ожидание появления сообщения об успешном заказе
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(), 'Заказ оформлен')]")
        ));

        // Проверяем появление всплывающего окна (проверяем, что текст содержит фразу об успехе)
        String successMessage = driver.findElement(
                By.xpath("//*[contains(text(), 'Заказ оформлен')]"
                )).getText();
        MatcherAssert.assertThat("Окно успешного заказа не появилось", successMessage, containsString("Заказ оформлен"));
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}