package com.cargoseller.tests.pageobjects;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;

import com.cargoseller.tests.browser.Browser;
import com.cargoseller.tests.tools.Tools;
import com.cargoseller.tests.tools.WaitTool;

public class MyCredit {

	private double availableCredit;
	private double withdrawalAmount;
	private double pendingCredit;
	private double pendingWithdrawalAmount;
	private double totalCredit;

	public MyCredit() throws Exception {
		if (!isAt().equals("my-credit | CargoSeller")) {
			throw new Exception("Not on the MyCredit page");
		}
	}

	private String isAt() {
		return Browser.instance.getTitle();
	}

	public void deposit() {
		WaitTool.waitForElement(Browser.instance, By.xpath("//div[@class='credit-recharge-btn-wrap']/a"), 3);
		Browser.instance.findElement(By.xpath("//div[@class='credit-recharge-btn-wrap']/a")).click();
	}

	public String selectPackage(String packageToReload) {
		WaitTool.waitForElement(Browser.instance, By.xpath("//li[@data-title='testPlan']/label[@class='fre-radio']"),
				5);
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
		WaitTool.waitForElementPresent(Browser.instance, By.xpath("//a[@data-type='cash']"), 10);
		Browser.instance.findElement(By.xpath("//a[@data-type='cash']")).click();
		WaitTool.waitForElementPresent(Browser.instance, By.linkText("Make Payment"), 10);
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
		String expectedMessage = "Payment Successfully Completed";
		String actualMessage = Browser.instance.findElement(By.xpath("//div[@class='step-payment-complete']/h2"))
				.getText();
		Assert.assertEquals("The payment was not complete - check again", expectedMessage, actualMessage);
		Browser.instance.findElement(By.linkText("Click here")).click();
	}

	public void clickWithdraw() throws InterruptedException {
		WaitTool.waitForElement(Browser.instance, By.linkText("Withdraw"), 3);
		Browser.instance.findElement(By.linkText("Withdraw")).click();
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
//		Thread.sleep(2000);
//		String str = Browser.instance.findElement(By.xpath("//p[text()='Available Credit']/following-sibling::p/b"))
//				.getText();
//		String availableWithdrawalAmount = str.substring(0, str.length() - 1);
//		availableWithdrawalAmount = availableWithdrawalAmount.replaceAll(",", "");
//		this.availableWithdrawalAmount = Double.parseDouble(availableWithdrawalAmount);
		Browser.instance.findElement(By.id("amount")).sendKeys(Double.toString(amount));
		Browser.instance.findElement(By.id("payment_method_chosen")).click();
		Browser.instance.findElement(By.xpath("//div[@id='payment_method_chosen']/div/ul/li[3]")).click();
		// if (isAlertPresent() == true) {
		// Alert alert = Browser.instance.switchTo().alert();
		// String alertText = alert.getText();
		// if (alertText.contains("Your available credit is not enough")) {
		// alert.accept();
		// throw new Exception("The amount input is higher than the available in the
		// account");
		// }
		// if (alertText.contains("greater than or equal to 50")) {
		// alert.accept();
		// throw new Exception("You must enter a withdrawal amount over 50");
		// }
		// return amount;
		// }
		Browser.instance.findElement(By.name("payment_info"))
				.sendKeys("Test Withdrawal Information " + Tools.generateRandomLetters(30));
		Browser.instance.findElement(By.xpath("//button[@class='fre-normal-btn fre-btn']")).click();
		return amount;
	}

	// public boolean isAlertPresent() {
	// try {
	// Browser.instance.switchTo().alert();
	// return true;
	// } catch (NoAlertPresentException Ex) {
	// return false;
	// }
	// }

	public void assertWithdrawalMade() throws InterruptedException {
		Thread.sleep(3000);
		double balanceToBeAvailable = this.availableCredit - this.withdrawalAmount;
		String actualBalance = Browser.instance
				.findElement(By.xpath("//p[text()='Available Credit']/following-sibling::p/b")).getText();
		actualBalance = actualBalance.replaceAll(",", "");
		Assert.assertEquals("The Withdrawal was not successful", actualBalance,
				Double.toString(balanceToBeAvailable) + "0€");
	}

