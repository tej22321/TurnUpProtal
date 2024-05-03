package Pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class LoginPage {


    private static final Logger log = LogManager.getLogger(LoginPage.class);

    public void LoginUser(WebDriver driver){

        // navigate to URL
        driver.get("http://horse.industryconnect.io");

        //maximise the page
        driver.manage().window().maximize();
        log.info("maximised the browser");

        // Finding the username text field
        WebElement UserNameField = driver.findElement(By.id("UserName"));

        // sending the username field value
        UserNameField.sendKeys("hari");

        // Finding the password text field
        WebElement PasswordField = driver.findElement(By.id("Password"));

        // sending the username field value
        PasswordField.sendKeys("123123");

        //finding the save button
        WebElement SaveBtnField = driver.findElement(By.xpath("//input[@type='submit']"));


        //click the save button
        SaveBtnField.click();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(100));



        // navigate to user logged in name in dashboard
        WebElement UserLoggedIn = driver.findElement(By.xpath("//a[contains(text(),'hari')]"));

        //verify user logged In
            Assert.assertTrue(UserLoggedIn.getText().contains("hari"),"user logged in");
            log.info("user logged in successfully");

    }


    public void NavigateToTMRecord(WebDriver driver){

                // navigate to Adminstraion tab in menu
            driver.findElement(By.xpath("//ul/li/a[contains(text(),'Administration')]")).click();

            //select the Time and Material tab under Administraion menu
        driver.findElement(By.xpath("//ul/li/a[contains(text(),'Time')]")).click();

        log.info("user Navigated to TM page under Adminstation menu");
    }

}