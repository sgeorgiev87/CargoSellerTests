package com.cargoseller.tests.browser;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.cargoseller.tests.tools.*;

public class Browser {
	public static WebDriver instance;

	public void init() throws IOException {
		InputStream is = this.getClass().getResourceAsStream("my.properties");
		Properties p = new Properties();
		p.load(is);
		String property = p.getProperty("headless");

		if (property.equals("true")) {
			instance = new HtmlUnitDriver(true);
			Browser.instance.get("http://www.cargoseller.com");
			WaitTool.waitForElement(Browser.instance, By.id("password_protected_pass"), 10);
			Browser.instance.findElement(By.id("password_protected_pass"))
					.sendKeys(Credentials.getPassword("sitepass"));
			Browser.instance.findElement(By.id("wp-submit")).click();
		} else {
			System.setProperty("webdriver.chrome.driver", "D:\\QA things\\webdrivers\\chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
			options.setBinary("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe");
			options.addArguments("--start-maximized");
			instance = new ChromeDriver(options);
			Browser.instance.get("http://www.cargoseller.com");
			WaitTool.waitForElement(Browser.instance, By.id("password_protected_pass"), 10);
			Browser.instance.findElement(By.id("password_protected_pass"))
					.sendKeys(Credentials.getPassword("sitepass"));
			Browser.instance.findElement(By.id("wp-submit")).click();
		}
	}

	public static void quit() {
		instance.quit();
	}
}
