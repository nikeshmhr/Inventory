/* global angular */

angular.module('Inventory').controller('TransactionAddController', TransactionAddController);

TransactionAddController.$inject = [
    'PartyService',
    'ItemService',
    'TransactionService',
    'ObjEqualityCheckService',
    'TransactionTypeService',
    'ItemStockService',
    '$uibModal',
    '$rootScope',
    '$localStorage',
    '$anchorScroll'
];

function TransactionAddController(PartyService, ItemService, TransactionService,
        ObjEqualityCheckService, TransactionTypeService, ItemStockService,
        $uibModal, $rootScope, $localStorage, $anchorScroll) {

    var vm = this;

    /************************* STARTS *************************
     ********* VARIABLE DECLARATION AND INITIALIZATION ********
     *********************************************************/


    // STORES Transaction FORM (AND EXTRA) DATA
    vm.transaction = {
        id: '',
        transactionType: '',
        party: '',
        transactionDate: '',
        totalAmount: ''
    };

    // STORES TransactionDetail FORM DATA
    vm.transactionDetail = {
        id: '',
        transaction: '',
        billNo: '',
        discountPercent: '',
        discountAmount: '',
        taxPercent: '',
        taxAmount: '',
        subTotal: '',
        paidAmount: '',
        balance: ''
    };

    // TRANSACTION ITEM OBJECT THAT WILL BE USED TO MAKE COPY OF TransactionItem 
    // THAT IS TO BE PUSHED INTO transactionItems LIST
    vm.transactionItemTemplate = {
        objId: '',
        id: '',
        item: '',
        transactionDetail: '',
        quantity: '',
        amount: '',
        purchasePrice: '',
        salePrice: '',
        availableStock: ''
    };

    // STORES TransactionItem FORM DATA
    vm.transactionItems = [];

    // BUNDLES TRANSACTION INFORMATION.
    vm.singleTransaction = {
        transaction: null,
        transactionDetail: null,
        transactionItems: []
    };

    // STORES ALL ALERT MESSAGES (EITHER SUCCESS OR ERROR)
    vm.alerts = [];

    // STORES LIST OF Parties
    vm.partyList = [];

    // STORES LIST OF Items
    vm.itemList = [];

    // STORES TransactionType OPTIONS
    vm.transactionTypeOptions = [];

    // FOR TransactionDate DATEPICKER
    vm.isOpened = false;

    vm.PURCHASE = 1;

    vm.SALE = 2;

    vm.CASH_IN = 3;

    vm.CASH_OUT = 4;

    vm.DECIMAL_PLACE = 1;

    // STORES STOCK INFO OF ITEMS
    vm.itemStocks = [];


    /************************* ENDS ***************************
     ********* VARIABLE DECLARATION AND INITIALIZATION ********
     *********************************************************/

    /**********************************************************
     ******************* FOR PAGINATION ***********************
     *********************************************************/
//    vm.viewby = 5;
//    vm.totalItems = (vm.partyList).length;
//    vm.currentPage = 1;
//    vm.itemsPerPage = vm.viewby;
//    vm.maxSize = 5;
//    vm.perPageValues = [
//        {num: 'Five', value: 5},
//        {num: 'Ten', value: 10},
//        {num: 'Fifteen', value: 15}
//    ];
//
//    vm.setPage = function (pageNo) {
//        vm.currentPage = pageNo;
//    };
//
//    vm.setItemsPerPage = function (num) {
//        vm.itemsPerPage = num;
//        vm.currentPage = 1;
//    };


    /**********************************************************
     ***** FUNCTION DECLARATION AND INITIALIZATION STARTS *****
     *********************************************************/

    vm.addTransaction = addTransaction;

    vm.resetMessages = resetMessages;

    vm.resetFormData = resetFormData;

    vm.closeAlert = closeAlert;

    vm.fetchParties = fetchParties;

    vm.fetchItems = fetchItems;

    vm.pushAlert = pushAlert;

    vm.fetchTransactionTypes = fetchTransactionTypes;

    vm.addMoreItems = addMoreItems;

    vm.deleteItem = deleteItem;

    vm.itemChanged = itemChanged;

    vm.calculateItemTotalAmount = calculateItemTotalAmount;

    vm.resetTransactionData = resetTransactionData;

    vm.calculateSubTotal = calculateSubTotal;

    vm.calculateTotal = calculateTotal;

    vm.calculateRemainingBalance = calculateRemainingBalance;

    vm.calculateDiscount = calculateDiscount;

    vm.calculateTax = calculateTax;

    vm.fetchItemStocks = fetchItemStocks;

    vm.setNoOfAvailableStocks = setNoOfAvailableStocks;

    vm.showRemainingBalance = showRemainingBalance;

    vm.init = init;

    /********************************************************
     ***** FUNCTION DECLARATION AND INITIALIZATION ENDS *****
     ********************************************************/


    vm.init();


    /**********************************************************
     ************* FUNCTION DEFINITION STARTS *****************
     *********************************************************/

    /**
     * Function to add party into system.
     * @param {type} formName Name of the form for the purpose of validation.
     * @returns {undefined}
     */
    function addTransaction(formName) {
        vm.resetMessages();

        vm.singleTransaction.transaction = vm.transaction;
        vm.singleTransaction.transactionDetail = vm.transactionDetail;
        vm.singleTransaction.transactionItems = vm.transactionItems;

        TransactionService.addTransaction(vm.singleTransaction)
                .then(
                        function (data) {
                            vm.alerts.push({type: 'success', msg: 'Transaction added successfully.'});
                            vm.resetFormData(formName);
                            vm.init();
                        },
                        function (err) {
                            vm.alerts.push({type: 'danger', msg: err.data.errorMessage});
                        }
                );
        $anchorScroll();
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
        // STORES Transaction FORM (AND EXTRA) DATA
        vm.transaction = {
            id: '',
            transactionType: '',
            party: '',
            transactionDate: '',
            totalAmount: ''
        };

        // STORES TransactionDetail FORM DATA
        vm.transactionDetail = {
            id: '',
            transaction: '',
            billNo: '',
            discountPercent: '',
            discountAmount: '',
            taxPercent: '',
            taxAmount: '',
            subTotal: '',
            paidAmount: '',
            balance: ''
        };

        // STORES TransactionItem FORM DATA
        vm.transactionItems = [];

        // BUNDLES TRANSACTION INFORMATION.
        vm.singleTransaction = {
            transaction: null,
            transactionDetail: null,
            transactionItems: []
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
        var partyType = '';

        var transType = vm.transaction.transactionType;

        if (transType == vm.PURCHASE || transType == vm.CASH_OUT) { // FETCH PAYABLE PARTIES ONLY.
            partyType = 'P';
        } else if (transType == vm.SALE || transType == vm.CASH_IN) { // FETCH RECEIVABLE PARTIES ONLY.
            partyType = 'R';
        }

        PartyService.fetchParitiesByPartyType(partyType)
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
     * Function that fetches list of all available items from the system.
     * Items are fetched to show in dropdown for user to choose while adding transaction.
     * @returns {undefined}
     */
    function fetchItems() {
        vm.itemList = [];
        ItemService.fetchItems()
                .then(
                        function (response) {
                            vm.itemList = response;
                        },
                        function (err) {
                            console.log("Error: " + err.data.errorMessage);
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

    /**
     * Function that fetches the transaction types.
     * @returns {undefined}
     */
    function fetchTransactionTypes() {
        vm.transactionTypeOptions = [];
        TransactionTypeService.fetchTransactionTypes()
                .then(
                        function (data) {
                            vm.transactionTypeOptions = data;
                        },
                        function (err) {
                            console.log("Error: " + err.data.errorMessage);
                        }
                );
    }

    function addMoreItems() {
        var itemCopy = angular.copy(vm.transactionItemTemplate);
        itemCopy.objId = (Math.random() * 1000).toFixed(4);

        vm.transactionItems.push(itemCopy);
    }

    function deleteItem(objId) {
        for (var i = 0; i < vm.transactionItems.length; i++) {
            if (objId == vm.transactionItems[i].objId) {
                vm.transactionItems.splice(i, 1);
            }
        }

        // ALSO RECALCULATE SUB TOTAL
        vm.calculateSubTotal();
    }

    function itemChanged(itemObject) {
        for (var i = 0; i < vm.itemList.length; i++) {
            if (vm.itemList[i].id == itemObject.item) {
                // SET PURCHASE AND SALE PRICE.
                itemObject.purchasePrice = vm.itemList[i].purchasePrice;
                itemObject.salePrice = vm.itemList[i].salePrice;
            }
        }

        itemObject.quantity = '';
        itemObject.amount = '';

        vm.setNoOfAvailableStocks(itemObject);
    }

    function calculateItemTotalAmount(itemObject) {
        var quantity = itemObject.quantity;
        var unitPrice = 0;

        if (vm.transaction.transactionType == vm.PURCHASE) {
            unitPrice = itemObject.purchasePrice;
        } else if (vm.transaction.transactionType == vm.SALE) {
            unitPrice = itemObject.salePrice;
        }

        itemObject.amount = parseInt(quantity) * parseFloat(unitPrice);

        vm.calculateSubTotal();
//        alert("Q: " + quantity + ", U: " + unitPrice);
    }

    function resetTransactionData() {
        vm.transaction.totalAmount = '';
        vm.transaction.party = '';
        vm.transaction.transactionDate = '';

        // RESET TransactionDetail FORM DATA
        vm.transactionDetail = {
            id: '',
            transaction: '',
            billNo: '',
            discountPercent: '',
            discountAmount: '',
            taxPercent: '',
            taxAmount: '',
            subTotal: '',
            paidAmount: '',
            balance: ''
        };

        // RESET TransactionItem FORM DATA
        vm.transactionItems = [];

        // ALSO SHOW FIRST ROW, DEFAULT
        vm.addMoreItems();
    }

    function calculateSubTotal() {
        var subTotal = 0;
        for (var i = 0; i < vm.transactionItems.length; i++) {
            if (vm.transactionItems[i].amount > 0) {
                subTotal += vm.transactionItems[i].amount;
            }
        }

        // alert(isNaN(subTotal));
        vm.transactionDetail.subTotal = subTotal;

        // ALSO CALCULATE THE TOTAL AMOUNT
        vm.calculateTotal();
    }

    function calculateTotal() {
        if (vm.transaction.transactionType == vm.CASH_IN || vm.transaction.transactionType == vm.CASH_OUT) {
            return;
        }
        var total = vm.transactionDetail.subTotal;

        // CALCULATE DISCOUNT % OR DISCOUNT AMOUNT
        // SUBSTRACT DISCOUNT AMOUNT
        var discount = 0;
        calculateDiscount();
        discount = vm.transactionDetail.discountAmount;

        // CALCULATE TAX % OR TAX AMOUNT
        // ADD TAX AMOUNT
        var tax = 0;
        calculateTax();
        tax = vm.transactionDetail.taxAmount;

        if (discount > 0) {
            total -= discount;
        }

        if (tax > 0) {
            total += tax;
        }

        vm.transaction.totalAmount = parseFloat(total.toFixed(vm.DECIMAL_PLACE));

        // ALSO CALCULATE REMAINING BALANCE
        vm.calculateRemainingBalance();
    }

    function calculateRemainingBalance() {
        if (vm.transaction.transactionType == vm.SALE || vm.transaction.transactionType == vm.PURCHASE) {
            if (vm.transactionDetail.paidAmount > 0) {
                vm.transactionDetail.balance = parseFloat((vm.transaction.totalAmount - vm.transactionDetail.paidAmount).toFixed(vm.DECIMAL_PLACE));
            } else {
                vm.transactionDetail.balance = parseFloat(vm.transaction.totalAmount.toFixed(vm.DECIMAL_PLACE));
            }
        }
    }

    function calculateDiscount() {
        if (vm.transactionDetail.discountAmount != '') {
            var discount = (100 / vm.transactionDetail.subTotal) * vm.transactionDetail.discountAmount;

            if (vm.transactionDetail.discountPercent == '' || vm.transactionDetail.discountPercent == 0) {
                vm.transactionDetail.discountPercent = parseFloat(discount.toFixed(vm.DECIMAL_PLACE));
            }
        } else if (vm.transactionDetail.discountPercent != '') {
            var discount = (vm.transactionDetail.discountPercent / 100) * vm.transactionDetail.subTotal;

            if (vm.transactionDetail.discountAmount == '' || vm.transactionDetail.discountAmount == 0) {
                vm.transactionDetail.discountAmount = parseFloat(discount.toFixed(vm.DECIMAL_PLACE));
            }
        }
    }

    function calculateTax() {
        if (vm.transactionDetail.taxAmount != '') {
            var tax = (100 / vm.transactionDetail.subTotal) * vm.transactionDetail.taxAmount;

            if (vm.transactionDetail.taxPercent == '' || vm.transactionDetail.taxPercent == 0) {
                vm.transactionDetail.taxPercent = parseFloat(tax.toFixed(vm.DECIMAL_PLACE));
            }
        } else if (vm.transactionDetail.taxPercent != '') {
            var tax = (vm.transactionDetail.taxPercent / 100) * vm.transactionDetail.subTotal;

            if (vm.transactionDetail.taxAmount == '' || vm.transactionDetail.taxAmount == 0) {
                vm.transactionDetail.taxAmount = parseFloat(tax.toFixed(vm.DECIMAL_PLACE));
            }
        }
    }

    function fetchItemStocks() {
        vm.itemStocks = [];

        ItemStockService.fetchItemStocks()
                .then(
                        function (data) {
                            vm.itemStocks = data;
                        },
                        function (err) {

                        }
                );
    }

    function setNoOfAvailableStocks(item) {
        for (var i = 0; i < vm.itemStocks.length; i++) {
            if (item.item == vm.itemStocks[i].item) {
                item.availableStock = vm.itemStocks[i].availableNoOfStocks;
                break;
            } else {
                item.availableStock = 0;
            }
        }
    }

    function showRemainingBalance() {
        if (vm.transaction.transactionType == vm.CASH_IN || vm.transaction.transactionType == vm.CASH_OUT) {
            for (var i = 0; i < vm.partyList.length; i++) {
                if (vm.transaction.party == vm.partyList[i].id) {
                    vm.transactionDetail.balance = vm.partyList[i].currentBalance;
                    break;
                } else {
                    vm.transactionDetail.balance = 0;
                }
            }
        }
    }

    function init() {
        vm.fetchTransactionTypes();

        // vm.fetchParties();

        vm.fetchItems();

        vm.addMoreItems();

        vm.fetchItemStocks();

        $anchorScroll();
    }


    /**********************************************************
     ************* FUNCTION DEFINITION ENDS *****************
     *********************************************************/
}