/* global angular */

//config for application
angular.module('Inventory').config(function ($routeProvider) {
    //console.log('called config function');
    //console.log('route provider' + $routeProvider);
    $routeProvider
            .when('/login', {
                controller: 'LoginController as loginCtrl',
                templateUrl: 'app/components/login/login.html'
            })
            .when('/home', {
                controller: 'HomeController as homeCtrl',
                templateUrl: 'home.html'
            })
            .when('/items/new', {
                controller: 'ItemController as itemCtrl',
                templateUrl: 'app/components/item/views/addItem.html'
            })
            .when('/items', {
                controller: 'ItemController as itemCtrl',
                templateUrl: 'app/components/item/views/manageItem.html'
            })
            .when('/parties/new', {
                controller: 'PartyController as partyCtrl',
                templateUrl: 'app/components/party/views/addParty.html'
            })
            .when('/parties', {
                controller: 'PartyController as partyCtrl',
                templateUrl: 'app/components/party/views/manageParty.html'
            })
            .otherwise({redirectTo: '/home'});
});

angular.module('Inventory').run(function ($rootScope, $location, $localStorage) {
    //checking if the user is logged in or not.
    if (angular.isUndefined($localStorage.loggedIn) || $localStorage.loggedIn === 0) {
        $rootScope.isLoggedIn = false;
    } else if ($localStorage.loggedIn === 1) {
        $rootScope.isLoggedIn = true;
    } else {
        $rootScope.isLoggedIn = true;
    }

    $rootScope.$on('$locationChangeStart', function (event, next, current) {
        //redirect to login page if not logged in
        if ($location.path() !== '/login' && !$rootScope.isLoggedIn) {   //
//            $location.path('/login');
        } else if ($location.path() === '/home' && $rootScope.isLoggedIn) {  // 
            alert("TO HOME");
            $location.path('/home');
        } else if ($location.path() === '') {
//            $localStorage.$reset();
//            $rootScope.isLoggedIn = false;
//            for (var prop in $rootScope) {
//                if (prop.substring(0, 1) !== '$') {
//                    delete $rootScope[prop];
//                }
//            }
            $location.path('/login');
        } else {
            /*var found = false;
             var profiles = JSON.parse($localStorage['profiles']);
             for (var i = 0; i < profiles.length; i++) {
             var childMenus = profiles[i].childMenus;
             for (var j = 0; j < childMenus.length; j++) {
             var actionKey = childMenus[j].actionKey.split('#')[1];
             if ($location.path() === actionKey && $rootScope.isLoggedIn) {
             found = true;
             $location.path(actionKey);
             }
             }
             }
             if (found === false) {
             $location.path('/error');
             }*/
        }
    });

});