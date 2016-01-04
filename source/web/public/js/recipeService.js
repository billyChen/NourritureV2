angular.module('recipeService', [])
.factory('recipeFactory', function($http) {

	var url = 'https://nourritureapi-v2.herokuapp.com';
	var recipeFactory = {};

	/*recipeFactory.addRecipe = function(Recipe) {
		return $http.get(url + '/addRecipes');
	};*/

	recipeFactory.all = function() {
		return $http.get(url + '/listRecipes');
	};

	return recipeFactory;
});