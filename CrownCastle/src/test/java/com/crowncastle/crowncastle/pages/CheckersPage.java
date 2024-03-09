package com.crowncastle.crowncastle.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

// page_url = https://www.gamesforthebrain.com/game/checkers/
public class CheckersPage {

    WebDriver driver;
    WebDriverWait w;

    public String page_url = "https://www.gamesforthebrain.com/game/checkers/";

    @FindBy(xpath = "//*[text() = 'Checkers']")
    public WebElement checkersTitle;

    

    public CheckersPage(WebDriver driver) {
        this.driver = driver;
        driver.get(page_url);
        w = new WebDriverWait(driver, Duration.ofSeconds(5));
        PageFactory.initElements(driver, this);
    }



    public boolean isPageLoaded(){
        //waiting 10 seconds for the page to load
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        String s = driver.getCurrentUrl();
        // checking condition if the URL is loaded
        return s.equals(page_url);
    }

    public boolean isTitleVisible() {

        //the try catch is necessary because if the element is not on the page it will crash and the tests will not continue
        try {
            return  checkersTitle.isDisplayed();
        } catch (Exception e) {
            return false;
        }

    }





}