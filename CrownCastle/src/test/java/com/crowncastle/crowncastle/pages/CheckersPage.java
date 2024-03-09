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

    public CheckersPage(WebDriver driver) {
        this.driver = driver;
        driver.get(page_url);
        w = new WebDriverWait(driver, Duration.ofSeconds(5));
        PageFactory.initElements(driver, this);
    }



    public void navigateToPage(){


    }





}