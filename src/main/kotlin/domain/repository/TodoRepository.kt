package domain.repository

import domain.Result
import domain.model.Todo
import domain.TodoError

interface TodoRepository {
    fun addTodo(todo: Todo): Todo
    fun getAllTodos(): List<Todo>
    fun getTodoById(id: Long): Result<Todo, TodoError>
    fun completeTodo(id: Long)
    fun removeTodo(id: Long): Result<Todo, TodoError>
}
