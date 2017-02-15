(function () {
    'use strict';

    angular
        .module('app')
        .controller('RegisterCompanyController', RegisterCompanyController);

    RegisterCompanyController.$inject = ['AuthenticationService', 'UserService', '$location', '$rootScope', 'FlashService'];
    function RegisterCompanyController(AuthenticationService, UserService, $location, $rootScope, FlashService) {
        var vm = this;

        vm.register = register;

        
        function register() {
            vm.dataLoading = true;
            UserService.CreateCompany(	vm.user.companyName, 
            							vm.user.loginName, 
            							vm.user.loginPassword, 
            							vm.user.companyName, 
            							vm.user.companyEmail, 
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
