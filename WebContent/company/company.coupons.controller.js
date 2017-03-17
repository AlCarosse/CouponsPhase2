(function () {
    'use strict';

    angular
        .module('app')
        .controller('CompanyCouponsController', CompanyCouponsController);

    CompanyCouponsController.$inject = ['CouponsService', '$cookies', '$rootScope', 'LoginService', 'FlashService'];
    
    function CompanyCouponsController( CouponsService, $cookies, $rootScope, LoginService , FlashService ) {
        
    	var vm = this;
        vm.user = null;
        
        vm.currentCompany = null;
        vm.currentCompanyNew = null;
        vm.updateCompanyStatus = null;
        
        vm.coupons = [];
        
        vm.currentCoupon = null;
        vm.currentCouponNew = null;
        vm.updateCouponStatus = null;

        
        vm.getCompanyCouponsQuery	= getCompanyCouponsQuery;
        vm.getCompanyCoupons 		= getCompanyCoupons;
        vm.deleteCoupon 			= deleteCoupon;
        vm.updateCoupon 			= updateCoupon;
        
        vm.setCurrentCoupon = setCurrentCoupon;
        vm.clearFilter	    = clearFilter;

        
		vm.newCoupon = {
			  	 	"couponId"       :  0
			    	,"couponEndDate" : ""
			    	,"couponPrice"   : 0
			 };

		vm.filter = {
			  	 	"couponTypeId"  :  null
			  	    ,"fromPrice" 	 :  null
			  	   	,"toPrice" 	     :  null
			  	   	,"fromDate" 	 :  null
			  	   	,"toDate" 	     :  null  
			  	};
        
		
        initController();

        function initController() {
        	loadCurrentUser();
        	getCompanyCoupons();
        }

        function clearFilter() {
    		vm.filter = {
			  	 	"couponTypeId"  :  null
			  	    ,"fromPrice" 	 :  null
			  	   	,"toPrice" 	     :  null
			  	   	,"fromDate" 	 :  null
			  	   	,"toDate" 	     :  null  
			  	};
        	getCompanyCouponsQuery();
        }


        function getCompanyCouponsQuery() 
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
			
			CouponsService.GetCompanyCouponsQuery(vm.queryParametrs,	function (response) 
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
        
        function updateCoupon() 
        {		
        	vm.newCoupon.couponId = vm.currentCouponNew.couponId;
        	vm.newCoupon.couponPrice = vm.currentCouponNew.couponPrice;

			var yyyymmdd = vm.currentCouponNew.couponEndDate.toISOString().slice(0,10).replace(/-/g,"");
			vm.newCoupon.couponEndDate = yyyymmdd;
        	
			CouponsService.UpdateCoupon(vm.newCoupon,	function (response) 
            {
                if (response.data.serviceStatus.success === "true") 
                {
                	vm.currentCoupon.couponPrice   = vm.currentCouponNew.couponPrice;
                	vm.currentCoupon.couponEndDate = vm.currentCouponNew.couponEndDate;
                	vm.updateCouponStatus = "success";
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
			vm.currentCouponNew = angular.copy(coupon);
			
			var price = Math.floor (vm.currentCouponNew.couponPrice); 
			vm.currentCouponNew.couponPrice = price;
				        	
			var st = vm.currentCouponNew.couponEndDateYyyyMmDd;
			var pattern = /(\d{4})(\d{2})(\d{2})/;
			var date =  new Date(st.replace(pattern, '$1-$2-$3'));
				   
			vm.currentCouponNew.couponEndDate = date;
			
			vm.updateCouponStatus = null;
        }

     
        function deleteCoupon( couponId , couponTitle ) 
        {
        	
        	bootbox.confirm
        	({
        		message: "Remove " + couponTitle + "?",
        	    buttons: { confirm: { label: 'Yes', className: 'btn-success'}, cancel:  { label: 'No',  className: 'btn-danger' }
        	    },
        	    callback: function (result) 
        	    {
                	if (result !=  true ){
                		// User canceled delete
                		return;
                	}

                	vm.dataLoading = true;
                	CouponsService.DeleteCoupon( couponId ,	function (response) 
                    {
                        if (response.data.serviceStatus.success === "true") 
                        {
                        	bootbox.alert("Successfully removed " +couponTitle);
                        	getCompanyCouponsQuery();                
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

        
        
        function getCompanyCoupons() {
            vm.dataLoading = true;
            CouponsService.GetCompanyCoupons(	 
            							function (response) 
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
                    FlashService.Error(response.data.serviceStatus.errorMessage);
                    vm.dataLoading = false;
                }
            });
        };

        
        
        function loadCurrentUser() {
        	vm.user = $rootScope.globals.currentUser;
        }

              
        
    }
})();