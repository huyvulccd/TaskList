package vn.amela.todoList.selenium;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TestMaster {

    @Test
    public void testValidatorRegistUI() {
        String url = "\\register\\testcase.txt";

        List<String> testCases = CommonReadTestCase.getTestCase(url);
        List<Boolean> reports = new ArrayList<>();

        for (String testCase : testCases) {
            System.setProperty("webdriver.chrome.driver", "D:\\chromedriver.exe");
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-allow-origins=*");
            WebDriver driver = new ChromeDriver(options);


            String baseUrl = "http://localhost:8080/register";
            driver.get(baseUrl);

            String[] testCaseEl = testCase.split(" ");
            driver.findElement(By.id("name")).sendKeys(testCaseEl[0]);
            driver.findElement(By.id("username")).sendKeys(testCaseEl[1]);
            driver.findElement(By.id("password")).sendKeys(testCaseEl[2]);
            driver.findElement(By.id("repassword")).sendKeys(testCaseEl[3]);

            boolean actual = !driver.findElement(By.id("submit-btn")).getCssValue("background-color").equals("1");
            boolean expect = Boolean.parseBoolean(testCaseEl[4]);
            driver.quit();

            reports.add(Objects.equals(expect, actual));
        }
        CommonReadTestCase.reportToText("\\register", "testValidatorRegistUI", reports);

        Assertions.assertFalse(reports.contains(false));
    }

    @Test
    void loginSucess() {
        String url = "\\login\\testcase.txt";

        List<String> testCases = CommonReadTestCase.getTestCase(url);
        List<Boolean> reports = new ArrayList<>();

        for (String testCase : testCases) {
            System.setProperty("webdriver.chrome.driver", "D:\\chromedriver.exe");
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-allow-origins=*");
            WebDriver driver = new ChromeDriver(options);


            String baseUrl = "http://localhost:8080/login";
            driver.get(baseUrl);

            String[] testCaseEl = testCase.split(" ");
            driver.findElement(By.id("username")).sendKeys(testCaseEl[0]);
            driver.findElement(By.id("password")).sendKeys(testCaseEl[1]);
            driver.findElement(By.id("submit-btn")).click();

            String actual = driver.getCurrentUrl();
            String expect = testCaseEl[2];
            driver.quit();

            reports.add(Objects.equals(expect, actual));
        }
        CommonReadTestCase.reportToText("\\register", "testValidatorRegistUI", reports);

        Assertions.assertFalse(reports.contains(false));
    }

    @Test
    void testFeatureCreateTask() throws InterruptedException {

        List<Boolean> reports = new ArrayList<>();
        System.setProperty("webdriver.chrome.driver", "D:\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        WebDriver driver = new ChromeDriver(options);


        String baseUrl = "http://localhost:8080/login";
        driver.get(baseUrl);

        driver.findElement(By.id("username")).sendKeys("admintest");
        driver.findElement(By.id("password")).sendKeys("123456");
        driver.findElement(By.id("submit-btn")).click();


        for (int i = 1; i <= 100; i++) {
            Thread.sleep(100);
            driver.findElement(By.cssSelector(".toolbar-newtask")).click();
            Thread.sleep(550);
            driver.findElement(By.cssSelector(".create #title")).sendKeys("admintest" + i);
            driver.findElement(By.id("content")).sendKeys("123456" + i);
            driver.findElement(By.cssSelector(".btn-create")).click();
        }

        driver.quit();
    }

    @Test
    void clearTasks() {
        System.setProperty("webdriver.chrome.driver", "D:\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        WebDriver driver = new ChromeDriver(options);


        String baseUrl = "http://localhost:8080/login";
        driver.get(baseUrl);

        driver.findElement(By.id("username")).sendKeys("admintest");
        driver.findElement(By.id("password")).sendKeys("123456");
        driver.findElement(By.id("submit-btn")).click();

        try {
            while (true) {
                driver.findElement(By.cssSelector(".delete_table")).click();
                Thread.sleep(550);
                driver.findElement(By.cssSelector(".gr_btn .accept")).click();
            }
        } catch (Exception e) {
            driver.quit();
        }

        driver.quit();
    }

}

/* NOTE
 * Name: 3 - 256 kí tự
 * Username: Dạng email
 * Password: Has 8 - 12 kí tự, có chữ in hoa, in thường, số, kí tự đặc biệt
 * Confirm password: Giống password
 */