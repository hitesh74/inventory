function categoryService($http) {

	var self = this;
	
	self.addCategory = function(category) {
		
		var uri = "/api/product/category/add";
		
		return $http.post(uri, category);
		
	}
	
	self.getCategoriesPage = function(pageNumber, pageSize, sortType, sortAsc, searchText) {
		
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
		
		var uri = "/api/product/category/get/page/" + pageNumber + "/" + pageSize;
		
		return $http.get(uri, config);
	}
	
}


/**
 * 
 * Pass all functions into module
 */
angular
   .module('inventory')
   .service('categoryService', categoryService)