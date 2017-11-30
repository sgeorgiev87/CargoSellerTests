package com.cargoseller.tests.stepsdef;

import java.io.IOException;
import java.util.Map;

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
	private MyCredit myCredit;
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

	@Given("^has his current balances recorded$")
	public void has_his_current_balances_recorded() throws Exception {
		this.availableBalance = myCredit.recordAllBalancesofaShipper().get("Available Credit");
	}

	@When("^he selects \"([^\"]*)\" and \"([^\"]*)\" as main categories$")
	public void he_selects_and_as_main_categories(String category1, String category2) {
		Map<String, String> mainCategories = postAProject.selectMainCategories(category1, category2);
		this.mainCategory1 = mainCategories.get("Main Category 1");
		this.mainCategory2 = mainCategories.get("Main Category 2");
	}

	@When("^selects \"([^\"]*)\" as a sub-category$")
	public void selects_as_a_subcategory(String subCategory) throws Throwable {
		this.subCategory = postAProject.selectSubCategory(subCategory);
	}

	@When("^inputs random dimensions, weight and units in \"([^\"]*)\" and \"([^\"]*)\"$")
	public void inputs_random_dimensions_weight_and_units_in(String lengthUnit, String weightUnit) throws Throwable {
		Map<String, String> projectMetrics = postAProject.inputDimensionsWeightAndUnit(lengthUnit, weightUnit);
		this.length = projectMetrics.get("length");
		this.height = projectMetrics.get("height");
		this.width = projectMetrics.get("width");
		this.weight = projectMetrics.get("weight");
		this.units = projectMetrics.get("units");
	}

	@When("^selects pick-up location as \"([^\"]*)\"$")
	public void selects_pickup_location_as(String pickupLocation) throws Throwable {
		this.pickUpLocation = postAProject.selectPickupLocation(pickupLocation);
	}

	@When("^selects delivery location as \"([^\"]*)\"$")
	public void selects_delivery_location_as(String deliveryLocation) throws Throwable {
		this.deliveryLocation = postAProject.selectDeliveryLocation(deliveryLocation);
	}

	@When("^selects pick-up date today with random description$")
	public void selects_pickup_date_today_with_random_description() throws Throwable {
		postAProject.selectPickupDateAsToday();
		this.projectDescription = postAProject.randomProjectDescription();
	}

	@When("^enters a budget of \"([^\"]*)\"$")
	public void enters_a_budget_of(double budget) {
		this.projectBudget = postAProject.enterBudget(budget);
	}

	@When("^submits the project$")
	public void submits_the_project() {
		postAProject.submitProject();
	}

	@Then("^ensure the project is posted successfully with all parameters correctly set$")
	public void ensure_the_project_is_posted_successfully_with_all_parameters_correctly_set() throws Throwable {
		String projectTitle = this.pickUpLocation + " -> " + this.deliveryLocation;
		String categories = this.mainCategory1 + ", " + this.mainCategory2 + "," + this.subCategory;
		postAProject.assertProjectPosted(projectTitle, categories, this.length, this.height, this.width, this.weight,
				this.units, this.projectBudget, this.projectDescription);
	}

	@Given("^on MyProfile page$")
	public void on_MyProfile_page() throws Exception {
		myProfile = header.goToMyProfile();
	}

	@Given("^on the Credits page$")
	public void on_the_Credits_page() throws Exception {
		myCredit = header.goToMyCredit();
	}

	@When("^he clicks on Recharge$")
	public void he_clicks_on_Recharge() throws Throwable {
		myCredit.deposit();
	}

	@When("^selects package \"([^\"]*)\"$")
	public void selects_package(String packageToReload) {
		this.packageToReload = myCredit.selectPackage(packageToReload);
	}

	@When("^makes the payment with Cash$")
	public void makes_the_payment_with_Cash() throws InterruptedException {
		myCredit.makePaymentWithCash();
	}

	@When("^makes the payment with BrainTree$")
	public void makes_the_payment_with_BrainTree() {
		myCredit.makePaymentWithBrainTree();
	}

	@Then("^ensure the payment is completed$")
	public void ensure_the_payment_is_completed() {
		myCredit.assertPaymentComplete();
	}

	@When("^he clicks on Withdraw$")
	public void he_clicks_on_Withdraw() throws Throwable {
		myCredit.clickWithdraw();
	}

	@When("^makes the withdrawal with amount of \"([^\"]*)\"$")
	public void makes_the_withdrawal_with_amount_of(double amount) throws Exception {
		this.withdrawalAmount = myCredit.makeWithdrawal(amount);
	}

	@Then("^ensure the withdrawal request is made$")
	public void ensure_the_withdrawal_request_is_made() throws Throwable {
		myCredit.assertWithdrawalMade();
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

	@Then("^the cargo budget has been taken from his account balance$")
	public void the_cargo_budget_has_been_taken_from_his_account_balance() throws Exception {
		header.goToMyCredit();
		myCredit.assertCargoBudgetTakenFromBalance(this.projectBudget);
	}

	@Then("^the transaction with type \"([^\"]*)\", amount \"([^\"]*)\" and payment \"([^\"]*)\" is shown in the Transactions tab$")
	public void the_transaction_with_type_amount_and_payment_is_shown_in_the_transactions_tab(String type,
			double amount, String paymentMethod) throws Exception {
		if (Browser.instance.getTitle().equals("my-credit | CargoSeller")) {
			myCredit.goToTransactions();
			myCredit.assertTransactionIsShownInTransactionTab(type, amount, paymentMethod);
		} else {
			header.goToMyCredit();
			myCredit.goToTransactions();
			myCredit.assertTransactionIsShownInTransactionTab(type, amount, paymentMethod);
		}
	}

	@Then("^the money \"([^\"]*)\" are added to pending balance$")
	public void the_money_are_added_to_pending_balance(double amount) {
		myCredit.assertMoneyAddedToPendingCredit(amount);
		
	}

	@Then("^the money \"([^\"]*)\" are added to actual balance$")
	public void the_money_are_added_to_actual_balance(double amount) throws Exception {
		myCredit.assertMoneyAddedToAvailableCredit(amount);
	}

	@After
	public void teardown() {
		DatabaseUtil.saveTestResults(testID, username, projectTitle, mainCategory1, mainCategory2, subCategory, length,
				height, width, weight, units, pickUpLocation, deliveryLocation, projectDescription, projectBudget,
				withdrawalAmount, packageToReload);
		Browser.instance.quit();
	}
}
