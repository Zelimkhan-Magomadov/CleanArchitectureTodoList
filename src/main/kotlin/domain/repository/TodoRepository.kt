package domain.repository

import domain.Result
import domain.model.Todo
import domain.TodoError

interface TodoRepository {
    fun addTodo(todo: Todo): Todo
    fun getAllTodos(): List<Todo>
    fun completeTodo(id: Long)
    fun removeTodo(id: Long): Result<Unit, TodoError>
}
