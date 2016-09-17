angular.module('Inventory').
        directive('myDatepicker', function ($parse) {
            return function (scope, element, attrs) {
                var ngModel = $parse(attrs.ngModel);
                $(function () {
                    element.datepicker({
                        showOn: "both",
                        changeYear: true,
                        changeMonth: true,
                        dateFormat: 'yy-mm-dd',
                        maxDate: new Date(),
                        setDate: new Date(),
                        yearRange: '1920:' + new Date(),
                        change: function (dateText, inst) {
                            scope.$apply(function (scope) {
                                // Change binded variable
                                ngModel.assign(scope, dateText);
                            });
                        }
                    });
                });
            };
        });

angular.module('Inventory').
        directive('futureDatepicker', function ($parse) {
            return function (scope, element, attrs) {
                var ngModel = $parse(attrs.ngModel);
                $(function () {
                    element.datepicker({
                        showOn: "both",
                        changeYear: true,
                        changeMonth: true,
                        dateFormat: 'yy-mm-dd',
                        maxDate: new Date('2020', '11', '31'),
                        setDate: new Date(),
                        minDate: new Date(),
                        yearRange: '2016:2020',
                        change: function (dateText, inst) {
                            scope.$apply(function (scope) {
                                // Change binded variable
                                ngModel.assign(scope, dateText);
                            });
                        }
                    });
                });
            };
        });

angular.module('Inventory').
        directive('normalDatepicker', function ($parse) {
            return function (scope, element, attrs) {
                var ngModel = $parse(attrs.ngModel);
                $(function () {
                    element.datepicker({
                        showOn: "both",
                        changeYear: true,
                        changeMonth: true,
                        dateFormat: 'yy-mm-dd',
                        maxDate: new Date('2020', '11', '31'),
                        setDate: new Date(),
                        yearRange: '2016:2020',
                        change: function (dateText, inst) {
                            scope.$apply(function (scope) {
                                // Change binded variable
                                ngModel.assign(scope, dateText);
                            });
                        }
                    });
                });
            };
        });


