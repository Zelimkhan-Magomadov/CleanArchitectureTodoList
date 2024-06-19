package data

import domain.Result
import domain.TodoError
import domain.model.Todo

interface TodoDataSource {
    fun addTodo(todo: Todo): Todo
    fun getAllTodos(): List<Todo>
    fun completeTodo(id: Long)
    fun removeTodo(id: Long): Result<Unit, TodoError>
}