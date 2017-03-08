(function () {
	'use strict';

	angular
	.module('app')
	.factory('CouponsService', CouponsService);

	CouponsService.$inject = [ '$http', '$cookies', '$rootScope', '$timeout'];
	function CouponsService( $http, $cookies, $rootScope, $timeout) {

		var service = {};

		service.UploadImage = UploadImage;
		service.CreateCoupon = CreateCoupon;

		return service;
/*
 * 				"couponTitle"    : "couponTitle"
					,"couponStartDate": null
					,"couponEndDate"  : null //(new Date).add(1,'years')
					,"couponsInStock": 100
					,"couponTypeId"  : null
					,"couponMessage": "couponMessage"  
						,"couponPrice"  : 10
 */
		
		
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
			
			$http.post("/CouponsPhase2/UploadServlet", fd, { withCredentials: true, headers: { 'Content-Type': undefined }, transformRequest: angular.identity})
			.then(function (response) {
				callback(response);
			});

			//callback(response);
			
			/*
			var xhr = createXhr("POST", "/CouponsPhase2/UploadServlet");


			xhr.open(method, url, true);
			forEach(headers, function(value, key) {
				if (isDefined(value)) {
					xhr.setRequestHeader(key, value);
				}
			});

			xhr.onload = function requestLoaded() {
				var statusText = xhr.statusText || '';

				// responseText is the old-school way of retrieving response (supported by IE9)
				// response/responseType properties were introduced in XHR Level2 spec (supported by IE10)
				var response = ('response' in xhr) ? xhr.response : xhr.responseText;
			}
withCredentials: true,
			 */
			
			//$http.post("/CouponsPhase2/UploadServlet", fd, { transformRequest: angular.identity, headers: { 'Content-Type': undefined }  });
			

			
			/*
			var request = $http({
                method: "post",
                url: "/CouponsPhase2/UploadServlet",
                transformRequest: angular.identity,
                headers: { 'Content-Type': undefined } 

            });
			
			
			$scope.upload = $upload.upload({
                url: '/CouponsPhase2/UploadServlet',
                method: 'POST',
                file: file
            });
			
			var fd = new FormData();
			
            */
			
		}

	}

})();
