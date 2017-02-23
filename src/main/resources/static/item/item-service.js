function itemService($http) {

	var self = this;
	
	self.addItem = function(item) {
		
		var uri = "/api/item/add";
		
		return $http.post(uri, item);
		
	}
	
	self.getProductsSummaryList = function() {
		
		var uri = "/api/product/get/list/summary";

		return $http.get(uri);
	}
	
	self.getSuppliersSummaryList = function() {
		
		var uri = "/api/supplier/get/list/summary";

		return $http.get(uri);
	}
	
	self.getSupplier = function(supplierId) {
		
		var uri = "/api/supplier/get/" + supplierId + "/";
		
		return $http.get(uri);
	}
	
	self.getItemsPage = function(pageNumber, pageSize, sortType, sortAsc, searchText, selectedProduct) {
		
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
		
		var uri = "/api/item/get/page/" + pageNumber + "/" + pageSize;
		
		return $http.get(uri, config);
	}
	
}


/**
 * 
 * Pass all functions into module
 */
angular
   .module('inventory')
   .service('itemService', itemService)