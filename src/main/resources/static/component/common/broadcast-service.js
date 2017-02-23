
/**
 * BroadCastService
 */                                  
function broadCastService($rootScope, $timeout) {

	var self = this;
	self.HANDLE_SESSION_EXPIRED = 'handleSessionExpired';

	self.broadcastSessionExpired = function() {
		$rootScope.$broadcast(self.HANDLE_SESSION_EXPIRED);
		console.log("Broadcasting event");
	};	
}

/**
 * 
 * Pass all functions into module
 */
angular
    .module('inventory')
    .service('broadCastService', broadCastService)
