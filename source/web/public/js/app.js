

angular.module('routerApp', ['routerRoutes', 'userService', 'recipeService'])

.controller('mainController', function() {
	var vm = this;

	vm.message = "hello";

})

.controller('homeController', function() {
	var vm = this;

	vm.message = "This is the home page!";
})

.controller('popularController', function($scope, $http, recipeFactory) {
	var vm = this;

	vm.message = "All recipes";

	$scope.cfdump = "";

	var getRecipe = function(res) {
		var i = 0;
		var out = "";
		//$scope.list = [];

		for(i = 0; i < res.length; i++) {
			out += 'name : ' + res[i].name + ' type : ' + res[i].type + '  ingredients : ' + res[i].ingredients + '  time : ' + res[i].time + '  description : ' + res[i].description + '	';
			/*if (res[i].type == "dessert") {
				//res[i].type = "TEST";
				//console.log("YESSS");
			}*/
		}
		vm.message = out;
		console.log(out);
	};

	$scope.showRecipes = function() {
		recipeFactory.all()
		.success(function(data) {
			vm.str = data;
			//console.log(typeof(data));
			getRecipe(data);
		})
	}
})


.controller('aboutController', function() {
	var vm = this;

	vm.message = "About page";
})

.controller('infosController', function() {
	var vm = this;

	vm.message = "Infos page";
})

.controller('loginController', function() {
	var vm = this;

	vm.message = "Login Page";
})

.controller('createUserController', function($scope, $http, userFactory) {
	var vm = this;

	$scope.cfdump = "";

	$scope.test = function(Username, Password, Gender, Allergy) {
		var request = $http({
			method: "post",
			url: "https://nourritureapi-v2.herokuapp.com/addUsers",
			data: {
				username: this.Username,
				password: this.Password,
				gender: this.Gender,
				allergens: this.Allergy
			}
		}).success(function() {
			$location.path('/logIn');
			alert("User profil saved !");
		})
	};
})


.controller('addRecipeController', function($scope, $http, recipeFactory) {

	var vm = this;
	vm.message = "Save your recipe!";
	$scope.cfdump = "";

	$scope.add = function(Name, Type, Ingredients, Time, Description) {

		var request = $http({
			method: "post",
			url: "https://nourritureapi-v2.herokuapp.com/addRecipes",
			data: {
				name: this.Name,
				type: this.Type,
				ingredients: this.Ingredients,
				time: this.Time,
				description: this.Description
			}
		}).success(function() {
			vm.message = "Recipe saved";
		})
	};

})

.controller('userController', function($scope, $http/*, userFactory*/) {
	var vm = this;

	$scope.cfdump = "";

		/*$scope.display = function() {
			userFactory.all()
			.success(function(data) {
				vm.str = data;
				console.log(data);
			})
		}*/
});
