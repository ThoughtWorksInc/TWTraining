Feature: Shopping cart
	In order to rent movies
	As a customer
	I want to see the movies I have chosen

@web
Scenario: View my Cart
	Given I have added the movie "Avatar"
	When I view my Cart
	Then I should see the movie "Avatar" with a 1 day rental

@web
Scenario: Check out
	Given I have added the movie "Avatar"
	When I navigate to my Cart
	And I check out
	Then I should see my statement

@web
Scenario: View History
	Given I have rented the movie "Avatar"
	When I navigate to my History
	Then I should see 1 history item
