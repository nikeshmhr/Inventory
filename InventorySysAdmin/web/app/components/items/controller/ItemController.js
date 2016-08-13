/* global angular */

angular.module('Inventory').controller('ItemController', ItemController);

ItemController.$inject = [
    'ItemService',
    'ObjEqualityCheckService',
    '$uibModal',
    '$rootScope',
    '$localStorage'
];

function ItemController(ItemService, ObjEqualityCheckService, $uibModal, $rootScope, $localStorage) {

    var vm = this;

    /************************* STARTS *************************
     ********* VARIABLE DECLARATION AND INITIALIZATION ********
     *********************************************************/

    // STORES Item FORM (AND EXTRA) DATA
    vm.itemData = {
        itemName: '',
        salePrice: '',
        purchasePrice: '',
        createdDate: ''
    };

    // STORES ALL ALERT MESSAGES (EITHER SUCCESS OR ERROR)
    vm.alerts = [];

    // STORES LIST OF Items
    vm.itemList = [];

    /************************* ENDS ***************************
     ********* VARIABLE DECLARATION AND INITIALIZATION ********
     *********************************************************/

    /**********************************************************
     ******************* FOR PAGINATION ***********************
     *********************************************************/
    vm.viewby = 5;
    vm.totalItems = (vm.itemList).length;
    vm.currentPage = 1;
    vm.itemsPerPage = vm.viewby;
    vm.maxSize = 5;
    vm.perPageValues = [
        {num: 'Five', value: 5},
        {num: 'Ten', value: 10},
        {num: 'Fifteen', value: 15}
    ];

    vm.setPage = function (pageNo) {
        vm.currentPage = pageNo;
    };

    vm.setItemsPerPage = function (num) {
        vm.itemsPerPage = num;
        vm.currentPage = 1;
    };


    /**********************************************************
     ***** FUNCTION DECLARATION AND INITIALIZATION STARTS *****
     *********************************************************/

    vm.addItem = addItem;

    vm.resetMessages = resetMessages;

    vm.resetFormData = resetFormData;

    vm.closeAlert = closeAlert;

    vm.fetchItems = fetchItems;

    vm.editAction = editAction;

    vm.deleteAction = deleteAction;

    vm.updateItem = updateItem;

    vm.deleteItem = deleteItem;

    vm.pushAlert = pushAlert;

    /********************************************************
     ***** FUNCTION DECLARATION AND INITIALIZATION ENDS *****
     ********************************************************/


    vm.fetchItems();


    /**********************************************************
     ************* FUNCTION DEFINITION STARTS *****************
     *********************************************************/

    /**
     * Function to add item into system.
     * @param {type} formName Name of the form for the purpose of validation.
     * @returns {undefined}
     */
    function addItem(formName) {
        vm.itemData.createdDate = new Date();
        ItemService.addItem(vm.itemData)
                .then(
                        function (data) {
                            vm.resetMessages();
                            vm.alerts.push({type: 'success', msg: 'Item \'' + vm.itemData.itemName + '\' added successfully.'});
                            vm.resetFormData(formName);
                        },
                        function (error) {
                            vm.errorMessage = error.data.errorMessage;
                        }
                );
    }

    /**
     * Function to reset all the alert messages.
     * @returns {undefined}
     */
    function resetMessages() {
        vm.alerts = [];
    }

    /**
     * Function to reset the form data and also make the form $Pristine and $Untouched
     * @param {type} form The name of the form to reset.
     * @returns {undefined}
     */
    function resetFormData(form) {
        // RESET TO THE INITIAL VALUES.
        vm.itemData = {
            itemName: '',
            salePrice: '',
            purchasePrice: '',
            createdDate: ''
        };

        form.$setPristine();
        form.$setUntouched();
    }

    /**
     * Function to remove the alert message object by removing the item of index(th) position
     * @param {type} index The position of the alert object to remove from the message.
     * @returns {undefined}
     */
    function closeAlert(index) {
        vm.alerts.splice(index, 1);
    }

    /**
     * Function to fetch all the available item of the system.
     * @returns {undefined}
     */
    function fetchItems() {
        vm.itemList = [];
        ItemService.fetchItems()
                .then(
                        function (data) {
                            vm.itemList = data;
                            console.log("SUCCESSFULLY FETCHED ITEM LIST");
                        },
                        function (err) {
                            console.log("Error: " + err.data.errorMessage);
                        }
                );
    }

    /**
     * Function that opens up the update modal.
     * @param {type} item The Item object to display in the edit modal.
     * @returns {undefined}
     */
    function editAction(item) {
        vm.resetMessages();
        var original = angular.copy(item);

        var modalInstance = $uibModal.open({
            size: 'md',
            templateUrl: 'app/components/items/modal/updateItem.html',
            controller: 'ItemUpdateModalCtrl',
            backdrop: false,
            resolve: {
                item: function () {
                    return angular.copy(item);
                }
            }
        });
        modalInstance.result
                .then(
                        function (updatedItem) {
                            var equal = ObjEqualityCheckService.areObjExactlySame(original, updatedItem);

                            if (equal) {
                                vm.pushAlert('danger', 'Nothing has changed to update.');
                            } else {
                                // TO-DO - UPDATE ITEM WITH NEW INFORMATION
                                vm.updateItem(updatedItem);
                            }
                        }
                );
    }

    /**
     * Function that asks the user to confirm before calling detail action.
     * The action is taken upon user's input.
     * @param {type} item The item to delete.
     * @returns {undefined}
     */
    function deleteAction(item) {
        vm.resetMessages();
        var confirmStatus = confirm("Are you sure?");

        if (confirmStatus) {
            vm.deleteItem(item);
        }
    }

    /**
     * Function that updates the item.
     * @param {type} item The modified item that is to be used for update process.
     * @returns {undefined}
     */
    function updateItem(item) {
        ItemService.updateItem(item)
                .then(
                        function (data) {
                            vm.pushAlert('success', 'Item \'' + data.itemName + '\' updated successfully.');

                            // FETCH ITEM LIST AGAIN AFTER SUCCESSFUL UPDATE
                            vm.fetchItems();
                        },
                        function (err) {
                            vm.pushAlert('danger', 'Failed to update item \'' + item.itemName + '\'.');
                        }
                );
    }

    /**
     * Function that deletes the item.
     * @param {type} item The item to be deleted.
     * @returns {undefined}
     */
    function deleteItem(item) {
        ItemService.deleteItem(item.id)
                .then(
                        function (data) {
                            vm.pushAlert('success', 'Item deleted successfully.');

                            // FETCH ITEM LIST AFTER SUCCESSFUL DELETION
                            vm.fetchItems();
                        },
                        function (err) {
                            vm.pushAlert('danger', 'Failed to delete item \'' + item.itemName + '\'.');
                        }
                );
    }

    /**
     * Function that pushes the alert message into an array.
     * @param {type} type The type of the message (i.e: success, danger, warning)
     * @param {type} msg The message to be displayed.
     * @returns {undefined}
     */
    function pushAlert(type, msg) {
        vm.alerts.push({type: type, msg: msg});
    }

    /**********************************************************
     ************* FUNCTION DEFINITION ENDS *****************
     *********************************************************/
}