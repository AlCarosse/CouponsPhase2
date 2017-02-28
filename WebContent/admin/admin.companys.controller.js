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

        vm.setCurrentCompany = setCurrentCompany;
        vm.currentCompany = null;

        vm.updateCompany = updateCompany;

    	vm.companyName = null;
    	vm.companyEmail = null;

        
        initController();

        function initController() {
        	console.log($cookies);
        	loadCurrentUser();
            getAllCompanys();
        }

        function updateCompany() {

        	vm.currentCompany.companyName;
        	vm.currentCompany.companyEmail;
        	
        	return;        	
        	
        }

        
        
        function loadCurrentUser() {
        	vm.user = $rootScope.globals.currentUser;
        }

        function setCurrentCompany(company) {
        	vm.currentCompany = company;
        	$rootScope.globals.currentCompany = company;
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

        
        function deleteCompany(companyId, companName) 
        {
        	
        	bootbox.confirm
        	({
        		message: "Remove "+companName+"?",
        	    buttons: { confirm: { label: 'Yes', className: 'btn-success'}, cancel:  { label: 'No',  className: 'btn-danger' }
        	    },
        	    callback: function (result) 
        	    {
                	if (result !=  true ){
                		// User canceled delete
                		return;
                	}

                	vm.dataLoading = true;
                    CompanysService.DeleteCompany(companyId,	function (response) 
                    {
                        if (response.data.serviceStatus.success === "true") 
                        {
                        	getAllCompanys();                } 
                        else 
                        {
                            FlashService.Error(response.data.serviceStatus.errorMessage);
                            vm.dataLoading = false;
                        }
                    });        	    }
        	});
        	        	
        };

        
        
    }

})();