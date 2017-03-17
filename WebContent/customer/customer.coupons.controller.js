(function () {
    'use strict';

    angular
        .module('app')
        .controller('CustomerCouponsController', CustomerCouponsController);

    CustomerCouponsController.$inject = ['CouponsService', '$cookies', '$rootScope', 'LoginService', 'FlashService'];
    
    function CustomerCouponsController( CouponsService, $cookies, $rootScope, LoginService , FlashService ) {
        
    	var vm = this;
        vm.user = null;
        
        vm.currentCompany = null;
        vm.currentCompanyNew = null;
        vm.updateCompanyStatus = null;
        
        vm.allCompanys = [];
        vm.coupons = [];

        vm.setCurrentCompany = setCurrentCompany;        
        vm.deleteCompany = deleteCompany;
        vm.updateCompany = updateCompany;

        
        vm.currentCoupon = null;
        vm.currentCouponNew = null;
        vm.updateCouponStatus = null;

        
        vm.getCustomerCouponsQuery	= getCustomerCouponsQuery;

        vm.getCouponsForSaleByCustomerId	= getCouponsForSaleByCustomerId;

        vm.getCompanyCouponsQuery	= getCompanyCouponsQuery;
        vm.getCompanyCoupons 		= getCompanyCoupons;
        vm.deleteCoupon 			= deleteCoupon;
        vm.updateCoupon 			= updateCoupon;
        vm.buyCoupon 				= buyCoupon;
        
        vm.setCurrentCoupon = setCurrentCoupon;
        vm.clearFilter	    = clearFilter;
        
		vm.newCoupon = {
			  	 	"couponId"       :  0
			    	,"couponEndDate" : "yyyymmdd"
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
        	getCustomerCouponsQuery();
        }

        function clearFilter() {
    		vm.filter = {
			  	 	"couponTypeId"  :  null
			  	    ,"fromPrice" 	 :  null
			  	   	,"toPrice" 	     :  null
			  	   	,"fromDate" 	 :  null
			  	   	,"toDate" 	     :  null  
			  	};
    		getCustomerCouponsQuery();
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
			vm.currentCouponNew = null;
			//vm.currentCouponNew = angular.copy(coupon);
			
			//var price = Math.floor (vm.currentCouponNew.couponPrice); 
			//vm.currentCouponNew.couponPrice = price;
				        	
			//var st = vm.currentCouponNew.couponEndDateYyyyMmDd;
			//var pattern = /(\d{4})(\d{2})(\d{2})/;
			//var date =  new Date(st.replace(pattern, '$1-$2-$3'));
				   
			//vm.currentCouponNew.couponEndDate = date;
			
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
                	//vm.coupons = response.data.coupons;  
                	vm.coupons = [];
                	vm.coupons = vm.coupons.concat(response.data.coupons);
                } 
                else 
                {
                    FlashService.Error(response.data.serviceStatus.errorMessage);
                    vm.dataLoading = false;
                }
            });
        };

        
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

        
        function loadCurrentUser() {
        	vm.user = $rootScope.globals.currentUser;
        }

        function setCurrentCompany(company) {
        	vm.currentCompany = company;
        	vm.currentCompanyNew = angular.copy(company);
        	vm.updateCompanyStatus = null;
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


        function deleteCompany(companyId, companyName) 
        {
        	
        	bootbox.confirm
        	({
        		message: "Remove "+companyName+"?",
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
                        	bootbox.alert("Successfully removed "+companyName);
                        	getAllCompanys();                } 
                        else 
                        {
                        	bootbox.alert("Action failed : "+response.data.serviceStatus.errorMessage);
                            vm.dataLoading = false;
                        }
                    });        	    }
        	});
        	        	
        };

        
        
    }
})();