(function () {
    'use strict';

    angular
        .module('app')
        .controller('RegisterCustomerController', RegisterCustomerController);

    RegisterCustomerController.$inject = [ 'CustomersService', '$location', '$rootScope', 'FlashService'];
    function RegisterCustomerController( CustomersService, $location, $rootScope, FlashService) {
        var vm = this;

        vm.register = register;

        
        function register() {
            vm.dataLoading = true;
            CustomersService.CreateCustomer(	vm.user.customerName, // User name in users table.
            									vm.user.loginName, 
            									vm.user.loginPassword, 
            									vm.user.customerName, 
            									function (response) 
            {
                if (response.data.serviceStatus.success === "true") 
                {
                        $location.path('/login');                	                    
                } 
                else 
                {
                    FlashService.Error(response.data.serviceStatus.errorMessage);
                    vm.dataLoading = false;
                }
            });
        };
    }

    
})();
