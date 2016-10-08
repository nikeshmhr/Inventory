/* global angular */

angular.module('Inventory').factory('HelperService', HelperService);

HelperService.$inject = [];

function HelperService() {

    var vm = this;

    return {
        findObjectInArrayById: function (id, arrayObj) {
            for (var i = 0; i < arrayObj.length; i++) {
                if (id == arrayObj[i].id) {
                    return arrayObj[i];
                }
            }

            return null;
        }
    };

}