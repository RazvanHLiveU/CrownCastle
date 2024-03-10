package com.crowncastle.crowncastle.tests;

import com.crowncastle.crowncastle.pages.CheckersPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.Objects;

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
        //waiting for the computer to make a move
        Thread.sleep(1200);
        assert checkersPage.canMakeMove();
        int movesMade = 1;
        boolean gotBlue = false;
        while(movesMade < 5 || !gotBlue) {
            String[][] board = checkersPage.getCurrentBoard();
            if(checkersPage.didGetBlue(board)) {
                //waiting for the computer to make a move
                Thread.sleep(1200);
                gotBlue = true;
                movesMade ++;
            }
            checkersPage.makeAnyMove(board);
            //waiting for the computer to make a move
            Thread.sleep(3200);
            movesMade++;
            //check if we can still make moves - maybe the game ended
            if(!checkersPage.canMakeMove() && !Objects.equals(checkersPage.getMessage(), checkersPage.pleaseWaitMessage)) {
                System.out.println("Game Over");
                break;

            }

        }
         assert movesMade >= 5;
         assert gotBlue;


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
        assert checkersPage.checkInitialBoard(checkersPage.getCurrentBoard());


    }

    @AfterAll()
    void quit() {
        driver.quit();
    }

}
