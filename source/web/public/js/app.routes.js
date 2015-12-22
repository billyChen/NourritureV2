// inject ngRoute for all our routing needs
angular.module('routerRoutes', ['ngRoute'])
// configure our routes
.config(function($routeProvider, $locationProvider) {
    $routeProvider
        // route for the home page
        .when('/', {
            templateUrl : 'views/pages/home.html',
            controller  : 'homeController',
            controllerAs: 'home'
        })

        // route for the add recipe page
        .when('/addRecipe', {
            templateUrl : 'views/pages/addRecipe.html',
            controller  : 'addRecipeController',
            controllerAs: 'addRecipe'
        })

        // route for popular page
        .when('/popular', {
            templateUrl : 'views/pages/popular.html',
            controller  : 'popularController',
            controllerAs: 'popular'
        })

        // route for news page
        /*.when('/news', {
            templateUrl : 'views/pages/news.html',
            controller  : 'newsController',
            controllerAs: 'news'
        })*/

        // route for the about page
        .when('/aboutUs', {
	        templateUrl : 'views/pages/aboutUs.html',
            controller  : 'aboutController',
            controllerAs: 'about'
        })

        .when('/infos', {
            templateUrl : 'views/pages/infos.html',
            controller  : 'infosController',
            controllerAs: 'infos'
        })

        .when('/logIn', {
            templateUrl : 'views/pages/logIn.html',
            controller  : 'loginController',
            controllerAs: 'login'
        })

        .when('/createUser', {
            templateUrl : 'views/pages/createUser.html',
            controller  : 'createUserController',
            controllerAs: 'createUser'
        });

    // set our app up to have pretty URLS
	$locationProvider.html5Mode(true);
});