	public Map<String, Double> recordAllBalancesofaShipper() {
		Map<String, Double> allShipperBalances = new HashMap<>();
		String availableCredit = Browser.instance.findElement(By.xpath("//div[@class='col-sm-3 col-xs-6']//p[2]"))
				.getText();
		availableCredit = availableCredit.replaceAll(",", "").replaceAll("€", "");
		allShipperBalances.put("Available Credit", Double.parseDouble(availableCredit));
		this.availableCredit = Double.parseDouble(availableCredit);

		String currentConsumption = Browser.instance.findElement(By.xpath("//div[@class='col-sm-3 col-xs-6'][2]//p[2]"))
				.getText();
		currentConsumption = currentConsumption.replaceAll(",", "").replaceAll("€", "");
		allShipperBalances.put("Current Consumption", Double.parseDouble(currentConsumption));

		String pendingWithdrawal = Browser.instance.findElement(By.xpath("//div[@class='col-sm-3 col-xs-6'][3]//p[2]"))
				.getText();
		pendingWithdrawal = pendingWithdrawal.replaceAll(",", "").replaceAll("€", "");
		allShipperBalances.put("Pending Withdrawal", Double.parseDouble(pendingWithdrawal));
		this.pendingWithdrawalAmount = Double.parseDouble(pendingWithdrawal);

		String totalCredit = Browser.instance.findElement(By.xpath("//div[@class='col-sm-3 col-xs-6'][4]//p[2]"))
				.getText();
		totalCredit = totalCredit.replaceAll(",", "").replaceAll("€", "");
		allShipperBalances.put("Total Credit", Double.parseDouble(totalCredit));
		this.totalCredit = Double.parseDouble(totalCredit);

		if (Browser.instance.findElement(By.xpath("//div[@class='credit-recharge']")).getText()
				.contains("pending credit")) {
			String pendingCredit = Browser.instance.findElement(By.xpath("//b[@class='credit-pending-price']"))
					.getText();
			pendingCredit = pendingCredit.replaceAll(",", "").replaceAll("€", "");
			allShipperBalances.put("Pending Credit", Double.parseDouble(pendingCredit));
			this.pendingCredit = Double.parseDouble(pendingCredit);
		} else {
			allShipperBalances.put("Pending Credit", 0.0);
			this.pendingCredit = 0;
		}
		return allShipperBalances;
	}

	public Map<String, Double> recordAllBalancesofaCarrier() {
		Map<String, Double> allCarrierBalances = new HashMap<>();

		String workingProject = Browser.instance.findElement(By.xpath("//div[@class='col-sm-3 col-xs-6']//p[2]"))
				.getText();
		workingProject = workingProject.replaceAll(",", "").replaceAll("€", "");
		allCarrierBalances.put("Working Project", Double.parseDouble(workingProject));

		String availableCredit = Browser.instance.findElement(By.xpath("//div[@class='col-sm-3 col-xs-6'][2]//p[2]"))
				.getText();
		availableCredit = availableCredit.replaceAll(",", "").replaceAll("€", "");
		allCarrierBalances.put("Available Credit", Double.parseDouble(availableCredit));
		this.availableCredit = Double.parseDouble(availableCredit);

		String pendingWithdrawal = Browser.instance.findElement(By.xpath("//div[@class='col-sm-3 col-xs-6'][3]//p[2]"))
				.getText();
		pendingWithdrawal = pendingWithdrawal.replaceAll(",", "").replaceAll("€", "");
		allCarrierBalances.put("Pending Withdrawal", Double.parseDouble(pendingWithdrawal));
		this.pendingWithdrawalAmount = Double.parseDouble(pendingWithdrawal);

		String totalCredit = Browser.instance.findElement(By.xpath("//div[@class='col-sm-3 col-xs-6'][4]//p[2]"))
				.getText();
		totalCredit = totalCredit.replaceAll(",", "").replaceAll("€", "");
		allCarrierBalances.put("Total Credit", Double.parseDouble(totalCredit));
		this.totalCredit = Double.parseDouble(totalCredit);

		if (Browser.instance.findElement(By.xpath("//div[@class='credit-recharge']")).getText()
				.contains("pending credit")) {
			String pendingCredit = Browser.instance.findElement(By.xpath("//b[@class='credit-pending-price']"))
					.getText();
			pendingCredit = pendingCredit.replaceAll(",", "").replaceAll("€", "");
			allCarrierBalances.put("Pending Credit", Double.parseDouble(pendingCredit));
			this.pendingCredit = Double.parseDouble(pendingCredit);
		} else {
			allCarrierBalances.put("Pending Credit", 0.0);
			this.pendingCredit = 0;
		}
		return allCarrierBalances;

	}

