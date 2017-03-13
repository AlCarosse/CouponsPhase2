(function () {
	'use strict';

	angular
	.module('app')
	.factory('CouponsService', CouponsService);

	CouponsService.$inject = [ '$http', '$cookies', '$rootScope', '$timeout'];
	function CouponsService( $http, $cookies, $rootScope, $timeout) 
	{
		var service = {};

		service.UploadImage       = UploadImage;
		service.CreateCoupon      = CreateCoupon;
		service.GetCompanyCoupons = GetCompanyCoupons;
		service.DeleteCoupon      = DeleteCoupon;
		

		return service;		


		function DeleteCoupon( couponId , callback) 
		{
			$http.delete('/CouponsPhase2/rest/api/coupons/deleteCoupon/couponId/' + couponId
			)
			.then(function (response) {
				callback(response);
			});
		}

		
		
		function GetCompanyCoupons( callback) 
		{
			$http.get('/CouponsPhase2/rest/api/coupons/getCompanyCoupons/'
			)
			.then(function (response) {
				callback(response);
			});
		}

		
		
		function CreateCoupon( coupon , callback) 
		{
			$http.post('/CouponsPhase2/rest/api/coupons/createCoupon/', coupon
			)
			.then(function (response) {
				callback(response);
			});
		}

		function UploadImage( file , callback) 
		{

			var fd = new FormData();
			fd.append("file", file);
			
			$http.post("/CouponsPhase2/UploadCouponImageFileServlet", fd, { withCredentials: true, headers: { 'Content-Type': undefined }, transformRequest: angular.identity})
			.then(function (response) {
				callback(response);
			});			
		}

	
		
	}
})();
