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

        function loadCurrentUser() {
        	vm.user = $rootScope.globals.currentUser;
        }
        

    }

})();