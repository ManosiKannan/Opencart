package testBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.GeckoDriverInfo;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import com.assertthat.selenium_shutterbug.core.Capture;
import com.assertthat.selenium_shutterbug.core.Shutterbug;

public class BaseClass {
	public static WebDriver driver;
	public String appURL;
	public String browser;
	public Logger logger; // Log4j
	public Properties prop;
	public FileInputStream fis;
	public static JavascriptExecutor js;

	@Parameters({ "operatingSystem", "browser" })
	@BeforeClass(groups = { "Sanity", "DataDriven", "Master" })
	public void setup(String operatingSystem, String browser) throws IOException {
		// Initialize the logger for this class
		logger = LogManager.getLogger(this.getClass());
		// Create a Properties object to load configuration settings
		prop = new Properties();
		// Load configuration properties from a file located at the specified path
		fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\config.properties");
		prop.load(fis);
		// browser = prop.getProperty("browser");
		// Retrieve the application URL from the properties file
		appURL = prop.getProperty("appURL");
		if (prop.getProperty("execution_env").equalsIgnoreCase("remote")) {
			DesiredCapabilities capabilities = new DesiredCapabilities();

			if (operatingSystem.equalsIgnoreCase("windows")) {
				capabilities.setPlatform(Platform.WIN11);
			} else if (operatingSystem.equalsIgnoreCase("linux")) {
				capabilities.setPlatform(Platform.LINUX);
			} else if (operatingSystem.equalsIgnoreCase("mac")) {
				capabilities.setPlatform(Platform.MAC);
			} else {
				System.out.println("No matching Operating System");
				return;
			}

			switch (browser.toLowerCase()) {
			// Initialize ChromeDriver if "chrome" is specified
			case "chrome":
				capabilities.setBrowserName("chrome");
				break;
			// Initialize EdgeDriver if "edge" is specified
			case "edge":
				capabilities.setBrowserName("edge");
				break;
			case "firefox":
				capabilities.setBrowserName("firefox");
				break;
			default:
				// Print an error message and exit if an unsupported browser is specified
				System.out.println("Specified browser is not found");
				return;
			}
			driver = new RemoteWebDriver(new URL("http://192.168.0.25:4444/wd/hub"), capabilities);
		}

		if (prop.getProperty("execution_env").equalsIgnoreCase("local")) {
			// Switch statement to select the browser driver based on the input string
			switch (browser.toLowerCase()) {
			// Initialize ChromeDriver if "chrome" is specified
			case "chrome":
				driver = new ChromeDriver();
				break;
			// Initialize EdgeDriver if "edge" is specified
			case "edge":
				driver = new EdgeDriver();
				break;
			case "firefox":
				driver = new FirefoxDriver();
				break;
			default:
				// Print an error message and exit if an unsupported browser is specified
				System.out.println("Specified browser is not found");
				return;
			}
		}
		// Delete all cookies to ensure a clean state before starting the tests
		driver.manage().deleteAllCookies();
		// Set an implicit wait time of 5 seconds for elements to be located
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		// Navigate to the application URL retrieved from the properties file
		driver.get(appURL);
		// Maximize the browser window
		driver.manage().window().maximize();
	}

	/**
	 * This method is executed after all tests in the class have completed. It
	 * closes the browser and ends the WebDriver session to clean up resources.
	 */
	@AfterClass(groups = { "Sanity", "DataDriven", "Master" })
	public void tearDown() {
		driver.quit();
	}

	/**
	 * Generates a random string of 5 alphabetic characters.
	 * 
	 * @return A random alphabetic string.
	 */
	public String randomString() {
		String generatedString = RandomStringUtils.randomAlphabetic(5);
		return generatedString;
	}

	public String randomNumeric() {
		String generatedNumeric = RandomStringUtils.randomNumeric(10);
		return generatedNumeric;
	}

	/**
	 * Generates a random alphanumeric string with 6 alphabetic characters followed
	 * by 3 numeric characters, separated by an "@" symbol.
	 *
	 * @return A random alphanumeric string in the format: "letters@numbers".
	 */
	public String randomAlphaNumeric() {
		String generatedString = RandomStringUtils.randomAlphabetic(6);
		String generatedNumber = RandomStringUtils.randomNumeric(3);
		return (generatedString + "@" + generatedNumber);
	}

	public String captureScreenshot(String name) {
		// Create a timestamp using the current date and time in the format
		// "yyyyMMddhhmmss"
		String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());

		// Cast the WebDriver instance to TakesScreenshot to access the screenshot
		// //functionality

		TakesScreenshot ts = (TakesScreenshot) driver;

		// Capture the screenshot and store it in a temporary file (srcFile)
		File srcFile = ts.getScreenshotAs(OutputType.FILE);

		// Define the target file path using the project directory, a subfolder named
		// "screenshots", // the provided name, and the timestamp to ensure uniqueness

		String targetFilePath = System.getProperty("user.dir") + "\\screenshots\\" + name + "_" + timeStamp + ".png";

		/*
		 * // Capture the full page screenshot using Shutterbug and save it to the
		 * specified path Shutterbug.shootPage(driver, Capture.FULL, true).withName(name
		 * + "_" + timeStamp) .save(System.getProperty("user.dir") + "\\screenshots");
		 */

		// Create a new file object for the target file path
		File targetFile = new File(targetFilePath);

		// Rename the temporary screenshot file to the target file path
		srcFile.renameTo(targetFile);

		// Return the path of the saved screenshot file
		return targetFilePath;
	}

}
