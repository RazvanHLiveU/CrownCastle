package com.crowncastle.crowncastle.tests;

import com.crowncastle.crowncastle.pages.CheckersPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

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
        //System.out.println(j.executeScript("return document.readyState").toString());
        assert j.executeScript("return document.readyState").toString().equals("complete");

    }

    @Test
    @Order(2)
    void makeFirstMove() throws InterruptedException {
        //Checking if we can make our first move if the initial message is displayed
        assert checkersPage.getMessage().equals(checkersPage.initialMessage);
        checkersPage.makeFirstMove();
        Thread.sleep(120);
        //checking if piece was moved successfully and the previous spot is now empty
        assert checkersPage.space73.getAttribute("src").equals(checkersPage.orangePieceMoved);
        assert checkersPage.space62.getAttribute("src").equals(checkersPage.emptySpace);

    }

    @Test
    @Order(3)
    void makeRestOfMoves() throws InterruptedException {
        int movesMade = 1;
        //waiting for the computer to make a move
        Thread.sleep(1200);
        //checking if we can make a move with the "Make Move" message
        assert checkersPage.canMakeMove();

    }

    @Test
    void testing() throws InterruptedException {
        String[][] board = checkersPage.getCurrentBoard();
        checkersPage.voidMakeAnyMove(board);

        Thread.sleep(1200);
    }

    @Test
    @Order(4)
    void resetTable() throws InterruptedException {
        //clicking the reset button
        checkersPage.restartGame();
        Thread.sleep(1000);
        //checking initial message is back
        assert checkersPage.getMessage().equals(checkersPage.initialMessage);
        //checking if all pieces are into place based on image

    }



    @AfterAll()
    void quit() {
        driver.quit();
    }

}
