package com.ShoppersStack.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.PublicKey;
import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

public class BaseClass {
	protected static Properties prop;
	protected static WebDriver driver;

	@BeforeSuite
	public void loadConfig() throws IOException {
		// load the configuration File
		prop = new Properties();
		FileInputStream fis = new FileInputStream("src/main/resources/config.properties");
		prop.load(fis);

	}

	@BeforeMethod
	public void Setup() throws Exception {
		System.out.println("Setting up WebDrive for :" + this.getClass().getSimpleName());
		launchBrowser();
		configureBrowser();
		staticwait(2);

	}

	// intialize the WebDriver based on browser Defind in config.properties
	private void launchBrowser() {

		String browser = prop.getProperty("browser");

		if (browser.equalsIgnoreCase("chrome")) {
			driver = new ChromeDriver();
		} else if (browser.equalsIgnoreCase("firefox")) {
			driver = new FirefoxDriver();
		} else if (browser.equalsIgnoreCase("edge")) {
			driver = new EdgeDriver();
		} else {
			throw new IllegalArgumentException("Browser Not Supported :" + browser);
		}
		
		
		
		
	}

//configure browser setting Like maximize and navigate
	private void configureBrowser() {
		// implicit wait
		int implicitwait = Integer.parseInt(prop.getProperty("implicitwait"));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitwait));

		// maximize the diver
		driver.manage().window().maximize();
		
		
		

		// Naviagte url
		try {
			driver.get(prop.getProperty("url"));
		} catch (Exception e) {
			System.out.println("fail to navigate to the url :" + e.getMessage());
		}

	}

	@AfterMethod
	public void tearDown() {
		if (driver != null) {
			try {
				driver.quit();
			} catch (Exception e) {
				System.out.println("unable to quit the driver" + e.getMessage());

			}
		}
	}
	
	//Getter method for prop
	public static Properties getProp() {
		return prop;
		
	}

	// Driver Getter method

	public WebDriver getDriver() {
		return driver;

	}

	// Driver Setter Method

	public void setDirver(WebDriver drievr) {
		this.driver = driver;

	}

	// static wait for pause
	public void staticwait(int seconds) {
		LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(seconds));

	}
}
