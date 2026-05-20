package scooter.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class MainPage {
    private final WebDriver driver;

    // Константа для времени ожидания
    private static final Duration WAIT_TIMEOUT = Duration.ofSeconds(10);

    // Кнопка принятия кук
    private final By cookieButton = By.id("rcc-confirm-button");

    // Верхняя кнопка «Заказать»
    private final By topOrderButton = By.xpath("//div[contains(@class, 'Header_Nav')]//button[text()='Заказать']");

    // Нижняя кнопка «Заказать»
    private final By bottomOrderButton = By.xpath(".//div[contains(@class, 'Home_FinishButton')]/button");

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    // Метод принятия кук
    public void acceptCookies() {
        WebDriverWait wait = new WebDriverWait(driver, WAIT_TIMEOUT);
        try {
            wait.until(ExpectedConditions.elementToBeClickable(cookieButton)).click();
        } catch (NoSuchElementException e) {
            System.out.println("Кнопка принятия куки не найдена — продолжаем без её нажатия.");
        }
    }

    // Нажать верхнюю кнопку заказа
    public void clickTopOrderButton() {
        new WebDriverWait(driver, WAIT_TIMEOUT)
                .until(ExpectedConditions.elementToBeClickable(topOrderButton));
        driver.findElement(topOrderButton).click();
    }

    // Прокрутить до нижней кнопки заказа и нажать её
    public void clickBottomOrderButton() {
        WebElement element = driver.findElement(bottomOrderButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
        element.click();
    }

    public void expandFaqQuestion(int index) {
        String faqQuestionId = String.format("accordion__heading-%d", index);
        WebDriverWait wait = new WebDriverWait(driver, WAIT_TIMEOUT);
        wait.until(ExpectedConditions.elementToBeClickable(By.id(faqQuestionId)));
        driver.findElement(By.id(faqQuestionId)).click();
    }

    public void waitForFaqAnswerVisibility(int index) {
        String answerId = String.format("accordion__panel-%d", index);
        WebDriverWait wait = new WebDriverWait(driver, WAIT_TIMEOUT);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(answerId)));
    }

    // Прокрутить до блока FAQ, кликнуть по вопросу и вернуть текст ответа
    public String getFaqAnswerText(int index) {
        // Преобразуем в локальные переменные
        String faqQuestionId = "accordion__heading-%d";
        String faqAnswerId = "accordion__panel-%d";

        By questionLocator = By.id(String.format(faqQuestionId, index));
        By answerLocator = By.id(String.format(faqAnswerId, index));

        WebElement question = driver.findElement(questionLocator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", question);

        question.click();

        // Ожидаем появление текста
        new WebDriverWait(driver, WAIT_TIMEOUT)
                .until(ExpectedConditions.visibilityOfElementLocated(answerLocator));

        return driver.findElement(answerLocator).getText();
    }
}
