(function () {
    'use strict';

    angular
        .module('app')
        .factory('CompanysService', CompanysService);

    CompanysService.$inject = ['$http', '$cookies', '$rootScope', '$timeout'];
    function CompanysService($http, $cookies, $rootScope, $timeout) {

    	var service = {};

        service.DeleteCompany = DeleteCompany;
        service.CreateCompany = CreateCompany;
        service.GetAllCompanys = GetAllCompanys;
        
        return service;


        function DeleteCompany(i_companyId , callback) 
        {
            $http.delete('/CouponsPhase2/rest/api/companys/deleteCompany/companyId/' + i_companyId 
            )
                .then(function (response) {
                    callback(response);
                });
        }

        function CreateCompany(i_userName, i_loginName, i_loginPassword, i_companyName, i_companyEmail , callback) 
        {
            $http.post('/CouponsPhase2/rest/register/createCompany/',  { userName: i_userName, loginName: i_loginName, loginPassword: i_loginPassword, companyName: i_companyName , companyEmail: i_companyEmail} 
            )
                .then(function (response) {
                    callback(response);
                });
        }

        function GetAllCompanys(callback) 
        {
            $http.get('/CouponsPhase2/rest/api/companys/getAllCompanys/'
            )
                .then(function (response) {
                    callback(response);
                });
        }


    }

})();
