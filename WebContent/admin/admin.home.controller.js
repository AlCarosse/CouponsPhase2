(function () {
    'use strict';

    angular
        .module('app')
        .controller('AdminController', AdminController);

    AdminController.$inject = ['UsersService', '$rootScope', 'LoginService'];
    
    function AdminController(UsersService, $rootScope, LoginService) {
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