/* global angular */

angular.module('Inventory').controller('ItemController', ItemController);

ItemController.$inject = [
    'ItemService',
    '$rootScope',
    '$localStorage'
];

function ItemController(ItemService, $rootScope, $localStorage) {

    var vm = this;

    // STORES ERROR MESSAGE
    vm.errorMessage = '';

    // STORES SUCCESS MESSAGE
    vm.successMessage = '';

    // STORES Item FORM (AND EXTRA) DATA
    vm.itemData = {
        itemName: '',
        salePrice: '',
        purchasePrice: '',
        createdDate: ''
    };

    /**********************************************************
     ***** FUNCTION DECLARATION AND INITIALIZATION STARTS *****
     *********************************************************/

    vm.addItem = addItem;

    /********************************************************
     ***** FUNCTION DECLARATION AND INITIALIZATION ENDS *****
     ********************************************************/


    /**********************************************************
     ************* FUNCTION DEFINITION STARTS *****************
     *********************************************************/

    function addItem() {
        vm.itemData.createdDate = new Date();
        ItemService.addItem(vm.itemData)
                .then(
                        function (data) {
                            console.log("Item added successfully." + data);
                        },
                        function (error) {
                            console.log("Error while adding item." + error.data);
                        }
                );
    }

    /**********************************************************
     ************* FUNCTION DEFINITION ENDS *****************
     *********************************************************/
}