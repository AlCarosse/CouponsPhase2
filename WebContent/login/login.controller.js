(function () {
    'use strict';

    angular
        .module('app')
        .controller('LoginController', LoginController);

    LoginController.$inject = ['$location', 'AuthenticationService', 'FlashService'];
    function LoginController($location, AuthenticationService, FlashService) {
        var vm = this;

        vm.login = login;

        (function initController() {
            // reset login status
            AuthenticationService.ClearCredentials();
        })();

        function login() {
            vm.dataLoading = true;
            AuthenticationService.Login(vm.username, vm.password, function (response) {
            	
                if (response.data.userId !== "0" ) 
                {
                	
                    //AuthenticationService.SetCredentials( vm.username, vm.password );
                    AuthenticationService.setCurrentUser( response.data );
                    
                    if (response.data.userProfileId === "1" )
                    {
                        $location.path('/admin');
                    }
                    else if (response.data.userProfileId === "2" )
                    {
                        $location.path('/company');
                    }
                    else if (response.data.userProfileId === "3" )
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
