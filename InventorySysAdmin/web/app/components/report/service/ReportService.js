/* global angular */

angular.module('Inventory').factory('ReportService', ReportService);

ReportService.$inject = ['HttpService'];

function ReportService(HttpService) {

    var vm = this;

    vm.resourceURI = "api/transactions/export";

    return {
        exportAsExcel: function (exportRequestData) {
            return HttpService.postForExcel(vm.resourceURI, exportRequestData);
        },
        exportAllAsExcel: function (exportRequestData){
            return HttpService.postForExcel(vm.resourceURI + '/all', exportRequestData);
        }
    };

}