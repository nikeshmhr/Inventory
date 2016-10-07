/* global angular */

angular.module('Inventory').controller('StockController', StockController);

StockController.$inject = [
    'StockService',
    'ItemService',
    '$rootScope',
    '$localStorage',
    '$anchorScroll'
];

function StockController(StockService, ItemService, $rootScope, $localStorage, $anchorScroll) {
    var vm = this;

    vm.itemStocks = [];

    vm.items = [];

    $anchorScroll();

    vm.fetchItemStocks = fetchItemStocks;

    vm.fetchItems = fetchItems;

    vm.getItemById = getItemById;

    vm.fetchItemStocks();

    vm.fetchItems();

    function fetchItemStocks() {
        vm.itemStocks = [];

        StockService.fetchItemStocks()
                .then(
                        function (data) {
                            vm.itemStocks = data;
                        },
                        function (err) {

                        }
                );
    }

    function fetchItems() {
        vm.items = [];

        ItemService.fetchItems()
                .then(
                        function (data) {
                            vm.items = data;
                        },
                        function (err) {

                        }
                );
    }

    function getItemById(itemId) {
        for (var i = 0; i < vm.items.length; i++) {
            if (itemId == vm.items[i].id) {
                return vm.items[i];
            }
        }
        
        return null;
    }

}