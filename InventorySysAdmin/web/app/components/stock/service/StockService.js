/* global angular */

angular.module('Inventory').factory('StockService', StockService);

StockService.$inject = ['HttpService', 'APIResourceConstantService'];

function StockService(HttpService, APIResourceConstantService) {

    var vm = this;

    vm.resourceURI = APIResourceConstantService.ITEM_STOCK_BASE();

    return {
        fetchItemStocks: function () {
            return HttpService.get(vm.resourceURI);
        }
    };

}