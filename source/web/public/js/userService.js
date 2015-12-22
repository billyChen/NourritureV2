
//var url = 'https://nourritureapi.herokuapp.com';

angular.module('userService', [])
.factory('User', function($http) {

var url = 'https://nourritureapi.herokuapp.com';
	// create the object
	var userFactory = {};
	
	//var tab = [];

		// get all users
	userFactory.all = function() {
		return $http.get(url + '/listUsers');
	};


	// get a single user
	userFactory.get = function(id) {
		return $http.get(url + '/showUsers/' + id);
	};


	//create user
	userFactory.get = function(userData) {
		return $http.post(url + '/addUsers/', userData);
	};

	//update user
	/*userFactory.get = function(id, userData) {
		return $http.put(url + )
	};*/


	//delete user
	userFactory.get = function(id) {
		return $http.get(url + '/deleteUsers/' + id);
	};


	return userFactory;
});

