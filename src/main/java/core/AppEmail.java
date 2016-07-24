package core;

import java.io.FileInputStream;
import java.util.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
// import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class AppEmail {

	// show element
	public static void showElement(WebDriver driver, WebElement element) {
		final String originalBackgroundColor = element.getCssValue("backgroundColor");
		final JavascriptExecutor myJS = ((JavascriptExecutor) driver); // java
																		// script
		myJS.executeScript("arguments[0].style.backgroundColor = 'rgb(0,200,0)'", element);

		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
		}
		myJS.executeScript("arguments[0].style.backgroundColor = '" + originalBackgroundColor + "'", element);
	}

	public String[][] appDrStoD() {
		String[][] resTemp = null; // out
		String[] arr = { "email", "error" };
		String propertiesFile = "./src/test/resources/DataProvider_Email.properties";
		Properties properties = new Properties();

		try {
			properties.load(new FileInputStream(propertiesFile));
			int n = properties.stringPropertyNames().size() / arr.length;
			resTemp = new String[n][arr.length]; // out
			for (int i = 0; i < n; i++) {
				resTemp[i][0] = properties.getProperty(arr[0] + (i + 1));
				resTemp[i][1] = properties.getProperty(arr[1] + (i + 1));
			} // for (int i = 0; i < n; i++) {

		} catch (Exception e) {
			properties = null;
			System.out.println("asdasd " + e);
		} // try {

		WebDriver driver = new FirefoxDriver();
		// WebDriver dr = new HtmlUnitDriver();
		String url = "http://www.ebates.com/";
		driver.manage().window().maximize();

		do {
			driver.get(url);
			try {
				if (driver.findElement(By.id("disable")).isEnabled())
					break;
			} catch (Exception e) {
				// System.out.println("don't see element");
			}
		} while (true);

		// String[][] id = { { "id", "sendKeys", "email_address" }, { "id",
		// "sendKeys", "password" }, { "id", "click", "next-button" } };
		WebElement element = null;

		// start
		for (int i = 0; i < resTemp.length; i++) {
			try {
				// email_address
				element = driver.findElement(By.id("email_address"));
				element.clear();
				// show element
				showElement(driver, element);
				element.sendKeys(resTemp[i][0]);

				// next-button
				element = driver.findElement(By.id("next-button"));
				element.click();
				// show element
				showElement(driver, element);

				// id_error = id('email-field')/x:div
				element = driver.findElement(By.id("id('email-field')/x:div"));
				// show element
				showElement(driver, element);
				resTemp[i][0] = element.getText();
			} catch (Exception e) {
				resTemp[i][0] = i + " element can't found";
			}
		}

		driver.quit();
		return resTemp;
	}

	public static void main(String[] args) {
		AppEmail app = new AppEmail();
		String[][] res = app.appDrStoD();
		for (int i = 0; i < res.length; i++) {
			System.out.println(i + " : " + res[i][0] + " - " + res[i][1]);
		}
	}
}