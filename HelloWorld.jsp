<!DOCTYPE html>
<html ng-app>
<head>
	<title>Hello World, AngularJS - ViralPatel.net</title>
	<script type="text/javascript"
		src="//ajax.googleapis.com/ajax/libs/angularjs/1.0.7/angular.min.js"></script>
</head>
<body>
	
	Write some text in textbox:
	<input type="text" ng-model="sometext" />

	<h1 style="color: green">Hello {{ sometext }}</h1>
	
	<h4>Uppercase: {{ sometext | uppercase }}</h4>
    <h4>Lowercase: {{ sometext | lowercase }}</h4>
	
	
</body>
</html>