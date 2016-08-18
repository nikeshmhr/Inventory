/* global angular */

angular.module('Inventory').controller('PartyUpdateModalCtrl', PartyUpdateModalCtrl);

PartyUpdateModalCtrl.$inject = [
    '$scope',
    '$uibModalInstance',
    'party'
];

function PartyUpdateModalCtrl($scope, $uibModalInstance, party) {

    var vm = $scope;

    vm.party = party;

    $scope.ok = function () {
        $uibModalInstance.close(vm.party);
    };

    $scope.cancel = function () {
        $uibModalInstance.dismiss();
    };

}