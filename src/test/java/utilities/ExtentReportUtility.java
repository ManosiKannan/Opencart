package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import testBase.BaseClass;

public class ExtentReportUtility implements ITestListener {

	public ExtentSparkReporter sparkReporter;
	public ExtentReports extent;
	public ExtentTest test;
	public String reportName;

	@Override
	public void onStart(ITestContext context) {

		String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		reportName = "Test-Report_" + timeStamp + ".html";

		// Specify location of the report
		sparkReporter = new ExtentSparkReporter(".\\reports\\" + reportName);

		sparkReporter.config().setDocumentTitle("Opencart Automation Report");
		sparkReporter.config().setReportName("Opencart Automation Testing");
		sparkReporter.config().setTheme(Theme.DARK);

		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);
		extent.setSystemInfo("Application", "Opencart");
		extent.setSystemInfo("Module", "Admin");
		extent.setSystemInfo("Sub Module", "Customers");
		extent.setSystemInfo("User Name", System.getProperty("user.name"));
		extent.setSystemInfo("Environment", "QA");

		/*
		 * String os = context.getCurrentXmlTest().getParameter("os");
		 * extent.setSystemInfo("Operating System", os);
		 * 
		 * String browser = context.getCurrentXmlTest().getParameter("browser");
		 * extent.setSystemInfo("Browser", browser);
		 */

		List<String> includedGroups = context.getCurrentXmlTest().getIncludedGroups();
		if (!includedGroups.isEmpty()) {
			extent.setSystemInfo("Groups", includedGroups.toString());
		}
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		// Create a test entry in the extent report with the name of the test class
		test = extent.createTest(result.getTestClass().getName());
		// Assign the groups to which this test method belongs, so they appear in the
		// report
		test.assignCategory(result.getMethod().getGroups());
		// Log the success status with the test name in the report
		test.log(Status.PASS, result.getName() + " got successfully executed");
		try {
			// Capture a screenshot with the test name and add it to the report
			String imgPath = new BaseClass().captureScreenshot(result.getName());
			test.addScreenCaptureFromPath(imgPath);
		} catch (Exception e) {
			// Print any exception that occurs during the screenshot capture
			e.printStackTrace();
		}
	}

	@Override
	public void onTestFailure(ITestResult result) {
		// Create a test entry in the extent report with the name of the test class
		test = extent.createTest(result.getTestClass().getName());

		// Assign the groups to which this test method belongs, so they appear in the
		// report
		test.assignCategory(result.getMethod().getGroups());

		// Log the failure status with the test name in the report
		test.log(Status.FAIL, result.getName() + " got failed");

		// Log additional information about the failure, specifically the exception
		// message
		test.log(Status.INFO, result.getThrowable().getMessage());

		try {
			// Capture a screenshot with the test name and add it to the report
			String imgPath = new BaseClass().captureScreenshot(result.getName());
			test.addScreenCaptureFromPath(imgPath);
		} catch (Exception e) {
			// Print any exception that occurs during the screenshot capture
			e.printStackTrace();
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		test = extent.createTest(result.getTestClass().getName());
		// To display groups in report
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.SKIP, result.getName() + " got skipped");
		test.log(Status.INFO, result.getThrowable().getMessage());
	}

	@Override
	public void onFinish(ITestContext context) {
		extent.flush();
		String pathOfExtentReport = System.getProperty("user.dir") + "\\reports\\" + reportName;
		File extentReport = new File(pathOfExtentReport);

		try {
			Desktop.getDesktop().browse(extentReport.toURI());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		/*
		 * try { URL url = new URL("file:////" + System.getProperty("user.dir") +
		 * "\\reports\\" + reportName); ImageHtmlEmail email = new ImageHtmlEmail();
		 * email.setDataSourceResolver(new DataSourceUrlResolver(url));
		 * email.setHostName("smtp.google.com"); email.setSmtpPort(465);
		 * email.setAuthenticator(new DefaultAuthenticator("manosikannan@gmail.com",
		 * "password")); email.setSSLOnConnect(true);
		 * email.setFrom("manosikannan@gmail.com"); email.setSubject("Test Results");
		 * email.setMsg("Please find attached Report.....");
		 * email.addTo("manosikannan@gmail.com"); email.attach(url, "extent report",
		 * "please check report..."); email.send(); } catch (Exception e) {
		 * 
		 * e.printStackTrace(); }
		 */
	}

}
