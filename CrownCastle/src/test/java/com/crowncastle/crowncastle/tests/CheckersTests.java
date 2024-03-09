package com.crowncastle.crowncastle.tests;

import com.crowncastle.crowncastle.pages.CheckersPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CheckersTests {

    WebDriver driver;
    CheckersPage checkersPage;

    @BeforeAll
    void setUp() {
        driver = new ChromeDriver();
        checkersPage = new CheckersPage(driver);
    }

    @Test
    @Order(1)
    void checkPageIsUp(){
        //checking if the page url is correct
       assert checkersPage.isPageLoaded();
       System.out.println("Page loaded");
       //to make sure that elements are visible we will also check for the "Checkers" title
       assert checkersPage.isTitleVisible();
        // Finally we will also check if the DOM of the page marks the page load as completed
        JavascriptExecutor j = (JavascriptExecutor) driver;
        System.out.println(j.executeScript("return document.readyState").toString());
        assert j.executeScript("return document.readyState").toString().equals("complete");

    }

    @AfterAll()
    void quit() {
        driver.quit();
    }

}
