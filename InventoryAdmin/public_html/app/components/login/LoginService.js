/* global angular */

angular.module('Inventory').factory('LoginService', LoginService);

LoginService.$inject = ['HttpService'];

function LoginService(HttpService) {

		var vm = this;

		vm.resourceURI = "login/";

		return {
				validateUser: function (credentials) {
          return HttpService.post(vm.resourceURI, credentials);
				}
		};

}
