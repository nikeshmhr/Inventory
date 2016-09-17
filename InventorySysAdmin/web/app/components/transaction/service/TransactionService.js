/* global angular */

angular.module('Inventory').factory('TransactionService', TransactionService);

TransactionService.$inject = ['HttpService', 'APIResourceConstantService'];

function TransactionService(HttpService, APIResourceConstantService) {

    var vm = this;

    vm.resourceURI = APIResourceConstantService.TRANSACTION_RESOURCE_URL();

    return {
        addTransaction: function (transactionData) {
            return HttpService.post(vm.resourceURI, transactionData);
        },
        fetchTransactions: function () {
            return HttpService.get(vm.resourceURI);
        }
        /*,
         updateTransaction: function (partyData) {
         return HttpService.put(vm.resourceURI + '/' + partyData.id, partyData);
         },
         deleteParty: function (partyId) {
         return HttpService.delete(vm.resourceURI + '/' + partyId);
         }*/
    };

}