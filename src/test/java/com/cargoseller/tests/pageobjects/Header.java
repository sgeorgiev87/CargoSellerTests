package com.cargoseller.tests.pageobjects;

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
		Browser.instance.findElement(By.xpath("//li[@class='fre-menu-employer dropdown']")).click();
		Browser.instance.findElement(By.linkText("POST A PROJECT")).click();
		return new PostAProject();
	}

	public MyProfile goToMyProfile() throws Exception {
		Browser.instance.findElement(By.xpath("//div[@class='fre-account-info dropdown-toggle']/span")).click();
		Browser.instance.findElement(By.xpath("//a[contains(text(),'MY PROFILE')]")).click();
		return new MyProfile();
	}

	public MyCredit goToMyCredit() throws Exception {
		Browser.instance.findElement(By.xpath("//div[@class='fre-account-info dropdown-toggle']/span")).click();
		Browser.instance.findElement(By.xpath("(//a[contains(text(),'My Credit')])[2]")).click();
		return new MyCredit();
	}

	public Projects goToProjectsAsCarrier() throws Exception {
		Browser.instance.findElement(By.linkText("PROJECTS")).click();
		return new Projects();
	}

	public MyProjects goToMyProjectsAsShipper() throws Exception {
		
		return new MyProjects();
	}

	public Freelancers goToFreelancers() throws Exception {
		Browser.instance.findElement(By.linkText("FREELANCERS")).click();
		return new Freelancers();
	}

}