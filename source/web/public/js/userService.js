angular.module('userService', [])
.factory('userFactory', function($http) {

	var url = 'https://nourritureapi-v2.herokuapp.com';
	
	// create the object
	var userFactory = {};

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
	// userFactory.get = function(id, userData) {
	// 	return $http.put(url + )
	// };


	//delete user
	userFactory.get = function(id) {
		return $http.get(url + '/deleteUsers/' + id);
	};


	return userFactory;
});

/*angular.module('recipeService', [])
.factory('recipeFactory', function($http) {

	var url = 'https://nourritureapi-v2.herokuapp.com';
	var recipeFactory = {};

	/*recipeFactory.addRecipe = function(Recipe) {
		return $http.get(url + '/addRecipes');
	};*

	recipeFactory.all = function() {
		return $http.get(url + '/listRecipes');
	};

	return recipeFactory;
});*/