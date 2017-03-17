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
        vm.deleteCustomer     = deleteCustomer;
        vm.updateCustomer     = updateCustomer;
        
        
        initController();

        function initController() {
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
        
        function getAllCustomers() 
        {
            CustomersService.GetAllCustomers( function (response) 
            {
                if (response.data.serviceStatus.success === "true") 
                {
                	vm.allCustomers = response.data.customers;                	                    
                } 
                else 
                {
                    FlashService.Error(response.data.serviceStatus.errorMessage);
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
        
        function updateCustomer() 
        {		
        	vm.dataLoading = true;
        	CustomersService.UpdateCustomer(vm.currentCustomerNew,	function (response) 
            {
                if (response.data.serviceStatus.success === "true") 
                {
                	vm.currentCustomer.customerName = vm.currentCustomerNew.customerName;
                	vm.updateCustomerStatus = "success";
                } 
                else 
                {
                	vm.updateCustomerStatus = "fail";
                	vm.errorMesage = response.data.serviceStatus.errorMessage;                    
                    vm.dataLoading = false;
                }
            });   
        }
     
        
    }
})();