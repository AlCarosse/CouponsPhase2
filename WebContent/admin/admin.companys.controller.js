(function () {
    'use strict';

    angular
        .module('app')
        .controller('AdminCompanysController', AdminCompanysController);

    AdminCompanysController.$inject = ['CompanysService', '$cookies', '$rootScope', 'LoginService', 'FlashService'];
    
    function AdminCompanysController(CompanysService, $cookies, $rootScope, LoginService , FlashService ) {
        var vm = this;
        
        vm.user = null;
        vm.currentCompany = null;
        vm.currentCompanyNew = null;
        
        vm.allCompanys = [];

        vm.setCurrentCompany = setCurrentCompany;
        
        vm.deleteCompany = deleteCompany;
        vm.updateCompany = updateCompany;

        /*
         vm.allUsers = [];
    	vm.companyName = null;
    	vm.companyEmail = null;
         */
        
        initController();

        function initController() {
        	console.log($cookies);
        	loadCurrentUser();
            getAllCompanys();
        }

        function updateCompany() {
        	
        	
        	vm.dataLoading = true;
            CompanysService.UpdateCompany(vm.currentCompanyNew,	function (response) 
            {
                if (response.data.serviceStatus.success === "true") 
                {
                	vm.currentCompany.companyName = vm.currentCompanyNew.companyName;
                	vm.currentCompany.companyEmail = vm.currentCompanyNew.companyEmail;
                	bootbox.alert("Successfully updated company details");
                } 
                else 
                {
                    //FlashService.Error(response.data.serviceStatus.errorMessage);
                	
                    bootbox.alert("Update company details failed : " + response.data.serviceStatus.errorMessage );
                    
                    vm.dataLoading = false;
                }
            });
            
        }

        
        function loadCurrentUser() {
        	vm.user = $rootScope.globals.currentUser;
        }

        function setCurrentCompany(company) {
        	vm.currentCompany = company;
        	vm.currentCompanyNew = angular.copy(company);
        	//$rootScope.globals.currentCompany = company;
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