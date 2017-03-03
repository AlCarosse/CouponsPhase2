(function () {
    'use strict';

    angular
        .module('app')
        .factory('CustomersService', CustomersService);

    CustomersService.$inject = ['$http', '$cookies', '$rootScope', '$timeout'];
    function CustomersService($http, $cookies, $rootScope, $timeout) {

    	var service = {};

        service.CreateCustomer = CreateCustomer;
        service.DeleteCustomer = DeleteCustomer;
        service.UpdateCustomer = UpdateCustomer;
        
        service.GetAllCustomers = GetAllCustomers;
        
        return service;

        
        function UpdateCustomer( customer , callback) 
        {
            $http.put('/CouponsPhase2/rest/api/customers/updateCustomer/' , { customerId : customer.customerId, customerName: customer.customerName } 
            )
                .then(function (response) {
                    callback(response);
                });
        }
        
        
        function DeleteCustomer(i_cusomerId , callback) 
        {
            $http.delete('/CouponsPhase2/rest/api/customers/deleteCustomer/customerId/' + i_cusomerId 
            )
                .then(function (response) {
                    callback(response);
                });
        }
        
        
        function CreateCustomer(i_userName, i_loginName, i_loginPassword, i_customerName, callback) 
        {            
        	$http.post('/CouponsPhase2/rest/register/createCustomer/', { userName: i_userName, loginName: i_loginName, loginPassword: i_loginPassword, customerName: i_customerName } )
        	.then(function (response) {
                    callback(response);
                });
        }
        
        
        function GetAllCustomers(callback) 
        {
            $http.get('/CouponsPhase2/rest/api/customers/getAllCustomers/'
            )
                .then(function (response) {
                    callback(response);
                });
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
