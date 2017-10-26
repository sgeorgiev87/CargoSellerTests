package com.cargoseller.tests.pageobjects;

import com.cargoseller.tests.browser.*;
import com.cargoseller.tests.tools.WaitTool;

public class Homepage {
	
	String title;
	
	public Homepage() throws Exception {
		WaitTool.waitForPageLoad(Browser.instance);
		try {
		if (!isAt().equals("CargoSeller | Online cargo auction platform")) {
			throw new Exception("Not on the homepage");
		}
		}
		catch(Exception e) {
			System.out.println(Browser.instance.getPageSource());
		}
	}
	
	public String isAt() throws InterruptedException {
		Thread.sleep(1000);
		return this.title = Browser.instance.getTitle();
	}
	
	
	public void goTo() {
		Browser.instance.get("http://testing.cargoseller.com");
	}
	
}
