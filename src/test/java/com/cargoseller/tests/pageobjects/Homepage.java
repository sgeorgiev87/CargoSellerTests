package com.cargoseller.tests.pageobjects;

import org.openqa.selenium.By;

import com.cargoseller.tests.browser.*;
import com.google.common.base.Enums;

public class Homepage {
	
	
	public Homepage() throws Exception {
		if (!isAt().equals("CargoSeller | Online cargo auction platform")) {
			throw new Exception("Not on the homepage");
		}
	}
	
	public String isAt() throws InterruptedException {
		Thread.sleep(2000);
		return Browser.instance.getTitle();
	}
	
	
	public void goTo() {
		Browser.instance.get("http://www.cargoseller.com");
	}
	
}
