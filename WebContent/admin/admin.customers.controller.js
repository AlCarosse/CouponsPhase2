(function () {
    'use strict';

    angular
        .module('app')
        .controller('AdminCustomersController', AdminCustomersController);

    AdminCustomersController.$inject = ['CustomersService', 'CompanysService', '$cookies', '$rootScope', 'LoginService', 'FlashService'];
    
    function AdminCustomersController(CustomersService, CompanysService, $cookies, $rootScope, LoginService , FlashService ) 
    {
        var vm = this;
        
        vm.user = null;
        vm.allCustomers = [];

        vm.currentCustomer = null;
        vm.currentCustomerNew = null;
        vm.updateCustomerStatus = null;

        vm.setCurrentCustomer = setCurrentCustomer;
        vm.deleteCustomer = deleteCustomer;

        /*
        vm.currentCompany = null;
        vm.currentCompanyNew = null;
        vm.updateCompanyStatus = null;

        vm.currentCustomer = null;
        vm.currentCustomerNew = null;
        vm.updateCustomerStatus = null;

        
        vm.allCompanys = [];
        vm.allCustomers = [];

        vm.setCurrentCompany = setCurrentCompany;
        vm.setCurrentCustomer = setCurrentCustomer;
        
        vm.deleteCompany = deleteCompany;
        vm.updateCompany = updateCompany;

        vm.deleteCustomer = deleteCustomer;
        vm.updateCustomer = updateCustomer;

        */
        
        initController();

        function initController() {
        	//console.log($cookies);
        	loadCurrentUser();
        	getAllCustomers();
        }

        function loadCurrentUser() {
        	vm.user = $rootScope.globals.currentUser;
        }

        function setCurrentCustomer(customer) {
        	vm.currentCustomer = customer;
        	vm.currentCustomerNew = angular.copy(customer);
        	vm.updateCustomerStatus = null;
        }

        
        function getAllCustomers() {
            vm.dataLoading = true;
            CustomersService.GetAllCustomers(	 
            							function (response) 
            {
                if (response.data.serviceStatus.success === "true") 
                {
                	vm.allCustomers = response.data.customers;                	                    
                } 
                else 
                {
                    FlashService.Error(response.data.serviceStatus.errorMessage);
                    vm.dataLoading = false;
                }
            });
        };

        

        function deleteCustomer(customerId, customerName) 
        {
        	
        	bootbox.confirm
        	({
        		message: "Remove "+customerName+"?",
        	    buttons: { confirm: { label: 'Yes', className: 'btn-success'}, cancel:  { label: 'No',  className: 'btn-danger' }
        	    },
        	    callback: function (result) 
        	    {
                	if (result !=  true ){
                		// User canceled delete
                		return;
                	}

                	vm.dataLoading = true;
                    CustomersService.DeleteCustomer(customerId,	function (response) 
                    {
                        if (response.data.serviceStatus.success === "true") 
                        {
                        	bootbox.alert("Successfully removed "+customerName);
                        	getAllCustomers();                
                        } 
                        else 
                        {
                        	bootbox.alert("Action failed : "+response.data.serviceStatus.errorMessage);
                            vm.dataLoading = false;
                        }
                    });        	    
        	    }
        	});
        	        	
        };
        
        
        
        /*
        function updateCompany() 
        {		
        	vm.dataLoading = true;
            CompanysService.UpdateCompany(vm.currentCompanyNew,	function (response) 
            {
                if (response.data.serviceStatus.success === "true") 
                {
                	vm.currentCompany.companyName = vm.currentCompanyNew.companyName;
                	vm.currentCompany.companyEmail = vm.currentCompanyNew.companyEmail;
                	vm.updateCompanyStatus = "success";
                } 
                else 
                {
                	vm.updateCompanyStatus = "fail";
                	vm.errorMesage = response.data.serviceStatus.errorMessage;                    
                    vm.dataLoading = false;
                }
            });
            
        }
        

        function setCurrentCompany(company) {
        	vm.currentCompany = company;
        	vm.currentCompanyNew = angular.copy(company);
        	vm.updateCompanyStatus = null;
        }

        function setCurrentCustomer(customer) {
        	vm.currentCustomer = customer;
        	vm.currentCustomerNew = angular.copy(customer);
        	vm.updateCustomerStatus = null;
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

        
        
        function getAllCustomers() {
            vm.dataLoading = true;
            CustomersService.GetAllCustomers(	 
            							function (response) 
            {
                if (response.data.serviceStatus.success === "true") 
                {
                	vm.allCustomers = response.data.customers;                	                    
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

     */
   
        
    }

})();