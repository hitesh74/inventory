/**
 * Inventory - The Complete Store Solution
 * 
 * It uses AngularUI Router to manage routing and views Each view are defined as
 * state.
 * 
 */
function config($stateProvider, $urlRouterProvider, $ocLazyLoadProvider, IdleProvider, KeepaliveProvider, $httpProvider) {
	

	/* Configure common headers to be send with each http request */
	$httpProvider.defaults.headers.common = {
		'X-Requested-With' : 'XMLHttpRequest',
	};

	/* Register intercepter service which handles expired sessions */
	$httpProvider.interceptors.push('authIntercepterService');
	
	
	// Configure Idle settings
	IdleProvider.idle(5); // in seconds
	IdleProvider.timeout(120); // in seconds

	// $urlRouterProvider.otherwise("/home/dashboard");
	$urlRouterProvider.otherwise("landing");

	$ocLazyLoadProvider.config({
		// Set to true if you want to see what and when is dynamically loaded
		debug : false
	});

	$stateProvider
	
	.state('login', {
		url : "/login",
		templateUrl : "component/auth/login.html",
		data : {
			pageTitle : 'Login',
			specialClass : 'gray-bg'
		}
	})

	.state('landing', {
		url : "/landing",
		templateUrl : "views/landing.html",
		data : {
			pageTitle : 'Landing page',
			specialClass : 'landing-page'
		},
		resolve : {
			loadPlugin : function($ocLazyLoad) {
				return $ocLazyLoad.load([ {
					files : [ 'js/plugins/wow/wow.min.js' ]
				} ]);
			}
		}
	})

	// Home => Dashboard
	.state('home', {
		abstract : true,
		url : "/home",
		templateUrl : "views/common/content.html",
	})

	.state(
			'home.user',
			{
				url : "/",
				templateUrl : "user/home.html",
				resolve : {
					loadPlugin : function($ocLazyLoad) {
						return $ocLazyLoad.load([
								{

									serie : true,
									name : 'angular-flot',
									files : [ 'js/plugins/flot/jquery.flot.js', 'js/plugins/flot/jquery.flot.time.js',
											'js/plugins/flot/jquery.flot.tooltip.min.js', 'js/plugins/flot/jquery.flot.spline.js',
											'js/plugins/flot/jquery.flot.resize.js', 'js/plugins/flot/jquery.flot.pie.js', 'js/plugins/flot/curvedLines.js',
											'js/plugins/flot/angular-flot.js', ]
								}, {
									name : 'angles',
									files : [ 'js/plugins/chartJs/angles.js', 'js/plugins/chartJs/Chart.min.js' ]
								}, {
									name : 'angular-peity',
									files : [ 'js/plugins/peity/jquery.peity.min.js', 'js/plugins/peity/angular-peity.js' ]
								} ]);
					}
				}
			})

	/*Home > Items*/
	.state('home.item', {
		url : "/item",
		templateUrl : "item/item.html",
		resolve : {
			loadPlugin : function($ocLazyLoad) {
				return $ocLazyLoad.load([ {
					name : 'angular-peity',
					files : [ 'js/plugins/peity/jquery.peity.min.js', 'js/plugins/peity/angular-peity.js' ]
				}, {
					files : [ 'css/plugins/iCheck/custom.css', 'js/plugins/iCheck/icheck.min.js' ]
				} ]);
			}
		}
	})
	
	
	.state('home.add-item', {
		url : "/item/add",
		templateUrl : "item/add-item.html",
		resolve : {
			loadPlugin : function($ocLazyLoad) {
				return $ocLazyLoad.load([ 
						  {
							  files: ['js/plugins/moment/moment.min.js']
						  },
                          {
                        	  name : 'angular-peity',
                        	  files : [ 'js/plugins/peity/jquery.peity.min.js', 'js/plugins/peity/angular-peity.js' ]
                          }, 
                          {
                        	  files : [ 'css/plugins/iCheck/custom.css', 'js/plugins/iCheck/icheck.min.js' ]
                          },
                          {
                              name: 'datePicker',
                              files: ['css/plugins/datapicker/angular-datapicker.css','js/plugins/datapicker/angular-datepicker.js']
                          },
                          {
                              name: 'ui.select',
                              files: ['js/plugins/ui-select/select.min.js', 'css/plugins/ui-select/select.min.css']
                          },
                 ]);
			}
		}
	})
	
	.state('home.add-supplier', {
		url : "/supplier/add",
		templateUrl : "supplier/add-supplier.html",
		resolve : {
			loadPlugin : function($ocLazyLoad) {
				return $ocLazyLoad.load([ 
				   {
					   name : 'angular-peity',
					   files : [ 'js/plugins/peity/jquery.peity.min.js', 'js/plugins/peity/angular-peity.js' ]
				   }, 
				   {
					   files : [ 'css/plugins/iCheck/custom.css', 'js/plugins/iCheck/icheck.min.js' ]
				   } 
				]);
			}
		}
	})
	
	.state('home.profile', {
		url : "/profile",
		templateUrl : "user/profile.html",
		resolve : {
			loadPlugin : function($ocLazyLoad) {
				return $ocLazyLoad.load([ 
				   {
					   name : 'angular-peity',
					   files : [ 'js/plugins/peity/jquery.peity.min.js', 'js/plugins/peity/angular-peity.js' ]
				   }, 
				   {
					   files : [ 'css/plugins/iCheck/custom.css', 'js/plugins/iCheck/icheck.min.js' ]
				   },
				   {
                   	name: 'ngFileUpload',
                       files: [ 'js/plugins/ng-file/ng-file-upload.js', 'js/plugins/ng-file/ng-file-upload-shim.js' ]
                   },
				]);
			}
		}
	})
	
	.state('home.add-product-category', {
		url : "/product/category/add",
		templateUrl : "category/add-product-category.html",
		resolve : {
			loadPlugin : function($ocLazyLoad) {
				return $ocLazyLoad.load([ 
				   {
					   name : 'angular-peity',
					   files : [ 'js/plugins/peity/jquery.peity.min.js', 'js/plugins/peity/angular-peity.js' ]
				   }, 
				   {
					files : [ 'css/plugins/iCheck/custom.css', 'js/plugins/iCheck/icheck.min.js' ]
				   }
				]);
			}
		}
	})

	.state('home.item-supplier', {
		url : "/item/supplier",
		templateUrl : "item/item-supplier.html",
		resolve : {
			loadPlugin : function($ocLazyLoad) {
				return $ocLazyLoad.load([ {
					name : 'angular-peity',
					files : [ 'js/plugins/peity/jquery.peity.min.js', 'js/plugins/peity/angular-peity.js' ]
				}, {
					files : [ 'css/plugins/iCheck/custom.css', 'js/plugins/iCheck/icheck.min.js' ]
				} ]);
			}
		}
	})

	.state('home.product', {
		url : "/product",
		templateUrl : "product/product.html",
		resolve : {
			loadPlugin : function($ocLazyLoad) {
				return $ocLazyLoad.load([ {
					name : 'angular-peity',
					files : [ 'js/plugins/peity/jquery.peity.min.js', 'js/plugins/peity/angular-peity.js' ]
				}, {
					files : [ 'css/plugins/iCheck/custom.css', 'js/plugins/iCheck/icheck.min.js' ]
				} ]);
			}
		}
	})
	
	.state('home.add-product', {
		url : "/product/add",
		templateUrl : "product/add-product.html",
		resolve : {
			loadPlugin : function($ocLazyLoad) {
				return $ocLazyLoad.load([ 
				   {
					   name : 'angular-peity',
					   files : [ 'js/plugins/peity/jquery.peity.min.js', 'js/plugins/peity/angular-peity.js' ]
				   }, 
				   {
					   files : [ 'css/plugins/iCheck/custom.css', 'js/plugins/iCheck/icheck.min.js' ]
				   },
				   {
                       name: 'ui.select',
                       files: ['js/plugins/ui-select/select.min.js', 'css/plugins/ui-select/select.min.css']
                   }
				]);
			}
		}
	})
	
	.state('home.product-category', {
		url : "/product/category",
		templateUrl : "category/category.html",
		resolve : {
			loadPlugin : function($ocLazyLoad) {
				return $ocLazyLoad.load([ {
					name : 'angular-peity',
					files : [ 'js/plugins/peity/jquery.peity.min.js', 'js/plugins/peity/angular-peity.js' ]
				}, {
					files : [ 'css/plugins/iCheck/custom.css', 'js/plugins/iCheck/icheck.min.js' ]
				} ]);
			}
		}
	})

	.state('home.supplier', {
		url : "/supplier",
		templateUrl : "supplier/supplier.html",
		resolve : {
			loadPlugin : function($ocLazyLoad) {
				return $ocLazyLoad.load([ {
					name : 'angular-peity',
					files : [ 'js/plugins/peity/jquery.peity.min.js', 'js/plugins/peity/angular-peity.js' ]
				}, {
					files : [ 'css/plugins/iCheck/custom.css', 'js/plugins/iCheck/icheck.min.js' ]
				} ]);
			}
		}
	})

	/*Invoice*/
	.state('home.invoice', {
		url : "/invoice",
		templateUrl : "invoice/invoice.html",
		resolve : {
			loadPlugin : function($ocLazyLoad) {
				return $ocLazyLoad.load([ {
					name : 'angular-peity',
					files : [ 'js/plugins/peity/jquery.peity.min.js', 'js/plugins/peity/angular-peity.js' ]
				}, {
					files : [ 'css/plugins/iCheck/custom.css', 'js/plugins/iCheck/icheck.min.js' ]
				} ]);
			}
		}
	})
	
	.state('home.create-invoice', {
		url : "/invoice/create",
		templateUrl : "invoice/create-invoice.html",
		data : {
			pageTitle : 'Invoice'
		},
		resolve : {
			loadPlugin : function($ocLazyLoad) {
				return $ocLazyLoad.load([ 
					{
						name : 'ui.select',
						files : [ 'js/plugins/ui-select/select.min.js', 'css/plugins/ui-select/select.min.css' ]
					},
				]);
			}
		}
	})
	
	.state('home.show-invoice', {
		url : "/invoice/show",
		templateUrl : "invoice/show-invoice.html",
		data : {
			pageTitle : 'Invoice'
		},
		resolve : {
			loadPlugin : function($ocLazyLoad) {
				return $ocLazyLoad.load([ 
					{
						name : 'ui.select',
						files : [ 'js/plugins/ui-select/select.min.js', 'css/plugins/ui-select/select.min.css' ]
					},
				]);
			}
		}
	})
}
angular.module('inventory').config(config).run(function($rootScope, $state) {
	$rootScope.$state = $state;
});
