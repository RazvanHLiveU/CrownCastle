package com.crowncastle.crowncastle.tests;

import com.crowncastle.crowncastle.pages.CheckersPage;
import com.crowncastle.crowncastle.pages.DeckOfCardsPage;
import org.junit.jupiter.api.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DeckOfCardsTests {

    WebDriver driver;
    DeckOfCardsPage cardsPage;

    @BeforeAll
    void setUp() {
        driver = new ChromeDriver();
        cardsPage = new DeckOfCardsPage(driver);
    }

    @Test
    @Order(1)
    void checkPageIsUp(){
        //checking if the page url is correct
        assert cardsPage.isCardPageLoaded();
        System.out.println("Page loaded");
        //to make sure that elements are visible we will also check for the "Deck Of Cards" title
        assert cardsPage.isCardTitleVisible();
        // Finally we will also check if the DOM of the page marks the page load as completed
        JavascriptExecutor j = (JavascriptExecutor) driver;
        //System.out.println(j.executeScript("return document.readyState").toString());
        assert j.executeScript("return document.readyState").toString().equals("complete");
    }

    @Test
    @Order(2)
    void getDeck() {
       cardsPage.getNewDeck();
       System.out.println("Deck iD: " + cardsPage.deckID);
       //to test if the API was successful we will see if the deck id was set properly -> different from null
        assert cardsPage.deckID != null;
    }

    @Test
    @Order(3)
    void shuffleDeck() {
        //first we will check if the deck was created, otherwise the other tests should fail as well so no need to continue
        assert cardsPage.deckID != null;
        //running API for shuffling deck
        cardsPage.shuffleDeck();
        //check if shuffled successfully
        assert cardsPage.isShuffled;
        assert cardsPage.isShuffledWithSuccess;

    }

    @Test
    @Order(4)
    void dealThree() {
        //first we will check if the deck was created, otherwise the other tests should fail as well so no need to continue
        assert cardsPage.deckID != null;
        cardsPage.dealThreeCards(1);
        cardsPage.dealThreeCards(2);
        //checking if cards were dealt successfully
        assert cardsPage.playerOne != 0;
        assert cardsPage.playerTwo != 0;

        //wriitng out if any of the players have BlackJack i.e. their score is 21
        if(cardsPage.playerOne == 21) {
            System.out.println("=====WINNER======");
            System.out.println("Player One Has Black Jack");
        }
        if(cardsPage.playerTwo == 21) {
            System.out.println("=====WINNER======");
            System.out.println("Player Two Has Black Jack");
        }
    }

    @AfterAll()
    void quit() {
        driver.quit();
    }




}
