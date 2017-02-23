/**
 * Inventory - The Complete Store Solution
 *
 */
(function () {
    angular.module('inventory', [
        'ui.router',                    // Routing
        'oc.lazyLoad',                  // ocLazyLoad
        'ui.bootstrap',                 // Ui Bootstrap
        'ngIdle',                       // Idle timer
        'ngSanitize',                    // ngSanitize
        'oitozero.ngSweetAlert',		// Sweet alerts
    ])
})();

// Other libraries are loaded dynamically in the config.js file using the library ocLazyLoad