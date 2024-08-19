package pageObjects;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AccountRegisterPage extends BasePage {

	JavascriptExecutor js;

	public AccountRegisterPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//input[@id='input-firstname']")
	WebElement txtFirstName;

	@FindBy(xpath = "//input[@id='input-lastname']")
	WebElement txtLastName;

	@FindBy(xpath = "//input[@id='input-email']")
	WebElement txtEmail;

	@FindBy(xpath = "//input[@id='input-telephone']")
	WebElement txtPhone;

	@FindBy(xpath = "//input[@id='input-password']")
	WebElement txtPassword;

	@FindBy(xpath = "//input[@id='input-confirm']")
	WebElement txtConfirmPassword;

	@FindBy(xpath = "//input[@name='agree']")
	WebElement chkPrivacyPlicy;

	@FindBy(xpath = "//input[@value='Continue']")
	WebElement btnContinue;

	@FindBy(xpath = "//h1[normalize-space()='Your Account Has Been Created!']")
	WebElement msgConfirmation;

	public void setFirstName(String fName) {
		txtFirstName.sendKeys(fName);
	}

	public void setLastName(String lName) {
		txtLastName.sendKeys(lName);
	}

	public void setEmail(String email) {
		txtEmail.sendKeys(email);
	}

	public void setPhone(String phone) {
		txtPhone.sendKeys(phone);
	}

	public void setPassword(String password) {
		txtPassword.sendKeys(password);
	}

	public void setConfirmPassword(String confirmPassword) {
		txtConfirmPassword.sendKeys(confirmPassword);
	}

	public void clickAgree() {
		js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView()", chkPrivacyPlicy);
		js.executeScript("arguments[0].click()", chkPrivacyPlicy);
	}

	public void clickContinue() {
		js.executeScript("arguments[0].click()", btnContinue);
	}

	public String getConfirmationMsg() {
		try {
			return msgConfirmation.getText();
		} catch (Exception e) {
			return (e.getMessage());
		}
	}
}
