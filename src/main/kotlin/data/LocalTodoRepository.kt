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

    override fun getTodoById(id: Long): Result<Todo, TodoError> {
        return localTodoDataSource.getTodoById(id)
    }

    override fun completeTodo(id: Long) {
        localTodoDataSource.completeTodo(id)
    }

    override fun removeTodo(id: Long): Result<Todo, TodoError> {
        return localTodoDataSource.removeTodo(id)
    }
}