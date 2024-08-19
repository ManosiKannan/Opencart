package pageObjects;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AccountHomePage extends BasePage {

	JavascriptExecutor js;
	public AccountHomePage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//h2[normalize-space()='My Account']")
	WebElement msgMyAccount;

	@FindBy(xpath = "//a[@class='list-group-item' and text()='Logout']")
	WebElement linkLogout;

	public boolean isMyAccountDisplayed() {
		try {
			return msgMyAccount.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public void clickLogout() {
		js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView()", linkLogout);
		js.executeScript("arguments[0].click()", linkLogout);
	}
}
