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
            controller: 'AdminController',
            templateUrl: 'admin/admin.customers.view.html',
            controllerAs: 'vm'
        })

        .when('/customer', {
            controller: 'CustomerController',
            templateUrl: 'customer/customer.view.html',
            controllerAs: 'vm'
        })

        .when('/company', {
            controller: 'CompanyController',
            templateUrl: 'company/company.view.html',
            controllerAs: 'vm'
        })
        
        .when('/', {
                controller: 'HomeController',
                templateUrl: 'home/home.view.html',
                controllerAs: 'vm'
            })

            .when('/login', {
                controller: 'LoginController',
                templateUrl: 'login/login.view.html',
                controllerAs: 'vm'
            })

            .when('/register', {
                templateUrl: 'register/register.view.html',
            })


            .when('/registercustomer', {
                controller: 'RegisterCustomerController',
                templateUrl: 'registercustomer/registercustomer.view.html',
                controllerAs: 'vm'
            })

            .when('/registercompany', {
                controller: 'RegisterCompanyController',
                templateUrl: 'registercompany/registercompany.view.html',
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
            var restrictedPage = $.inArray($location.path(), ['/login', '/register', '/registercustomer', '/registercompany']) === -1;
            var loggedIn = $rootScope.globals.currentUser;
            if (restrictedPage && !loggedIn) {
                $location.path('/login');
            }
        });
    }

})();