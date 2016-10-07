/* global angular */

angular.module('Inventory').controller('HomeController', HomeController);

HomeController.$inject = [
    '$rootScope',
    '$location',
    '$timeout',
    '$localStorage'
];

function HomeController($rootScope, $location, $timeout, $localStorage) {

    var vm = this;

    vm.tets = {};
}