	public void assertCargoBudgetTakenFromBalance(double budget) {
		double creditToBeAvailable = availableCredit - budget;
		if (creditToBeAvailable < 1000) {
			Assert.assertEquals("The available balance is different than it is supposed to",
					Double.toString(creditToBeAvailable) + "0€", Browser.instance
							.findElement(By.xpath("//p[text()='Available Credit']/following-sibling::p/b")).getText());
		} else if (creditToBeAvailable < 10000) {
			String availableBalanceUpTo10k = Double.toString(creditToBeAvailable);
			availableBalanceUpTo10k = availableBalanceUpTo10k.substring(0, 1) + ","
					+ availableBalanceUpTo10k.substring(1, availableBalanceUpTo10k.length()) + "0€";
			Assert.assertEquals("The available balance is different than it is supposed to", availableBalanceUpTo10k,
					Browser.instance.findElement(By.xpath("//p[text()='Available Credit']/following-sibling::p/b"))
							.getText());
		} else if (creditToBeAvailable < 100000) {
			String availableBalanceUpTo100k = Double.toString(creditToBeAvailable);
			availableBalanceUpTo100k = availableBalanceUpTo100k.substring(0, 2) + ","
					+ availableBalanceUpTo100k.substring(2, availableBalanceUpTo100k.length()) + "0€";
			Assert.assertEquals("The available balance is different than it is supposed to", availableBalanceUpTo100k,
					Browser.instance.findElement(By.xpath("//p[text()='Available Credit']/following-sibling::p/b"))
							.getText());
		}
	}

	public void goToTransactions() {
		Browser.instance.findElement(By.linkText("Transaction")).click();
	}

	public void assertTransactionIsShownInTransactionTab(String type, double amount, String paymentMethod) {
		Assert.assertTrue("The transaction type is different",
				Browser.instance
						.findElement(By.xpath("//div[@id='fre-credit-transaction']//div[@class='fre-table-row']/div"))
						.getText().contains(type));
		String amountToCheck = Double.toString(amount) + "0€";
		Assert.assertEquals("The amount is different", amountToCheck,
				Browser.instance
						.findElement(
								By.xpath("//div[@id='fre-credit-transaction']//div[@class='fre-table-row']/div[2]"))
						.getText());
		Assert.assertEquals("The payment method is different", paymentMethod,
				Browser.instance
						.findElement(
								By.xpath("//div[@id='fre-credit-transaction']//div[@class='fre-table-row']/div[4]"))
						.getText());
	}

	public void assertMoneyAddedToPendingCredit(double amount) {
		double pendingCreditToBeAvailable = this.pendingCredit + amount;
		if (pendingCreditToBeAvailable < 1000) {
			Assert.assertEquals("The available pending credit is different than it is supposed to",
					Double.toString(pendingCreditToBeAvailable) + "0€",
					Browser.instance.findElement(By.xpath("//b[@class='credit-pending-price']")).getText());
		} else if (pendingCreditToBeAvailable < 10000) {
			String availablePendingCreditUpTo10k = Double.toString(pendingCreditToBeAvailable);
			availablePendingCreditUpTo10k = availablePendingCreditUpTo10k.substring(0, 1) + ","
					+ availablePendingCreditUpTo10k.substring(1, availablePendingCreditUpTo10k.length()) + "0€";
			Assert.assertEquals("The available pending credit is different than it is supposed to",
					availablePendingCreditUpTo10k,
					Browser.instance.findElement(By.xpath("//b[@class='credit-pending-price']")).getText());
		} else if (pendingCreditToBeAvailable < 100000) {
			String availablePendingCreditUpTo100k = Double.toString(pendingCreditToBeAvailable);
			availablePendingCreditUpTo100k = availablePendingCreditUpTo100k.substring(0, 2) + ","
					+ availablePendingCreditUpTo100k.substring(2, availablePendingCreditUpTo100k.length()) + "0€";
			Assert.assertEquals("The available pending credit is different than it is supposed to",
					availablePendingCreditUpTo100k,
					Browser.instance.findElement(By.xpath("//b[@class='credit-pending-price']")).getText());
		}

	}

