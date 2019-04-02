/**
 *
 */
var myApp = angular.module('myApp', []);

var recordApp = angular.module('recordApp',[]);

recordApp.controller('RecordController', ['$scope', function($scope){

	var json = JSON.parse(angular.element("#response").val());
	var values = Object.values(json);

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

}]);

