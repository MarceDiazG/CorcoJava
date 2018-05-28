package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.NoSuchElementException;

import static java.lang.Integer.parseInt;

public class SearchPage extends BaseClass {

    private static final String BASIC_URL = "\"http://streeteasy.com\"";

    private static final By BUTTON_SEARCH = By.className("Button Button--classicBlue");
    private static final By INPUT_NEIGHBORHOOD = By.className("AreaSearch-textBox");
    private static final By LABEL_ADRESS_SEARCH = By.className("CheckBox-label CheckBox-label--outsideInput AreaList-listInput AreaList-listInput--indent1");
    private static final By SELECT_PRICE = By.id("price_from");
    private static final By OPTION_CUSTOM = By.xpath("//select[@id='price_from']//*[contains(text(),'Custom')]");
    private static final By INPUT_CUSTOM_VALUE = By.className("InputText Home-searchRangeCustom");
    private static final By ELEMENTS_OPTIONS = By.className("price");
    private WebDriverWait wait = new WebDriverWait(driver, 20);

    public SearchPage(WebDriver driver) {

        super(driver);
    }

    /**
     * Method used to load Search Page
     * @return boolean value
     */
    public boolean loadSearchPage(){
        driver.get(BASIC_URL);
        return isPageLoaded();
    }

    /**
     * Method used to validate that Search Page is loaded successfully
     * @return boolean value
     */
    public boolean isPageLoaded(){
    try {

        WebElement buttonSearch;
        buttonSearch = wait.until(ExpectedConditions.visibilityOfElementLocated(BUTTON_SEARCH));
        System.out.println("Button Search Text:"+buttonSearch.getText());
        return true;

    } catch (NoSuchElementException e) {
        System.out.println("Error loading Search Page!" + e.getMessage());
        return false;
    }
}
    public boolean searchWithParameters(String Location, String Custom,
                                        String Price) {
        try {
            WebElement inputNeighborhood = driver.findElement(INPUT_NEIGHBORHOOD);
            inputNeighborhood.click();
            inputNeighborhood.sendKeys(Location);


            WebElement labelAdressSearch = driver.findElement(LABEL_ADRESS_SEARCH);
            labelAdressSearch.click();
            selectPriceInForm(Custom,Price);
            System.out.println("Parameters selected OK");

            WebElement buttonCheckout = driver.findElement(BUTTON_SEARCH);
            buttonCheckout.click();

            List<WebElement> elementsAsOption =
            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(ELEMENTS_OPTIONS));
            return true;

        } catch (NoSuchElementException e) {
            System.out.println("Error searching component of Search Page!\n" + e.getMessage());
            return false;
        }
    }

    /**
     * Method used to select certain customized price in search form
     * @param Custom
     * @param Price
     */
    private void selectPriceInForm(String Custom, String Price) {
        WebElement mySelect = driver.findElement(SELECT_PRICE);
        System.out.println(">> Select Price found OK!, let's continue....\n");
        mySelect.click();

        if (Custom.compareToIgnoreCase("Yes")==0){
            WebElement customOption = driver.findElement(OPTION_CUSTOM);
            customOption.click();
            WebElement inputCustomValue=driver.findElement(INPUT_CUSTOM_VALUE);
            inputCustomValue.sendKeys(Price);
        }
    }
    public String readFirstValue(String Price) {
        //driver.get("https://streeteasy.com/for-sale/manhattan/price:80000000-");
        List<WebElement> elementsAsOption =
                wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(ELEMENTS_OPTIONS));

        //List<WebElement> elementsAsOption =driver.findElements(ELEMENTS_OPTIONS);
        String value = elementsAsOption.get(0).getAttribute("textContent").trim();

        return value;


    }
}
