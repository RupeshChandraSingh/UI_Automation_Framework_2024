package com.inter.base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import com.inter.factory.DriverFactory;
import com.inter.pages.AccountsPage;
import com.inter.pages.LoginPage;
import com.inter.pages.ProductInfoPage;
import com.inter.pages.SearchPage;

public class BaseTest {
	private DriverFactory driverFactory;
	protected WebDriver driver;
	protected LoginPage loginPage;
	protected AccountsPage accountsPage;
	protected SearchPage searchPage;
	protected ProductInfoPage productInfoPage;
	protected Properties prop;
	
	protected SoftAssert softAssert;
	
	@Parameters({"browser"})
	@BeforeTest
	public void setUp(String browserName) {
		

		driverFactory = new DriverFactory();
		prop = driverFactory.initProp();
		if (browserName != null) {
			prop.setProperty("browser", browserName);
//			prop.setProperty("browserversion", browserVersion);
//			prop.setProperty("testcasename", testCaseName);

		}
		driver = driverFactory.initDriver(prop);
		loginPage = new LoginPage(driver);
		accountsPage = new AccountsPage(driver);
		softAssert = new SoftAssert();
	}
	
	@AfterTest
	public void tearDown() {
		driver.quit();
	}
}
