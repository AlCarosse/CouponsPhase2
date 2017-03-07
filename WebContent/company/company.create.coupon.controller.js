(function () {
	'use strict';

	angular
	.module('app')
	.controller( 'CompanyCreateCouponController', CompanyCreateCouponController);

	CompanyCreateCouponController.$inject = ['$http', 'UsersService', '$rootScope', 'LoginService'];

	function CompanyCreateCouponController( $http, UsersService, $rootScope, LoginService) {
		var vm = this;

		vm.coupon = {
					"couponTitle"    : "couponTitle"
					,"couponStartDate": null
					,"couponEndDate"  : null //(new Date).add(1,'years')
					,"couponsInStock": 100
					,"couponTypeId"  : null
					,"couponMessage": "couponMessage"  
						,"couponPrice"  : 10
						,"imageFileName": "UnlockSubmitButton"	  
		};

		vm.user = null;
		vm.createCoupon = createCoupon;
		vm.uploadFile = uploadFile;

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


			vm.selectedFile = document.getElementById('imageFileName').files[0];

			
			uploadFile(vm.selectedFile);
			
			vm.coupon;

			vm.coupon;


		}

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





	}

})();