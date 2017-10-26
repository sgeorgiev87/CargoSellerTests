package com.cargoseller.tests.pageobjects;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.cargoseller.tests.browser.Browser;
import com.cargoseller.tests.tools.WaitTool;

public class Freelancers {
	
	private int cargoNumber;
	private String cargoTitle;
	private String carrier;
	
	public Freelancers() throws Exception {
		if (!isAt().equals("Profiles | CargoSeller")) {
			throw new Exception("Not on the freelancers page");
		}
	}
	
	public String isAt() throws InterruptedException {
		Thread.sleep(1000);
		return Browser.instance.getTitle();
	}

	public void selectCarrier(String carrier) {
		this.carrier = carrier;
		Browser.instance.findElement(By.linkText(carrier)).click();
	}

	public void inviteToBidForCargoNumber(int cargoNumber) throws Exception {
		this.cargoNumber = cargoNumber;
		Browser.instance.findElement(By.linkText("Invite me to join")).click();
		WaitTool.waitForElement(Browser.instance, By.id("submit_invite"), 4);
		WebElement cargoToSelect = Browser.instance.findElement(By.xpath("//div[@id='mCSB_1_container']/div[" + Integer.toString(this.cargoNumber) + "]//label"));
		this.cargoTitle = cargoToSelect.getText();
		cargoToSelect.click();
		Browser.instance.findElement(By.xpath("//div[@class='footer-invest']/button")).click();
	}

	public void assertInviteToCarrierSent() { //this is not working - still trying to find a way to do it
		Assert.assertTrue(Browser.instance.findElement(By.xpath("div[@class='notification autohide success-bg having-adminbar']")).isDisplayed());
		
	}
	
}
