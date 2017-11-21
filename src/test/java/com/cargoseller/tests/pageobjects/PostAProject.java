package com.cargoseller.tests.pageobjects;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

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
	
	public String[] selectMainCategories(String category1, String category2) {
		String array[] = { category1, category2 };
		WaitTool.waitForElement(Browser.instance,
				By.xpath("//div[@id='fre-post-project']//div[@data-title='Household']"), 4);
		Browser.instance.findElement(By.xpath("//div[@id='fre-post-project']//div[@data-title='" + category1 + "']"))
				.click();
		Browser.instance.findElement(By.xpath("//div[@id='fre-post-project']//div[@data-title='" + category2 + "']"))
				.click();
		return array;
	}
	public String selectSubCategory(String subCategory) {
		Select select = new Select(Browser.instance.findElement(By.name("category_lvl3_id[0]")));
		select.selectByVisibleText(subCategory);
		return subCategory;
	}

	public String[] inputDimensionsWeightAndUnit(String lengthUnit, String weightUnit) throws Exception {
		if (lengthUnit.equals("cm") && weightUnit.equals("kg")) {
			String array[] = { Tools.generateRandomNumbers(3), Tools.generateRandomNumbers(3),
					Tools.generateRandomNumbers(3), Tools.generateRandomNumbers(2), Tools.generateRandomNumbers(1) };
			Select length = new Select(Browser.instance.findElement(By.name("package_metrics[0]")));
			length.selectByVisibleText("cm");
			Select weight = new Select(Browser.instance.findElement(By.name("weight_metrics[0]")));
			weight.selectByVisibleText("kg");
			Browser.instance.findElement(By.name("lenght[0]")).sendKeys(array[0]);
			Browser.instance.findElement(By.name("height[0]")).sendKeys(array[1]);
			Browser.instance.findElement(By.name("width[0]")).sendKeys(array[2]);
			Browser.instance.findElement(By.name("weight[0]")).sendKeys(array[3]);
			Browser.instance.findElement(By.name("units[0]")).sendKeys(array[4]);
			Browser.instance.findElement(By.xpath("//button[@class='btn btn-info to-step-3']")).click();
			array[0] = array[0] + lengthUnit;
			array[1] = array[1] + lengthUnit;
			array[2] = array[2] + lengthUnit;
			array[3] = array[3] + weightUnit;
			return array;
		} else if (lengthUnit.equals("in") && weightUnit.equals("lbs")) {
			String array[] = { Tools.generateRandomNumbers(3), Tools.generateRandomNumbers(3),
					Tools.generateRandomNumbers(3), Tools.generateRandomNumbers(2), Tools.generateRandomNumbers(1) };
			Select length = new Select(Browser.instance.findElement(By.name("package_metrics[0]")));
			length.selectByVisibleText("in");
			Select weight = new Select(Browser.instance.findElement(By.name("weight_metrics[0]")));
			weight.selectByVisibleText("lbs");
			Browser.instance.findElement(By.name("lenght[0]")).sendKeys(array[0]);
			Browser.instance.findElement(By.name("height[0]")).sendKeys(array[1]);
			Browser.instance.findElement(By.name("width[0]")).sendKeys(array[2]);
			Browser.instance.findElement(By.name("weight[0]")).sendKeys(array[3]);
			Browser.instance.findElement(By.name("units[0]")).sendKeys(array[4]);
			Browser.instance.findElement(By.xpath("//button[@class='btn btn-info to-step-3']")).click();
			array[0] = array[0] + lengthUnit;
			array[1] = array[1] + lengthUnit;
			array[2] = array[2] + lengthUnit;
			array[3] = array[3] + weightUnit;
			return array;
		} else {
			throw new Exception("The length and weight metrics do not match");
		}
	}

	public String selectPickupLocation(String pickupLocation) {
		Browser.instance.findElement(By.id("pick-up-location")).sendKeys(pickupLocation);
		return pickupLocation;
	}

	public String selectDeliveryLocation(String deliveryLocation) {
		Browser.instance.findElement(By.id("delivery-location")).sendKeys(deliveryLocation);
		return deliveryLocation;
	}

	public void selectPickupDateAsToday() {
		DateFormat dateFormat = new SimpleDateFormat("d");

		Date date = new Date();

		String today = dateFormat.format(date);

		Browser.instance.findElement(By.id("pickup_date")).click();
		WebElement calendar = Browser.instance.findElement(
				By.xpath("//div[@class='bootstrap-datetimepicker-widget dropdown-menu usetwentyfour top']"));
		List<WebElement> dates = calendar.findElements(By.tagName("td"));

		for (WebElement cell : dates) {
			if (cell.getText().equals(today)) {
				cell.click();
				break;
			}
		}
	}

	public String randomProjectDescription() {
		String projectDescription = "Test Project " + Tools.generateRandomLetters(15);
		Browser.instance.switchTo().frame("post_content_ifr");
		Browser.instance.findElement(By.id("tinymce")).sendKeys(projectDescription);
		Browser.instance.switchTo().defaultContent();
		Browser.instance.findElement(By.id("to-step-4")).click();
		return projectDescription;
	}

	public double enterBudget(double budget) {
		Browser.instance.findElement(By.id("project-budget")).sendKeys(Double.toString(budget));
		return budget;
	}

	public void submitProject() {
		Browser.instance.findElement(By.xpath("//div[@class='fre-post-project-btn']/button")).click();
		;
	}

	public void assertProjectPosted(String projectTitle, String categories,
			String length, String height, String width, String weight, String units, double projectBudget,
			String projectDescription) {
		Assert.assertEquals("The project title doesn't match", projectTitle,
				Browser.instance.findElement(By.xpath("//h1[@class='project-detail-title']")).getText());
		
		Browser.instance.findElement(By.xpath("//div[@id='item-category-0']/a")).click();
		WaitTool.waitForElement(Browser.instance, By.xpath("//div[@class='col-md-12']//dd"), 5);
		
		Assert.assertEquals("One or more of the categories do not match", categories,
				Browser.instance.findElement(By.xpath("//div[@class='col-md-12']//dd")).getText());
		WaitTool.waitForElement(Browser.instance, By.xpath("//span[text()='Lenght:']/following-sibling::span"), 5);
		Assert.assertEquals("The length doesn't match", length ,
				Browser.instance.findElement(By.xpath("//span[text()='Lenght:']/following-sibling::span")).getText());
		Assert.assertEquals("The height doesn't match", height,
				Browser.instance.findElement(By.xpath("//span[text()='Height:']/following-sibling::span")).getText());
		Assert.assertEquals("The width doesn't match", width,
				Browser.instance.findElement(By.xpath("//span[text()='Width:']/following-sibling::span")).getText());
		Assert.assertEquals("The weight doesn't match", weight,
				Browser.instance.findElement(By.xpath("//span[text()='Weight:']/following-sibling::span")).getText());
		Assert.assertEquals("The units doesn't match", units,
				Browser.instance.findElement(By.xpath("//span[text()='Units:']/following-sibling::span")).getText());
		Assert.assertEquals("The budget doesn't match", Double.toString(projectBudget) + "0€",
				Browser.instance.findElement(By.xpath("//span[text()='Budget']/following-sibling::span")).getText());
		Assert.assertEquals("The project description doesn't match", projectDescription,
				Browser.instance.findElement(By.xpath("//div[@class='project-detail-desc']/p[2]")).getText());
	}

}
