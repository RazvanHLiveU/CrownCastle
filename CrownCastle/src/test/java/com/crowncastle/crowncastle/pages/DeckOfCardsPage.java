package com.crowncastle.crowncastle.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

// page_url = https://deckofcardsapi.com
public class DeckOfCardsPage {
    WebDriver driver;
    WebDriverWait w;

    public String PAGE_URL = "https://deckofcardsapi.com/";

    @FindBy(xpath = "//*[text() = 'Deck of Cards']")
    public WebElement cardsTitle;
    
    
    
    public DeckOfCardsPage(WebDriver driver) {
        
        this.driver = driver;
        driver.get(PAGE_URL);
        w = new WebDriverWait(driver, Duration.ofSeconds(5));
        
        PageFactory.initElements(driver, this);
    }

    public boolean isCardPageLoaded(){
        //waiting 10 seconds for the page to load
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        String s = driver.getCurrentUrl();
        // checking condition if the URL is loaded
        return s.equals(PAGE_URL);
    }
    
    public boolean isCardTitleVisible() {

        //the try catch is necessary because if the element is not on the page it will crash and the tests will not continue
        try {
            return  cardsTitle.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}