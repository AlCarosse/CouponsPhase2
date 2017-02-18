(function () {
    'use strict';

    angular
        .module('app')
        .controller('AdminCompanysController', AdminCompanysController);

    AdminCompanysController.$inject = ['CompanysService', '$cookies', '$rootScope', 'LoginService', 'FlashService'];
    
    function AdminCompanysController(CompanysService, $cookies, $rootScope, LoginService , FlashService ) {
        var vm = this;
        
        vm.user = null;
        vm.allUsers = [];
        vm.deleteCompany = deleteCompany;
        vm.allCompanys = [];

        vm.deleteCompany = deleteCompany;

        initController();

        function initController() {
        	console.log($cookies);
        	loadCurrentUser();
            getAllCompanys();
        }

        function loadCurrentUser() {
        	vm.user = $rootScope.globals.currentUser;
        }
        
        function getAllCompanys() {
            vm.dataLoading = true;
            CompanysService.GetAllCompanys(	 
            							function (response) 
            {
                if (response.data.serviceStatus.success === "true") 
                {
                	vm.allCompanys = response.data.companys;                	                    
                } 
                else 
                {
                    FlashService.Error(response.data.serviceStatus.errorMessage);
                    vm.dataLoading = false;
                }
            });
        };

        
        function deleteCompany(companyId) {
            vm.dataLoading = true;
            CompanysService.DeleteCompany(companyId,	 
            							function (response) 
            {
                if (response.data.serviceStatus.success === "true") 
                {
                	getAllCompanys();                } 
                else 
                {
                    FlashService.Error(response.data.serviceStatus.errorMessage);
                    vm.dataLoading = false;
                }
            });
        };

        
        function deleteUser(id) {
            UsersService.Delete(id)
            .then(function () {
                loadAllUsers();
            });
        }
    }

})();