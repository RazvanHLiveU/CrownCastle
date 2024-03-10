package com.crowncastle.crowncastle.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

// page_url = https://www.gamesforthebrain.com/game/checkers/
public class CheckersPage {

    WebDriver driver;
    WebDriverWait w;

    public String page_url = "https://www.gamesforthebrain.com/game/checkers/";
    final public String makeMoveMessage = "Make a move.";
    final public String initialMessage = "Select an orange piece to move.";

    final  public String orangePieceMoved = "https://www.gamesforthebrain.com/game/checkers/you2.gif";
    final public String orangePieceInitial = "https://www.gamesforthebrain.com/game/checkers/you1.gif";
    final public String bluePieceInitial = "https://www.gamesforthebrain.com/game/checkers/me1.gif";
    final public String bluePieceMoved = "https://www.gamesforthebrain.com/game/checkers/me2.gif";
    final public String emptySpace = "https://www.gamesforthebrain.com/game/checkers/gray.gif";
    final static String blackSpace = "https://www.gamesforthebrain.com/game/checkers/black.gif";

    @FindBy(xpath = "//*[text() = 'Checkers']")
    public WebElement checkersTitle;

    @FindBy(id = "message")
    public WebElement message;

    
    @FindBy(xpath = "//img[@name='space62']")
    public WebElement space62;

    @FindBy(xpath = "//img[@name='space73']")
    public WebElement space73;

    @FindBy(xpath = "//*[text() = 'Restart...']")
    public WebElement linkRestart;

    public CheckersPage(WebDriver driver) {
        this.driver = driver;
        driver.get(page_url);
        w = new WebDriverWait(driver, Duration.ofSeconds(5));
        PageFactory.initElements(driver, this);
    }



    public boolean isPageLoaded(){
        //waiting 10 seconds for the page to load
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        String s = driver.getCurrentUrl();
        // checking condition if the URL is loaded
        return s.equals(page_url);
    }

    public boolean isTitleVisible() {

        //the try catch is necessary because if the element is not on the page it will crash and the tests will not continue
        try {
            return  checkersTitle.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getMessage(){
        return message.getText();
    }

    public  boolean canMakeMove(){
        System.out.println(getMessage());
        return getMessage().equals(makeMoveMessage);
    }

    public void restartGame() {
        linkRestart.click();
    }


    public void makeFirstMove() {
        //in our case our first move will be to move the orane checker from space 62 to space 73
        space62.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        space73.click();
        System.out.println(space73.getAttribute("src"));

    }

    public String[][] getCurrentBoard() {
        String[][] board = new String[8][8];
        int i;
        int j;
        for (i = 0; i<=7; i++ ) {
            for(j = 0; j<=7; j++) {
                String srcImage = driver.findElement(By.xpath("//img[@name='space" + i + j + "']")).getAttribute("src");
                switch (srcImage) {
                    case orangePieceMoved, orangePieceInitial -> board[i][j] = "orange";
                    case bluePieceInitial, bluePieceMoved -> board[i][j] = "blue";
                    case emptySpace -> board[i][j] = "empty";
                    case blackSpace -> board[i][j] = "black";
                }

            }
        }
        return board;
    }

    //This method will make any available move with the orange checker
    public void makeAnyMove(String[][] board) throws InterruptedException {

        int i;
        int j;
        for (i = 0; i<=7; i++ ) {
            for(j = 0; j<=7; j++) {
                if(Objects.equals(board[i][j], "orange")) {

                    //checking if we can move to the up right diagonal
                    //check for out of bounds first
                    if(i-1 >=0 && j-1>=0) {
                        if(board[i-1][j-1].equals("empty")) {
                            driver.findElement(By.xpath("//img[@name='space" + i + j + "']")).click();
                            Thread.sleep(100);
                            driver.findElement(By.xpath("//img[@name='space" + (i-1) + (j-1) + "']")).click();
                            //we moved once, no need to continue
                            break;
                        }
                    }
                    //checking if we can move to the up left diagonal one space
                    //check for out of bounds first
                    if(i+1 <=7 && j+1<=7) {
                        if(board[i+1][j+1].equals("empty")) {
                            driver.findElement(By.xpath("//img[@name='space" + i + j + "']")).click();
                            Thread.sleep(100);
                            driver.findElement(By.xpath("//img[@name='space" + (i+1) + (j+1) + "']")).click();
                            //we moved once, no need to continue
                            break;
                        }
                    }
                }

            }
        }
    }

    //this function tries to get a blue
   public boolean didGetBlue(String[][] board) throws InterruptedException {

        int i;
        int j;
        for (i = 0; i<=7; i++ ) {
            for(j = 0; j<=7; j++) {
                if(Objects.equals(board[i][j], "orange")) {
                    //checking if we can move to the up left diagonal one space
                    //check for out of bounds first
                    if(i+1 <=7 && j+1<=7) {
                        if(board[i+1][j+1].equals("blue")) {

                            //checking if next available space is empty and not out of bounds first
                            if(i+2 <=7 && j+2<=7) {
                                if(board[i+2][j+2].equals("empty")) {
                                    driver.findElement(By.xpath("//img[@name='space" + i + j + "']")).click();
                                    Thread.sleep(100);
                                    driver.findElement(By.xpath("//img[@name='space" + (i+2) + (j+2) + "']")).click();
                                    //we got a blue, we can stop
                                    return true;
                                }
                            }
                        }
                    }
                    //checking if we can move to the up right diagonal
                    //check for out of bounds first
                    if(i-1 >=0 && j-1>=0) {
                        if(board[i-1][j-1].equals("blue")) {
                            //checking if next available space is empty and not out of bounds first
                            if(i-2 >=0 && j-2>=0) {
                                if(board[i-2][j-2].equals("empty")) {
                                    driver.findElement(By.xpath("//img[@name='space" + i + j + "']")).click();
                                    Thread.sleep(100);
                                    driver.findElement(By.xpath("//img[@name='space" + (i-2) + (j-2) + "']")).click();
                                    //we got a blue, we can stop
                                    return true;
                                }
                            }
                        }
                    }
                }

            }
        }
        //we couldn't get any blue pieces so we stop
    return false;
    }





}