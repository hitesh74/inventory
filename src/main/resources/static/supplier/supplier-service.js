function supplierService($http) {

	var self = this;
	
	self.addSupplier = function(supplier) {
		
		var uri = "/api/supplier/add";
		
		return $http.post(uri, supplier);
		
	}
	
	self.getSuppliersPage = function(pageNumber, pageSize, sortType, sortAsc, searchText, selectedProduct) {
		
		var data = {};
		if (sortType != null) {
			data.sortType = sortType;
			data.sortAsc = sortAsc;
		}
		
		if (searchText != undefined && searchText != null && searchText.trim() != '') {
			data.searchText = searchText;
		}
		
		if (selectedProduct != null) {
			data.productId = selectedProduct.productId;
		}

		var config = {
		 params: data,
		 headers : {'Accept' : 'application/json'}
		};
		
		var uri = "/api/supplier/get/page/" + pageNumber + "/" + pageSize;
		
		return $http.get(uri, config);
	}
	
}


/**
 * 
 * Pass all functions into module
 */
angular
   .module('inventory')
   .service('supplierService', supplierService)