/* global angular */

angular.module('Inventory').controller('PartyController', PartyController);

PartyController.$inject = [
    'PartyService',
    'ObjEqualityCheckService',
    '$uibModal',
    '$rootScope',
    '$localStorage'
];

function PartyController(PartyService, ObjEqualityCheckService, $uibModal, $rootScope, $localStorage) {

    var vm = this;

    /************************* STARTS *************************
     ********* VARIABLE DECLARATION AND INITIALIZATION ********
     *********************************************************/

    // STORES Party FORM (AND EXTRA) DATA
    vm.partyData = {
        name: '',
        phoneNumber: '',
        email: '',
        currentBalance: 0,
        balanceAsOfDate: new Date(),
        partyType: 'P'
    };

    // STORES ALL ALERT MESSAGES (EITHER SUCCESS OR ERROR)
    vm.alerts = [];

    // STORES LIST OF Items
    vm.partyList = [];

    /************************* ENDS ***************************
     ********* VARIABLE DECLARATION AND INITIALIZATION ********
     *********************************************************/

    /**********************************************************
     ******************* FOR PAGINATION ***********************
     *********************************************************/
    vm.viewby = 5;
    vm.totalItems = (vm.partyList).length;
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

    vm.addParty = addParty;

    vm.resetMessages = resetMessages;

    vm.resetFormData = resetFormData;

    vm.closeAlert = closeAlert;

    vm.fetchParties = fetchParties;

    vm.editAction = editAction;

    vm.deleteAction = deleteAction;

    vm.updateParty = updateParty;

    vm.deleteParty = deleteParty;

    vm.pushAlert = pushAlert;

    /********************************************************
     ***** FUNCTION DECLARATION AND INITIALIZATION ENDS *****
     ********************************************************/


    vm.fetchParties();


    /**********************************************************
     ************* FUNCTION DEFINITION STARTS *****************
     *********************************************************/

    /**
     * Function to add party into system.
     * @param {type} formName Name of the form for the purpose of validation.
     * @returns {undefined}
     */
    function addParty(formName) {
        vm.partyData.createdDate = new Date();
        PartyService.addParty(vm.partyData)
                .then(
                        function (data) {
                            vm.resetMessages();
                            vm.alerts.push({type: 'success', msg: 'Party \'' + vm.partyData.name + '\' added successfully.'});
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
        vm.partyData = {
            name: '',
            phoneNumber: '',
            email: '',
            currentBalance: 0,
            balanceAsOfDate: new Date(),
            partyType: 'P'
        };

        form.$setPristine();
        form.$setUntouched();
    }

    /**
     * Function to remove the alert message object by removing the party of index(th) position
     * @param {type} index The position of the alert object to remove from the message.
     * @returns {undefined}
     */
    function closeAlert(index) {
        vm.alerts.splice(index, 1);
    }

    /**
     * Function to fetch all the available Party of the system.
     * @returns {undefined}
     */
    function fetchParties() {
        vm.partyList = [];
        PartyService.fetchParties()
                .then(
                        function (data) {
                            vm.partyList = data;
                            console.log("SUCCESSFULLY FETCHED PARTY LIST");
                        },
                        function (err) {
                            console.log("Error: " + err.data.errorMessage);
                        }
                );
    }

    /**
     * Function that opens up the update modal.
     * @param {type} party The Party object to display in the edit modal.
     * @returns {undefined}
     */
    function editAction(party) {
        vm.resetMessages();
        var original = angular.copy(party);

        var modalInstance = $uibModal.open({
            size: 'md',
            templateUrl: 'app/components/party/modal/updateParty.html',
            controller: 'PartyUpdateModalCtrl',
            backdrop: false,
            resolve: {
                party: function () {
                    return angular.copy(party);
                }
            }
        });
        modalInstance.result
                .then(
                        function (updatedParty) {
                            var equal = ObjEqualityCheckService.areObjExactlySame(original, updatedParty);

                            if (equal) {
                                vm.pushAlert('danger', 'Nothing has changed to update.');
                            } else {
                                // TO-DO - UPDATE PARTY WITH NEW INFORMATION
                                vm.updateParty(updatedParty);
                            }
                        }
                );
    }

    /**
     * Function that asks the user to confirm before calling detail action.
     * The action is taken upon user's input.
     * @param {type} party The party to delete.
     * @returns {undefined}
     */
    function deleteAction(party) {
        vm.resetMessages();
        var confirmStatus = confirm("Are you sure?");

        if (confirmStatus) {
            vm.deleteParty(party);
        }
    }

    /**
     * Function that updates the party.
     * @param {type} party The modified party that is to be used for update process.
     * @returns {undefined}
     */
    function updateParty(party) {
        PartyService.updateParty(party)
                .then(
                        function (data) {
                            vm.pushAlert('success', 'Party \'' + data.name + '\' updated successfully.');

                            // FETCH PARTY LIST AGAIN AFTER SUCCESSFUL UPDATE
                            vm.fetchParties();
                        },
                        function (err) {
                            vm.pushAlert('danger', 'Failed to update party \'' + party.name + '\'.');
                        }
                );
    }

    /**
     * Function that deletes the party.
     * @param {type} party The party to be deleted.
     * @returns {undefined}
     */
    function deleteParty(party) {
        PartyService.deleteParty(party.id)
                .then(
                        function (data) {
                            vm.pushAlert('success', 'Party deleted successfully.');

                            // FETCH PARTY LIST AFTER SUCCESSFUL DELETION
                            vm.fetchParties();
                        },
                        function (err) {
                            vm.pushAlert('danger', 'Failed to delete party \'' + party.name + '\'.');
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