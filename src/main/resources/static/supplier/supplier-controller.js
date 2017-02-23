function supplierCtrl($scope, supplierService, commonService) {
    
	var self = this;
	
	// Expose data from common service
	self.commonService = commonService
	
	self.suppliers = [];
	
	self.pageNo = 1;
	self.suppliersPerPage = '5';
	
	self.startingIndex = 0;
	self.endingIndex = self.startingIndex + (self.suppliers.length - 1);
	
	self.totalSuppliers = 0;
	
	self.sortType = null;
	self.sortAsc = false;
	
	self.searchText = '';
	
	$scope.$watch('supplierCtrl.suppliersPerPage', function() {
		self.reloadSuppliersPage();
	});
	
	self.searchSuppliers = function() {
		self.pageNo = 1;
		self.reloadSuppliersPage();
	}

	$scope.$watchGroup([ 'supplierCtrl.sortAsc', 'supplierCtrl.sortType' ], function() {
		self.pageNo = 1;
		self.reloadSuppliersPage();
	});

	self.watchMe = function(expr) {
		
	}
	
	self.init = function(x) {
		self.getSuppliers();
	}
	
	self.pageCountChanged = function(x) {
		self.getSuppliers();
		console.log(self.suppliersPerPage);
	}

	self.getSuppliers = function() {

		// params.total(13);
		supplierService.getSuppliersPage(self.pageNo, self.suppliersPerPage, self.sortType, self.sortAsc, self.searchText, commonService.selectedProduct)
		.then(
				function(response) {
					self.suppliers = response.data.content;
					self.totalSuppliers = response.data.totalElements;
					self.startingIndex = self.suppliersPerPage * (self.pageNo -1);
					self.endingIndex = self.startingIndex + (self.suppliers.length - 1);
				},
				
				function(errResponse) {
					self.suppliers = [];
				}
		);
	}
	
	self.reloadSuppliersPage = function() {
		console.log(self.pageNo, self.suppliersPerPage)
		self.getSuppliers();
	}
	
	self.init();
}

function createSupplierCtrl($scope, supplierService, commonService, SweetAlert, $state) {
	
	var self = this;
	
	// Expose data from common service
	self.commonService = commonService;
	
	self.init = function(x) {
		self.supplier = {};
	}
	
	self.reset = function(form) {
		self.supplier = {};
		
		form.$setPristine();
        form.$setUntouched();
	}
	
	self.createSupplier = function() {
		
		supplierService.addSupplier(self.supplier).then(
				
			function(response){
				SweetAlert.swal(
						{
							title: 'Success',
				            text: 'Added supplier successfully',
				            html: true,
				            type: "success",
				            showCancelButton: true,
				            showConfirmButton: true,
						    confirmButtonText: 'Done',
						    cancelButtonText: 'Add More',
						    closeOnConfirm: true,
						},
						function (isConfirm) {
						    if (isConfirm) {
						    	$state.go(commonService.STATE_SUPPLIER_LANDING_VIEW);
						    } else {
						    	self.reset($scope.createSupplierReqForm);
						    }
						}
				);
			},
			function(errResponse) {
				SweetAlert.swal('Error', 'Failed to add supplier', "error");
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
   .controller('SupplierCtrl', supplierCtrl)
   .controller('CreateSupplierCtrl', createSupplierCtrl)