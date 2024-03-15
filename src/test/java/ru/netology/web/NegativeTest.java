package ru.netology.web;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NegativeTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void negativeOrderBankCardPhoneTest() {
        driver.findElement(By.cssSelector("[data-test-id ='name'] input")).sendKeys("Иван Смирнов");
        driver.findElement(By.cssSelector("[data-test-id ='phone'] input")).sendKeys("+7911111111");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button__content")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id = phone].input_invalid .input__sub")).getText().trim();

        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void negativeOrderBankCardNameTest() {
        driver.findElement(By.cssSelector("[data-test-id ='name'] input")).sendKeys("Ivan Smirnov");
        driver.findElement(By.cssSelector("[data-test-id ='phone'] input")).sendKeys("+79111111111");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button__content")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id = name].input_invalid .input__sub")).getText().trim();

        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void negativeOrderBankCardNotClickTest() {
        driver.findElement(By.cssSelector("[data-test-id ='name'] input")).sendKeys("Иван Смирнов");
        driver.findElement(By.cssSelector("[data-test-id ='phone'] input")).sendKeys("+79111111111");
        //  driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button__content")).click();
        Boolean actual = driver.findElement(By.cssSelector("[data-test-id = agreement].input_invalid .checkbox__text")).isDisplayed();

        Assertions.assertTrue(actual);
    }

    @Test
    void negativeNotTextTest() {
        driver.findElement(By.cssSelector("[data-test-id ='name'] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id ='phone'] input")).sendKeys("+79111111111");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button__content")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id = name].input_invalid .input__sub")).getText().trim();

        String expected = "Поле обязательно для заполнения";
        Assertions.assertEquals(expected, actual);

    }

    @Test
    void negativeNotTextPhoneTest() {
        driver.findElement(By.cssSelector("[data-test-id ='name'] input")).sendKeys("Иван Смирнов");
        driver.findElement(By.cssSelector("[data-test-id ='phone'] input")).sendKeys("");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button__content")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id = phone].input_invalid .input__sub")).getText().trim();

        String expected = "Поле обязательно для заполнения";
        Assertions.assertEquals(expected, actual);

    }
}