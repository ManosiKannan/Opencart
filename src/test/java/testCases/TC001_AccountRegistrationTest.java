package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.AccountRegisterPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass {
	@Test(groups = { "Sanity", "Master" })
	public void verify_account_registration() {
		logger.info("***** Starting TC001_AccountRegistrationTest *****");
		try {
			HomePage homePage = new HomePage(driver);
			homePage.clickMyAccount();
			logger.info("Clicked on MyAccount Link");
			homePage.clickRegister();
			logger.info("Clicked on Register Link");
			AccountRegisterPage accountRegPage = new AccountRegisterPage(driver);
			accountRegPage.setFirstName(randomString().toUpperCase());
			accountRegPage.setLastName(randomString().toUpperCase());
			accountRegPage.setEmail(randomString() + "@gmail.com");
			accountRegPage.setPhone(randomNumeric());
			String pwd = randomAlphaNumeric();
			accountRegPage.setPassword(pwd);
			accountRegPage.setConfirmPassword(pwd);
			accountRegPage.clickAgree();
			accountRegPage.clickContinue();
			logger.info("Validating confirmation message");
			String actualConfirmMsg = accountRegPage.getConfirmationMsg();
			if (actualConfirmMsg.equals("Your Account Has Been Created!")) {
				Assert.assertTrue(true);
			} else {
				logger.error("Test Failed");
				Assert.assertTrue(false);
			}
		} catch (Exception e) {
			Assert.fail();
		}
		logger.info("***** Finished TC001_AccountRegistrationTest *****");
	}
}
