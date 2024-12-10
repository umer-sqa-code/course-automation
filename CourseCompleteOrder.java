//This code automates a user journey on the QA site using Selenium WebDriver,  
//from logging in to enrolling in a course and placing an order.
//Let's Start

package New;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

public class QA_Hazwoper {
    public static void main(String[] args) {

        System.setProperty("webdriver.gecko.driver", "C:\\Users\\Qasim Shabbir\\Desktop\\geckodriver-v0.35.0-win32\\geckodriver.exe");

        FirefoxOptions options = new FirefoxOptions();
        WebDriver driver = new FirefoxDriver(options);

        try {
            driver.get("https://qa.hazwoper-osha.com/");

            driver.findElement(By.xpath("/html/body/div[1]/header[1]/div[1]/div/div/div[2]/ul/li[4]/a")).click();

            driver.findElement(By.xpath("//*[@id=\"Login_username\"]")).sendKeys("umer.sqa");

            driver.findElement(By.xpath("//*[@id=\"login_password\"]")).sendKeys("12345678");

            // Wait for user interaction (e.g., CAPTCHA)
            Thread.sleep(20000);

            // Click the login button
            driver.findElement(By.xpath("//*[@id=\"login_lms\"]")).click();

            Thread.sleep(5000);

            // Navigate to the course page
            driver.get("https://qa.hazwoper-osha.com/online-courses/fire-safety-training");

            // Click the "Enroll" button
            WebElement enrollButton = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.elementToBeClickable(
                            By.xpath("/html/body/div[1]/div/div/div/div[2]/div/div/div[2]/div/a[1]")));
            enrollButton.click();

            // Wait for the "Proceed to Checkout" button to appear
            WebElement proceedToCheckoutButton = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.elementToBeClickable(
                            By.xpath("/html/body/div[6]/div/div/div[2]/div/div[2]/div[1]/a")));
            proceedToCheckoutButton.click();

            WebElement streetAddressField = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"billing_address_1\"]")));
            driver.findElement(By.xpath("//*[@id=\"billing_address_1\"]")).sendKeys("12345678");

            // Activate the field and clear existing text
            streetAddressField.click();
            streetAddressField.sendKeys(Keys.CONTROL + "a");
            streetAddressField.sendKeys(Keys.BACK_SPACE);

            // Type "123" into the field
            streetAddressField.sendKeys("123");

            Thread.sleep(2000);

            streetAddressField.sendKeys(Keys.ARROW_DOWN);
            streetAddressField.sendKeys(Keys.ENTER);

            WebElement payByPurchaseOrderRadio = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"payment_method_cod\"]")));
            payByPurchaseOrderRadio.click();

            WebElement poNumberField = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='po_number']")));
            poNumberField.sendKeys("PO123456");

            WebElement others = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".chk-plus > i:nth-child(1)")));

            // Ensure the element is scrolled into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", others);

            // Perform a JavaScript click if necessary
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", others);

            // Handle the "Place Order" button
            try {
                WebElement placeOrderButton = new WebDriverWait(driver, Duration.ofSeconds(10))
                        .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"purchase_submit\"]")));

                // Wait for any overlays to disappear
                new WebDriverWait(driver, Duration.ofSeconds(10))
                        .until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".overlay-selector-if-any"))); // Replace selector

                // Ensure the button is in view
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", placeOrderButton);

                // Perform a JavaScript click
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", placeOrderButton);

            } catch (Exception e) {
                System.out.println("Failed to interact with the Place Order button: " + e.getMessage());
                
            }

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
           // driver.quit();
        }
    }
}
