/* global angular */

angular.module('Inventory').factory('ItemStockService', ItemStockService);

ItemStockService.$inject = ['HttpService', 'APIResourceConstantService'];

function ItemStockService(HttpService, APIResourceConstantService) {

    var vm = this;

    vm.resourceURI = APIResourceConstantService.ITEM_STOCK_BASE();

    return {
        fetchItemStocks: function () {
            return HttpService.get(vm.resourceURI);
        },
        saveItemStock: function (data) {
            return HttpService.post(vm.resourceURI, data);
        },
        updateItemStock: function (data) {
            return HttpService.put(vm.resourceURI, data);
        }
    };

}

