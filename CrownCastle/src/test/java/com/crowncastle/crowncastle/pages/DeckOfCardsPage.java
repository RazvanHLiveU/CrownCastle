package com.crowncastle.crowncastle.pages;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.Scanner;

// page_url = https://deckofcardsapi.com
public class DeckOfCardsPage {
    WebDriver driver;
    WebDriverWait w;

    public String PAGE_URL = "https://deckofcardsapi.com/";
    public String deckID;
    public boolean isShuffled = false;
    public boolean isShuffledWithSuccess = false;

    public int playerOne = 0;
    public int playerTwo = 0;

    private String apiURL = "https://deckofcardsapi.com/api/deck/";

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

    public void getNewDeck()  {
        try {
            URL url = new URL(apiURL + "new/shuffle/?deck_count=1");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            //checking if connection is ok
            int responseCode = conn.getResponseCode();

            //200 OK
            if(responseCode != 200) {
                throw new RuntimeException("HttpResponseCode:" + responseCode);
            }
            else {
                //start decoding the response
              StringBuilder informationString = new StringBuilder();
              Scanner scanner =  new Scanner(url.openStream());
              while(scanner.hasNext()) {
                  informationString.append(scanner.nextLine());
              }
              //closing the scanner after appending the entire response
                scanner.close();
                System.out.println(informationString);

                //convert string to JSON using JSON Simple library
                JSONParser parse = new JSONParser();
                JSONObject deckData = (JSONObject) parse.parse(String.valueOf(informationString));
                deckID = deckData.get("deck_id").toString();


            }

        } catch (Exception e)
        {
            System.out.println("Error" + e);
        }
    }

    public void shuffleDeck() {
        if(deckID != null ) {
            try {
                URL url = new URL(apiURL + deckID +"/shuffle/");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();

                //checking if connection is ok
                int responseCode = conn.getResponseCode();

                //200 OK
                if(responseCode != 200) {
                    throw new RuntimeException("HttpResponseCode:" + responseCode);
                }
                else {
                    //start decoding the response
                    StringBuilder informationString = new StringBuilder();
                    Scanner scanner =  new Scanner(url.openStream());
                    while(scanner.hasNext()) {
                        informationString.append(scanner.nextLine());
                    }
                    //closing the scanner after appending the entire response
                    scanner.close();
                    System.out.println(informationString);

                    //convert string to JSON using JSON Simple library
                    JSONParser parse = new JSONParser();
                    JSONObject deckData = (JSONObject) parse.parse(String.valueOf(informationString));
                    isShuffled = Boolean.parseBoolean(deckData.get("shuffled").toString());
                    isShuffledWithSuccess = Boolean.parseBoolean(deckData.get("success").toString());
                }

            } catch (Exception e)
            {
                System.out.println("Error" + e);
            }
        }
    }


    public void dealThreeCards(int toPlayer) {

        if(deckID != null ) {
            try {
                URL url = new URL(apiURL + deckID +"/draw/?count=3");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();

                //checking if connection is ok
                int responseCode = conn.getResponseCode();

                //200 OK
                if(responseCode != 200) {
                    throw new RuntimeException("HttpResponseCode:" + responseCode);
                }
                else {
                    //start decoding the response
                    StringBuilder informationString = new StringBuilder();
                    Scanner scanner =  new Scanner(url.openStream());
                    while(scanner.hasNext()) {
                        informationString.append(scanner.nextLine());
                    }
                    //closing the scanner after appending the entire response
                    scanner.close();
                    //convert string to JSON using JSON Simple library
                    JSONParser parse = new JSONParser();
                    JSONObject responseData = (JSONObject) parse.parse(String.valueOf(informationString));
                    JSONArray cardsData = (JSONArray)  responseData.get("cards");

                    for(int i = 0; i<= 2; i++) {
                        JSONObject card = (JSONObject)  cardsData.get(i);
                        System.out.println("Your card is: " +card.get("value").toString()+ " with value of: ");
                        if(toPlayer == 1) {
                            int cardValue = getCardValue(playerOne,card.get("value").toString());
                            System.out.println(cardValue);
                            playerOne = playerOne + cardValue;
                            System.out.println("Player One Score: " + playerOne);
                        } else {
                            int cardValue = getCardValue(playerTwo,card.get("value").toString());
                            System.out.println(cardValue);
                            playerTwo = playerTwo + cardValue;
                            System.out.println("Player Two Score: " + playerTwo);
                        }



                    }



                }

            } catch (Exception e)
            {
                System.out.println("Error" + e);
            }
        }

    }

    //this function is needed to convert JACK, QUEEN , KING into their value and ACE to 1 or 10 depending on current value
    //if the current value plus ace is less than 21 the ace will be 11, otherwise 1 -> we assume that we don't know what is the next card of the player and that he is forced to get 3 cards
    int getCardValue(int currentPlayerValue, String cardValue) {
        switch(cardValue) {
            case "JACK", "QUEEN", "KING":  return 10;
            case "ACE" : {
                if(currentPlayerValue + 11 > 21) {
                    return 1;
                } else {
                    return 11;
                }
            }
            //all value cards return the face value i.e 6 is 6 and 5 is 5
            default: return Integer.parseInt(cardValue);

        }
    }
}