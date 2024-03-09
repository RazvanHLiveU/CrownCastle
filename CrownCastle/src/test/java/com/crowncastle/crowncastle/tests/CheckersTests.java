package com.crowncastle.crowncastle.tests;

import com.crowncastle.crowncastle.pages.CheckersPage;
import org.junit.jupiter.api.*;
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
        System.out.print("code from here");
    }

    @AfterAll()
    void quit() {
        driver.quit();
    }

}
