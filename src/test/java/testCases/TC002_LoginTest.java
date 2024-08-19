package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountHomePage;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import testBase.BaseClass;
import utilities.DataProviders;

public class TC002_LoginTest extends BaseClass {

	// getting data provider from different class
	@Test(dataProvider = "LoginData", dataProviderClass = DataProviders.class, groups={"DataDriven","Master"})
	public void verify_login(String email, String password, String expectedResult) {
		logger.info("******* Starting TC002_LoginTest *******");
		try {
			// Home Page
			HomePage homePage = new HomePage(driver);
			homePage.clickMyAccount();
			homePage.clickLogin();

			// Login Page
			LoginPage loginPage = new LoginPage(driver);
			loginPage.setEmailAddress(email);
			loginPage.setPassword(password);
			loginPage.clickLogin();

			// Account Home Page
			AccountHomePage accountHomePage = new AccountHomePage(driver);
			boolean targetPage = accountHomePage.isMyAccountDisplayed();

			if (expectedResult.equalsIgnoreCase("valid")) {
				if (targetPage == true) {
					accountHomePage.clickLogout();
					Assert.assertTrue(true);
				} else {
					Assert.assertTrue(false);
				}
			} else if (expectedResult.equalsIgnoreCase("Invalid")) {
				if (targetPage == true) {
					accountHomePage.clickLogout();
					Assert.assertTrue(false);
				} else {
					Assert.assertTrue(true);
				}
			}
		} catch (Exception e) {
			Assert.fail();
		}
		logger.info("******* Finished TC002_LoginTest *******");
	}
}
