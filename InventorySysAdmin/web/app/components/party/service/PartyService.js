/* global angular */

angular.module('Inventory').factory('PartyService', PartyService);

PartyService.$inject = ['HttpService'];

function PartyService(HttpService) {

    var vm = this;

    var resourceURI = "api/parties";

    return {
        addParty: function (partyData) {
            return HttpService.post(resourceURI, partyData);
        },
        fetchParties: function () {
            return HttpService.get(resourceURI);
        },
        updateParty: function (partyData) {
            return HttpService.put(resourceURI + '/' + partyData.id, partyData);
        },
        deleteParty: function (partyId) {
            return HttpService.delete(resourceURI + '/' + partyId);
        }
    };

}