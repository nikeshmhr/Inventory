/* global angular */

angular.module('Inventory').controller('TransactionDetailController', TransactionDetailController);

TransactionDetailController.$inject = [
    'TransactionService',
    'ObjEqualityCheckService',
    'TransactionTypeService',
    'PartyService',
    'ItemService',
    '$uibModal',
    '$rootScope',
    '$localStorage',
    '$anchorScroll'
];

function TransactionDetailController(TransactionService, ObjEqualityCheckService,
        TransactionTypeService, PartyService, ItemService, $uibModal, $rootScope,
        $localStorage, $anchorScroll) {

    var vm = this;

    /***************************************************************************
     ********************VARIABLE DECLARATION STARTS****************************
     **************************************************************************/

    // STORES LIST OF TRANSACTIONS
    vm.transactions = [];

    // STORES LIST OF TRANSACTION TYPES
    vm.transactionTypes = [];

    // STORES LIST OF PARTIES
    vm.parties = [];

    // STORES LIST OF ITEMS
    vm.items = [];

    /**********************************************************
     ******************* FOR PAGINATION ***********************
     *********************************************************/
    vm.viewby = 5;
    vm.totalItems = (vm.transactions).length;
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


    /***************************************************************************
     ********************FUNCATION DECLARATION STARTS***************************
     **************************************************************************/

    vm.fetchAllTransactions = fetchAllTransactions;

    vm.fetchTransactionTypes = fetchTransactionTypes;

    vm.getTransactionTypeNameById = getTransactionTypeNameById;

    vm.fetchAllParties = fetchAllParties;

    vm.getPartyById = getPartyById;

    vm.detailAction = detailAction;

    vm.fetchAllItems = fetchAllItems;


    /**
     * INITIAL FUNCTION CALLS.
     */
    vm.fetchAllTransactions();

    vm.fetchTransactionTypes();

    vm.fetchAllParties();

    vm.fetchAllItems();

    /***************************************************************************
     ********************FUNCTION DEFINITION STARTS*****************************
     **************************************************************************/

    function fetchAllTransactions() {
        vm.transactions = [];

        TransactionService.fetchTransactions()
                .then(
                        function (data) {
                            vm.transactions = data;
                        },
                        function (err) {
                            // ERROR
                        }
                );
    }

    function fetchTransactionTypes() {
        vm.transactionTypes = [];

        TransactionTypeService.fetchTransactionTypes()
                .then(
                        function (data) {
                            vm.transactionTypes = data;
                        },
                        function (err) {
                            // ERROR
                        }
                );
    }

    function getTransactionTypeNameById(typeId) {
        for (var i = 0; i < vm.transactionTypes.length; i++) {
            if (typeId == vm.transactionTypes[i].id) {
                return vm.transactionTypes[i].name;
            }
        }

        return null;
    }

    function fetchAllParties() {
        vm.parties = [];

        PartyService.fetchParties()
                .then(
                        function (data) {
                            vm.parties = data;
                        },
                        function (err) {
                            // ERROR
                        }
                );
    }

    function getPartyById(partyId) {
        for (var i = 0; i < vm.parties.length; i++) {
            if (partyId == vm.parties[i].id) {
                return vm.parties[i];
            }
        }

        return null;
    }

    function detailAction(transaction) {
        var extras = {};

        var partyName = vm.getPartyById(transaction.transaction.party).name;
        var transactionType = vm.getTransactionTypeNameById(transaction.transaction.transactionType);

        extras.partyName = partyName;
        extras.transactionType = transactionType;
        extras.items = vm.items;

        var modalInstance = $uibModal.open({
            size: 'lg',
            templateUrl: 'app/components/transaction/modal/transactionDetail.html',
            controller: 'TransactionDetailModalCtrl',
            backdrop: false,
            resolve: {
                transaction: function () {
                    return transaction;
                },
                extras: function () {
                    return extras;
                }
            }
        });
        modalInstance.result
                .then(
                        function () {
                            console.log("Modal dismissed at " + new Date());
                        }
                );
        $anchorScroll();
    }

    function fetchAllItems() {
        vm.items = [];

        ItemService.fetchItems()
                .then(
                        function (data) {
                            vm.items = data;
                        },
                        function (err) {
                            // ERROR
                        }
                );
    }


}