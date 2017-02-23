function itemCtrl($scope, itemService, commonService, $http, $state) {
    
	var self = this;
	
	// Expose data from common service
	self.commonService = commonService
	
	self.items = [];
	
	self.pageNo = 1;
	self.itemsPerPage = '5';
	
	self.startingIndex = 0;
	self.endingIndex = self.startingIndex + (self.items.length - 1);
	
	self.totalItems = 0;
	
	self.sortType = null;
	self.sortAsc = false;
	
	self.searchText = '';
	
	self.goToItemSupplier = function(item) {
		commonService.selectedItem = item;
		$state.go(commonService.STATE_ITEM_SUPPLIER_VIEW);
	}
	
	self.searchItems = function() {
		self.pageNo = 1;
		self.reloadItemsPage();
	}
	
	self.init = function(x) {
		self.getItems();
		
		$scope.$watchGroup([ 'itemCtrl.sortAsc', 'itemCtrl.sortType' ], function() {
			self.pageNo = 1;
			self.reloadItemsPage();
		});
		
		$scope.$watch('itemCtrl.itemsPerPage', function() {
			self.reloadItemsPage();
		});
	}

	self.getItems = function() {

		itemService.getItemsPage(self.pageNo, self.itemsPerPage, self.sortType, self.sortAsc, self.searchText, commonService.selectedProduct)
		.then(
				function(response) {
					self.items = response.data.content;
					self.totalItems = response.data.totalElements;
					self.startingIndex = self.itemsPerPage * (self.pageNo -1);
					self.endingIndex = self.startingIndex + (self.items.length - 1);
				},
				
				function(errResponse) {
					self.items = [];
				}
		);
	}
	
	self.reloadItemsPage = function() {
		self.getItems();
	}
	
	self.init();
}

/**
 * ItemSupplier Ctrl
 */

function itemSupplierCtrl($scope, itemService, commonService, $http, $state) {
    
	var self = this;
	
	// Expose data from common service
	self.commonService = commonService;
	
	self.goToItems = function() {
		$state.go(commonService.STATE_SUPPLIER_LANDING_VIEW);
	}
	
	self.init = function() {
		self.supplier = {};
		self.getSupplier();
	}

	self.getSupplier = function() {

		itemService.getSupplier(self.commonService.selectedItem.supplierDto.supplierId)
		.then(
				function(response) {
					self.supplier = response.data
				},
				
				function(errResponse) {
					self.supplier = {};
				}
		);
	}
	
	self.init();
}


function createItemCtrl($scope, itemService, commonService, datePickerService, SweetAlert, $state) {

	var self = this;

	// Expose data from common service
	self.commonService = commonService;

	self.init = function(x) {
		self.item = {};
		self.item.expiryDate = datePickerService.nextMonth(24);
		
		self.suppliers = [];
		self.getSuppliersSummaryList();
		
		self.products = [];
		if (commonService.selectedProduct == null) {
			self.getProductsSummaryList();			
		} else {
			self.products.push(commonService.selectedProduct);
			self.item.productDto = commonService.selectedProduct;
		}
	}
	
	self.reset = function(form) {
		self.item = {};
		self.item.expiryDate = datePickerService.nextMonth(24);
		
		if (commonService.selectedProduct != null) {
			self.item.productDto = commonService.selectedProduct;
		} else if (self.products.length > 0) {
			self.item.productDto = self.products[0];						
		}
		
		if(self.suppliers.length > 0){
			self.item.supplierDto = self.suppliers[0];						
		}
		
		form.$setPristine();
        form.$setUntouched();
	}
	
	self.getProductsSummaryList = function() {
		itemService.getProductsSummaryList().then(
				function(response) {
					self.products = response.data;
					if (self.products.length > 0) {
						self.item.productDto = self.products[0];						
					}
				},
				function(errResponse) {
					self.products = [];
				}
		);
	}
	
	self.getSuppliersSummaryList = function() {
		itemService.getSuppliersSummaryList().then(
				function(response) {
					self.suppliers = response.data;
					if(self.suppliers.length > 0){
						self.item.supplierDto = self.suppliers[0];						
					}
				},
				function(errResponse) {
					self.suppliers=[];
				}
		);
	}

	self.createItem = function() {
		
		itemService.addItem(self.item).then(
			function(response) {
				SweetAlert.swal({
					title : 'Success',
					text : 'Added item successfully',
					html : true,
					type : "success",
					showCancelButton : true,
					showConfirmButton : true,
					confirmButtonText : 'Done',
					cancelButtonText : 'Add More',
					closeOnConfirm : true,
				}, function(isConfirm) {
					if (isConfirm) {
						$state.go(commonService.STATE_ITEM_LANDING_VIEW);
					} else {
						self.reset($scope.createItemReqForm);
					}
				});
			}, 
			function(errResponse) {
				SweetAlert.swal('Error', 'Failed to add item', "error");
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
   .controller('ItemCtrl', itemCtrl)
   .controller('ItemSupplierCtrl', itemSupplierCtrl)
   .controller('CreateItemCtrl', createItemCtrl)