Feature: Home Page
	In order to select some movies
	As a renter
	I want to see which movies are available

@web
Scenario: View available movies
	When I view the list of available movies
	Then the list includes the movie "Avatar"
	And the list includes the movie "Up in the Air"
	And the list includes the movie "Finding Nemo"

@web
Scenario: Add some movies to my shopping cart  
	When I view the list of available movies
	And I add the movie "Avatar" to my cart
	Then I see "1 item in your cart"
