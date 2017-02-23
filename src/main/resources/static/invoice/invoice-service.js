function invoiceService($http) {

	var self = this;
	
	self.getUser = function() {
		var uri = "/api/user/get";
		return $http.get(uri);
	}
	
	self.getInvoiceItems = function(invoiceId) {
		
		var uri = "/api/invoice/items/" + invoiceId + "/";
		return $http.get(uri);
	}
	
	self.getInvoicesPage = function(pageNumber, pageSize, sortType, sortAsc, searchText) {
		
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
		
		var uri = "/api/invoice/get/page/" + pageNumber + "/" + pageSize;
		
		return $http.get(uri, config);
	}
	
	self.getItem = function(barCode) {
		
		var uri = "/api/item/get/" + barCode + "/";
		
		return $http.get(uri);
	}
	
	self.saveInvoice = function(invoice) {
		var uri = "/api/invoice/save";
		return $http.post(uri, invoice);
	}
	
}


/**
 * 
 * Pass all functions into module
 */
angular
   .module('inventory')
   .service('invoiceService', invoiceService)