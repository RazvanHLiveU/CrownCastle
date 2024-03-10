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


}