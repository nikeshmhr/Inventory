/* global angular */

angular.module('Inventory').factory('APIResourceConstantService', APIResourceConstantService);

APIResourceConstantService.$inject = [];

function APIResourceConstantService() {

    var vm = this;

    vm.PREFIX = "/api";

    vm.PARTY_BASE = vm.PREFIX + "/parties";

    vm.ITEM_BASE = vm.PREFIX + "/items";

    vm.TRANSACTION_BASE = vm.PREFIX + "/transactions";

    vm.TRANSACTION_TYPE_BASE = vm.PREFIX + "/transaction-types";

    vm.ITEM_STOCK_BASE = vm.PREFIX + "/item-stocks";

    return {
        PARTY_RESOURCE_URL: function () {
            return vm.PARTY_BASE;
        },
        ITEM_RESOURCE_URL: function () {
            return vm.ITEM_BASE;
        },
        TRANSACTION_RESOURCE_URL: function () {
            return vm.TRANSACTION_BASE;
        },
        TRANSACTION_TYPE_URL: function () {
            return vm.TRANSACTION_TYPE_BASE;
        },
        ITEM_STOCK_BASE: function () {
            return vm.ITEM_STOCK_BASE;
        }
    };
}

