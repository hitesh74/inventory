function invoiceCtrl($scope, invoiceService, commonService, $state) {
    
	var self = this;
	
	// Expose data from common service
	self.commonService = commonService
	
	self.invoices = [];
	
	self.pageNo = 1;
	self.invoicesPerPage = '5';
	
	self.startingIndex = 0;
	self.endingIndex = self.startingIndex + (self.invoices.length - 1);
	
	self.totalInvoices = 0;
	
	self.sortType = null;
	self.sortAsc = false;
	
	self.searchText = '';
	
	$scope.$watch('invoiceCtrl.invoicesPerPage', function() {
		self.reloadInvoicesPage();
	});
	
	self.searchInvoices = function() {
		self.pageNo = 1;
		self.reloadInvoicesPage();
	}
	
	self.showInvoice = function(invoice) {
		commonService.selectedInvoice = invoice;
		$state.go(commonService.STATE_SHOW_INVOICE_VIEW);
	}

	$scope.$watchGroup([ 'invoiceCtrl.sortAsc', 'invoiceCtrl.sortType' ], function() {
		self.pageNo = 1;
		self.reloadInvoicesPage();
	});

	self.watchMe = function(expr) {
		
	}
	
	self.init = function(x) {
		self.getInvoices();
	}
	
	self.pageCountChanged = function(x) {
		self.getInvoices();
		console.log(self.invoicesPerPage);
	}

	self.getInvoices = function() {

		// params.total(13);
		invoiceService.getInvoicesPage(self.pageNo, self.invoicesPerPage, self.sortType, self.sortAsc, self.searchText, commonService.selectedProduct)
		.then(
				function(response) {
					self.invoices = response.data.content;
					self.totalInvoices = response.data.totalElements;
					self.startingIndex = self.invoicesPerPage * (self.pageNo -1);
					self.endingIndex = self.startingIndex + (self.invoices.length - 1);
				},
				
				function(errResponse) {
					self.invoices = [];
				}
		);
	}
	
	self.reloadInvoicesPage = function() {
		console.log(self.pageNo, self.invoicesPerPage)
		self.getInvoices();
	}
	
	self.init();
}


