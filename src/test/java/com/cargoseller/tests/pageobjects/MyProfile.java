package com.cargoseller.tests.pageobjects;

import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;

import com.cargoseller.tests.browser.Browser;
import com.cargoseller.tests.tools.*;

public class MyProfile {

	public MyProfile() throws Exception {
		if (!isAt().equals("profile | CargoSeller")) {
			throw new Exception("Not on the MyProfile page");
		}
	}

	private String isAt() {
		return Browser.instance.getTitle();
	}

	
}
