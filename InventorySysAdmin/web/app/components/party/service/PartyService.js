/* global angular */

angular.module('Inventory').factory('PartyService', PartyService);

PartyService.$inject = ['HttpService', 'APIResourceConstantService'];

function PartyService(HttpService, APIResourceConstantService) {

    var vm = this;

    vm.resourceURI = APIResourceConstantService.PARTY_RESOURCE_URL();

    return {
        addParty: function (partyData) {
            return HttpService.post(vm.resourceURI, partyData);
        },
        fetchParties: function () {
            return HttpService.get(vm.resourceURI);
        },
        updateParty: function (partyData) {
            return HttpService.put(vm.resourceURI + '/' + partyData.id, partyData);
        },
        deleteParty: function (partyId) {
            return HttpService.delete(vm.resourceURI + '/' + partyId);
        },
        fetchParitiesByPartyType: function (partyType) {
            return HttpService.get(vm.resourceURI + '/partyTypes/' + partyType);
        }
    };

}