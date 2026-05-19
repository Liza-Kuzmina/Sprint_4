package scooter.tests;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import scooter.pageobject.MainPage;

@RunWith(Parameterized.class)
public class FaqTest {
    private WebDriver driver;
    private final int index;
    private final String expectedAnswer;

    public FaqTest(int index, String expectedAnswer) {
        this.index = index;
        this.expectedAnswer = expectedAnswer;
    }

    // Передаем индекс вопроса и ожидаемый текст ответа
    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][] {
                { 0, "Сутки — 400 рублей. Оплата курьеру — наличными или картой." },
                { 1, "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете оформить несколько заказов — один за другим." },
                { 2, "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если самокат нужен раньше 8 мая, лучше об этом сообщить курьеру заранее." },
                // ... остальные данные ...
        };
    }

    @Before
    public void setUp() {
        driver = new ChromeDriver();
        driver.get("https://qa-scooter.praktikum-services.ru/");
    }

    @Test
    public void checkFaqAnswers() {
        MainPage mainPage = new MainPage(driver);
        mainPage.acceptCookies();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id(String.format("accordion__panel-%d", index))
        ));

        String actualAnswer = mainPage.getFaqAnswerText(index);
        Assert.assertEquals("Текст ответа не совпадает с ожидаемым!", expectedAnswer, actualAnswer);
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
