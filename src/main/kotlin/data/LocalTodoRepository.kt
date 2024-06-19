package data

import domain.Result
import domain.model.Todo
import domain.TodoError
import domain.repository.TodoRepository

class LocalTodoRepository(
    private val localTodoDataSource: TodoDataSource
) : TodoRepository {
    override fun addTodo(todo: Todo): Todo {
        return localTodoDataSource.addTodo(todo)
    }

    override fun getAllTodos(): List<Todo> {
        return localTodoDataSource.getAllTodos()
    }

    override fun completeTodo(id: Long) {
        localTodoDataSource.completeTodo(id)
    }

    override fun removeTodo(id: Long): Result<Unit, TodoError> {
        return localTodoDataSource.removeTodo(id)
    }
}