/* global angular */

angular.module('Inventory').controller('ItemUpdateModalCtrl', ItemUpdateModalCtrl);

ItemUpdateModalCtrl.$inject = [
    '$scope',
    '$uibModalInstance',
    'item'
];

function ItemUpdateModalCtrl($scope, $uibModalInstance, item) {

    var vm = $scope;

    vm.item = item;

    $scope.ok = function () {
        $uibModalInstance.close(vm.item);
    };

    $scope.cancel = function () {
        $uibModalInstance.dismiss();
    };

}