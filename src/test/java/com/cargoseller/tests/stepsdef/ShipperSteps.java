package com.cargoseller.tests.stepsdef;

import java.io.IOException;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
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
	Browser browser = new Browser();
	
	@Before
	public void setup() throws IOException {
		browser.init();
		testID = Tools.generateTestID();
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
		this.projectCategory = postAProject.selectCategory(category);
	}

	@When("^random test title as a title$")
	public void random_test_title_as_a_title() {
		this.projectTitle = postAProject.inputTitle();
	}

	@When("^random description$")
	public void random_description() {
		this.projectDescription = postAProject.addDescription();
	}

	@When("^attaches a file$")
	public void attaches_a_file() {
		postAProject.addAttachment();
	}

	@When("^selects \"([^\"]*)\" as a skill$")
	public void selects_as_a_skill(String skill) {
		this.projectSkill = postAProject.selectSkill(skill);
	}

	@When("^enters a budget of \"([^\"]*)\"$")
	public void enters_a_budget_of(double budget) {
		this.projectBudget = postAProject.enterBudget(budget);
	}

	@When("^location is select as \"([^\"]*)\"$")
	public void location_is_select_as(String country) {
		this.projectCountry = postAProject.selectCountry(country);
	}
	
	@When("^submits the project$")
	public void submits_the_project() {
		postAProject.submitProject();
	}

	@Then("^ensure the project is posted successfully with all parameters correctly set$")
	public void ensure_the_project_is_posted_successfully_with_all_parameters_correctly_set() throws Throwable {
		postAProject.assertProjectPosted(this.projectCategory, this.projectTitle, this.projectDescription, this.projectSkill, this.projectBudget);
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
	public void makes_the_payment_with_Cash() throws InterruptedException {
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
		DatabaseUtil.saveTestResults(testID, username, password, projectTitle, projectDescription, projectCategory, projectSkill, projectBudget, projectCountry, withdrawalAmount, packageToReload);
		Browser.instance.quit();
	}
}
