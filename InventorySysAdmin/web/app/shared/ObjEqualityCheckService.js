angular.module('Inventory').factory('ObjEqualityCheckService', ObjEqualityCheckService);

ObjEqualityCheckService.$inject = [];

function ObjEqualityCheckService() {

    return {
        /**
         * Checks if the property value of both object are exactly same.
         * @param {type} object1 The first object.
         * @param {type} object2 The second object.
         * @returns {undefined} True if both objects' property value are same.
         */
        areObjExactlySame: function (object1, object2) {
            var equal = false;

            var object1PropertyLength = Object.getOwnPropertyNames(object1).length;
            var object2PropertyLength = Object.getOwnPropertyNames(object2).length;

            // if the number of properties doesn't match then they are not same.
            if (object1PropertyLength !== object2PropertyLength) {
                console.log("Length not same");
                console.log("OBJ1 Length: " + object1PropertyLength);
                console.log(Object.getOwnPropertyNames(object1));

                console.log("OBJ2 Length: " + object2PropertyLength);
                console.log(Object.getOwnPropertyNames(object2));

                return false;
            } else {
                var obj1PropertiesArray = Object.getOwnPropertyNames(object1);
                var obj2PropertiesArray = Object.getOwnPropertyNames(object2);

                console.log("Properties: " + obj1PropertiesArray);

                var length = obj1PropertiesArray.length;

                for (var i = 0; i < length; i++) {
                    // extract the ith value of both object
                    var value1 = object1[obj1PropertiesArray[i]];
                    var value2 = object2[obj2PropertiesArray[i]];

                    // if the value is of type object then continue to the next value
                    if (typeof value1 === 'object' && typeof value2 === 'object') {
                        console.log('object');
                        continue;
                    } else {	// if not
                        //alert(typeof obj1PropertiesArray[i]);

                        // check if both values are exactly same.
                        if (value1 === value2) {
                            equal = true;
                        } else {	// if not flip equal value and we are done here in this function.
                            equal = false;
                            break;
                        }
                    }
                }

            }

            return equal;
        }
    };

}