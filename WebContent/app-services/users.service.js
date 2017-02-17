(function () {
    'use strict';

    angular
        .module('app')
        .factory('UsersService', UsersService);

    UsersService.$inject = ['$http', '$cookies', '$rootScope', '$timeout'];
    function UsersService($http, $cookies, $rootScope, $timeout) {

    	var service = {};

        service.GetAll = GetAll;
        service.GetById = GetById;
        service.GetByUsername = GetByUsername;
        service.Create = Create;
        service.Update = Update;
        service.Delete = Delete;
        service.Login = Login;
        service.CreateCustomer = CreateCustomer;
        service.CreateCompany = CreateCompany;
        
        return service;

        function CreateCustomer(i_userName, i_loginName, i_loginPassword, i_customerName, callback) 
        {            
        	$http.post('/CouponsPhase2/rest/register/createCustomer/', { userName: i_userName, loginName: i_loginName, loginPassword: i_loginPassword, customerName: i_customerName } )
        	.then(function (response) {
                    callback(response);
                });
        }
        
        function CreateCompany(i_userName, i_loginName, i_loginPassword, i_companyName, i_companyEmail , callback) 
        {
            $http.post('/CouponsPhase2/rest/register/createCompany/',  { userName: i_userName, loginName: i_loginName, loginPassword: i_loginPassword, companyName: i_companyName , companyEmail: i_companyEmail} 
            )
                .then(function (response) {
                    callback(response);
                });
        }

        
        function GetAll() {
            return $http.get('/api/users').then(handleSuccess, handleError('Error getting all users'));
        }

        function GetById(id) {
            return $http.get('/api/users/' + id).then(handleSuccess, handleError('Error getting user by id'));
        }

        function GetByUsername(username) {
            return $http.get('/api/users/' + username).then(handleSuccess, handleError('Error getting user by username'));
        }

        function Create(user) {
            return $http.post('/api/users', user).then(handleSuccess, handleError('Error creating user'));
        }

        function Update(user) {
            return $http.put('/api/users/' + user.id, user).then(handleSuccess, handleError('Error updating user'));
        }

        function Delete(id) {
            return $http.delete('/api/users/' + id).then(handleSuccess, handleError('Error deleting user'));
        }

        function Login(user) {
            return $http.put('/rest/login/' , user).then(handleSuccess, handleError('Login failed'));
        }

        // private functions

        function handleSuccess(res) {
            return res.data;
        }

        function handleError(error) {
            return function () {
                return { success: false, message: error };
            };
        }
    }

})();
