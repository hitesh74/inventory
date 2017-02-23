function categoryCtrl($scope, categoryService, commonService, $http, $state) {

	var self = this;

	// Expose data from common service
	self.commonService = commonService

	self.categorys = [];

	self.pageNo = 1;
	self.categorysPerPage = '5';

	self.startingIndex = 0;
	self.endingIndex = self.startingIndex + (self.categorys.length - 1);

	self.totalCategories = 0;

	self.sortType = null;
	self.sortAsc = false;

	self.searchText = '';

	$scope.$watch('categoryCtrl.categorysPerPage', function() {
		self.reloadCategoriesPage();
	});

	self.searchCategories = function() {
		self.pageNo = 1;
		self.reloadCategoriesPage();
	}

	$scope.$watchGroup([ 'categoryCtrl.sortAsc', 'categoryCtrl.sortType' ], function() {
		self.pageNo = 1;
		self.reloadCategoriesPage();
	});

	self.init = function(x) {
		self.getCategories();
	}

	self.pageCountChanged = function(x) {
		self.getCategories();
	}

	self.goToItems = function(category) {
		commonService.selectedCategory = category;
		$state.go(commonService.STATE_ITEM_LANDING_VIEW);
	}

	self.getCategories = function() {

		categoryService.getCategoriesPage(self.pageNo, self.categorysPerPage, self.sortType, self.sortAsc, self.searchText).then(function(response) {
			self.categorys = response.data.content;
			self.totalCategories = response.data.totalElements;
			self.startingIndex = self.categorysPerPage * (self.pageNo - 1);
			self.endingIndex = self.startingIndex + (self.categorys.length - 1);
		},

		function(errResponse) {
			self.categorys = [];
		});
	}

	self.reloadCategoriesPage = function() {
		console.log(self.pageNo, self.categorysPerPage)
		self.getCategories();
	}
	
	self.addProduct = function(category) {
		commonService.selectedCategory = category;
		$state.go(commonService.STATE_ADD_PRODUCT_VIEW);
	}

	self.init();
}

function createCategoryCtrl($scope, categoryService, commonService, SweetAlert, $state) {

	var self = this;

	// Expose data from common service
	self.commonService = commonService;

	self.init = function(x) {
		self.category = {};
	}

	self.createCategory = function() {

		categoryService.addCategory(self.category).then(

		function(response) {
			SweetAlert.swal({
				title : 'Success',
				text : 'Added category successfully',
				html : true,
				type : "success",
				showCancelButton : true,
				showConfirmButton : true,
				confirmButtonText : 'Done',
				cancelButtonText : 'Add More',
				closeOnConfirm : true,
			}, function(isConfirm) {
				if (isConfirm) {
					$state.go(commonService.STATE_CATEGORY_LANDING_VIEW);
				} else {
					self.category = {};
				}
			});
		}, function(errResponse) {
			SweetAlert.swal('Error', 'Failed to add category', "error");
		}

		);
	}
	
	self.reset = function(form) {
		self.category = {};
		form.$setPristine();
        form.$setUntouched();
	}

	self.init();

}

/**
 * 
 * Pass all functions into module
 */
angular.module('inventory')
	.controller('CategoryCtrl', categoryCtrl)
	.controller('CreateCategoryCtrl', createCategoryCtrl)
