(function () {
    'use strict';

    angular
        .module('app')
        .controller('CustomerCouponsController', CustomerCouponsController);

    CustomerCouponsController.$inject = ['CouponsService', '$cookies', '$rootScope', 'LoginService', 'FlashService'];
    
    function CustomerCouponsController( CouponsService, $cookies, $rootScope, LoginService , FlashService ) 
    {    
    	var vm = this;
        vm.user = null;
                
        vm.coupons = [];
        vm.currentCoupon = null;
        vm.updateCouponStatus = null;
        
        vm.getCustomerCouponsQuery	= getCustomerCouponsQuery;   
        vm.setCurrentCoupon         = setCurrentCoupon;
        vm.clearFilter	            = clearFilter;
        
		
        initController();

        function initController() 
        {
        	loadCurrentUser();
        	clearFilter();
        }

        function loadCurrentUser() 
        {
        	vm.user = $rootScope.globals.currentUser;
        }

        function clearFilter() 
        {
    		vm.filter = {
			  	 	"couponTypeId"   :  null
			  	    ,"fromPrice" 	 :  null
			  	   	,"toPrice" 	     :  null
			  	   	,"fromDate" 	 :  null
			  	   	,"toDate" 	     :  null  
			  	};
    		getCustomerCouponsQuery();
        }

        function setCurrentCoupon(coupon) 
        {
			vm.currentCoupon = coupon;
			vm.updateCouponStatus = null;
        }
        
        
        function getCustomerCouponsQuery() 
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
			
        	
			CouponsService.GetCustomerCouponsQuery(vm.queryParametrs,	function (response) 
            {
                if (response.data.serviceStatus.success === "true") 
                {
                	vm.coupons = [];
                	if ( response.data.customerCoupons !== undefined )
                	{
                		vm.coupons = vm.coupons.concat(response.data.customerCoupons);
                	}
                } 
                else 
                {
                	vm.updateCouponStatus = "fail";
                	vm.errorMesage = response.data.serviceStatus.errorMessage;                    
                }
            });
            
        }
                
        
    }
})();