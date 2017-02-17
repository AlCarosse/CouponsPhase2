(function () {
    'use strict';

    angular
        .module('app')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['UsersService', '$rootScope', 'LoginService'];
    
    function HomeController(UsersService, $rootScope, LoginService) {
        var vm = this;
        
        vm.user = null;
        vm.allUsers = [];
        vm.deleteUser = deleteUser;

        initController();

        function initController() {
        	loadCurrentUser();
            loadAllUsers();
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


        
        function loadAllUsers() {
        	UsersService.GetAll()
                .then(function (users) {
                    vm.allUsers = users;
                });
        }

        function deleteUser(id) {
        	UsersService.Delete(id)
            .then(function () {
                loadAllUsers();
            });
        }
    }

})();