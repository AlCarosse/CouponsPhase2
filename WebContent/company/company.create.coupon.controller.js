(function () {
	'use strict';

	angular
	.module('app')
	.controller( 'CompanyCreateCouponController', CompanyCreateCouponController);

	CompanyCreateCouponController.$inject = [ 'CompanysService', 'CouponsService', '$rootScope'];

	function CompanyCreateCouponController( CompanysService,CouponsService,   $rootScope) {
		var vm = this;

		vm.coupon = {
					"couponTitle"    : "couponTitle"
					,"couponStartDate": null
					,"couponEndDate"  : null 
					,"couponsInStock": 100
					,"couponTypeId"  : null
					,"couponMessage": "couponMessage"  
					,"couponPrice"  : 10
					,"imageFileName": "FileUpload"	  
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

			vm.newCoupon = angular.copy(vm.coupon);
			
			var yyyymmdd;
			
    		yyyymmdd = moment(vm.coupon.couponStartDate).format('YYYYMMDD');
			vm.newCoupon.couponStartDate = yyyymmdd;
			
			yyyymmdd = moment(vm.coupon.couponEndDate).format('YYYYMMDD');
			vm.newCoupon.couponEndDate = yyyymmdd;
			
			
			CouponsService.CreateCoupon( vm.newCoupon, function (response) 
			{
				if (response.data.serviceStatus.success === "true") 
				{
					vm.updateCompanyStatus = "success";

					vm.selectedFile = document.getElementById('imageFileName').files[0];

					
					CouponsService.UploadImage( vm.selectedFile, function (response) 
					{
						if (response.status === 200 ) 
						{
							vm.updateCompanyStatus = "success";
						} 
						else 
						{
							vm.updateCompanyStatus = "fail";
							vm.errorMesage = response.data.serviceStatus.errorMessage;                    
						}
					});

					
				} 
				else 
				{
					vm.updateCompanyStatus = "fail";
					vm.errorMesage = response.data.serviceStatus.errorMessage;                    
				}
				
			});



			
		}

		/*
		function uploadFile(file, callback) {


			var fd = new FormData();

			fd.append("file", file);

			$http.post("/CouponsPhase2/UploadServlet", fd, {
				withCredentials: true,
				headers: {'Content-Type': undefined },
				transformRequest: angular.identity
			})
			.then(function (response) {
				callback(response);
			});

			vm.coupon;


		}
		 */




	}

})();