function productCtrl($scope, productService, commonService, $http, $state) {

	var self = this;

	// Expose data from common service
	self.commonService = commonService

	self.products = [];

	self.pageNo = 1;
	self.productsPerPage = '5';

	self.startingIndex = 0;
	self.endingIndex = self.startingIndex + (self.products.length - 1);

	self.totalProducts = 0;

	self.sortType = null;
	self.sortAsc = false;

	self.searchText = '';

	$scope.$watch('productCtrl.productsPerPage', function() {
		self.reloadProductsPage();
	});

	self.searchProducts = function() {
		self.pageNo = 1;
		self.reloadProductsPage();
	}

	$scope.$watchGroup([ 'productCtrl.sortAsc', 'productCtrl.sortType' ], function() {
		self.pageNo = 1;
		self.reloadProductsPage();
	});

	self.init = function(x) {
		self.getProducts();
	}

	self.pageCountChanged = function(x) {
		self.getProducts();
	}

	self.goToItems = function(product) {
		commonService.selectedProduct = product;
		$state.go(commonService.STATE_ITEM_LANDING_VIEW);
	}

	self.getProducts = function() {

		productService.getProductsPage(self.pageNo, self.productsPerPage, self.sortType, self.sortAsc, self.searchText).then(function(response) {
			self.products = response.data.content;
			self.totalProducts = response.data.totalElements;
			self.startingIndex = self.productsPerPage * (self.pageNo - 1);
			self.endingIndex = self.startingIndex + (self.products.length - 1);
		},

		function(errResponse) {
			self.products = [];
		});
	}

	self.reloadProductsPage = function() {
		console.log(self.pageNo, self.productsPerPage)
		self.getProducts();
	}
	
	self.addItem = function(product) {
		commonService.selectedProduct = product;
		$state.go(commonService.STATE_ADD_ITEM_VIEW);
	}

	self.init();
}

function createProductCtrl($scope, productService, commonService, SweetAlert, $state) {

	var self = this;

	// Expose data from common service
	self.commonService = commonService;

	self.init = function(x) {
		self.categories = [];
		self.product = {};
		
		if (commonService.selectedCategory == null) {
			self.getCategoriesSummaryList();			
		} else {
			self.categories.push(commonService.selectedCategory);
			self.product.productCategoryDto = commonService.selectedCategory;
		}
	}
	
	self.getCategoriesSummaryList = function() {
		productService.getCategoriesSummaryList().then(
				function(response) {
					self.categories = response.data;
					if (self.categories.length > 0) {
						self.product.productCategoryDto = self.categories[0];						
					}
				},
				function(errResponse) {
					self.categories = [];
				}
		);
	}
	
	self.reset = function(form) {
		self.product = {};
		if (commonService.selectedCategory != null) {
			self.product.productCategoryDto = commonService.selectedCategory;
		} else if (self.categories.length > 0) {
			self.product.productCategoryDto = self.categories[0];
		}
		form.$setPristine();
        form.$setUntouched();
	}

	self.createProduct = function() {
		
/*		self.product.productCategoryDto = {
				'categoryId' : commonService.selectedCategory.categoryId
		};*/

		productService.addProduct(self.product).then(

		function(response) {
			SweetAlert.swal({
				title : 'Success',
				text : 'Added product successfully',
				html : true,
				type : "success",
				showCancelButton : true,
				showConfirmButton : true,
				confirmButtonText : 'Done',
				cancelButtonText : 'Add More',
				closeOnConfirm : true,
			}, function(isConfirm) {
				if (isConfirm) {
					$state.go(commonService.STATE_PRODUCT_LANDING_VIEW);
				} else {
					self.reset($scope.createProductReqForm);
				}
			});
		}, function(errResponse) {
			SweetAlert.swal('Error', 'Failed to add product', "error");
		}

		);
	}

	self.init();

}

/**
 * 
 * Pass all functions into module
 */
angular.module('inventory')
	.controller('ProductCtrl', productCtrl)
	.controller('CreateProductCtrl', createProductCtrl)