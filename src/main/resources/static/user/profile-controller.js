function profileCtrl($scope, profileService, commonService, SweetAlert, $state) {
	
	var self = this;
	
	// Expose data from common service
	self.commonService = commonService;
	
	self.init = function(x) {
		self.user = {};
		self.getUser();
	}
	
	self.reset = function(form) {
		self.user = {};
//		self.getUser();
		self.user = commonService.user;
		
		form.$setPristine();
        form.$setUntouched();
	}
	
	self.getUser = function() {
		
		profileService.getUser().then(
				
			function(response){
				self.user = response.data;
			},
			function(errResponse) {
				self.user = {};
			}
		
		);
	}
	
	self.updateUser = function() {
		
		var updatePicture = false;
		
		if ($scope.picFile == null || $scope.picFile == undefined) {
			self.user.picture = null;
		} else {
			self.user.picture = $scope.picFile.$ngfDataUrl.split(",")[1];
			updatePicture = true;
		}
		
		
		profileService.updateUser(self.user, updatePicture).then(
				
			function(response){
				commonService.user = response.data;
				SweetAlert.swal(
						{
							title: 'Success',
				            text: 'Updated profile successfully',
				            html: true,
				            type: "success",
				            showCancelButton: true,
				            showConfirmButton: true,
						    confirmButtonText: 'Ok',
						    cancelButtonText: 'Go To Home',
						    closeOnConfirm: true,
						},
						function (isConfirm) {
						    if (isConfirm) {
						    	self.reset($scope.updateProfileReqForm);
						    } else {
						    	$state.go(commonService.STATE_HOME_VIEW);
						    }
						}
				);
			},
			function(errResponse) {
				SweetAlert.swal('Error', 'Failed to update profile', "error");
			}
		
		);
	}
	
	self.init();
	
}

/**
*
* Pass all functions into module
*/
angular
   .module('inventory')
   .controller('ProfileCtrl', profileCtrl)