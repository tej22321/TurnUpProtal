package Tests;

import Pages.LoginPage;
import Pages.TMPage;
import Utilities.CommonDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TurnUpPortalTest extends CommonDriver {
    private static final Logger log = LogManager.getLogger(TurnUpPortalTest.class);
    LoginPage loginPageObj = new LoginPage();
    TMPage TMPageObj = new TMPage();

    @BeforeTest
    public void SetUp(){
        driver = new ChromeDriver();
        loginPageObj.LoginUser(driver);
        loginPageObj.NavigateToTMRecord(driver);


    }

    @Test(priority = 1)
    public void CreateTMRecord(){


        TMPageObj.CreateTMRecord(driver);

    }
    @Test(priority = 2)
public void EditTMRecord() {
        TMPageObj.EditTMRecord(driver);

}

@Test(priority = 3)
public void DeleteTMRecord(){
        TMPageObj.DeleteTMRecord(driver);
}
    @AfterTest
    public void AfterTest(){
        driver.quit();
    }
}
