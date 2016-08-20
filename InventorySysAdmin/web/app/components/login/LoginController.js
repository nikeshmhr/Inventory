/* global angular */

angular.module('Inventory').controller('LoginController', LoginController);

LoginController.$inject = [
    'LoginService',
    '$rootScope',
    '$location',
    '$route',
    '$timeout',
    '$localStorage'
];

function LoginController(LoginService, $rootScope, $location, $route, $timeout, $localStorage) {

    var vm = this;

    // STORES LOGIN INFO
    vm.loginData = {
        username: '',
        password: ''
    };

    vm.errorMessage = '';

    vm.login = login;

    function login() {
        if (vm.loginData.username === 'admin' && vm.loginData.password === 'admin') {
            $route.reload();
            LoginService.setLoggedIn(true);
            $location.path("/home");
        } else {
            LoginService.resetLoggedIn();
            $route.reload();
            vm.errorMessage = "Username or password incorrect.";
            alert(vm.errorMessage);
        }
    }
}