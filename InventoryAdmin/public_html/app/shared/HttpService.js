/* global angular */

angular.module('Inventory').factory('HttpService', HttpService);

HttpService.$inject = ['$http', '$q', '$location'];

function HttpService($http, $q, $location) {

    var vm = this;
    vm.url = "http://127.0.0.1:8080/inventory-api/"; //$location.absUrl().split('#')[0];

    return {
        get: function (resourceURI) {
            return $http.get(vm.url + resourceURI)
                    .then(
                            function (resp) {
                                return resp.data;
                            },
                            function (err) {
                                return $q.reject(err);
                            }
                    );
        },
        post: function (resourceURI, data) {
            return $http.post(vm.url + resourceURI, data)
                    .then(
                            function (resp) {
                                return resp.data;
                            },
                            function (err) {
                                return $q.reject(err);
                            }
                    );
        },
        put: function (resourceURI, data) {
            return $http.put(vm.url + resourceURI, data)
                    .then(
                            function (resp) {
                                return resp.data;
                            },
                            function (err) {
                                return $q.reject(err);
                            }
                    );
        },
        delete: function (resourceURI) {
            return $http.delete(vm.url + resourceURI)
                    .then(
                            function (resp) {
                                return resp.data;
                            },
                            function (err) {
                                return $q.reject(err);
                            }
                    );
        }
    };

}

