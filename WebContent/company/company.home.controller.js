(function () {
    'use strict';

    angular
        .module('app')
        .controller('CompanyHomeController', CompanyHomeController);

    CompanyHomeController.$inject = ['UsersService', '$rootScope', 'LoginService'];
    
    function CompanyHomeController(UsersService, $rootScope, LoginService) {
        var vm = this;
        
        vm.user = null;

        initController();

        function initController() {
        	loadCurrentUser();
        }

        function loadCurrentUserOld() {
        	UsersService.GetByUsername($rootScope.globals.currentUser.username)
                .then(function (user) {
                    vm.user = user;
                });
        }

        function loadCurrentUser() {
        	vm.user = $rootScope.globals.currentUser;
        }
        

    }

})();