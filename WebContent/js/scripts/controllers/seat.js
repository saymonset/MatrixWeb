'use strict';

angular.module('ticketApp').controller(
    'SeatCtrl',
    function ($scope, SeatService, AccountService,$http) {
    	
    	
    	
        $scope.seats = SeatService.query();
        $scope.account = AccountService.query();

        $scope.alerts = [];

        
        $scope.upload = function() {
			var fd = new FormData();
			fd.append("file", $scope.file);
			$http({
				withCredentials : true,
				method : 'POST',
				url : './readFile',
				data : fd,
				headers : {
					'Content-Type' : undefined
				},
				transformRequest : angular.identity
			}).success(function(data, status, headers, config) {
                $scope.resp = data.name;
            }).error(function(data, status, headers, config) {
                    // called asynchronously if an error occurs
                    // or server returns response with an error status.
            });;
		}
        
        
        
        
        $scope.bookTicket = function (seat) {
            seat.$book({}, function success() {
                $scope.account.$query();
            }, function err(httpResponse) {

                $scope.alerts.push({

                    type: 'danger',
                    msg: 'Error booking ticket for seat '
                        + httpResponse.config.data.id + " "
                        + ((typeof httpResponse.data.entity != 'undefined' ) ? httpResponse.data.entity : "")
                });
            });
        };

        $scope.closeAlert = function (index) {
            $scope.alerts.splice(index, 1);
        };

        $scope.clearWarnings = function () {
            $scope.alerts.length = 0;
        };

        $scope.resetAccount = function () {
            $scope.account.$reset();
        };
//        var ws = new WebSocket("ws://localhost:8080/matrixWeb/tickets");
//        ws.onmessage = function (message) {
//            var receivedData = message.data;
//            var bookedSeat = JSON.parse(receivedData);
//
//            $scope.$apply(function () {
//                for (var i = 0; i < $scope.seats.length; i++) {
//                    if ($scope.seats[i].id === bookedSeat.id) {
//                        $scope.seats[i].booked = bookedSeat.booked;
//                        break;
//                    }
//                }
//            });
//        };
//        ws.onopen = function (event) {
//            $scope.$apply(function () {
//                $scope.alerts.push({
//                    type: 'info',
//                    msg: 'Push connection from server is working'
//                });
//            });
//        };
//        ws.onclose = function (event) {
//            $scope.$apply(function () {
//                $scope.alerts.push({
//                    type: 'warning',
//                    msg: 'Error on push connection from server '
//                });
//            });
//        };
    }).directive('fileModel', [ '$parse', function($parse) {
		return {
			restrict : 'A',
			link : function(scope, element, attrs) {
				var model = $parse(attrs.fileModel);
				var modelSetter = model.assign;

				element.bind('change', function() {
					scope.$apply(function() {
						modelSetter(scope, element[0].files[0]);
					});
				});
			}
		};
	} ]);