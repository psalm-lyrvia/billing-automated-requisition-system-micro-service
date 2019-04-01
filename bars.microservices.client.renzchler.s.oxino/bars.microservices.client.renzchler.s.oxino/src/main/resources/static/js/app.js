/**
 *
 */
var myApp = angular.module('myApp', []);

var recordApp = angular.module('recordApp',[]);

recordApp.controller('RecordController', ['$scope', function($scope){
	//$scope.hey = "";


	console.log(JSON.parse(angular.element("#response").val()));

	var json = JSON.parse(angular.element("#response").val());
	var values = Object.values(json);
	console.log(values);

	$scope.records = [{}];
	$scope.msg = "";
	if (values.length > 0) {
		var count = 0;
		for(var x of values[1]){

			$scope.records[count] = {
				billingCycle: x["billingCycle"],
				startDate: x["strSDate"],
				endDate: x["strEDate"],
				amount: x["amount"],
				customerFirstName: x["customerFirstName"],
				customerLastName: x["customerLastName"],
			}
			count++;
		}

	}
	$scope.msg = values[0];
	console.log($scope.msg);
	console.log($scope.records);

}]);


myApp.controller('BarsController', [ '$scope', '$http', function($scope, $http) {

	$scope.removeNinja = function(ninja){
		var removeNinja = $scope.ninjas.indexOf(ninja);
		$scope.ninjas.splice(removeNinja, 1);
	}

	$scope.addNinja = function(){
		$scope.ninjas.push({
			name: $scope.newninja.name,
			belt: $scope.newninja.belt,
			rate: parseInt($scope.newninja.rate),
			available:true
		});

		$scope.newninja.name = "";
		$scope.newninja.belt = "";
		$scope.newninja.rate = "";
	}



	$scope.message = "test";
	$scope.ninjas = [ {
		name : "yosi",
		belt : "blue",
		rate : 3000,
		available: false,
		thumb: "https://www.gravatar.com/avatar/000dsad0000000000?d=monsterid"

	}, {
		name : "dante gulapa",
		belt : "red",
		rate : 4000,
		available: true,
		thumb: "https://www.gravatar.com/avatar/0000000aaa000000000?d=monsterid"
	}, {
		name : "naruto",
		belt : "yellow",
		rate : 3800,
		available: false,
		thumb: "https://www.gravatar.com/avatar/0000000000222000000?d=monsterid"
	}, {
		name : "luffy",
		belt : "green",
		rate : 2500,
		available: true,
		thumb: 	"https://www.gravatar.com/avatar/0000000000001110000?d=monsterid"
	}, ];

	 $http({
	      method: 'GET',
	      url: 'https://jsonplaceholder.typicode.com/posts'
	   }).then(function (response){
		   console.log(response)
	   },function (error){
		   console.log(error)
	   });

} ]);


