(function () {
    'use strict';

    angular
        .module('app')
        .controller('CustomerHomeController', CustomerHomeController);

    CustomerHomeController.$inject = ['UsersService', '$rootScope', 'LoginService'];
    
    function CustomerHomeController(UsersService, $rootScope, LoginService) {
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