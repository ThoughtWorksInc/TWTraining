<html>
<head>
    <title>Rent a Movie</title>
    <link href="styles/wizard.css" rel="stylesheet" type="text/css"></link>
    <script type="text/javascript" src="js/wizard.js"></script>
    <script type="text/javascript">
	$(function() {
	    $('#wizard').wizard({
	    	validate: function(idx, elem) {
	    		switch(idx) {
	    			case 0:
	    				if ($('input[name=movieNames]:checked').size() == 0) {
	    					alert('Please select at least one movie');
	    					return false;
	    				}
	    				return true;
	    			case 2:
	    				if ($('input[name=customerName]:checked').size() != 1) {
	    					alert('Please select a customer');
	    					return false;
	    				}
	    				return true;
	    			default:
	    				return true;
	    		}
	    	}
	    });
	}); 
  </script>
</head>
<body>
    <h1>Rent a Movie</h1>
	<form id="wizard" class="wiz-container" action="rentMovies">
		<ul class="wiz-list">
			<li><a href="#wizard-1">
				<h2>Now Showing!</h2>
				<small>Pick a movie</small>
			</a></li>
			<li><a href="#wizard-2"><h2>How long for?</h2>
				<small>Tell us how many days you want to rent for</small>
			</a></li>
		</ul>
		<div class="wiz-body">
			<div id="wizard-1">
				<div class="wiz-content movielist">					
			    	<#list movies as movie>
			    		<div class="movie">
			    			<p><input type="checkbox" name="movieNames" value="${movie.title}"/> ${movie.title}</p>
			    		</div>
			    	</#list>
				</div>
				<div class="wiz-nav">
					<input class="back btn" type="button" value="< Prev" />
					<input class="next btn" type="button" value="Next >" />
				</div>
			</div>
			<div id="wizard-2">
				<div class="wiz-content">					
					<p>Number of days:
						<select name="rentalDuration">
							<option value="1">1</option>
							<option value="2">2</option>
							<option value="3">3</option>
							<option value="4">4</option>
							<option value="5">5</option>
							<option value="6">6</option>
							<option value="7">7</option>
						</select>
					</p>
				</div>
				<div class="wiz-nav">
					<input class="back btn" type="button" value="< Prev" />
					<input class="done btn" type="submit" value="Done" />
				</div>
			</div>
		</div>
	</form>
</body>
</html>