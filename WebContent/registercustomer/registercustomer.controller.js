(function () {
    'use strict';

    angular
        .module('app')
        .controller('RegisterCustomerController', RegisterCustomerController);

    RegisterCustomerController.$inject = [ 'LoginService', 'CustomersService', '$location', '$rootScope', 'FlashService'];
    function RegisterCustomerController( LoginService, CustomersService, $location, $rootScope, FlashService) {
        var vm = this;
        
        vm.register = register;

        
        function register() 
        {
            vm.dataLoading = true;
            CustomersService.CreateCustomer(	vm.user.customerName, // User name in users table.
            									vm.user.loginName, 
            									vm.user.loginPassword, 
            									vm.user.customerName, 
            									function (response) 
            {
                if (response.data.serviceStatus.success === "true") 
                {
                		login(vm.user.loginName, vm.user.loginPassword);              	                    
                } 
                else 
                {
                    FlashService.Error(response.data.serviceStatus.errorMessage);
                    vm.dataLoading = false;
                }
            });
        };
        
        
        function login( loginName, loginPassword ) 
        {
            LoginService.Login(loginName, loginPassword, function (response) {
            	            	
                if (response.data.userId !== "0" ) 
                {                	
                    LoginService.setCurrentUser( response.data );
                    
                    if (response.data.userProfileId === "3" )
                    {
                        $location.path('/customer');
                    }
                    else
                	{
                        $location.path('/');                	
                	}
                } 
                else 
                {
                    FlashService.Error("Login failed");
                    vm.dataLoading = false;
                }
            });
        };
        
        
        
    }

})();