function createInvoiceCtrl($scope, invoiceService, commonService, SweetAlert, datePickerService) {
	
	var self = this;
	
	// Expose data from common service
	self.commonService = commonService;

	self.init = function() {
		
		this.items = [];
		
		self.invoice = {};
		self.invoice.invoiceDate = datePickerService.today();
		self.invoice.invoiceItemDtos = [];
		self.invoice.invoiceNumber = '';
		
		if(commonService.user == null) {
			self.getUser();
		}
	}
	
	self.getUser = function() {
		
		invoiceService.getUser().then(
				
			function(response){
				commonService.user = response.data;
			},
			function(errResponse) {
				commonService.user = {};
			}
		
		);
	}
	
	function printElement(elem, printSection) {
        // clones the element you want to print
        var domClone = elem.cloneNode(true);
        printSection.innerHTML = '';
        printSection.appendChild(domClone);
    }
	
	self.save = function() {
		
		console.log("Storing");
		self.invoice.invoiceItemDtos = [];
		for(var i = 0; i < self.items.length; i++){
	        var item = self.items[i];
	        
	        var invoiceItem = {};
	        
	        invoiceItem.itemDto = {};
	        invoiceItem.itemDto.itemId = item.itemId;
	        
	        invoiceItem.quantity= item.quantity;
	        
	        self.invoice.invoiceItemDtos.push(invoiceItem);
	    }
		
		invoiceService.saveInvoice(self.invoice).then(
				function(response) {
					self.invoice.invoiceNumber = response.data.invoiceNumber;
					SweetAlert.swal('Succes', 'Saved Invoice Successfully', "success");
						
				},
				function() {
					SweetAlert.swal('Error', 'Failed to save invoice', "error");
				}
		);
	}
	
	self.print = function() {
		
		console.log("Printing");
		
	    var printSection = document.getElementById('printSection');

	    // if there is no printing section, create one
	    if (!printSection) {
	        printSection = document.createElement('div');
	        printSection.id = 'printSection';
	        document.body.appendChild(printSection);
	    }
		
		window.onafterprint = function () {
            // clean the print section before adding new content
            printSection.innerHTML = '';
            console.log("Cleaning");
        }
		
		var elemToPrint = document.getElementById('printThisElement');
        if (elemToPrint) {
            printElement(elemToPrint, printSection);
            window.print();
        }
	}
	
	self.removeItem = function(item) {
		self.items = self.items.filter(function(myItem) { 
		    return item !== myItem;
		});
	}
	
	self.addItem = function () {
		self.items.push(
				{
					'name' : '',
					'category': '',
					'quantity': 0,
					'unitPrice': 0,
					'taxPercent': 0,
					'autofocus': true,
				}
		);
	}
	
	self.changeQuantity = function(item) {
		// TODO
	}
	

	self.getSubTotal = function() {
	    var total = 0;
	    for(var i = 0; i < self.items.length; i++){
	        var item = self.items[i];
	        total += (item.unitPrice * item.quantity);
	    }
	    return total;
	}
	
	self.getTotalTax = function() {
	    var total = 0;
	    for(var i = 0; i < self.items.length; i++){
	        var item = self.items[i];
	        total += (item.unitPrice * item.quantity) * item.taxPercent / 100;
	    }
	    return total;
	}
	
	self.getTotal = function() {
	    var total = 0;
	    for(var i = 0; i < self.items.length; i++){
	        var item = self.items[i];
	        total += (item.unitPrice * item.quantity) * (100 + item.taxPercent) / 100;
	    }
	    self.invoice.totalAmount = total;
	    return total;
	}
	
	self.searchItem = function(item) {
		var invItem = invoiceService.getItem(item.searchText);
		
		item.unitPrice = 0;
		item.taxPercent = 0;
		item.quantity = 0;
		item.category='';
		
		invItem.then(
			function(response) {
				console.log(response);
				if (response.status == 200) {
					item.itemId = response.data.itemId;
					item.name = response.data.productDto.name;
					item.category = response.data.productDto.productCategoryDto.name;
					item.unitPrice = response.data.unitSP;
					item.taxPercent = response.data.taxPercent;
					item.quantity = 1;
					item.searchText = item.name;
				}
				else if(response.status == 404){
					item.searchText = 'Not found';
				}else{
					item.searchText = 'Internal Server Error';
				}
			}
		);
	}
	
	self.init();
}

function showInvoiceCtrl($scope, invoiceService, commonService, SweetAlert, datePickerService) {
	
	var self = this;
	
	// Expose data from common service
	self.commonService = commonService;

	self.init = function() {
		
		self.invoice = commonService.selectedInvoice;
		self.invoiceItems = [];
		
		self.getInvoiceItems();
	}
	
	self.getInvoiceItems = function() {
		
		invoiceService.getInvoiceItems(self.invoice.invoiceId).then(
				function(response) {
					self.invoiceItems = response.data;
				},
				function() {
					self.invoiceItems = [];
				}
		);
	}

	self.getSubTotal = function() {
	    var total = 0;
	    for(var i = 0; i < self.invoiceItems.length; i++){
	        var invoiceItem = self.invoiceItems[i];
	        total += (invoiceItem.itemDto.unitSP * invoiceItem.quantity);
	    }
	    return total;
	}
	
	self.getTotalTax = function() {
	    var total = 0;
	    for(var i = 0; i < self.invoiceItems.length; i++){
	        var invoiceItem = self.invoiceItems[i];
	        total += (invoiceItem.itemDto.unitSP * invoiceItem.quantity) * invoiceItem.itemDto.taxPercent / 100;
	    }
	    return total;
	}
	
	self.getTotal = function() {
	    var total = 0;
	    for(var i = 0; i < self.invoiceItems.length; i++){
	        var invoiceItem = self.invoiceItems[i];
	        total += (invoiceItem.itemDto.unitSP * invoiceItem.quantity) * (100 + invoiceItem.itemDto.taxPercent) / 100;
	    }
	    self.invoice.totalAmount = total;
	    return total;
	}
	
	self.init();
}

/**
*
* Pass all functions into module
*/
angular
   .module('inventory')
   .controller('InvoiceCtrl', invoiceCtrl)
   .controller('CreateInvoiceCtrl', createInvoiceCtrl)
   .controller('ShowInvoiceCtrl', showInvoiceCtrl)