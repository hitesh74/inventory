function authService($http, commonService) {

	var self = this;
	
	self.logout = function() {
		var uri = "/logout";
		return $http.get(uri);
	}
	
	self.login = function(credentials) {
		
		var uri = "/login";
		
		//Remove custom headers like X-Requested-With.
		var httpConfig = {
			headers : {
				'X-Requested-With' : undefined,
				'X-DONT-INTERCEPT' : true,
				'Content-Type' : "application/x-www-form-urlencoded;charset=utf-8",
			}
		}
		
		return $http.post(uri, $.param(credentials), httpConfig).then(
			function(response) {
				if(commonService.user == null) {
					self.getUser();
				}
				return response;
			},
			function (errResponse) {
			    // called asynchronously if an error occurs
			    // or server returns response with an error status.
				return errResponse;
			}
		);
	}
	
	self.getUser = function() {
		
		var uri = "/api/user/get";
		
		$http.get(uri).then(
				
			function(response){
				commonService.user = response.data;
			},
			function(errResponse) {
				commonService.user = {};
			}
		
		);
	}
}

/* handle session expired */
function authIntercepterService($q, broadCastService, SweetAlert) {
	
    var self = this;

    self.responseError = function(response) {
    	
		if (response.config.headers['X-DONT-INTERCEPT']) {
    		return $q.reject(response);
    	} else {
    		if (response.status == 401) {
    			SweetAlert.swal("Session Expired", "Login again to continue.", "warning")
    			broadCastService.broadcastSessionExpired();
            }
    	}
        return $q.reject(response);
    };
    
};


/**
 * 
 * Pass all functions into module
 */
angular
   .module('inventory')
   .service('authService', authService)
   .service('authIntercepterService', authIntercepterService)