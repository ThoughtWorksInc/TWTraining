<html>
<head>
    <title>Login</title>
</head>
<body>
    <h1>Login</h1>
	<form id="login">
	    <p>Select user:</p>
		<select class="customer" name="customerName">
	    	<#list customers as customer>
	    	<option value="${customer.name}">${customer.name}</option>
	    	</#list>
		</select>
    	<input type="submit" value="login" />
	</form>
</body>
</html>