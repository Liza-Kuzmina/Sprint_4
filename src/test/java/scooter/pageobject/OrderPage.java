package scooter.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class OrderPage {
    private final WebDriver driver;
    private static final long WAIT_TIMEOUT = 10; // в секундах

    // Для кого самокат
    // Поле «Имя»
    private final By nameInput = By.xpath(".//input[@placeholder='* Имя']");
    // Поле «Фамилия»
    private final By surnameInput = By.xpath(".//input[@placeholder='* Фамилия']");
    // Поле «Адрес»
    private final By addressInput = By.xpath(".//input[@placeholder='* Адрес: куда привезти заказ']");
    // Поле «Станция метро»
    private final By metroInput = By.xpath(".//input[@placeholder='* Станция метро']");
    // Элемент выпадающего списка метро
    private final By metroSelectOption = By.className("select-search__row");
    // Поле «Телефон»
    private final By phoneInput = By.xpath(".//input[@placeholder='* Телефон: на него позвонит курьер']");
    // Кнопка «Далее»
    private final By nextButton = By.xpath(".//button[text()='Далее']");

    // Про аренду
    // Поле «Когда привезти самокат»
    private final By dateInput = By.xpath(".//input[@placeholder='* Когда привезти самокат']");
    // Выпадающий список «Срок аренды»
    private final By rentPeriodDropdown = By.className("Dropdown-control");
    // Чекбокс цвета «Чёрный жемчуг»
    private final By colorBlackCheckbox = By.id("black");
    // Чекбокс цвета «Серая безысходность»
    private final By colorGreyCheckbox = By.id("grey");
    // Поле «Комментарий для курьера»
    private final By commentInput = By.xpath(".//input[@placeholder='Комментарий для курьера']");
    // Кнопка «Заказать»
    private final By finalOrderButton = By.xpath(".//div[contains(@class, 'Order_Buttons')]/button[text()='Заказать']");
    // Кнопка подтверждения заказа «Да» во всплывающем окне
    private final By confirmYesButton = By.xpath(".//button[contains(@class, 'Button_Button') and text()='Да']");
    public OrderPage(WebDriver driver) {
        this.driver = driver;
    }

    // Заполнение первой формы
    public void fillFirstForm(String name, String surname, String address, String metro, String phone) {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT))
                .until(ExpectedConditions.visibilityOfElementLocated(nameInput));
        driver.findElement(nameInput).sendKeys(name);

        driver.findElement(surnameInput).sendKeys(surname);
        driver.findElement(addressInput).sendKeys(address);

        driver.findElement(metroInput).click();
        driver.findElement(metroInput).sendKeys(metro);
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT))
                .until(ExpectedConditions.visibilityOfElementLocated(metroSelectOption));
        driver.findElement(metroSelectOption).click(); // клик по первому результату поиска метро

        driver.findElement(phoneInput).sendKeys(phone);
        driver.findElement(nextButton).click();
    }

    // Заполнение второй формы
    public void fillSecondForm(String date, String period, String color, String comment) {
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT))
                .until(ExpectedConditions.visibilityOfElementLocated(dateInput));
        driver.findElement(dateInput).sendKeys(date);
        driver.findElement(dateInput).sendKeys(Keys.ENTER);

        driver.findElement(rentPeriodDropdown).click();
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT))
                .until(ExpectedConditions.elementToBeClickable(rentPeriodDropdown));
        driver.findElement(By.xpath(String.format(".//*[contains(text(), '%s')]", period))).click();

        if ("black".equalsIgnoreCase(color)) {
            driver.findElement(colorBlackCheckbox).click();
        } else if ("grey".equalsIgnoreCase(color)) {
            driver.findElement(colorGreyCheckbox).click();
        }

        driver.findElement(commentInput).sendKeys(comment);
        new WebDriverWait(driver, Duration.ofSeconds(WAIT_TIMEOUT))
                .until(ExpectedConditions.elementToBeClickable(finalOrderButton));
        driver.findElement(finalOrderButton).click();
        driver.findElement(confirmYesButton).click();
    }
}
