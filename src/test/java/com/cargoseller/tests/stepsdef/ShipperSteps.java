package com.cargoseller.tests.stepsdef;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gherkin.lexer.Id;
import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.Before;

import com.cargoseller.tests.tools.*;
import com.cargoseller.tests.browser.*;
import com.cargoseller.tests.enums.Username;
import com.cargoseller.tests.pageobjects.*;

public class ShipperSteps extends CargoSellerTest {
	
	private Header header;
	private Homepage homepage;
	private MyProfile myProfile;
	private PostAProject postAProject;
	private Freelancers freelancers;
	
	@Before
	public void setup() {
		Browser.init();
		testID = Tools.generateRandomNumbers(5);
	}

	@Given("^the shipper is logged in$")
	public void the_shipper_is_logged_in() throws Exception {
		header = new Header();
		homepage = header.login(Username.SHIPPER);
		homepage.isAt();
	}

	@Given("^on the Post a project page$")
	public void on_the_Post_a_project_page() throws Exception {
		postAProject = header.goToPostAProject();
		postAProject.isAt();
	}

	@When("^he input \"([^\"]*)\" as a category$")
	public void he_input_as_a_category(String category) {
		this.category = postAProject.selectCategory(category);
	}

	@When("^random test title as a title$")
	public void random_test_title_as_a_title() {
		this.title = postAProject.inputTitle();
	}

	@When("^random description$")
	public void random_description() {
		this.description = postAProject.addDescription();
	}

	@When("^attaches a file$")
	public void attaches_a_file() {
		postAProject.addAttachment();
	}

	@When("^selects \"([^\"]*)\" as a skill$")
	public void selects_as_a_skill(String skill) {
		this.skill = postAProject.selectSkill(skill);
	}

	@When("^enters a budget of \"([^\"]*)\"$")
	public void enters_a_budget_of(double budget) {
		this.budget = postAProject.enterBudget(budget);
	}

	@When("^location is select as \"([^\"]*)\"$")
	public void location_is_select_as(String country) {
		this.country = postAProject.selectCountry(country);
	}
	
	@When("^submits the project$")
	public void submits_the_project() {
		postAProject.submitProject();
	}

	@Then("^ensure the project is posted successfully with all parameters correctly set$")
	public void ensure_the_project_is_posted_successfully_with_all_parameters_correctly_set() throws Throwable {
		postAProject.assertProjectPosted(this.category, this.title, this.description, this.skill, this.budget);
	}

	@Given("^on MyProfile page$")
	public void on_MyProfile_page() throws Exception {
		myProfile = header.goToMyProfile();
	}
	
	@Given("^on the Credits page$")
	public void on_the_Credits_page() {
		myProfile.goToCredits();
	}

	@When("^he clicks on Recharge$")
	public void he_clicks_on_Recharge() throws Throwable {
		myProfile.clickRecharge();
	}

	@When("^selects package \"([^\"]*)\"$")
	public void selects_package(String packageToReload) {
		this.packageToReload = myProfile.selectPackage(packageToReload);
	}

	@When("^makes the payment with Cash$")
	public void makes_the_payment_with_Cash() {
		myProfile.makePaymentWithCash();
	}
	
	@When("^makes the payment with BrainTree$")
	public void makes_the_payment_with_BrainTree() {
		myProfile.makePaymentWithBrainTree();
	}

	@Then("^ensure the payment is completed$")
	public void ensure_the_payment_is_completed() {
		myProfile.assertPaymentComplete();
	}

	@When("^he clicks on Withdraw$")
	public void he_clicks_on_Withdraw() throws Throwable {
		myProfile.clickWithdraw();
	}

	@When("^makes the withdrawal with amount of \"([^\"]*)\"$")
	public void makes_the_withdrawal_with_amount_of(double amount) throws Exception {
		this.withdrawalAmount = myProfile.makeWithdrawal(amount);
	}

	@Then("^ensure the withdrawal request is made$")
	public void ensure_the_withdrawal_request_is_made() throws Throwable {
		myProfile.assertWithdrawalMade();
	}

	@When("^he posts a Cargo with \"([^\"]*)\" title$")
	public void he_posts_a_Cargo_with_title(String arg1) throws Throwable {
		
	}
	
	@Given("^on the Freelancers page$")
	public void on_the_Freelancers_page() throws Exception {
		freelancers = header.goToFreelancers();
	}
	
	@When("^he selects carrier \"([^\"]*)\"$")
	public void he_selects_carrier(String carrier) {
		freelancers.selectCarrier(carrier);
	}
	
	@When("^invites him to bid for cargo posted under number \"([^\"]*)\"$")
	public void invites_him_to_bid_for_cargo_posted_under_number(int cargoNumber) throws Exception {
		freelancers.inviteToBidForCargoNumber(cargoNumber);
	}
	
	@Then("^ensure the invite has been sent$")
	public void ensure_the_invite_has_been_sent() throws Throwable {
		freelancers.assertInviteToCarrierSent();
	}

	@After
	public void teardown() {
		Browser.instance.quit();
	}
}
