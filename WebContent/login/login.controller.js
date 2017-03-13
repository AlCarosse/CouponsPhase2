(function () {
    'use strict';

    angular
        .module('app')
        .controller('LoginController', LoginController);

    LoginController.$inject = ['$location', '$cookies', 'LoginService', 'FlashService'];
    function LoginController($location, $cookies, LoginService, FlashService) {
        var vm = this;

        vm.login = login;
        
        

        (function initController() {
            // reset login status
            LoginService.ClearCredentials();
        })();

        function login() {
            vm.dataLoading = true;
            
            LoginService.Login(vm.username, vm.password, function (response) {
            	            	
                if (response.data.userId !== "0" ) 
                {                	
                    LoginService.setCurrentUser( response.data );
                    
                    if (response.data.userProfileId === "1" )
                    {
                        $location.path('/admin.home');
                    }
                    else if (response.data.userProfileId === "2" )
                    {
                        $location.path('/company.home');
                    }
                    else if (response.data.userProfileId === "3" )
                    {
                        $location.path('/customer.home');
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
