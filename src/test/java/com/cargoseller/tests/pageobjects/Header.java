package com.cargoseller.tests.pageobjects;

import org.junit.Assert;
import org.openqa.selenium.By;

import com.cargoseller.tests.browser.*;
import com.cargoseller.tests.enums.Username;
import com.cargoseller.tests.tools.*;

public class Header {

	public Homepage login(Username role) throws Exception {
		Browser.instance.findElement(By.linkText("LOGIN")).click();
		Browser.instance.findElement(By.name("user_login")).sendKeys(role.getUsername());
		Browser.instance.findElement(By.name("user_pass")).sendKeys(Credentials.getPassword(role.getUsername()));
		Browser.instance.findElement(By.xpath("//div[@class='fre-input-field']/button")).click();
		return new Homepage();
	}
	
	public PostAProject goToPostAProject() throws Exception {
		Browser.instance.findElement(By.linkText("Post a Project")).click();
		return new PostAProject();
	}
	
	public MyProfile goToMyProfile() throws Exception {
		Browser.instance.findElement(By.xpath("//div[@class='fre-account-info dropdown-toggle']/span")).click();
		Browser.instance.findElement(By.xpath("(//a[contains(text(),'MY PROFILE')])[2]")).click();
		return new MyProfile();
	}
	
	public Projects goToProjects() throws Exception {
		Browser.instance.findElement(By.linkText("PROJECTS")).click();
		return new Projects();
	}

	public Freelancers goToFreelancers() throws Exception {
		Browser.instance.findElement(By.linkText("FREELANCERS")).click();
		return new Freelancers();
	}
	
}