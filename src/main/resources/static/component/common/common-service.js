function commonService() {

	var self = this;
	
	self.STATE_HOME= "landing";
	
	self.STATE_ITEM_LANDING_VIEW = "home.item"; 
	self.STATE_ADD_ITEM_VIEW = "home.add-item"; 
	
	
	self.STATE_PRODUCT_LANDING_VIEW = "home.product"; 
	self.STATE_ADD_PRODUCT_VIEW = "home.add-product"; 
	
	self.STATE_HOME_VIEW = "home.user";
	
	self.STATE_SUPPLIER_LANDING_VIEW = "home.supplier";
	self.STATE_ITEM_SUPPLIER_VIEW = "home.item-supplier";
	self.STATE_ADD_SUPPLIER_VIEW = "home.add-supplier";
	
	self.STATE_CATEGORY_LANDING_VIEW = "home.product-category";
	self.STATE_ADD_CATEGORY_VIEW = "home.add-product-category";
	
	self.STATE_INVOICE_LANDING_VIEW = "home.invoice";
	self.STATE_SHOW_INVOICE_VIEW = "home.show-invoice";
	
	// Selected a category
	self.selectedCategory = null;
	
	// Selected a product from products view
	self.selectedProduct = null;
	
	// Selected an item from Items view
	self.selectedItem = null;
	
	// Selected an invoice
	self.selectedInvoice = null;
}

function datePickerService() {
	var self = this;

	self.today = function() {
		return (new Date());
	};

	self.previousMonth = function(months) {
		var myDate = new Date();
		var previousMonth = new Date(myDate);
		previousMonth.setMonth(myDate.getMonth() - months);
		return previousMonth;
	};
	
	self.nextMonth = function(months) {
		var myDate = new Date();
		var nextMonth = new Date(myDate);
		nextMonth.setMonth(myDate.getMonth() + months);
		return nextMonth;
	};

	self.previousMinutes = function(minutes) {
		var myDate = new Date();
		var previousMinutes = new Date(myDate);
		previousMinutes.setMinutes(myDate.getMinutes() - minutes);
		return previousMinutes;
	};

	self.nextMinutes = function(myDate, minutes) {
		var nextMinutes = new Date(myDate.getTime());
		nextMinutes.setMinutes(myDate.getMinutes() + minutes);
		return nextMinutes;
	};

	self.nextSecond = function(myDate, seconds) {
		var nextSeconds = new Date(myDate.getTime());
		nextSeconds.setSeconds(myDate.getSeconds() + seconds);
		return nextSeconds;
	};

	self.tomorrow = function() {
		var tomorrow = new Date();
		tomorrow.setDate(tomorrow.getDate() + 1);
		return tomorrow;
	}

	self.afterTomorrow = function() {
		var afterTomorrow = new Date();
		afterTomorrow.setDate(tomorrow.getDate() + 1);
		return afterTomorrow;
	}

	self.toggleMin = function(minDate) {
		return minDate ? null : new Date();
	};

}



/**
 * 
 * Pass all functions into module
 */
angular
   .module('inventory')
   .service('commonService', commonService)
   .service('datePickerService', datePickerService)
   