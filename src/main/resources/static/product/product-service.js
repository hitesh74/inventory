function productService($http) {

	var self = this;
	
	self.addProduct = function(product) {
		
		var uri = "/api/product/add";
		
		return $http.post(uri, product);
		
	}
	
	self.getCategoriesSummaryList = function() {
		
		var uri = "/api/product/category/get/list/summary";

		return $http.get(uri);
	}
	
	self.getProductsPage = function(pageNumber, pageSize, sortType, sortAsc, searchText) {
		
		var data = {};
		if (sortType != null) {
			data.sortType = sortType;
			data.sortAsc = sortAsc;
		}
		if (searchText != undefined && searchText != null && searchText.trim() != '') {
			data.searchText = searchText;
		}

		var config = {
		 params: data,
		 headers : {'Accept' : 'application/json'}
		};
		
		var uri = "/api/product/get/page/" + pageNumber + "/" + pageSize;
		
		return $http.get(uri, config);
	}
	
}


/**
 * 
 * Pass all functions into module
 */
angular
   .module('inventory')
   .service('productService', productService)