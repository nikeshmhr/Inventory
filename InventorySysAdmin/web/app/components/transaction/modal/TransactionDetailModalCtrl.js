/* global angular */

angular.module('Inventory').controller('TransactionDetailModalCtrl', TransactionDetailModalCtrl);

TransactionDetailModalCtrl.$inject = [
    '$scope',
    '$uibModalInstance',
    'transaction',
    'extras'
];

function TransactionDetailModalCtrl($scope, $uibModalInstance, transaction, extras) {

    var vm = $scope;

    vm.transaction = transaction;

    vm.extras = extras;

    vm.getItemById = getItemById;

    function getItemById(itemId) {
        for (var i = 0; i < vm.extras.items.length; i++) {
            if (itemId == vm.extras.items[i].id) {
                return vm.extras.items[i];
            }
        }

        return null;
    }

    $scope.ok = function () {
        $uibModalInstance.close();
    };

    $scope.cancel = function () {
        $uibModalInstance.dismiss();
    };

}