package com.cargoseller.tests.pageobjects;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;

import com.cargoseller.tests.browser.Browser;
import com.cargoseller.tests.tools.Tools;
import com.cargoseller.tests.tools.WaitTool;

public class Projects {
	private double budget;
	private int deadlineDays;
	private String cargoTitle;
	
	public Projects() throws Exception {
		if (!isAt().equals("Projects | CargoSeller")) {
			throw new Exception("Not on the Projects page");
		}
	}
	
	public String isAt() {
		return Browser.instance.getTitle();
	}

	public void selectProject(int projectNumber) {
		Browser.instance.findElement(By.xpath("//li[@class='project-item'][" + Integer.toString(projectNumber) + "]" + "//a")).click();
	}

	
	public void bidForProject(double budget, int deadlineDays) throws Exception {
		if (Tools.isElementPresent(By.linkText("Bid"))) {
			this.budget = budget;
			this.deadlineDays = deadlineDays;
			Browser.instance.findElement(By.linkText("Bid")).click();
			Browser.instance.findElement(By.id("bid_budget")).sendKeys(Double.toString(budget));
			Browser.instance.findElement(By.id("bid_time")).sendKeys(Integer.toString(deadlineDays));
			Browser.instance.findElement(By.id("bid_content")).sendKeys(Tools.generateRandomLetters(15));
			Browser.instance.findElement(By.xpath("//form[@id='bid_form']//button[@type='submit']")).click();
		} else {
			throw new Exception("You have already made a bid for this project.");
		}
	}
	
	public void cancelBid() throws Exception {
		if (Tools.isElementPresent(By.xpath("//a[@title='Cancel this bidding']"))) {
			Browser.instance.findElement(By.xpath("//a[@title='Cancel this bidding']")).click();
		} else {
			throw new Exception("You have never made a bid for this project.");
		}
	}
	
	public void assertBidSuccessful() {
		WaitTool.waitForElement(Browser.instance, By.xpath("//span[@class='number-price']"), 8);		
		Assert.assertEquals("The budget is different", Double.toString(this.budget) + "0€", Browser.instance.findElement(By.xpath("//span[@class='number-price']")).getText());
		Assert.assertEquals("The days are different", "in " + Integer.toString(this.deadlineDays) + " days", Browser.instance.findElement(By.xpath("//span[@class='number-day']")).getText());
		
	}
	
	public void assertBidCancelled() throws InterruptedException {
		Thread.sleep(5000);
		Assert.assertEquals("The bid was not cancelled", "Bid", Browser.instance.findElement(By.linkText("Bid")).getText());
	}
	
	public void searchForProjectByTitle(String cargoTitle) throws InterruptedException {
		this.cargoTitle = cargoTitle;
		Browser.instance.findElement(By.id("s")).sendKeys(cargoTitle);
		Thread.sleep(3000);
	}
	
	public void assertCargoFoundByTitle() {
		Assert.assertTrue(Browser.instance.getPageSource().contains(this.cargoTitle));
		
	}
}
