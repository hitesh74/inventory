function profileService($http) {

	var self = this;
	
	self.updateUser = function(profile, updatePicture) {
		var uri = "/api/user/update?updatePicture=" + updatePicture;
		return $http.put(uri, profile);		
	}
	
	self.getUser = function() {
		var uri = "/api/user/get";
		return $http.get(uri, config);
	}
	
}


/**
 * 
 * Pass all functions into module
 */
angular
   .module('inventory')
   .service('profileService', profileService)