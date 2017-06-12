<!-- /.row -->
<div class="row">
    <div class="col-md-5">
        <h1 class="page-header">Reports</h1>

        <div uib-alert ng-repeat="alert in reportCtrl.alerts" type="{{alert.type}}" close ="reportCtrl.closeAlert($index)">
            {{alert.msg}}
        </div>

        <div class="form-group">
            <label>Transaction Type <required>*</required></label>
            <div>
                <select class="form-control" ng-model="reportCtrl.reportExportParams.transactionTypeId"
                        ng-options="transactionType.id as transactionType.name for transactionType in reportCtrl.transactionTypes"
                        required>
                    <option value="" ng-disabled="reportCtrl.reportExportParams.transactionTypeId > 0">----SELECT----</option>
                </select>
            </div>
        </div>

        <div class="form-group">
            <label>Item <required>*</required></label>
            <div>
                <select class="form-control" ng-model="reportCtrl.reportExportParams.itemId"
                        ng-options="item.id as item.itemName for item in reportCtrl.items"
                        required>
                    <option value="" ng-disabled="reportCtrl.reportExportParams.itemId >= 0">----SELECT----</option>
                </select>
            </div>
        </div>



        <div class="form-group">
            <label>From Date <required>*</required></label>
            <input class="form-control" pattern="^\d{4}-\d{2}-\d{2}$" type="text"
                   name="fromDate" ng-model="reportCtrl.reportExportParams.fromDate" 
                   uib-datepicker-popup is-open="reportCtrl.isFromDateOpened" ng-click="reportCtrl.isFromDateOpened = !reportCtrl.isFromDateOpened;"
                   close-text="Close" required />

            <!--            <input type="date" class="form-control" name="fromDate" ng-model="reportCtrl.reportExportParams.fromDate"
                               required />-->
        </div>

        <div class="form-group">
            <label>To Date <required>*</required></label>
            <input class="form-control" pattern="^\d{4}-\d{2}-\d{2}$" type="text"
                   name="toDate" ng-model="reportCtrl.reportExportParams.toDate" 
                   uib-datepicker-popup is-open="reportCtrl.isToDateOpened" ng-click="reportCtrl.isToDateOpened = !reportCtrl.isToDateOpened;"
                   close-text="Close" required />

            <!--            <input type="date" pattern="YYYY-MM-DD" class="form-control" name="toDate" ng-model="reportCtrl.reportExportParams.toDate"
                               required />-->
        </div>

        <button type="button" ng-click="reportCtrl.exportAsExcel()" class="btn btn-success" ng-disabled="exportReportForm.$invalid">Export</button>
    </div>
</div>