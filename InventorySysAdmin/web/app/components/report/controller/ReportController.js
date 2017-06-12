/* global angular */

angular.module('Inventory').controller('ReportController', ReportController);

ReportController.$inject = [
    'ReportService',
    'ItemService',
    'TransactionTypeService',
    '$rootScope',
    '$localStorage',
    '$anchorScroll'
];

function ReportController(ReportService, ItemService, TransactionTypeService, $rootScope, $localStorage, $anchorScroll) {
    var vm = this;

    vm.items = [];

    vm.transactionTypes = [];

    vm.reportExportParams = {
        fromDate: '',
        toDate: '',
        transactionTypeId: '',
        itemId: 0,
        fileType: 'excel'
    };

    vm.isFromDateOpened = false;

    vm.isToDateOpened = false;

    // STORES ALL ALERT MESSAGES (EITHER SUCCESS OR ERROR)
    vm.alerts = [];

    $anchorScroll();

    vm.fetchItems = fetchItems;

    vm.getItemById = getItemById;

    vm.fetchTransactionTypes = fetchTransactionTypes;

    vm.exportAsExcel = exportAsExcel;

    vm.closeAlert = closeAlert;

    vm.exportAllAsExcel = exportAllAsExcel;


    vm.fetchItems();

    vm.fetchTransactionTypes();

    function fetchItems() {
        vm.items = [];

        ItemService.fetchItems()
                .then(
                        function (data) {
                            vm.items = data;
                            vm.items.push({id: 0, itemName: "ALL"});
                        },
                        function (err) {

                        }
                );
    }

    function getItemById(itemId) {
        for (var i = 0; i < vm.items.length; i++) {
            if (itemId == vm.items[i].id) {
                return vm.items[i];
            }
        }

        return null;
    }

    function fetchTransactionTypes() {
        vm.transactionTypes = [];

        TransactionTypeService.fetchTransactionTypes()
                .then(
                        function (data) {
                            for (i in data) {
                                if (data[i].id <= 2) {
                                    vm.transactionTypes.push(data[i]);
                                }
                            }
                        },
                        function (err) {
                        }
                );
    }

    function exportAsExcel() {

        if (vm.reportExportParams.itemId == 0) {
            // Means export all
            vm.exportAllAsExcel();
        } else {
            var txnTypeName = getTransactionTypeNameById(vm.reportExportParams.transactionTypeId);
            ReportService.exportAsExcel(vm.reportExportParams)
                    .then(
                            function (data) {
                                var file = new Blob([data], {
                                    type: 'application/vnd.ms-excel'
                                });

                                var fileURL = URL.createObjectURL(file);
                                var a = document.createElement('a');
                                a.href = fileURL;
                                a.target = '_blank';
                                a.download = txnTypeName + '.xls';
                                document.body.appendChild(a);
                                a.click();

                                resetAction();
                            },
                            function (err) {
                                vm.alerts.push({type: 'danger', msg: "Transaction not found."});
                            }
                    );
        }
    }

    function resetAction() {
        vm.reportExportParams = {
            fromDate: '',
            toDate: '',
            transactionTypeId: '',
            itemId: 0,
            fileType: 'excel'
        };
    }

    /**
     * Function to remove the alert message object by removing the party of index(th) position
     * @param {type} index The position of the alert object to remove from the message.
     * @returns {undefined}
     */
    function closeAlert(index) {
        vm.alerts.splice(index, 1);
    }

    function exportAllAsExcel() {
        var txnTypeName = getTransactionTypeNameById(vm.reportExportParams.transactionTypeId);

        ReportService.exportAllAsExcel(vm.reportExportParams)
                .then(
                        function (data) {
                            var file = new Blob([data], {
                                type: 'application/vnd.ms-excel'
                            });

                            var fileURL = URL.createObjectURL(file);
                            var a = document.createElement('a');
                            a.href = fileURL;
                            a.target = '_blank';
                            a.download = txnTypeName + '.xls';
                            document.body.appendChild(a);
                            a.click();

                            resetAction();
                        },
                        function (err) {
                            vm.alerts.push({type: 'danger', msg: "Transaction not found."});
                        }
                );
    }

    function getTransactionTypeNameById(txnTypeId) {
        for (var i = 0; i < vm.transactionTypes.length; i++) {
            if (txnTypeId == vm.transactionTypes[i].id) {
                return vm.transactionTypes[i].name;
            }
        }
    }

}