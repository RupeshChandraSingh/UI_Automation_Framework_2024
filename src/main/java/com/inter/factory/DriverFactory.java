package com.inter.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;

import com.inter.exception.FrameworkException;

public class DriverFactory {

	public static String highlight;
	public WebDriver driver;
	private Properties prop;
	private FileInputStream ip;
	private OptionsManager optionsManager;
	

	private static ThreadLocal<WebDriver> tLocal = new ThreadLocal<WebDriver>();

	public WebDriver initDriver(Properties prop) {
		optionsManager = new OptionsManager(prop);
		String browserName = prop.getProperty("browser");
		highlight = prop.getProperty("highlight");
		switch (browserName.toLowerCase().trim()) {
		case "chrome":
//			driver = new ChromeDriver(optionsManager.getChromeOptions());
			tLocal.set(new ChromeDriver(optionsManager.getChromeOptions()));
			break;
		case "firefox":
//			driver = new FirefoxDriver(optionsManager.getFirefoxOptions());
			tLocal.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
			break;
		case "edge":
//			driver = new EdgeDriver(optionsManager.getEdgeOptions());
			tLocal.set(new EdgeDriver(optionsManager.getEdgeOptions()));
			break;

		default:
			System.out.println("Please provide valid browser name.");
		}

		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().get(prop.getProperty("url"));
		return getDriver();
	}

	private synchronized static WebDriver getDriver() {
		return tLocal.get();
	}

	/**
	 * this method is reading the properties from the .properties file
	 * 
	 * @return
	 */
	public Properties initProp() {

		// mvn clean install -Denv="qa"
		// mvn clean install

		prop = new Properties();

		String envName = System.getProperty("env");
		System.out.println("Running test cases on Env: " + envName);

		try {
			if (envName == null) {
				System.out.println("no env is passed....Running tests on QA env...");
				ip = new FileInputStream("./src/test/resources/config/config.properties");
			} else {
				switch (envName.toLowerCase().trim()) {
				case "qa":
					ip = new FileInputStream("./src/test/resources/config/config.properties");
					break;
				case "stage":
					ip = new FileInputStream("./src/test/resources/config/stageconfig.properties");
					break;
				case "dev":
					ip = new FileInputStream("./src/test/resources/config/devconfig.properties");
					break;
				case "prod":
					ip = new FileInputStream("./src/test/resources/config/prodconfig.properties");
					break;

				default:
					System.out.println("....Wrong env is passed....No need to run the test cases....");
					throw new FrameworkException("WRONG ENV IS PASSED...");
				// break;
				}

			}
		} catch (FileNotFoundException e) {

		}

		try {
			prop.load(ip);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return prop;
	}

	/**
	 * take screenshot
	 */
	public static String getScreenshot() {
		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
		String path = System.getProperty("user.dir") + "/screenshot/" + System.currentTimeMillis() + ".png";
		File destination = new File(path);
		try {
			FileHandler.copy(srcFile, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}
}
