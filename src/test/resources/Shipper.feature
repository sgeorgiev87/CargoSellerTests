Feature: Testing shipper's functionalities
		As a shipper to test all functionalities provided on the website
		
Scenario: Post a cargo as a shipper
	Given the shipper is logged in
	And on the Post a project page
	When he input "Chemicals" as a category
	And random test title as a title
	And random description
	And attaches a file
	And selects "ADR" as a skill
	And enters a budget of "60"
	And location is select as "BG"
	And submits the project
	Then ensure the project is posted successfully with all parameters correctly set
	
Scenario: Recharge the balance with the Cash method
	Given the shipper is logged in
	And on MyProfile page
	And on the Credits page
	When he clicks on Recharge
	And selects package "Testplan"
	And makes the payment with Cash
	Then ensure the payment is completed
	
Scenario: Recharge the balance with the BrainTree method
	Given the shipper is logged in
	And on MyProfile page
	And on the Credits page
	When he clicks on Recharge
	And selects package "100 credits"
	And makes the payment with BrainTree
	Then ensure the payment is completed
	
Scenario: Withdraw money from the balance
	Given the shipper is logged in
	And on MyProfile page
	And on the Credits page
	When he clicks on Withdraw
	And makes the withdrawal with amount of "60"
	Then ensure the withdrawal request is made
	
Scenario: Invite a Carrier to bid for your project
	Given the shipper is logged in
	And on the Freelancers page
	When he selects carrier "Carrier Test"
	And invites him to bid for cargo posted under number "2"
	Then ensure the invite has been sent
	
	
	
	
