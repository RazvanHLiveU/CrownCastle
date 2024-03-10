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
       System.out.println(cardsPage.deckID);
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

    @AfterAll()
    void quit() {
        driver.quit();
    }




}