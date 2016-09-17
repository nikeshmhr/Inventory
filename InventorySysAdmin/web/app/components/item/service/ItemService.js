/* global angular */

angular.module('Inventory').factory('ItemService', ItemService);

ItemService.$inject = ['HttpService', 'APIResourceConstantService'];

function ItemService(HttpService, APIResourceConstantService) {

    var vm = this;

    vm.resourceURI = APIResourceConstantService.ITEM_RESOURCE_URL();

    return {
        addItem: function (itemData) {
            return HttpService.post(vm.resourceURI, itemData);
        },
        fetchItems: function () {
            return HttpService.get(vm.resourceURI);
        },
        updateItem: function (itemData) {
            return HttpService.put(vm.resourceURI + '/' + itemData.id, itemData);
        },
        deleteItem: function (itemId) {
            return HttpService.delete(vm.resourceURI + '/' + itemId);
        }
    };

}