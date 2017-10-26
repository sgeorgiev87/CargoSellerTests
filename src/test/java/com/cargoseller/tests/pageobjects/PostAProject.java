package com.cargoseller.tests.pageobjects;

import org.junit.Assert;
import org.openqa.selenium.By;

import com.cargoseller.tests.browser.Browser;
import com.cargoseller.tests.tools.Tools;
import com.cargoseller.tests.tools.WaitTool;

public class PostAProject {
	
	public PostAProject() throws Exception {
		if (!isAt().equals("submit-project | CargoSeller")) {
			throw new Exception("Not on the Post A Project page");
		}
	}
	
	public String isAt() {
		return Browser.instance.getTitle();
	}
	
	public String selectCategory(String category) {
		Browser.instance.findElement(By.id("project_category_chosen")).click();
		WaitTool.waitForElement(Browser.instance, By.xpath("//div[@id='project_category_chosen']//li[text()='" + category + "']"), 3);
		Browser.instance.findElement(By.xpath("//div[@id='project_category_chosen']//li[text()='"+ category +"']")).click();
		return category;
	}

	public String inputTitle() {
		String title = "Test Title " + Tools.generateRandomLetters(5);
		Browser.instance.findElement(By.id("fre-project-title")).sendKeys(title);
		return title;
	}

	public String addDescription() {
		String description = " Test Description " + Tools.generateRandomLetters(25);
		Browser.instance.findElement(By.id("post_content_ifr")).sendKeys(description);
		return description;
	}
	
	public void addAttachment() {
		Browser.instance.findElement(By.xpath("//input[starts-with(@id, 'html5_')]")).sendKeys("C:\\Users\\Stefan\\Desktop\\CSTests\\1.jpg");
	}
	
	public String selectSkill(String skill) {
		Browser.instance.findElement(By.id("skill_chosen")).click();
		WaitTool.waitForElement(Browser.instance, By.xpath("//div[@id='skill_chosen']/div/ul/li[text()='" + skill + "']"), 3);
		Browser.instance.findElement(By.xpath("//div[@id='skill_chosen']/div/ul/li[text()='" + skill + "']")).click();
		return skill;
	}
	
	public double enterBudget (double budget) {
		Browser.instance.findElement(By.id("project-budget")).sendKeys(Double.toString(budget));
		return budget;
	}
	
	public String selectCountry (String country) {
		Browser.instance.findElement(By.id("country_chosen")).click();
		WaitTool.waitForElement(Browser.instance, By.xpath("//div[@id='country_chosen']/div/ul/li[text()='" + country + "']"), 3);
		Browser.instance.findElement(By.xpath("//div[@id='country_chosen']/div/ul/li[text()='" + country + "']")).click();
		return country;
	}
	
	public void submitProject() {
		Browser.instance.findElement(By.xpath("//div[@class='fre-post-project-btn']/button")).click();;
	}
	
	public void assertProjectPosted(String category, String title, String description, String skill, double budget) {
		WaitTool.waitForElement(Browser.instance, By.xpath("//div[contains(@class,'project_category')]//a/span"), 5);
		Assert.assertEquals("The category doesn't match", category, Browser.instance.findElement(By.xpath("//div[contains(@class,'project_category')]//a/span")).getText());
		Assert.assertEquals("The title doesn't match", title, Browser.instance.findElement(By.xpath("//h1[@class='content-title-project-item']")).getText());
		Assert.assertEquals("The description doesn't match", description, " " + Browser.instance.findElement(By.cssSelector("p")).getText());
		Assert.assertEquals("The skill doesn't match", skill, Browser.instance.findElement(By.xpath("//div[contains(@class, 'list-skill')]//a/span")).getText());
		Assert.assertEquals("The budget doesn't match", Double.toString(budget) + "0€", Browser.instance.findElement(By.xpath("//span[@class='budget-project-item']")).getText());
	}
	
}