	public void assertMoneyAddedToAvailableCredit(double amount) throws Exception {
		double creditToBeAvailable = availableCredit + amount;
		if (Browser.instance.findElement(By.xpath("//div[@class='col-sm-3 col-xs-6']//p")).getText()
				.equals("Available Credit")) {
			if (creditToBeAvailable < 1000) {
				Assert.assertEquals("The available balance is different than it is supposed to",
						Double.toString(creditToBeAvailable) + "0€",
						Browser.instance.findElement(By.xpath("//div[@class='col-sm-3 col-xs-6']//p[2]")).getText());
			} else if (creditToBeAvailable < 10000) {
				String availableBalanceUpTo10k = Double.toString(creditToBeAvailable);
				availableBalanceUpTo10k = availableBalanceUpTo10k.substring(0, 1) + ","
						+ availableBalanceUpTo10k.substring(1, availableBalanceUpTo10k.length()) + "0€";
				Assert.assertEquals("The available balance is different than it is supposed to",
						availableBalanceUpTo10k,
						Browser.instance.findElement(By.xpath("//div[@class='col-sm-3 col-xs-6']//p[2]")).getText());
			} else if (creditToBeAvailable < 100000) {
				String availableBalanceUpTo100k = Double.toString(creditToBeAvailable);
				availableBalanceUpTo100k = availableBalanceUpTo100k.substring(0, 2) + ","
						+ availableBalanceUpTo100k.substring(2, availableBalanceUpTo100k.length()) + "0€";
				Assert.assertEquals("The available balance is different than it is supposed to",
						availableBalanceUpTo100k,
						Browser.instance.findElement(By.xpath("//div[@class='col-sm-3 col-xs-6']//p[2]")).getText());
			}
		} else if (Browser.instance.findElement(By.xpath("//div[@class='col-sm-3 col-xs-6'][2]//p)")).getText()
				.equals("Available Credit")) {
			if (creditToBeAvailable < 1000) {
				Assert.assertEquals("The available balance is different than it is supposed to",
						Double.toString(creditToBeAvailable) + "0€",
						Browser.instance.findElement(By.xpath("//div[@class='col-sm-3 col-xs-6'][2]//p[2]")).getText());
			} else if (creditToBeAvailable < 10000) {
				String availableBalanceUpTo10k = Double.toString(creditToBeAvailable);
				availableBalanceUpTo10k = availableBalanceUpTo10k.substring(0, 1) + ","
						+ availableBalanceUpTo10k.substring(1, availableBalanceUpTo10k.length()) + "0€";
				Assert.assertEquals("The available balance is different than it is supposed to",
						availableBalanceUpTo10k,
						Browser.instance.findElement(By.xpath("//div[@class='col-sm-3 col-xs-6'][2]//p[2]")).getText());
			} else if (creditToBeAvailable < 100000) {
				String availableBalanceUpTo100k = Double.toString(creditToBeAvailable);
				availableBalanceUpTo100k = availableBalanceUpTo100k.substring(0, 2) + ","
						+ availableBalanceUpTo100k.substring(2, availableBalanceUpTo100k.length()) + "0€";
				Assert.assertEquals("The available balance is different than it is supposed to",
						availableBalanceUpTo100k,
						Browser.instance.findElement(By.xpath("//div[@class='col-sm-3 col-xs-6'][2]//p[2]")).getText());
			}
		} else {
			throw new Exception ("No Available Credit to be checked");
		}
	}
}