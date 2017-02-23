/**
 * Inventory - The Complete Store Solution
 *
 * Main controller.js file
 *
 */

function mainCtrl($scope, authService, $state, commonService, broadCastService) {
	
	var self = this;
	
	// Expose data from common service
	self.commonService = commonService
	
	$scope.$on(broadCastService.HANDLE_SESSION_EXPIRED, function() {
		console.log("Catched Broadcasting event");
		self.logout();
	});
	
	self.logout = function() {
		
		//$state.go(commonService.STATE_HOME);
		
		authService.logout(
				function(response) {
					// Do nothing
				}, 
				function(errResponse) {
					// Do nothing
				}
		)
		.then(
				function() {
					$state.go(commonService.STATE_HOME);
				}
		);
	}
	
};

/**
 *
 * Pass all functions into module
 */
angular
    .module('inventory')
    .controller('MainCtrl', mainCtrl)

