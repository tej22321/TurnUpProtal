package Pages;

import Utilities.WebUtilities;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;

public class TMPage {

    private static final Logger log = LogManager.getLogger(TMPage.class);

    public void CreateTMRecord(WebDriver driver){


        //navigate to create new record
        driver.findElement(By.xpath("//div/p/a[contains(text(),'Create New')]")).click();

        //navigate to TypeCode input element and select the dropdown
        driver.findElement(By.xpath("//input[contains(@id,'TypeCode')]/ancestor::span//span[contains(text(),'Material')]")).click();

        //navigate to Time option and select it
        driver.findElement(By.xpath("//ul[contains(@id,'TypeCode_listbox')]/li[2]")).click();

        //navigate to Code input and enter the value
        WebElement CodeValue = driver.findElement(By.id("Code"));
        CodeValue.sendKeys("TMtest");
        String CodeValueText = CodeValue.getAttribute("value");

        //navigate to Description  input and enter the value
        driver.findElement(By.id("Description")).sendKeys("TMtest Description");

        //navigate to price per unit input and enter the value
        driver.findElement(By.xpath("//input[@id='Price']/preceding-sibling::input")).sendKeys("25");

        //navigate to select files and upload a image from the folder in the project

        String baseDirectory = System.getProperty("user.dir");
        String relativePath = "src/test/java/images/img.png";
        String uploadFile = Paths.get(baseDirectory,relativePath).toAbsolutePath().toString();
        driver.findElement(By.id("files")).sendKeys(uploadFile);

        //navigate to save button and select
        driver.findElement(By.id("SaveButton")).click();


        String action = "create";
        // verify if record successfully created
        VerifyTMRecordCreated(driver,CodeValueText,action);


    }

    public void EditTMRecord(WebDriver driver){
        //Go to the last page
        driver.findElement(By.xpath("//span[contains(text(),'last page')]/parent::a")).click();

        //selct the row which has Code value that we have created recently
        String CodeText = driver.findElement(By.xpath("//div[@id=\"tmsGrid\"]/div[position()=3]/table/tbody/tr[last()]/td[1]")).getText();

        //if the code matches the text provided , navigate to edit button and select it
        if(CodeText.equals("TMtest")){
            driver.findElement(By.xpath("//div[@id=\"tmsGrid\"]/div[position()=3]/table/tbody/tr[last()]/td/a[text()='Edit']")).click();
        }

        //Go to the Code text input filed and edit its value
        driver.findElement(By.id("Code")).sendKeys("edit");
        String CodeValueText = driver.findElement(By.id("Code")).getAttribute("value");
        log.info("CodeValueTextfrom edit method "+CodeValueText);



        //navigate to save button and select
        driver.findElement(By.id("SaveButton")).click();

        String action ="edit";
        // verify if record successfully created
        VerifyTMRecordCreated(driver,CodeValueText,action);

    }


    public void DeleteTMRecord(WebDriver driver){
        //Go to the last page
        driver.findElement(By.xpath("//span[contains(text(),'last page')]/parent::a")).click();

        //selct the row which has Code value that we have created recently
        String CodeText = driver.findElement(By.xpath("//div[@id=\"tmsGrid\"]/div[position()=3]/table/tbody/tr[last()]/td[1]")).getText();

        //if the code matches the text provided , navigate to edit button and select it
        if(CodeText.equals("TMtestedit")){
            driver.findElement(By.xpath("//div[@id=\"tmsGrid\"]/div[position()=3]/table/tbody/tr[last()]/td/a[text()='Delete']")).click();
        }


        // Switch to the alert dialog
        Alert alert = driver.switchTo().alert();

        // Accept the alert (click on the OK button)
        alert.accept();

        String action = "delete";
       // verify if record deleted successfully
        VerifyTMRecordCreated(driver,"TMtestedit",action);

    }

    public void VerifyTMRecordCreated(WebDriver driver,String CodeValueText, String action) {
        try {
            //navigate to last page in the TM table and get the no.of pages count, and go back to first page to check all the records

            WebUtilities.waitToBeVisible(driver, "//span[contains(text(),'last page')]/parent::a", 100);
            driver.findElement(By.xpath("//span[contains(text(),'last page')]/parent::a")).click();

            int totalPages = Integer.parseInt(driver.findElement(By.xpath("//span[contains(text(),'last page')]/parent::a")).getAttribute("data-page"));
            log.info("totalpages for table" + totalPages);
            driver.findElement(By.xpath("//a[contains(@data-page, '1')]/span[contains(text(),'Go to the first page')]")).click();
            //System.out.print(CodeValueText);
            log.info("This the code value text" + CodeValueText);


            boolean recordFound = false;

            for (int i = 1; i <= totalPages; i++) {
                // get the number of rows for a page so we can loop through all rows
                int pageRows = driver.findElements(By.xpath("//div[@id=\"tmsGrid\"]/div[3]/table/tbody/tr")).size();
                log.info("page no:" + i + " no of rows:" + pageRows);

                for (int j = 1; j <= pageRows; j++) {
                    String CodeValue = driver.findElement(By.xpath("//*[@id=\"tmsGrid\"]/div[3]/table/tbody/tr[" + j + "]/td[1]"))
                            .getText();
                    log.info("code value"+ CodeValue);

                    if (CodeValue.equals(CodeValueText)) {
                        log.info("im here");
                        recordFound = true;
                        break;
                    }
                }

                if (recordFound) {
                    break; // Exit the outer loop if record is found
                }

                // Move to next page
                driver.findElement(By.xpath("//*[@id=\"tmsGrid\"]/div[4]/a[3]/span")).click();

            }
            switch (action) {
                case "create":
                    // Check if the record is found
                    if (recordFound) {
                        Assert.assertTrue(recordFound, "Record saved successfully");
                    } else {
                        Assert.fail("Record not saved ");
                    }
                    break;
                case "edit":
                    // Check if the Edit record is found
                    if (recordFound) {
                        Assert.assertTrue(recordFound, "Editted Record saved successfully");
                    } else {
                        Assert.fail("Edit Record not saved");
                    }
                    break;
                case "delete":
                    // Check if the record is found
                    if (!recordFound) {
                        Assert.assertTrue(!recordFound, "Record Deleted successfully");
                    } else {
                        Assert.fail("Record not Deleted");
                    }
                    break;

            }


        } catch (Exception e) {
            // System.out.println("Error Message"+e.getMessage());
            log.fatal(e.getMessage());
        }

    }
}
