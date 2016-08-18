/* global angular */

angular.module('Inventory').factory('ItemService', ItemService);

ItemService.$inject = ['HttpService'];

function ItemService(HttpService) {

    var vm = this;

    var resourceURI = "api/items";

    return {
        addItem: function (itemData) {
            return HttpService.post(resourceURI, itemData);
        },
        fetchItems: function () {
            return HttpService.get(resourceURI);
        },
        updateItem: function (itemData) {
            return HttpService.put(resourceURI + '/' + itemData.id, itemData);
        },
        deleteItem: function (itemId) {
            return HttpService.delete(resourceURI + '/' + itemId);
        }
    };

}