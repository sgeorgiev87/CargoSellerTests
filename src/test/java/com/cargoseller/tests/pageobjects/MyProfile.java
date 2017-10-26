package com.cargoseller.tests.pageobjects;

import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;

import com.cargoseller.tests.browser.Browser;
import com.cargoseller.tests.tools.*;

public class MyProfile {

	private double availableWithdrawalAmount;
	private double withdrawalAmount;

	public MyProfile() throws Exception {
		if (!isAt().equals("profile | CargoSeller")) {
			throw new Exception("Not on the MyProfile page");
		}
	}

	private String isAt() {
		return Browser.instance.getTitle();
	}

	public void goToCredits() {
		WaitTool.waitForElement(Browser.instance, By.linkText("Credits"), 2);
		Browser.instance.findElement(By.linkText("Credits")).click();
	}

	public void clickRecharge() {
		WaitTool.waitForElement(Browser.instance, By.xpath("//div[@class='button-functions ']/button"), 3);
		Browser.instance.findElement(By.xpath("//div[@class='button-functions ']/button")).click();
	}

	public String selectPackage(String packageToReload) {
		WaitTool.waitForElement(Browser.instance, By.xpath("//li[@data-title='testPlan']/label[@class='fre-radio']"), 5);
		if (packageToReload.toLowerCase().equals("testplan")) {
			Browser.instance.findElement(By.xpath("//li[@data-title='testPlan']/label[@class='fre-radio']")).click();
			Browser.instance.findElement(By.xpath("//input[@value='Next Step']")).click();
		}
		if (packageToReload.toLowerCase().equals("100 credits")) {
			Browser.instance.findElement(By.xpath("//li[@data-title='100 Credits']/label[@class='fre-radio']")).click();
			Browser.instance.findElement(By.xpath("//input[@value='Next Step']")).click();
		}
		if (packageToReload.toLowerCase().equals("500 credits")) {
			Browser.instance.findElement(By.xpath("//li[@data-title='500 Credits']/label[@class='fre-radio']")).click();
			Browser.instance.findElement(By.xpath("//input[@value='Next Step']")).click();
		}
		return packageToReload;
	}

	public void makePaymentWithCash() throws InterruptedException {
		WaitTool.waitForElementPresent(Browser.instance, By.xpath("//div[@id='fre-payment-cash']//a"), 10);
		Browser.instance.findElement(By.xpath("//a[@data-type='cash']")).click();
		Browser.instance.findElement(By.linkText("Make Payment")).click();
	}

	public void makePaymentWithBrainTree() {
		WaitTool.waitForElement(Browser.instance, By.xpath("//a[@data-type='braintree']"), 5);
		Browser.instance.findElement(By.xpath("//a[@data-type='braintree']")).click();
		Browser.instance.switchTo().frame("braintree-dropin-frame");
		Browser.instance.findElement(By.id("credit-card-number")).sendKeys("4111111111111111");
		Browser.instance.findElement(By.id("expiration")).sendKeys("1019");
		Browser.instance.findElement(By.id("cvv")).sendKeys("123");
		Browser.instance.switchTo().defaultContent();
		Browser.instance.findElement(By.xpath("//button[@type='submit']")).click();
	}

	public void assertPaymentComplete() {
		String expectedMessage = "Payment Complete";
		String actualMessage = Browser.instance.findElement(By.xpath("//div[@class='step-payment-complete']/h2"))
				.getText();
		Assert.assertEquals("The payment was not complete - check again", expectedMessage, actualMessage);

	}

	public void clickWithdraw() throws InterruptedException {
		WaitTool.waitForElement(Browser.instance, By.xpath("//div[@class='button-functions ']/button[2]"), 3);
		Browser.instance.findElement(By.xpath("//div[@class='button-functions ']/button[2]")).click();
		Thread.sleep(1000);
	}

	/**
	 * A method to request withdrawal, giving the withdrawal amount. The payment
	 * information is automatically input as "Test Withdrawal Information + 30
	 * random symbols"
	 * 
	 * @param amount
	 *            - the amount you want to withdraw
	 * @return the amount you had input
	 * @throws Exception 
	 */
	public double makeWithdrawal(double amount) throws Exception {
		this.withdrawalAmount = amount;
		Thread.sleep(2000);
		String str = Browser.instance.findElement(By.xpath("//div[@class='available']/span[2]")).getText();
		String availableWithdrawalAmount = str.substring(0, str.length() - 1);
		this.availableWithdrawalAmount = Double.parseDouble(availableWithdrawalAmount);
		Browser.instance.findElement(By.xpath("//input[@name='amount']")).sendKeys(Double.toString(amount));
		Browser.instance.findElement(By.name("payment_info")).click();
		if (isAlertPresent() == true) {
			Alert alert = Browser.instance.switchTo().alert();
			String alertText = alert.getText();
			if (alertText.contains("Your available credit is not enough")) {
				alert.accept();
				throw new Exception("The amount input is higher than the available in the account");
			}
			if (alertText.contains("greater than or equal to 50")) {
				alert.accept();
				throw new Exception("You must enter a withdrawal amount over 50");
			}
			return amount;
		}
		Browser.instance.findElement(By.name("payment_info"))
				.sendKeys("Test Withdrawal Information " + Tools.generateRandomLetters(30));
		Browser.instance.findElement(By.xpath("//form[@id='fre_credit_withdraw_form']//button[@type='submit']"))
				.click();
		return amount;
	}

	public boolean isAlertPresent() {
		try {
			Browser.instance.switchTo().alert();
			return true;
		} catch (NoAlertPresentException Ex) {
			return false;
		}
	}

	public void assertWithdrawalMade() throws InterruptedException {
		Thread.sleep(3000);
		double balanceToBeAvailable = availableWithdrawalAmount - withdrawalAmount;
		Assert.assertEquals("The Withdrawal was not successful",
				Browser.instance.findElement(By.xpath("//div[@class='row info-balance']//span[2]")).getText(),
				Double.toString(balanceToBeAvailable) + "0€");
	}

}
