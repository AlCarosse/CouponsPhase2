(function () {
	'use strict';

	angular
	.module('app', ['ngRoute', 'ngCookies'])
	.config(config)
	.run(run);

	config.$inject = ['$routeProvider', '$locationProvider', '$httpProvider'];
	function config($routeProvider, $locationProvider, $httpProvider) {    	
		$locationProvider.hashPrefix('');

		$httpProvider.defaults.withCredentials = true;

		$routeProvider


		/* =================================================
		 *	Admin views 
		   ==================================================*/

		.when('/admin.home', {
			controller: 'AdminController',
			templateUrl: 'admin/admin.home.view.html',
			controllerAs: 'vm'
		})

		.when('/admin.companys', {
			controller: 'AdminCompanysController',
			templateUrl: 'admin/admin.companys.view.html',
			controllerAs: 'vm'
		})

		.when('/admin.customers', {
			controller: 'AdminCustomersController',
			templateUrl: 'admin/admin.customers.view.html',
			controllerAs: 'vm'
		})

		/* =================================================
		 *	Customer views 
		   ==================================================*/

		.when('/customer.home', {
			controller: 'CustomerHomeController',
			templateUrl: 'customer/customer.home.view.html',
			controllerAs: 'vm'
		})

		.when('/customer.profile', {
			controller: 'CustomerProfileController',
			templateUrl: 'customer/customer.profile.view.html',
			controllerAs: 'vm'
		})

		.when('/customer.coupons', {
			controller: 'CustomerCouponsController',
			templateUrl: 'customer/customer.coupons.view.html',
			controllerAs: 'vm'
		})

		.when('/customer.buy.coupon', {
			controller: 'CustomerBuyCouponController',
			templateUrl: 'customer/customer.buy.coupon.view.html',
			controllerAs: 'vm'
		})

		/* =================================================
		 *	Company views 
		   ==================================================*/

		.when('/company.home', {
			controller: 'CompanyHomeController',
			templateUrl: 'company/company.home.view.html',
			controllerAs: 'vm'
		})

		.when('/company.profile', {
			controller: 'CompanyProfileController',
			templateUrl: 'company/company.profile.view.html',
			controllerAs: 'vm'
		})

		.when('/company.create.coupon', {
			controller: 'CompanyCreateCouponController',
			templateUrl: 'company/company.create.coupon.view.html',
			controllerAs: 'vm'
		})

		.when('/company.coupons', {
			controller: 'CompanyCouponsController',
			templateUrl: 'company/company.coupons.view.html',
			controllerAs: 'vm'
		})


		/* =================================================
		 *	Login / registration views 
		   ==================================================*/

		.when('/login', {
			controller: 'LoginController',
			templateUrl: 'login/login.view.html',
			controllerAs: 'vm'
		})

		.when('/register.home', {
			templateUrl: 'register/register.home.view.html',
		})


		.when('/register.customer', {
			controller: 'RegisterCustomerController',
			templateUrl: 'register/register.customer.view.html',
			controllerAs: 'vm'
		})

		.when('/register.company', {
			controller: 'RegisterCompanyController',
			templateUrl: 'register/register.company.view.html',
			controllerAs: 'vm'
		})


		/* =================================================
		 *	Default views 
		   ==================================================*/

		.when('/', {
			controller: 'HomeController',
			templateUrl: 'home/home.view.html',
			controllerAs: 'vm'
		})


		.otherwise({ redirectTo: '/login' });

	}

	run.$inject = ['$rootScope', '$location', '$cookies', '$http'];
	function run($rootScope, $location, $cookies, $http) {
		// keep user logged in after page refresh
		$rootScope.globals = $cookies.getObject('globals') || {};
		if ($rootScope.globals.currentUser) {
			$http.defaults.headers.common['Authorization'] = 'Basic ' + $rootScope.globals.currentUser.authdata;
		}

		$rootScope.$on('$locationChangeStart', function (event, next, current) {
			// redirect to login page if not logged in and trying to access a restricted page
			var restrictedPage = $.inArray($location.path(), ['/register', '/login', '/register.home', '/register.customer', '/register.company']) === -1;
			var loggedIn = $rootScope.globals.currentUser;
			if (restrictedPage && !loggedIn) {
				$location.path('/login');
			}
		});
	}

})();