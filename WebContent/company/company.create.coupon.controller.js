(function () {
    'use strict';

    angular
        .module('app')
        .controller('CompanyCreateCouponController', CompanyCreateCouponController);

    CompanyCreateCouponController.$inject = ['UsersService', '$rootScope', 'LoginService'];
    
    function CompanyCreateCouponController(UsersService, $rootScope, LoginService) {
        var vm = this;
        
    	vm.coupon = {
   		   	 "couponTitle"    : ""
   		   	  	,"couponStartDate": null
   		   	  	,"couponEndDate"  : null //(new Date).add(1,'years')
   		   	    ,"couponsInStock": 100
   		   	  	,"couponTypeId"  : 0
   		   	  	,"couponMessage": ""  
   		   	  	,"couponPrice"  : 10
   		   	  	,"imageFileName": "Browse..."	  
   		   	};
        
        vm.user = null;
        vm.createCoupon = createCoupon;

        initController();

        function initController() {        	
        	vm.couponStartDate = new Date();
        	vm.coupon.couponStartDate = vm.couponStartDate;
        	
        	vm.couponEndDate = new Date();
        	vm.year = vm.couponEndDate.getFullYear();
        	vm.year = vm.year + 1;
        	
        	vm.couponEndDate.setYear(vm.year);
        	vm.coupon.couponEndDate = vm.couponEndDate;
        	
        	loadCurrentUser();
        }


        function loadCurrentUser() {
        	vm.user = $rootScope.globals.currentUser;
        }


        
        function createCoupon() {
        	
        	vm.coupon;

        	vm.coupon;

        }

        
        
    }

})();