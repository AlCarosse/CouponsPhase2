(function () {
    'use strict';

    angular
        .module('app')
        .controller('CustomerBuyCouponController', CustomerBuyCouponController);

    CustomerBuyCouponController.$inject = ['CouponsService', '$cookies', '$rootScope', 'LoginService', 'FlashService'];
    
    function CustomerBuyCouponController( CouponsService, $cookies, $rootScope, LoginService , FlashService ) {
        
    	var vm = this;
        vm.user = null;
        
        
        vm.coupons = [];
        vm.currentCoupon = null;
        vm.currentCouponNew = null;
        vm.updateCouponStatus = null;

        vm.getCouponsForSaleByCustomerId = getCouponsForSaleByCustomerId;
        vm.buyCoupon 				     = buyCoupon;
        
        vm.setCurrentCoupon = setCurrentCoupon;
        vm.clearFilter	    = clearFilter;
        

        initController();

        function initController() {
        	loadCurrentUser();
        	clearFilter();
        }

        function clearFilter() {
    		vm.filter = {
			  	 	"couponTypeId"  :  null
			  	    ,"fromPrice" 	 :  null
			  	   	,"toPrice" 	     :  null
			  	   	,"fromDate" 	 :  null
			  	   	,"toDate" 	     :  null  
			  	};
    		getCouponsForSaleByCustomerId();
        }


        function buyCoupon() 
        {		        	
			CouponsService.BuyCoupon(vm.currentCoupon.couponId,	function (response) 
            {
                if (response.data.serviceStatus.success === "true") 
                {
                	vm.updateCouponStatus = "success";
                	getCouponsForSaleByCustomerId();
                } 
                else 
                {
                	vm.updateCouponStatus = "fail";
                	vm.errorMesage = response.data.serviceStatus.errorMessage;                    
                }
            });
        }


        function getCouponsForSaleByCustomerId() 
        {		
        	vm.queryParametrs = angular.copy(vm.filter);

        	var yyyymmdd;
        	if ( vm.filter.fromDate != null )
        	{
        		yyyymmdd = moment(vm.filter.fromDate).format('YYYYMMDD');
        		vm.queryParametrs.fromDate = yyyymmdd;
        	}

        	if ( vm.filter.toDate != null )
        	{
        		yyyymmdd = moment(vm.filter.toDate).format('YYYYMMDD');
        		vm.queryParametrs.toDate = yyyymmdd;
        	}
			
        	
			CouponsService.GetCouponsForSaleByCustomerId(vm.queryParametrs,	function (response) 
            {
                if (response.data.serviceStatus.success === "true") 
                {
                	vm.coupons = [];
                	if ( response.data.coupons !== undefined )
                	{
                		vm.coupons = vm.coupons.concat(response.data.coupons);
                	}
                } 
                else 
                {
                	vm.updateCouponStatus = "fail";
                	vm.errorMesage = response.data.serviceStatus.errorMessage;                    
                }
            });
            
        }
        
        function setCurrentCoupon(coupon) {
			vm.currentCoupon = coupon;
			vm.currentCouponNew = null;
			vm.updateCouponStatus = null;
        }
        
        function loadCurrentUser() {
        	vm.user = $rootScope.globals.currentUser;
        }

    
        
    // End of controller
    }
})();