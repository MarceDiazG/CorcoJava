package pageobjects;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.NoSuchElementException;

import static java.lang.Integer.parseInt;

public class SearchPage extends BaseClass {

    private static final String STREET_EASY_URL = "http://streeteasy.com";
    private static final String BASIC_URL = "https://www.corcoran.com/";
    private static final By BUTTON_SEARCH = By.xpath("//div[@class='row ShoveUp NudgeDown']//button[@class='primary-btn big-button FormTrigger']");
    private static final By INPUT_NEIGHBORHOOD = By.className("AreaSearch-textBox");
    private static final By LABEL_ADDRESS_SEARCH = By.className("CheckBox-label CheckBox-label--outsideInput AreaList-listInput AreaList-listInput--indent1");
    private static final By SELECT_PRICE = By.id("price_from");
    private static final By OPTION_CUSTOM = By.xpath("//select[@id='price_from']//*[contains(text(),'Custom')]");
    private static final By INPUT_CUSTOM_VALUE = By.className("InputText Home-searchRangeCustom");
    private static final By ELEMENTS_OPTIONS = By.className("price ");
    private static final By LINK_SELECT_LOCATION = By.xpath("//div[@class='filter SlideDown-Trigger Generic-Trigger Neighborhoods col-md-3']//span[@class='secondary-btn Primary-Color']");
    private static final By INPUT_OPTION_MANHATTAN = By.xpath("//input[@value='Manhattan']");
    private static final By PRICE_SLIDER_LOCATOR = By.xpath("(//div[@id='price-slider']//div[@class='ui-slider-range ui-corner-all ui-widget-header'])[1]");
    private static final By DIV_SEARCH_RESULTS = By.className("Search-Results-Block");
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

        wait.until(ExpectedConditions.visibilityOfElementLocated(LINK_SELECT_LOCATION));
        System.out.println("Link 'Select Location' located successfully ! ");
        return true;

    } catch (NoSuchElementException e) {
        System.out.println("Error loading Search Page!" + e.getMessage());
        return false;
    }
}
    public boolean searchCorcoranWithParameters(String Location, String Custom,
    String Price) {
        try {
            WebElement linkSelectLocation = driver.findElement(LINK_SELECT_LOCATION);
            linkSelectLocation.click();
            waitSeconds(1);

            WebElement allInManhattanLink = driver.findElement(INPUT_OPTION_MANHATTAN);
            //allInManhattanLink.click();

            selectPriceInSlider(Price);
            waitSeconds(2);

            WebElement buttonSearch = driver.findElement(BUTTON_SEARCH);
            buttonSearch.click();

            wait.until(ExpectedConditions.visibilityOfElementLocated(DIV_SEARCH_RESULTS));

            System.out.println("Parameters selected OK");
            return true;

        } catch (NoSuchElementException e) {
            System.out.println("Error searching components of Search Page!\n" + e.getMessage());
            return false;
        }
    }

    private void waitSeconds(int i) {
        try {
            Thread.sleep(i*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean searchWithParameters(String Location, String Custom,
                                        String Price) {
        try {
            WebElement inputNeighborhood = driver.findElement(INPUT_NEIGHBORHOOD);
            inputNeighborhood.click();
            inputNeighborhood.sendKeys(Location);


            WebElement labelAddressSearch = driver.findElement(LABEL_ADDRESS_SEARCH);
            labelAddressSearch.click();
            selectPriceInForm(Custom,Price);
            System.out.println("Parameters selected OK\n");

            WebElement buttonCheckout = driver.findElement(BUTTON_SEARCH);
            buttonCheckout.click();

            wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(ELEMENTS_OPTIONS));
            return true;

        } catch (NoSuchElementException e) {
            System.out.println("Error searching component of Search Page!\n" + e.getMessage());
            return false;
        }
    }

    private void selectPriceInSlider(String Price){
        WebElement priceSlider = driver.findElement(PRICE_SLIDER_LOCATOR);

        Dimension sliderSize = priceSlider.getSize();
        int sliderWidth = sliderSize.getWidth();

        int xCoord = priceSlider.getLocation().getX();
        xCoord = Math.toIntExact(Math.round(xCoord * (0.65)));

        Actions builder = new Actions(driver);
        /*builder.moveToElement(priceSlider)
                .click()
                .dragAndDropBy
                        (priceSlider,xCoord + sliderWidth, 0)
                .build()
                .perform();*/
        int iCount = 0; int iRange=20;
        if (iRange > 0) {
            for(iCount = 0; iCount < iRange; iCount++) {
                builder.moveToElement(priceSlider).click(priceSlider).sendKeys(Keys.ARROW_UP).perform();
            }
        } else {
            for (iCount = 0; iCount > iRange; iCount--) {
                builder.click(priceSlider).sendKeys(Keys.ARROW_DOWN) .perform();
            }
        }

    }
    public String castDollarValue(String inputValue){
        inputValue= inputValue.replace("$"," ");
        inputValue= inputValue.replaceAll(",","");

        return inputValue.trim();
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
    public String readFirstValue() {

        List<WebElement> elementsAsOption =
                wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(ELEMENTS_OPTIONS));

        String value = elementsAsOption.get(0).getAttribute("textContent").trim();
        return castDollarValue(value);
    }
}
