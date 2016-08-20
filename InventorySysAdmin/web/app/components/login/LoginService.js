/* global angular */

angular.module('Inventory').factory('LoginService', LoginService);

LoginService.$inject = ['HttpService', '$localStorage'];

function LoginService(HttpService, $localStorage) {

    var vm = this;

    vm.resourceURI = "login/";

    return {
        validateUser: function (credentials) {
            return HttpService.post(vm.resourceURI, credentials);
        },
        setLoggedIn: function (value) {
            $localStorage.isLoggedIn = value;
        },
        resetLoggedIn: function () {
            $localStorage.$reset();
        }
    };

}
