angular.module('Inventory').controller('LoginController', LoginController);

LoginController.$inject = [
    '$rootScope',
    '$location',
    '$timeout',
    '$localStorage'
];

function LoginController($rootScope, $location, $timeout, $localStorage) {

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
            $rootScope.isLoggedIn = true;
            $location.path("/home");
        } else {
            vm.errorMessage = "Username or password incorrect.";
            alert(vm.errorMessage);
        }
    }
}