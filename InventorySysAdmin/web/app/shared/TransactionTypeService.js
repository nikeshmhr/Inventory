/* global angular */

angular.module('Inventory').factory('TransactionTypeService', TransactionTypeService);

TransactionTypeService.$inject = ['HttpService', 'APIResourceConstantService'];

function TransactionTypeService(HttpService, APIResourceConstantService) {

    var vm = this;

    vm.resourceURI = APIResourceConstantService.TRANSACTION_TYPE_URL();

    return {
        fetchTransactionTypes: function () {
            return HttpService.get(vm.resourceURI);
        },
        fetchTransactionTypeById: function (id) {
            return HttpService.get(vm.resourceURI + '/' + id);
        }
    };

}

