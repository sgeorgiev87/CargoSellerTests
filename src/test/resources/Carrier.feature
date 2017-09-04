Feature: Testing carrier's functionalities
		As a carrier to test all functionalities provided on the website
		
Scenario: Make a bid for a project
	Given the carrier is logged in
	And on the projects page
	When he selects available project number "1"
	And bids on it with "60" as Budget, "2" as Deadline days and Random Notes
	Then ensure the bid is successful
	
Scenario: Cancel a bid for a project
	Given the carrier is logged in
	And on the projects page
	When he selects available project number "1"
	And he clicks on Cancel
	Then ensure the bid is cancelled

Scenario: Search for the last posted Cargo
	Given the carrier is logged in
	And on the projects page
	When he searches for cargo with title "Test title"
	Then ensure the cargo is shown in the results
	

