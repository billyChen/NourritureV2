/*angular.module('myApp', [])
		.controller('mainController', function() {
			var vm = this;
			/*var vm.mydata = [];
			var url = 'https://nourritureapi.herokuapp.com';

		 $http.get(url)
			 .then(function(result) {
			 console.log(result);
			 vm.mydata = result.data;*
			 vm.message = "salut";
			 console.log(vm.message);
		 //});
});*/


angular.module('myApp', [])
	.controller('mainController', function($http) {

		var vm = this;

		$http.get('https://nourritureapi.herokuapp.com/listUsers')
			.then(function(data) {
				vm.users = data.users;
				console.log(data.users);
			});
		vm.message = "salut";
	});


/*angular.module('routerApp', ['routerRoutes', 'userService'])

.controller('mainController', function() {
	var vm = this;

	vm.message = "hello";

})

.controller('homeController', function() {
	var vm = this;

	vm.message = "This is the home page!";
})

.controller('addRecipeController', function() {
	var vm = this;

	vm.message = "add Recipe page";
})

.controller('popularController', function() {
	var vm = this;

	vm.message = "Popular Page";
})

/*.controller('newsController', function() {
	var vm = this;

	vm.message = "News Page";
})*

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

.controller('createUserController', function() {
	var vm = this;

	vm.message = "Create user page";
});*/



/*angular.module('myApp', ['userService'])
	// create a controller and inject the User factory
	.controller('userController', function(User) {
	 	var vm = this;
	  	vm.mydata = [];


	  	// get all the stuff
		User.all()
	   
	    // promise object
		.success(function(data) {
		
		console.log(data);
		// bind the data to a controller variable 
		// this comes from the stuffService 
		
		//vm.message = data;
		vm.mydata = data;
	});
});*/
