<html>
<head>
    <title>Current Rentals</title>
</head>
<body>
    <h1>Current Rentals</h1>
    <ul>
	<#list rentals as rental>
	<li>${rental.movie.title} (ends ${rental.period.endDate})</li>
	</#list>
	</ul>
</body>
</html>