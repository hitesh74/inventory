function loginCtrl($scope, commonService, authService, $state) {
    
	var self = this;
	
	// Expose data from common service
	self.commonService = commonService;
	
	self.login = function() {
		self.errMsg = '';
		authService.login(self.creds)
		.then(
				function(response) {
					console.log(response);
					if(response.status == 401) {
						self.errMsg = "Invalid username or password";
					}else{
						$state.go(commonService.STATE_HOME_VIEW);						
					}
				},
				
				function(errResponse) {
					console.log(errResponse);
				}
		);
	}
	
	self.init = function() {
		self.errMsg = '';
		self.creds = {};
	}
	
	self.init();
}

/**
*
* Pass all functions into module
*/
angular
   .module('inventory')
   .controller('LoginCtrl', loginCtrl)