angular.module('authService', [])
.factory('Auth', function($http, $q, AuthToken) {

	var authFactory = {};

	return authFactory;

})

.factory('AuthToken', function($window) {

	var authTokenFactory = {};

	return authTokenFactory;

})

.factory('AuthInterceptor', function($q, AuthToken) {

	var interceptorFactory = {};

	return interceptorFactory;

});