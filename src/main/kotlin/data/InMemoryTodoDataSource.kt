package data

import domain.Result
import domain.model.Todo
import domain.TodoError

class InMemoryTodoDataSource : TodoDataSource {
    private val todos = mutableListOf<Todo>()
    private var nextId = 1L

    override fun addTodo(todo: Todo): Todo {
        val newItem = todo.copy(id = nextId++)
        todos.add(newItem)
        return newItem
    }

    override fun getAllTodos(): List<Todo> {
        return todos.toList()
    }

    override fun getTodoById(id: Long): Result<Todo, TodoError> {
        val todoIndex = todos.indexOfFirst { it.id == id }
        return if (todoIndex == -1) {
            Result.Error(TodoError.NON_EXISTENT_ID)
        } else {
            Result.Success(todos[todoIndex])
        }
    }

    override fun completeTodo(id: Long) {
        val index = todos.indexOfFirst { it.id == id }
        if (index != -1) {
            val item = todos[index]
            todos[index] = item.copy(isCompleted = true)
        }
    }

    override fun removeTodo(id: Long): Result<Todo, TodoError> {
        val todoToRemove = todos.find { it.id == id }
        val isRemoved = todos.remove(todoToRemove)
        return if (isRemoved) {
            Result.Success(todoToRemove!!)
        } else {
            Result.Error(TodoError.NON_EXISTENT_ID)
        }
    }
}