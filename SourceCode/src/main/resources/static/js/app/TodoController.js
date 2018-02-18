'use strict';

angular.module('crudApp').controller('TodoController',
    ['TodoService', '$scope',  function( TodoService, $scope) {

        var self = this;
        self.todo = {};
        self.todos=[];

        self.submit = submit;
        self.getAllTodos = getAllTodos;
        self.createTodo = createTodo;
        self.updateTodo = updateTodo;
        self.removeTodo = removeTodo;
        self.editTodo = editTodo;
        self.reset = reset;

        self.successMessage = '';
        self.errorMessage = '';
        self.done = false;

        // self.onlyIntegers = /^\d+$/;
        // self.onlyNumbers = /^\d+([,.]\d+)?$/;

        function submit() {
            console.log('Submitting');
            if (self.todo.id === undefined || self.todo.id === null) {
                console.log('Saving New Task', self.todo);
                createTodo(self.todo);
            } else {
                updateTodo(self.todo, self.todo.id);
                console.log('Task updated with id ', self.todo.id);
            }
        }

        function createTodo(todo) {
            console.log('About to create Task');
            TodoService.createTodo(todo)
                .then(
                    function (response) {
                        console.log('Task created successfully');
                        self.successMessage = 'Task created successfully';
                        self.errorMessage='';
                        self.done = true;
                        self.todo={};
                        $scope.myForm.$setPristine();
                    },
                    function (errResponse) {
                        console.error('Error while creating Task');
                        self.errorMessage = 'Error while creating Task: ' + errResponse.data.errorMessage;
                        self.successMessage='';
                    }
                );
        }


        function updateTodo(todo, id){
            console.log('About to update Task');
            TodoService.updateTodo(todo, id)
                .then(
                    function (response){
                        console.log('Task updated successfully');
                        self.successMessage='Task updated successfully';
                        self.errorMessage='';
                        self.done = true;
                        self.todo={};
                        $scope.myForm.$setPristine();
                    },
                    function(errResponse){
                        console.error('Error while updating Task');
                        self.errorMessage='Error while updating Task '+errResponse.data;
                        self.successMessage='';
                    }
                );
        }


        function removeTodo(id){
            console.log('About to remove Todo with id '+id);
            TodoService.removeTodo(id)
                .then(
                    function(){
                    	self.successMessage='Task removed successfully';
                        console.log('Task '+id + ' removed successfully');
                        self.todo={};
                    },
                    function(errResponse){
                        console.error('Error while removing Task '+id +', Error :'+errResponse.data);
                    }
                );
        }


        function getAllTodos(){
            return TodoService.getAllTodos();
        }

        function editTodo(id) {
            self.successMessage='';
            self.errorMessage='';
            TodoService.getTodo(id).then(
                function (todo) {
                    self.todo = todo;
                },
                function (errResponse) {
                    console.error('Error while removing Task ' + id + ', Error :' + errResponse.data);
                }
            );
        }
        function reset(){
            self.successMessage='';
            self.errorMessage='';
            self.todo={};
            $scope.myForm.$setPristine(); //reset Form
        }
    }


    ]);