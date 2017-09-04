package com.cargoseller.tests.stepsdef;

import org.junit.Assert;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import com.cargoseller.tests.enums.Username;
import com.cargoseller.tests.pageobjects.*;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.PendingException;
import cucumber.api.java.After;

public class CarrierSteps extends CargoSellerTest{
	
	private Header header;
	private Homepage homepage;
	private Projects projects;
	
	@Given("^the carrier is logged in$")
	public void the_carrier_is_logged_in() throws Exception {
		header = new Header();
		homepage = header.login(Username.CARRIER);
		homepage.isAt();
	}
	
	@Given("^on the projects page$")
	public void on_the_projects_page() throws Exception {
		projects = header.goToProjects();
	}

	@When("^he selects available project number \"([^\"]*)\"$")
	public void he_selects_available_project_number(int projectNumber) {
		projects.selectProject(projectNumber);
	}

	@When("^bids on it with \"([^\"]*)\" as Budget, \"([^\"]*)\" as Deadline days and Random Notes$")
	public void bids_on_it_with_as_Budget_as_Deadline_days_and_Random_Notes(int budget, int deadlineDays) throws Throwable {
		projects.bidForProject(budget, deadlineDays);
	}

	@Then("^ensure the bid is successful$")
	public void ensure_the_bid_is_successful()  {
	   projects.assertBidSuccessful();
	}

	@When("^he clicks on Cancel$")
	public void he_clicks_on_Cancel() throws Throwable {
	   projects.cancelBid();
	}

	@Then("^ensure the bid is cancelled$")
	public void ensure_the_bid_is_cancelled() throws Throwable {
	    projects.assertBidCancelled();
	}
	
	@When("^he searches for cargo with title \"([^\"]*)\"$")
	public void when_he_searches_for_cargo_with_title(String cargoTitle) throws InterruptedException {
		projects.searchForProjectByTitle(cargoTitle);
	}
	
	@Then("^ensure the cargo is shown in the results$")
	public void ensure_the_cargo_is_shown_in_the_results_() {
	    projects.assertCargoFoundByTitle();
	}
}
