package step_definitions;

import static java.lang.Integer.parseInt;
import static org.testng.AssertJUnit.assertEquals;

import java.util.Map;

import helpers.Log;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import pageobjects.SearchPage;

public class BlankStepDefs {
    public WebDriver driver;
    private Map<String, String> sites;
    private String currentSite;
    SearchPage sp;
    
    public BlankStepDefs() {
        driver = Hooks.driver;
        }

    @Given("^I open (.*) webPage$")
    public void iOpenParamwebWebPage(String paramweb){
        sp= new SearchPage(driver);
        sp.loadSearchPage();
    }

    @When("^Search (.*), (.*) for a start value of (.*)$")
    public void searchLocationAndPriceCriteria(String Location,
                                               String Custom, String Price) {

        Log.info("Values: " + Location+"/"+Custom+"/"+Price);
        sp.searchCorcoranWithParameters(Location,Custom,Price);

    }

    @Then("^I validate that first element showed is according to criteria (.*)$")
    public void validateFirstOptionValue(String Price) {

        String showedFirstValue = sp.readFirstValue();

        System.out.println("\nChecking values: "+parseInt(showedFirstValue)+" >= "+parseInt(Price)+"\n");

        boolean result = parseInt(showedFirstValue) >= parseInt(Price);
        Assert.assertTrue(result);

        Log.info("  - First showed value checked OK!\n");
    }

}