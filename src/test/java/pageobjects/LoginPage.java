package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage extends BaseClass{

	public LoginPage(WebDriver driver){
		super(driver);
	}

	private WebDriverWait wait = new WebDriverWait(driver, 20);
	private static final By LINK_USER = By.linkText("Sign In");

	@FindBy(how=How.ID, using="login")
	public static WebElement input_email;
	
	@FindBy(how=How.ID, using="input_password")
	public static WebElement input_password;
	
	@FindBy(how=How.NAME, using="do_login")
	public static WebElement sign_in_button;

	
	public void SignInUser(String user, String pass){

		try {
			WebElement sign_in_link = wait.until(ExpectedConditions.visibilityOfElementLocated(LINK_USER));
			sign_in_link.click();
			wait.wait(1000);

			// Switch to new window opened
			for(String winHandle : driver.getWindowHandles()){
				driver.switchTo().window(winHandle);
			}
			input_email.sendKeys(user);
			input_password.sendKeys(pass);
			sign_in_button.click();

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	}
		

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
