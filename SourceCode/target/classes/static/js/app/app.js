var app = angular.module('crudApp',['ui.router','ngStorage']);

app.constant('urls', {
    BASE: 'http://localhost:8222/SpringBootTodoApp',
    USER_SERVICE_API : 'http://localhost:8222/SpringBootTodoApp/api/todo/'
});

app.config(['$stateProvider', '$urlRouterProvider',
    function($stateProvider, $urlRouterProvider) {

        $stateProvider
            .state('home', {
                url: '/',
                templateUrl: 'partials/list',
                controller:'TodoController',
                controllerAs:'ctrl',
                resolve: {
                    users: function ($q, TodoService) {
                        console.log('Load all Todos');
                        var deferred = $q.defer();
                        TodoService.loadAllTodos().then(deferred.resolve, deferred.resolve);
                        return deferred.promise;
                    }
                }
            });
        $urlRouterProvider.otherwise('/');
    }]);

